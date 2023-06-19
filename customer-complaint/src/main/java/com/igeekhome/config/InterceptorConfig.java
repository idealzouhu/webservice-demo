package com.igeekhome.config;

import com.igeekhome.util.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration//配置文件
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Filter())
                .addPathPatterns("/**")//拦截哪些请求
                .excludePathPatterns("/","/assets/**","/login","/customerService/login","/register");//哪些请求不拦截
    }
}
