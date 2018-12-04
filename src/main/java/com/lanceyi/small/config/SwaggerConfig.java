package com.lanceyi.small.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Lance YI
 * @date 2018/12/4
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class SwaggerConfig {

    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lanceyi.small.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        StringBuilder desc = new StringBuilder();
        desc.append("</br>");
        desc.append("<strong>主机说明：</strong></br>");
        desc.append("<ul>");
        desc.append("<li>测试地址：http://xxxx</li></br>");
        desc.append("<li>正式地址：http://xxxxx</li></br>");
        desc.append("</ul>");
        desc.append("</br>");
        desc.append("<strong>其他文档：</strong></br></br>");
        desc.append("</br>");
        desc.append("</br>");
        desc.append("<strong>全局返回说明：</strong></br></br>");
        desc.append("0：操作成功；小于0：系统错误； 大于0：业务错误 </br>");

        return new ApiInfoBuilder()
                .title("（新项目）小程序")
                .description(desc.toString())
                .version("1.0")
                .build();

    }


}
