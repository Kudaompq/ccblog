package top.kudaompq.controller.admin;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.Result;
import top.kudaompq.entity.SiteSetting;
import top.kudaompq.service.SiteSettingService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/7 15:41
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/siteSetting")
@Api(description = "后台网站设置模块")
public class SiteSettingAdminController {

    @Autowired
    SiteSettingService siteSettingService;


    /**
    * @Description: 获得所有站点配置信息
    * @Param: []
    * @return: top.kudaompq.common.Result
    * @Author: Kudaompq
    * @Date: 2022/2/7
    */
    @RequiresAuthentication
    @GetMapping("/list")
    public Result list(){
        Map<String, List<SiteSetting>> list = siteSettingService.getList();
        return Result.success(list);
    }

    @RequiresAuthentication
    @OperationLogger("更新站点信息")
    @PostMapping("/update")
    public Result update(@RequestBody Map<String, Object> map){
        List<LinkedHashMap> settings = (List<LinkedHashMap>) map.get("settings");
        List<Integer> deleteIds = (List<Integer>) map.get("deleteIds");
        siteSettingService.updateSiteSettings(settings,deleteIds);
        return Result.success("更新站点信息成果");
    }



}
