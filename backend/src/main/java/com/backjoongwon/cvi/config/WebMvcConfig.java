package com.backjoongwon.cvi.config;

import com.backjoongwon.cvi.user.auth.AuthenticationPrincipalArgumentResolver;
import com.backjoongwon.cvi.user.auth.SigninInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final SigninInterceptor signinInterceptor;
    private final AuthenticationPrincipalArgumentResolver resolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signinInterceptor)
                .addPathPatterns("/api/v1/users/*")
                .addPathPatterns("/api/v1/posts/users/*")
                .addPathPatterns("/api/v1/posts/*/users/*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
