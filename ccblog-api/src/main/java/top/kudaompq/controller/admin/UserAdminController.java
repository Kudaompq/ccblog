package top.kudaompq.controller.admin;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.common.Result;
import top.kudaompq.entity.LoginLog;
import top.kudaompq.entity.User;
import top.kudaompq.model.dto.LoginDto;
import top.kudaompq.model.vo.UserInfo;
import top.kudaompq.service.LoginLogService;
import top.kudaompq.service.UserService;
import top.kudaompq.utils.IpAddressUtils;
import top.kudaompq.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/31 11:56
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin")
@Api(description = "后台用户登录接口模块")
@Slf4j
public class UserAdminController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    LoginLogService loginLogService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto,HttpServletRequest request){
        User user = userService.getUserByName(loginDto.getUsername());
        Assert.notNull(user,"用户不存在");
        String hashedPassword = new Md5Hash(loginDto.getPassword(), loginDto.getUsername(), 1024).toHex();
        if (!user.getPassword().equals(hashedPassword)){
            log.error("用户" + loginDto.getUsername()+": 登陆失败");
            LoginLog log = handleLog(request, user, false, "用户名或者密码错误");
            loginLogService.saveLoginLog(log);
            return Result.error("用户名或者密码不正确");
        }

        String jwt = jwtUtils.generateToken(user.getId());
        log.info("用户"+loginDto.getUsername()+": 登录成功");
        UserInfo userInfo = new UserInfo();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("userInfo",userInfo);
        map.put("token",jwt);
        LoginLog log = handleLog(request, user, true, "登录成功");
        loginLogService.saveLoginLog(log);
        BeanUtils.copyProperties(user,userInfo);
        return Result.success("登录成功",map);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        log.info("用户登出");
        return Result.success("登出成功");
    }

    private LoginLog handleLog(HttpServletRequest request,User user,boolean status,String description){
        String username = user.getUsername();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        LoginLog loginLog = new LoginLog(username, ip, status, description, userAgent);
        return loginLog;
    }
}
