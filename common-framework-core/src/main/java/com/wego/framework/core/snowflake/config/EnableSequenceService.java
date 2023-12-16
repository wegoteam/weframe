package com.wego.framework.core.snowflake.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description: Logable注解的切面实现
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SequenceAutoConfiguration.class)
public @interface EnableSequenceService {

}
