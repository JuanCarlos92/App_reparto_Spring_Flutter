package com.reparto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Esta configuración permite que Spring lance excepción para 404 en lugar de usar BasicErrorController
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }
}
