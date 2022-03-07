package top.kudaompq.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.FinalCode;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.service.MomentService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/moment")
public class MomentController {

    @Autowired
    MomentService momentService;

    @VisitLogger(VisitBehavior.MOMENT)
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum){
        return Result.success(PageResult.create(momentService.getMomentListByPage(pageNum, FinalCode.pageSize)));
    }

    @VisitLogger(VisitBehavior.LIKE_MOMENT)
    @PostMapping("/like/{id}")
    public Result like(@PathVariable Long id){
        momentService.addLikeById(id);
        return Result.success("点赞成功");
    }

}
