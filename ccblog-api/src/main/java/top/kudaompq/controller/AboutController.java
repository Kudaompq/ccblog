package top.kudaompq.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.Result;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.service.AboutService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/about")
public class AboutController {

    @Autowired
    AboutService aboutService;

    @VisitLogger(VisitBehavior.ABOUT)
    @GetMapping("")
    public Result about(){
        return Result.success(aboutService.getAbout());
    }
}
