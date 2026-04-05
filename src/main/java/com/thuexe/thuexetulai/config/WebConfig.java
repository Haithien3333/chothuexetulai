package com.thuexe.thuexetulai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)

                // CHỈ CHẶN ADMIN
                .addPathPatterns("/admin/**")

                // KHÔNG CHẶN CÁC TRANG PUBLIC
                .excludePathPatterns(
                        "/login",
                        "/register",
                        "/",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                );
    }
}