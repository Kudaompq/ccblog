package top.kudaompq.controller;


import io.swagger.models.auth.In;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    BlogService blogService;

    @VisitLogger(VisitBehavior.CATEGORY)
    @GetMapping("/blogList")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam Integer id){
        return Result.success(PageResult.create(blogService.getBlogInfoListPageByCategoryId(pageNum,id)));
    }
}
