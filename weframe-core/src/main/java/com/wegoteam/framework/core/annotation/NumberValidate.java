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
@Constraint(validatedBy = {NumberValidateConstraint.class})
public @interface NumberValidate {
    /**
     * 默认错误消息
     * @return
     */
    String message() default "数字输入有误！";

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
    boolean isValiNull() default true;

    /**
     * 数字正则
     * 默认大于0数字正则,包含小数
     * @return
     */
    String numberRegularization() default "^[+]{0,1}(\\d+)$|^[+]{0,1}(\\d+\\.\\d+)$";
}
