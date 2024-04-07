package com.wegoteam.framework.core.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author XUCHANG
 * @description:
 * @Date 2020/9/10 15:33
 */
@Inherited
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
	/**
	 * 每秒发放许可数
	 * @return
	 */
	double permitsPerSecond() default 100.0;

	/**
	 * 超时时间，即能否在指定内得到令牌，如果不能则立即返回，不进入目标方法/类
	 * 默认为0，即不等待，获取不到令牌立即返回
	 * @return
	 */
	long timeout() default 0;

	/**
	 * 超时时间单位，默认取毫秒
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
