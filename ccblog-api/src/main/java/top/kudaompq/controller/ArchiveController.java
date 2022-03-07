package top.kudaompq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.Result;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.service.BlogService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/3 13:26
 * @version: v1.0
 */

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    BlogService blogService;

    @VisitLogger(VisitBehavior.ARCHIVE)
    @GetMapping("")
    public Result archive(){
        return Result.success(blogService.getArchiveBlog());
    }

}
