package top.kudaompq.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.service.OperationLogService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/12 15:12
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/operationLog")
public class OperationLogAdminController {

    @Autowired
    OperationLogService operationLogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "") String[] date,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize){

        return Result.success(PageResult.create(operationLogService.getOperationLogListByDate(date,pageNum,pageSize)));
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long id){
        operationLogService.deleteOperationLog(id);
        return Result.success("操作成功");
    }
}
