package com.bettorlogic.victoryexch.middlelayer.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private static final String BASE_PACKAGE_NAME = "com.bettorlogic.victoryexch.middlelayer";
    private static final String BASE_URL_PATH_REGEX = "/bm-exchange-middle-layer.*";
    private static final String RESOURCE_HANDLERS_PATH_PATTERN_1 = "swagger-ui.html";
    private static final String RESOURCE_HANDLERS_PATH_PATTERN_2 = "/webjars/**";
    private static final String RESOURCE_LOCATION_1 = "classpath:/META-INF/resources/";
    private static final String RESOURCE_LOCATION_2 = "classpath:/META-INF/resources/webjars/";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_NAME))
                .paths(PathSelectors.regex(BASE_URL_PATH_REGEX))
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCE_HANDLERS_PATH_PATTERN_1)
                .addResourceLocations(RESOURCE_LOCATION_1);

        registry.addResourceHandler(RESOURCE_HANDLERS_PATH_PATTERN_2)
                .addResourceLocations(RESOURCE_LOCATION_2);
    }
}