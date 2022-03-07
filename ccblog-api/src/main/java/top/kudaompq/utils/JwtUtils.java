package top.kudaompq.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: jwt工具类
 * @author: kudaompq
 * @date: 2022/1/2 14:32
 * @version: v1.0
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "datealive.jwt")
public class JwtUtils {

    private  String secret;
    private  long expire;
    private  String header;

    /**
     * 生成jwt token
     */
    public  String generateToken(long userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 校验jwt是否合法
     *
     * @param token
     * @return
     */
    public  Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public  boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}