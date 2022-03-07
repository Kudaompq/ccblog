package top.kudaompq.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.RedisKey;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Blog;
import top.kudaompq.entity.Category;
import top.kudaompq.entity.Tag;
import top.kudaompq.model.dto.BlogDetail;
import top.kudaompq.model.dto.BlogVisibility;
import top.kudaompq.service.BlogService;
import top.kudaompq.service.CategoryService;
import top.kudaompq.service.RedisService;
import top.kudaompq.service.TagService;

import java.util.HashMap;
import java.util.List;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/31 22:53
 * @version: v1.0
 */
@RestController
@RequestMapping("/admin/blog")
@Api(description = "后台文章管理模块")
@Slf4j
public class BlogAdminController {

    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    RedisService redisService;

    @RequiresAuthentication
    @GetMapping("/list")
    public Result getAllArticles(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String title,
                                 @RequestParam(defaultValue = "") Integer categoryId){
        IPage page = blogService.getAllArticleInfosByPage(pageNum,pageSize,title,categoryId);
        PageResult blogs = PageResult.create(page);
        List<Category> categoryList = categoryService.list();
        HashMap<String, Object> map = new HashMap<>();
        map.put("blogs",blogs);
        map.put("categories",categoryList);
        return Result.success(map);
    }

    @RequiresAuthentication
    @GetMapping("/categoryAndTag")
    public Result categoryAndTag(){
        List<Category> categoryList = categoryService.list();
        List<Tag> tagList = tagService.list();
        HashMap<String, Object> map = new HashMap<>();
        map.put("categoryList",categoryList);
        map.put("tagList",tagList);
        return Result.success(map);
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "保存博客")
    @PostMapping("/save")
    public Result save(@Validated @RequestBody BlogDetail blog){
        boolean save = blogService.saveBlog(blog);
        if (save) {
            log.info("添加了标题为"+blog.getTitle()+"的文章");
            return Result.success("添加文章成功");
        }
        else {
            log.error("添加文章失败");
            return Result.error("添加文章失败");
        }
    }

    @RequiresAuthentication
    @GetMapping("/{id}")
    public Result getBlog(@PathVariable("id") Integer id){
        BlogDetail blog = blogService.getBlogDetailById(id);
        return Result.success(blog);
    }

    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "修改博客置顶状态")
    @PutMapping("/top")
    public Result updateTop(Integer id,Boolean top){
        Blog blog = new Blog();
        blog.setId(id.longValue());
        blog.setTop(top);
        boolean update = blogService.updateById(blog);
        if (update){
            log.info("修改ID为"+blog.getId()+"的文章置顶状态");
            return Result.success("更改置顶状态成功");
        }else {
            log.error("置顶文章操作失败");
            return Result.error("置顶文章操作失败");
        }
    }

    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "修改博客推荐状态")
    @PutMapping("/recommend")
    public Result updateRecommend(Integer id,Boolean recommend){
        Blog blog = new Blog();
        blog.setId(id.longValue());
        blog.setTop(recommend);
        boolean update = blogService.updateById(blog);
        if (update){
            redisService.deleteCacheByKey(RedisKey.ARCHIVE_BLOG_MAP);
            log.info("修改ID为"+blog.getId()+"的推荐状态");
            return Result.success("修改文章推荐状态成功");
        }else {
            log.error("修改文章推荐状态失败");
            return Result.error("修改文章推荐状态失败");
        }
    }

    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "修改博客可见化状态")
    @PutMapping("/{id}/visibility")
    public Result updateVisibility(@PathVariable Integer id, @RequestBody BlogVisibility blogVisibility){
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogVisibility,blog);
        blog.setId(id.longValue());
        boolean update = blogService.updateById(blog);
        if (update){
            log.info("修改id为"+id+"的文章的可见性");
            return Result.success("修改文章可见性成功");
        }else{
            log.warn("修改id为"+id+"的文章的可见性失败");
            return Result.error("修改文章可见性失败");
        }
    }

    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "删除博客")
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id){
        boolean delete = blogService.deleteBlogById(id);
        if (delete) {
            log.info("删除了id为"+id+"的文章");
            return Result.success("删除成功");
        }else{
            log.error("对di为"+id+"的文章删除失败");
            return Result.error("删除失败");
        }
    }




}
