package com.wego.framework.core.validate;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * @author xuchang
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(ValidatorConfig.class)
public @interface EnableValidator {
}
