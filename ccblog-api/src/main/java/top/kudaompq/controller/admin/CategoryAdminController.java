package top.kudaompq.controller.admin;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.OperateType;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Category;
import top.kudaompq.service.CategoryService;

/**
 * @description: 后台分类管理
 * @author: kudaompq
 * @date: 2022/1/31 13:24
 * @version: v1.0
 */
@Api(description = "后台分类管理模块")
@RequestMapping("/admin/category")
@RestController
public class CategoryAdminController {

    @Autowired
    CategoryService categoryService;

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "保存分类")
    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        categoryService.saveCategory(category);
        return Result.success("添加分类成功");
    }

    @RequiresAuthentication
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(PageResult.create(categoryService.getCategoryListByPage(pageNum,pageSize)));
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "删除分类")
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id){
        categoryService.deleteCategory(id);
        return Result.success("删除分类成功");
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @RequiresAuthentication
    @OperationLogger(value = "更新分类")
    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        categoryService.updateCategory(category);
        return Result.success("修改分类成功");

    }



}
