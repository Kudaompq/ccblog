package top.kudaompq.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.Result;
import top.kudaompq.model.vo.AboutVO;
import top.kudaompq.service.AboutService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/8 15:35
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/about")
public class AboutAdminController {

    @Autowired
    AboutService aboutService;

    @GetMapping("")
    public Result getResult(){
        return Result.success(aboutService.getAboutToAdmin());
    }

    @OperationLogger("修改关于我页面")
    @PutMapping("")
    public Result update(@RequestBody AboutVO about){
        aboutService.updateAbout(about);
        return Result.success("修改成功");
    }
}
