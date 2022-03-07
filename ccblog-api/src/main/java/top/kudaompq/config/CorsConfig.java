package top.kudaompq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: CorsConfig
 * @author: kudaompq
 * @date: 2022/1/4 15:12
 * @version: v1.0
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {
   
    /** 
    * @Description: 解决跨域问题 
    * @Param: [registry] 
    * @return: void 
    * @Author: Kudaompq
    * @Date: 2022/1/4 
    */ 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
