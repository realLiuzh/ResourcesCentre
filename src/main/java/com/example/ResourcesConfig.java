package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源映射
 *
 * @author 志昊的刘
 * @date 2021/12/27
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**","/image/**")

                .addResourceLocations("file:/html/","file:/picture/");
    }
}
