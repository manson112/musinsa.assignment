package manson112.github.musinsa.assignment.config;

import manson112.github.musinsa.assignment.config.web.DefaultPageRequestHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Bean
    public DefaultPageRequestHandlerMethodArgumentResolver defaultPageRequestHandlerMethodArgumentResolver() {
        return new DefaultPageRequestHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(defaultPageRequestHandlerMethodArgumentResolver());
    }
}
