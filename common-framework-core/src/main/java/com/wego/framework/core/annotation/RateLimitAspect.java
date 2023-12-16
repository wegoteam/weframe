package com.wego.framework.core.annotation;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.wego.framework.core.em.SystemCodeBean;
import com.wego.framework.core.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 使用Guava的RateLimiter实现限流，设置每秒最大的请求数，仅适用于单体
 *  不保证公平访问
 *  允许先消费，后付款，就是它可以来一个请求的时候一次性取走几个或者是剩下所有的令牌甚至多取
 *  但是后面的请求就得为上一次请求买单，它需要等待桶中的令牌补齐之后才能继续获取令牌
 *  所以实际上每秒能够通过的数量会比设置的permitsPerSecond大
 *  在设置permitsPerSecond的时候应比实际估计的流量要小
 *  限制能处理的总数
 * @author xuchang
 */
@Aspect
@Component
public class RateLimitAspect {
	/**
     * 	com.google.common.collect.Maps 别导错包了
	 * 	存放RateLimiter,一个url对应一个令牌桶
	 */
	private Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();
	@Autowired
	private Environment environment;

	private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);
	
	@Pointcut("@annotation(com.wego.framework.core.annotation.RateLimit)")
	private void pointcut() {
	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) {
		Object obj = null;
		// 获取注解
		RateLimit rateLimit = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RateLimit.class);
		// 获取request,然后获取请求的url，存入map中
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String url = request.getRequestURI();
		// 若获取注解不为空
		if (rateLimit != null) {
			// 获取注解的permitsPerSecond与timeout
			double permitsPerSecond = rateLimit.permitsPerSecond();
			if (environment.containsProperty("permitsPerSecond")){
				permitsPerSecond = environment.getProperty("permitsPerSecond",Double.class);
			}
			long timeout = rateLimit.timeout();
			TimeUnit timeUnit = rateLimit.timeUnit();
			RateLimiter rateLimiter = null;
			// 判断map集合中是否有创建有创建好的令牌桶
			// 若是第一次请求该url，则创建新的令牌桶
			if (!limitMap.containsKey(url)) {
				// 创建令牌桶
				rateLimiter = RateLimiter.create(permitsPerSecond);
				limitMap.put(url, rateLimiter);
				logger.info("请求URL为{}，创建令牌桶，容量为：{}",url,permitsPerSecond);
			}else {
				// 否则从已经保存的map中取
				rateLimiter = limitMap.get(url);
			}
			// 若得到令牌
			if (rateLimiter.tryAcquire(timeout, timeUnit)) {
				// 开始执行目标controller
				try {
					obj = joinPoint.proceed();
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			} else {
				// 否则直接返回错误信息
				logger.error("请求URL为{}，请求频繁，请稍后重试！",url);
				throw new AppException(SystemCodeBean.SystemCode.RETE_EXCEPTION.getCode());
			}
		}
		return obj;
	}

}
