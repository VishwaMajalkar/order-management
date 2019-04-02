package com.vish.example.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class to Configure Swagger REST Documentation
 * Created by Vishwanath on 18/03/2019.
 */
@Configuration
@EnableSwagger2
public class OrderManagementSwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.vish.example.order.controller"))
        .paths(PathSelectors.any())
        .build();
    }
}
