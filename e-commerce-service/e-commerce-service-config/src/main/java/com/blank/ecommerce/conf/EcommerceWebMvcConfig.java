package com.blank.ecommerce.conf;

import com.alibaba.cloud.seata.web.SeataHandlerInterceptor;
import com.blank.ecommerce.filter.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class EcommerceWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginUserInfoInterceptor())
                .addPathPatterns("/**")
                .order(0);

//        registry.addInterceptor(new SeataHandlerInterceptor())
//                .addPathPatterns("/**");
    }

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath*:/static/");
//        registry.addResourceHandler("/swagger-ui.html")
//                .addResourceLocations("classpath*:/META-INF/resources");
//        registry.addResourceHandler("/doc.html")
//                .addResourceLocations("classpath*:/META-INF/resources");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath*:/META-INF/resources/webjars/");
//
//        super.addResourceHandlers(registry);
//    }
}
