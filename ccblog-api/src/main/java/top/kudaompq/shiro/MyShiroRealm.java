package top.kudaompq.shiro;

import cn.hutool.core.bean.BeanUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.kudaompq.entity.User;
import top.kudaompq.service.UserService;
import top.kudaompq.utils.JwtUtils;

/**
 * @ClassName: MyShiroRealm
 * @Description: TODO
 * @author: kudaompq
 * @date: 2022/1/4  15:51
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户登录信息
        MyProfile user = (MyProfile) principalCollection.getPrimaryPrincipal();
        //这里根据用户信息再查一遍权限表  由于这里没有权限表  所以直接查询用户表  获取它的role
        User userInfo = userService.getById(user.getId());
        //添加角色和权限
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermission(userInfo.getRole());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwt = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();
        User user = userService.getById(Integer.valueOf(userId));
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        MyProfile profile=new MyProfile();
        BeanUtil.copyProperties(user, profile);

        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}
