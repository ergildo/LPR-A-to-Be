package com.atobe.lpr.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Controller
public class SwaggerConfig {

  @GetMapping(value = "/")
  public String swagger() {
    return "redirect:swagger-ui.html";
  }

  @Bean
  public Docket regularApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.atobe.lpr"))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)
        .groupName("Rest")
        .apiInfo(metaData())
        .securitySchemes(Collections.emptyList());
  }

  private ApiInfo metaData() {
    return new ApiInfo(
        "Licence Plate Review API",
        "Application whose purpose is allow authorized users to review and label poorly recognized photos with the correct license plate number.",
        "1.0",
        "Terms of service",
        new Contact("Licence Plate Review API", "https://github.com/ergildo", "ergildo@gmail.com"),
        "Apache License Version 2.0",
        "https://www.apache.org/licenses/LICENSE-2.0",
        Arrays.asList());
  }
}
