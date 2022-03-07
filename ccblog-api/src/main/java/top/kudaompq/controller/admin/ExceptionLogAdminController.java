package top.kudaompq.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.service.ExceptionLogService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/12 16:39
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/exceptionLog")
public class ExceptionLogAdminController {

    @Autowired
    ExceptionLogService exceptionLogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "") String[] date,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(PageResult.create(exceptionLogService.getLoginLogListByDate(date,pageNum,pageSize)));
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long id){
        exceptionLogService.deleteExceptionLog(id);
        return Result.success("操作成功");
    }
}
