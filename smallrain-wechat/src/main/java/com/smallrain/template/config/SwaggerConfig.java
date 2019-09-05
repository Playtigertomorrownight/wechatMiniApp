package com.smallrain.template.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangying.dz3 swagger2 的配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket createRestApi() {
    List<Parameter> pars = new ArrayList<>();
    boolean swaggerEnable = Boolean.valueOf(System.getenv("DEV_MODE"));
    return new Docket(DocumentationType.SWAGGER_2)
        .enable(swaggerEnable)
        .apiInfo(apiInfo())
        .select()
        .paths(PathSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("com.smallrain"))
        .build()
        .globalOperationParameters(pars);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("模板服务 API ")
        .description("restful 风格API")
        .termsOfServiceUrl("http://localhost:8080/")
        .version("1.0")
        .build();
   }
}
