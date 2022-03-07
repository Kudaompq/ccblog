package top.kudaompq.controller;


import cn.hutool.core.util.HashUtil;
import org.apache.commons.codec.digest.MurmurHash3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.web.bind.annotation.*;

import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Blog;
import top.kudaompq.entity.Comment;
import top.kudaompq.entity.User;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.model.vo.AboutVO;
import top.kudaompq.model.vo.FriendInfo;
import top.kudaompq.service.*;
import top.kudaompq.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @Autowired
    AboutService aboutService;

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    MailUtils mailUtils;

    private String blogName;
    private String cmsUrl;
    private String websiteUrl;

    @Value("${custom.blog.name}")
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    @Value("${custom.url.cms}")
    public void setCmsUrl(String cmsUrl) {
        this.cmsUrl = cmsUrl;
    }

    @Value("${custom.url.website}")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }


    @GetMapping("/list")
    public Result list(@RequestParam Integer page,
                       @RequestParam(defaultValue = "") Long blogId,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestHeader(value = "Authorization",defaultValue = "") String jwt){
        int judgeResult = judgeCommentEnabled(page, blogId);
        if (judgeResult == 2){
            return Result.error("该博客不存在");
        }else if (judgeResult == 1){
            return Result.error("评论已关闭");
        }
        //查询该页面所有评论的数量
        Integer allComment = commentService.countByPageAndIsPublished(page, blogId, null);
        Integer openComment = commentService.countByPageAndIsPublished(page,blogId,true);

        /**
         * 获得评论列表的思路
         * 1. 先用分页查找到pageSize个父评论
         * 2. 再根据这些父评论找到所有子评论
         */
        PageResult pageResult = PageResult.create(commentService.getPageCommentListByPage(page, blogId, pageNum, pageSize));
        HashMap<String, Object> map = new HashMap<>();
        map.put("allComment",allComment);
        map.put("closeComment", allComment - openComment);
        map.put("comments", pageResult);
        return Result.success(map);
    }

    /**
    * @Description: 0：普通文章 1：关于我 2：友链
    * @Param: [page, blogId]
    * @return: int 0: 可查询 1：评论关闭 2：博客不存在
    * @Author: Kudaompq
    * @Date: 2022/2/3
    */
    private int judgeCommentEnabled(Integer page,Long blogId){
        if (page == 0){
            Blog blog = blogService.getById(blogId);
            if (blog == null) return 2; // 博客不存在
            Boolean commentEnabled = blog.getCommentEnabled();
            Boolean published = blog.getPublished();
            if (!published) return 2; // 博客未公开
            if (!commentEnabled) return 1; // 博客未开放评论功能
        }else if (page == 1){
            AboutVO about = aboutService.getAbout();
            if (!about.getCommentEnabled()) return 1; // 关于我页面评论已关闭
        }else{
            FriendInfo friendInfo = friendService.getFriendInfo();
            if (!friendInfo.getCommentEnabled()) return 1; // 友链评论已关闭
        }
        return 0;
    }

    @PostMapping("/submit")
    public Result submitComment(@RequestBody Comment comment,
                                HttpServletRequest request,
                                @RequestHeader(value = "Authorization",defaultValue = "") String jwt){
        //评论内容合法性校验
        if (StringUtils.isEmpty(comment.getContent()) || comment.getContent().length() > 250 ||
                comment.getPage() == null || comment.getParentCommentId() == null) {
            return Result.error("参数有误");
        }
        boolean isVisitorComment = false;
        Comment parentComment = null;

        if (comment.getParentCommentId() != -1){
            parentComment = commentService.getById(comment.getParentCommentId());
            Integer page = parentComment.getPage();
            Long blogId = page == 0 ? parentComment.getBlogId() : null;
            comment.setPage(page);
            comment.setBlogId(blogId);
        }else{
            if (comment.getPage() != 0){
                comment.setBlogId(null);
            }
        }
        int judgeResult = judgeCommentEnabled(comment.getPage(), comment.getBlogId());
        if (judgeResult == 2){
            return Result.error("该博客不存在");
        }else if (judgeResult == 1){
            return Result.error("评论已关闭");
        }
        if (jwt != null && !jwt.equals("")){
            String userId = jwtUtils.getClaimByToken(jwt).getSubject();
            User user = userService.getById(Integer.valueOf(userId));
            if (user == null){
                return Result.error("博主身份Token已失效，请重新登录！");
            }
            if (!user.getRole().equals("admin")){
                return Result.error("该账号不属于admin账号无法完成评论！");
            }
            setAdminComment(comment,user,request);
        }else{
            //对访客的评论昵称、邮箱合法性校验
            if (StringUtils.isEmpty(comment.getNickname(), comment.getEmail()) || comment.getNickname().length() > 15) {
                return Result.error("参数有误");
            }
            setVisitorComment(comment,request);
            isVisitorComment = true;
        }
        boolean save = commentService.save(comment);
        if (!save) return Result.error("评论失败");
        // 邮件发送功能待实现
        judgeSendMail(comment, isVisitorComment, parentComment);
        return Result.success("评论成功");

    }

    private void setAdminComment(Comment comment,User user,HttpServletRequest request){
        comment.setAdminComment(true);
        comment.setCreateTime(LocalDateTime.now());
        comment.setPublished(true);
        comment.setAvatar(user.getAvatar());
        comment.setWebsite("/");
        comment.setNickname(user.getNickname());
        comment.setEmail(user.getEmail());
        comment.setIp(IpAddressUtils.getIpAddress(request));
        comment.setNotice(false);
    }

    private void setVisitorComment(Comment comment,HttpServletRequest request){
        String nickname = comment.getNickname();
        try{
            if (QQInfoUtils.isQQNumber(nickname)){
                comment.setQq(nickname);
                comment.setNickname(QQInfoUtils.getQQNickname(nickname));
                comment.setAvatar(QQInfoUtils.getQqAvatarURLByGithubUpload(nickname));
            }else{
                comment.setNickname(comment.getNickname().trim());
                comment.setAvatar(getCommentRandomAvatar(comment.getNickname()));
            }
        }catch (Exception e){
            e.printStackTrace();
            comment.setNickname(comment.getNickname().trim());
            comment.setAvatar(getCommentRandomAvatar(comment.getNickname()));
        }

        String website = comment.getWebsite().trim();
        if (!"".equals(website) && !website.startsWith("http://") && !website.startsWith("https://")) {
            website = "http://" + website;
        }
        comment.setAdminComment(false);
        comment.setCreateTime(LocalDateTime.now());
        comment.setPublished(true);//默认不需要审核
        comment.setWebsite(website);
        comment.setEmail(comment.getEmail().trim());
        comment.setIp(IpAddressUtils.getIpAddress(request));
    }

    private String getCommentRandomAvatar(String nickName){
        long hash = HashUtils.getMurmurHash32(nickName);
        long num = hash % 10 + 1;
        return "/img/comment-avatar/" + num + ".jpg";
    }

    /**
    * @Description: 判断是否发送邮件
     * 1. 我以父评论评价，不用提醒
     * 2. 我回复我自己： 不用提醒
     * 3. 我回复访客，提醒访客
     * 4. 访客以父评论提交，提醒我
     * 5. 访客回复我评论，提醒我
     * 6. 访客回复访客的评论(即使是他自己先前的评论)：提醒我和他回复的评论
    * @Param: [comment, isVisitorComment, parentComment]
    * @return: void
    * @Author: Kudaompq
    * @Date: 2022/2/10
    */
    private void judgeSendMail(Comment comment,boolean isVisitorComment,Comment parentComment){
        if (parentComment != null && !parentComment.getAdminComment() && parentComment.getNotice()){
            // 回复的评论，并且对方接收提醒，邮件提醒对方
            sendMailToParentComment(parentComment,comment);
        }
        if (isVisitorComment){
            sendMailToMe(comment);
        }
    }

    private void sendMailToParentComment(Comment parentComment,Comment comment){
        String path = "";
        String title = "";
        if (comment.getPage() == 0){
            //普通博客
            title = blogService.getTitleById(parentComment.getBlogId());
            path = "/blog/" + comment.getBlogId();
        } else if (comment.getPage() == 1) {
            //关于我页面
            title = "关于我";
            path = "/about";
        } else if (comment.getPage() == 2) {
            //友链页面
            title = "友人帐";
            path = "/friends";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("parentNickname", parentComment.getNickname());
        map.put("nickname", comment.getNickname());
        map.put("title", title);
        map.put("time", Date.from(comment.getCreateTime().atZone(ZoneId.systemDefault()).toInstant()));
        map.put("parentContent", parentComment.getContent());
        map.put("content", comment.getContent());
        map.put("url", websiteUrl + path);
        String toAccount = parentComment.getEmail();
        String subject = "您在" + blogName + "的评论有了新回复";
        mailUtils.sendHtmlTemplateMail(map,toAccount,subject,"guest.html");
    }

    private void sendMailToMe(Comment comment){
        String path = "";
        String title = "";
        if (comment.getPage() == 0){
            title = blogService.getTitleById(comment.getBlogId());
            path = "/blog/" + comment.getBlogId();
        }else if(comment.getPage() == 1){
            //关于我页面
            title = "关于我";
            path = "/about";
        }else if (comment.getPage() == 2){
            //友链页面
            title = "友人帐";
            path = "/friends";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("time", Date.from(comment.getCreateTime().atZone(ZoneId.systemDefault()).toInstant()));
        map.put("nickname", comment.getNickname());
        map.put("content", comment.getContent());
        map.put("ip", comment.getIp());
        map.put("email", comment.getEmail());
        map.put("status", comment.getPublished() ? "公开" : "待审核");
        map.put("url", websiteUrl + path);
        map.put("manageUrl", cmsUrl + "/comments");
        String toAccount = mailProperties.getUsername();
        String subject = blogName + " 收到新评论";
        mailUtils.sendHtmlTemplateMail(map, toAccount, subject, "owner.html");
    }

}
