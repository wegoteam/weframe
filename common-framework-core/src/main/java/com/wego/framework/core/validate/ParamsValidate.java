package com.wego.framework.core.validate;

import javax.validation.groups.Default;
import java.lang.annotation.*;


/**
 * @author xuchang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamsValidate {

    int[] argsIndexs() default {0};

    Class<?>[] groups() default {Default.class};
}
