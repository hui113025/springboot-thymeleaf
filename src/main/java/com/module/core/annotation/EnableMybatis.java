package com.module.core.annotation;

import com.module.core.mybatis.MybatisConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * Mybatis 配置 簡化注解，使用该注解自动配置Mybatis 通用 Mapper 及 PageHelper 插件
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisConfigurer.class)
public @interface EnableMybatis {}

