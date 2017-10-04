package com.zju.app.config;


import com.zju.app.interceptor.LoginConfigVO;
import com.zju.app.interceptor.LoginInterceptor;
import com.zju.utils.springmvc.ContentParaVersionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

/**
 * Created by xujingfeng on 2017/8/11.
 */
@Configuration
public class SpringMvcConfig {

    @Configuration
    @AutoConfigureOrder()
    protected static class MvcConfigurerAdapter extends WebMvcConfigurerAdapter {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/META-INF/resources/"
                            , "classpath:/resources/", "classpath:/static/"
                            , "classpath:/public/")
                    .setCachePeriod(365 * 24 * 3600 * 1000)
                    .resourceChain(true).addResolver(new VersionResourceResolver()
                    .addVersionStrategy(new ContentParaVersionStrategy(), "/**"));

        }

        @Bean
        LoginInterceptor loginInterceptor(){
            return new LoginInterceptor();
        }

        @Autowired
        LoginConfigVO loginConfigVO;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            super.addInterceptors(registry);
            registry.addInterceptor(loginInterceptor()).excludePathPatterns(loginConfigVO.getExcludes());
        }
    }

}
