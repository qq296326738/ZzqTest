package com.zzq.zzq.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    /**
     * 配置过滤器
     *
     * @return
     */
//    @Bean
//    public FilterRegistrationBean testFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new FilterTest());
//        registration.addUrlPatterns("/*");
//        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("MyFilter");
//        registration.setOrder(1);
//        return registration;
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //统一静态资源目录
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("classpath:/static/resources/");
//        registry.addResourceHandler("/bak/**")
//                .addResourceLocations("classpath:/static/bak/");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/**");
    }

    /**
     * 拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

}
