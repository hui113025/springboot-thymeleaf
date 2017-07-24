package com.module.core.annotation;

import com.module.product.configuration.WebMvcConfiguration;
import com.module.core.mybatis.MybatisConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * Ruhang Spring Boot 通用配置 , Spring MVC 、 Mybatis ..
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebMvcConfiguration.class, MybatisConfigurer.class})
public @interface EnableGeneralConfiguration {
}

