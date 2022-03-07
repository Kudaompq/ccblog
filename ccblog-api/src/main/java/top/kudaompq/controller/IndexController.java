package top.kudaompq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Category;
import top.kudaompq.entity.Tag;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.model.vo.RandomBlog;
import top.kudaompq.service.BlogService;
import top.kudaompq.service.CategoryService;
import top.kudaompq.service.SiteSettingService;
import top.kudaompq.service.TagService;

import java.util.List;
import java.util.Map;

/**
 * @description:  主页Controller
 * @author: kudaompq
 * @date: 2022/1/30 13:48
 * @version: v1.0
 */

@RestController
public class IndexController {

    @Autowired
    SiteSettingService siteSettingService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    /**
    * @Description: 获得站点信息
    * @Param: []
    * @return: top.kudaompq.common.Result
    * @Author: Kudaompq
    * @Date: 2022/1/30
    */
    @GetMapping("/site")
    public Result site(){
        Map<String, Object> map = siteSettingService.getSiteInfo();
        List<Tag> tagList = tagService.list();
        List<Category> categoryList = categoryService.getCategoryListAndCount();
        List<RandomBlog> randomList = blogService.getRandomBlogList();
        map.put("tagList",tagList);
        map.put("categoryList",categoryList);
        map.put("randomBlogList",randomList);
        return Result.success(map);

    }
}
