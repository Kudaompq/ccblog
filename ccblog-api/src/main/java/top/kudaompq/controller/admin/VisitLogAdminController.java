package top.kudaompq.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.service.VisitLogService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/12 22:43
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/visitLog")
public class VisitLogAdminController {

    @Autowired
    VisitLogService visitLogService;


    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "") String[] date,
                       @RequestParam(defaultValue = "") String uuid,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){

        return Result.success(PageResult.create(visitLogService.getVisitLogListByDate(date, StringUtils.trim(uuid),pageNum,pageSize)));
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long id){
        visitLogService.deleteVisitLog(id);
        return Result.success("操作成功");
    }
}
