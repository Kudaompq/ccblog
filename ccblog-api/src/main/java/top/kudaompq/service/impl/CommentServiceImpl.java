package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.kudaompq.entity.Comment;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.CommentMapper;
import top.kudaompq.model.vo.PageComment;
import top.kudaompq.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public int countByPageAndIsPublished(Integer page, Long blogId, Boolean isPublished) {
        return commentMapper.countByPageAndIsPublished(page,blogId,isPublished);
    }


    @Override
    public IPage<PageComment> getPageCommentListByPage(Integer page,Long blogId,Integer pageNum,Integer pageSize) {
        // 1. 首先获取所有顶级评论
        Page<PageComment> commentPage = new Page<>(pageNum, pageSize);
        Page<PageComment> resultPage = commentMapper.getParentCommentListByPage(commentPage, page, blogId);
        List<PageComment> records = resultPage.getRecords();
        // 获得每个顶级评论的所有子评论
        ArrayList<PageComment> pageComments = new ArrayList<>();
        for (PageComment record : records) {
            List<PageComment> comments = getReplyCommentListByParentCommentId(page, blogId, record.getId());
            record.setReplyComments(comments);
        }
        for (PageComment record : records) {
            List<PageComment> tmpComments = new ArrayList<>();
            getReplyComments(tmpComments,record.getReplyComments());
            tmpComments.sort(new Comparator<PageComment>() {
                @Override
                public int compare(PageComment o1, PageComment o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
            record.setReplyComments(tmpComments);
            pageComments.add(record);
        }
        resultPage.setRecords(pageComments);
        return resultPage;
    }

    @Override
    public IPage<Comment> getCommentListByPage(Integer page, Long blogId, Integer pageNum, Integer pageSize) {
        Page<Comment> commentPage = new Page<>(pageNum, pageSize);
        Page<Comment> resultPage = commentMapper.getParentCommentListByPageInAdmin(commentPage, page, blogId);
        List<Comment> records = resultPage.getRecords();
        for (Comment c : records) {
            List<Comment> replyComments = getCommentListByParentCommentId(page, blogId, c.getId());
            c.setReplyComments(replyComments);
        }
        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCommentById(Long id) {
        List<Comment> comments = getAllReplyComments(id);
        for (Comment comment : comments) {
            deleteComment(comment);
        }
        if (commentMapper.deleteById(id) == 0) throw new PersistenceException("删除评论失败");
    }

    private List<Comment> getCommentListByParentCommentId(Integer page,Long blogId,Long parentCommentId){
        List<Comment> comments = commentMapper.getCommentListByParentCommentId(page, blogId, parentCommentId);
        for (Comment c : comments) {
            List<Comment> replyComments = getCommentListByParentCommentId(page, blogId, c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }

    private List<PageComment> getReplyCommentListByParentCommentId(Integer page,Long blogId,Long parentCommentId){
        List<PageComment> comments = commentMapper.getPageCommentListByPageAndParentCommentId(page,blogId,parentCommentId);
        for (PageComment c : comments) {
            List<PageComment> replyComments = getReplyCommentListByParentCommentId(page, blogId, c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }

    /**
     * 将所有子评论递归取出到一个List中
     */
    private void getReplyComments(List<PageComment> tmpComments, List<PageComment> comments) {
        for (PageComment c : comments) {
            tmpComments.add(c);
            getReplyComments(tmpComments, c.getReplyComments());
        }
    }

    private List<Comment> getAllReplyComments(Long parentCommentId){
        List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("parent_comment_id", parentCommentId));
        for (Comment comment : comments) {
            List<Comment> replyComments = getAllReplyComments(comment.getId());
            comment.setReplyComments(replyComments);
        }
        return comments;
    }

    private void deleteComment(Comment comment){
        List<Comment> comments = comment.getReplyComments();
        for (Comment c : comments) {
            deleteComment(c);
        }
        if (commentMapper.deleteById(comment.getId()) == 0) throw new PersistenceException("删除评论失败");
    }
}
