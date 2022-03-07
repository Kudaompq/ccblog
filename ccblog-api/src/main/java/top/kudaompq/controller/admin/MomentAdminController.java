package top.kudaompq.controller.admin;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.OperateType;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Moment;
import top.kudaompq.service.MomentService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/1 16:03
 * @version: v1.0
 */
@RestController
@RequestMapping("/admin/moment")
@Api(description = "后台动态管理模块")
@Slf4j
public class MomentAdminController {

    @Autowired
    MomentService momentService;

    @RequiresAuthentication
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(PageResult.create(momentService.getMomentListByPage(pageNum,pageSize)));
    }

    @RequiresAuthentication
    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @OperationLogger(value = "保存动态")
    @PostMapping("/save")
    public Result save(@RequestBody Moment moment){
        momentService.saveMoment(moment);
        return Result.success("保存动态成功");
    }

    @RequiresAuthentication
    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @OperationLogger(value = "修改动态")
    @PutMapping("/update")
    public Result update(@RequestBody Moment moment){
        momentService.updateMoment(moment);
        return Result.success("修改动态成功");
    }

    @RequiresAuthentication
    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @OperationLogger(value = "删除动态")
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id){
        momentService.deleteMoment(id);
        return Result.success("删除动态成功");

    }

    @RequiresAuthentication
    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @OperationLogger(value = "修改动态发布状态")
    @PutMapping("/publish")
    public Result published(@RequestParam Integer id,
                            @RequestParam Boolean published){
        Moment moment = new Moment();
        moment.setId(id.longValue());
        moment.setPublished(published);
        momentService.updateById(moment);
        return Result.success("修改动态发布状态成功");

    }

    @RequiresAuthentication
    @GetMapping("/{id}")
    public Result moment(@PathVariable(value = "id") Integer id){
        return Result.success(momentService.getById(id));
    }



}
