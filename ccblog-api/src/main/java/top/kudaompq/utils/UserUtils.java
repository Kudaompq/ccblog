package top.kudaompq.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.kudaompq.shiro.MyProfile;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 获取当前登录的用户
 * @author: kudaompq
 * @date: 2022/1/4 20:54
 * @version: v1.0
 */

@Component
@Slf4j
public class UserUtils {

    /**
    * @Description: 获取shiro中登录的用户
    * @Param: []
    * @return: top.kudaompq.shiro.MyProfile
    * @Author: Kudaompq
    * @Date: 2022/1/4
    */
    public static MyProfile getSubjectUser(){
        return (MyProfile) SecurityUtils.getSubject().getPrincipal();
    }

    /**
    * @Description: 从shiro中移除登录的用户
    * @Param: []
    * @return: void
    * @Author: Kudaompq
    * @Date: 2022/1/4
    */
    public static void removeSubjectUser() {
        SecurityUtils.getSubject().logout();
    }

    /**
    * @Description: 获取当前的request
    * @Param: []
    * @return: javax.servlet.http.HttpServletRequest
    * @Author: Kudaompq
    * @Date: 2022/1/4
    */
    public static HttpServletRequest getRequest() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
