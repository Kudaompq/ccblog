package top.kudaompq.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.entity.LoginLog;
import top.kudaompq.service.LoginLogService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/12 15:55
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/loginLog")
public class LoginLogAdminController {

    @Autowired
    LoginLogService loginLogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "") String[] date,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(PageResult.create(loginLogService.getLoginLogListByDate(date,pageNum,pageSize)));
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long id){
        loginLogService.deleteLoginLog(id);
        return Result.success("操作成功");
    }
}
