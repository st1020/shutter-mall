package com.st1020.shuttermall.config;

import com.st1020.shuttermall.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.st1020.shuttermall.interceptor.JwtInterceptor;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    final private UserRepository userRepository;

    public InterceptorConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor(userRepository))
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/user/resetPwd");
    }
}
