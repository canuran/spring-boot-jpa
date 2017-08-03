package ewing.config;

import ewing.common.DateStringConverter;
import ewing.common.StringDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    public static Logger logger = LoggerFactory.getLogger(WebAppConfigurer.class);

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 自定义首页路径 默认是index.html
        registry.addViewController("/").setViewName("redirect:swagger-ui.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    /**
     * 字符串转换成日期。
     */
    @Bean
    public Converter<String, Date> stringDateConverter() {
        return new StringDateConverter();
    }

    /**
     * 日期转换成字符串。
     */
    @Bean
    public Converter<Date, String> dateStringConverter() {
        return new DateStringConverter();
    }

    /**
     * 国际化资源绑定到容器和请求上下文，使用Header中的Accept-Language。
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasename("messages/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}