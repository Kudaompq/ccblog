package top.kudaompq.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description: 自定义Token
 * @author: kudaompq
 * @date: 2022/1/2 14:24
 * @version: v1.0
 */

public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
