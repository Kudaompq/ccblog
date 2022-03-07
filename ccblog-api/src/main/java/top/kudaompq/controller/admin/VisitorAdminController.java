package top.kudaompq.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.service.VisitorService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/12 23:25
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/visitor")
public class VisitorAdminController {

    @Autowired
    VisitorService visitorService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "") String[] date,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(PageResult.create(visitorService.getVisitorListByDate(date,pageNum,pageSize)));
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long id,@RequestParam String uuid){
        visitorService.deleteVisitor(id,uuid);
        return Result.success("操作成功");
    }
}
