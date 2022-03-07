package top.kudaompq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: Swagger配置文件
 * @author: kudaompq
 * @date: 2022/1/4 16:27
 * @version: v1.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enable;

    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder accessTokenBuilder = new ParameterBuilder();
        accessTokenBuilder.name("authorization")
                .description("自测时传输AccessToken入口")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(false);
        pars.add(accessTokenBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.kudaompq.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .enable(enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("CC博客系统接口文档") //设置文档的标题
                .description("博客系统接口文档") // 设置文档的描述
                /**设置文档的联系方式*/
                .contact(new Contact("Kudaompq", "http://kudaompq.top/","1187873694@qq.com"))
                /**设置文档的License信息->1.3 License information*/
                .version(VERSION) // 设置文档的版本信息-> 1.0.0 Version information
                .build();
    }
}
