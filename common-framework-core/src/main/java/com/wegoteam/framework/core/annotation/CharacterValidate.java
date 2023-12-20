package com.wegoteam.framework.core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-06-01 10:11
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CharacterValidateConstraint.class})
public @interface CharacterValidate {
    /**
     * 默认错误消息
     * @return
     */
    String message() default "字符串输入有误！";

    /**
     * 分组
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     * @return
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 是否允许为空
     * @return
     */
    boolean isAllowNull() default true;

    /**
     * 正则
     * 身份证、邮箱、手机号等等
     * @return
     */
    String regularization() default "";

    /**
     * 最小字符长度
     * @return
     */
    int min() default 0;
    /**
     * 最大字符长度
     * @return
     */
    int max() default 2147483647;
}
