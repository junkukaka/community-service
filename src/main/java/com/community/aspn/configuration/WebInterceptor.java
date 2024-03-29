package com.community.aspn.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 跨域请求支持/token拦截
 * tip:只能写在一个配置类里
 */
@Configuration
public class WebInterceptor implements WebMvcConfigurer {

    private TokenInterceptor tokenInterceptor;

    //构造方法
    public WebInterceptor(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
        excludePath.add("/member/members/login");  //登录
        excludePath.add("/member/members/token");
        excludePath.add("/member/members");
        excludePath.add("/communityMenu/dashboard/*");
        excludePath.add("/wikiMenu/dashboard/*");
        excludePath.add("/member/members/department");
        excludePath.add("/member/memberApplication");
        excludePath.add("/member/memberVerify");
        excludePath.add("/wiki/wikiMain");
        excludePath.add("/community/communitys/mainPage");
        excludePath.add("/");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}