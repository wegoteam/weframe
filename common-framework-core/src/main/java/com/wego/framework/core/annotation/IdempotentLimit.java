package com.wego.framework.core.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-05-18 17:38
 */
@Inherited
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdempotentLimit {
    /**
     * 幂等类型：目前支持提交内容
     * @return
     */
    String type() default "CONTENT";

    /**
     * 令牌名称
     * @return
     */
    String tokenName() default "Authorization";
    /**
     * 过期时间
     * 默认单位时间内的3s
     * @return
     */
    long timeout() default 3000;
    /**
     * 时间单位，默认取毫秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}

