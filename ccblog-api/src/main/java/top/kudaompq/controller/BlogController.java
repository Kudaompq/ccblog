package top.kudaompq.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.model.dto.BlogDetail;
import top.kudaompq.model.vo.SearchBlog;
import top.kudaompq.service.BlogService;
import top.kudaompq.utils.StringUtils;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;

    @VisitLogger(VisitBehavior.INDEX)
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum){
        return Result.success(PageResult.create(blogService.getBlogInfoListByPage(pageNum)));
    }


    @VisitLogger(VisitBehavior.BLOG)
    @GetMapping("")
    public Result blog(@RequestParam Integer id){
        BlogDetail blog = blogService.getBlogById(id);
        blogService.updateViewsToRedis(id.longValue());
        return Result.success(blog);
    }

    @VisitLogger(VisitBehavior.SEARCH)
    @GetMapping("/search")
    public Result search(@RequestParam String query){
        if (StringUtils.isEmpty(query) || StringUtils.hasSpecialChar(query) || query.trim().length() > 20){
            return Result.error("参数错误");
        }
        List<SearchBlog> result = blogService.getSearchBlogListByQuery(query);
        return Result.success(result);
    }
}
