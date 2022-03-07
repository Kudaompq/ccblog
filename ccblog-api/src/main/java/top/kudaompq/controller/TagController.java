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
import top.kudaompq.service.BlogService;
import top.kudaompq.service.TagService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    BlogService blogService;

    @VisitLogger(VisitBehavior.TAG)
    @GetMapping("/blogList")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam Integer id){
        return Result.success(PageResult.create(blogService.getBlogInfoListPageByTagId(pageNum,id)));
    }

}
