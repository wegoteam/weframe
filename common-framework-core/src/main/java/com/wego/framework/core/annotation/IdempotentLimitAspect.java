package com.wego.framework.core.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wego.framework.core.em.SystemCodeBean;
import com.wego.framework.core.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-05-18 17:43
 */
@Aspect
@Component
public class IdempotentLimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(IdempotentLimitAspect.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.itools.core.annotation.IdempotentLimit)")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        //获取当前的请求体
        //获取当前的请求体
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        //返回对象
        Object obj = null;
        // 获取注解
        IdempotentLimit idempotentLimit = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(IdempotentLimit.class);
        Object[] args = joinPoint.getArgs();
        //key=前缀+token+url
        String url = httpServletRequest.getRequestURI().toString();
        String tokenName = httpServletRequest.getHeader(idempotentLimit.tokenName());
        if (StringUtils.isBlank(tokenName)){
            //token不存在
        }
        String key = "idempotent:"+tokenName+url;
        boolean flag = stringRedisTemplate.hasKey(key).booleanValue();
        //切面的参数
        String parameterString = this.getParamString(args);
        if (StringUtils.isBlank(parameterString)){
            logger.error("接口{}暂无提交数据内容",url);
            throw new AppException(SystemCodeBean.SystemCode.CONTENT_NULL_EXCEPTION.getCode());
        }

        if (flag){
            String hasKeyParams = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.equals(hasKeyParams,parameterString)){
                logger.error("接口为{}的数据重复提交，请稍后重试！提交参数为：{}",url,parameterString);
                throw new AppException(SystemCodeBean.SystemCode.IDEMPOTENT_EXCEPTION.getCode());
            }
        }else {
            stringRedisTemplate.opsForValue().set(key,parameterString,idempotentLimit.timeout(),idempotentLimit.timeUnit());
        }

        try {
            //切面正确执行
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }
    /**
     * 获取参数
     * @param args
     * @return
     */
    private String getParamString(Object[] args) {
        String parameterString = null;
        List<Object> params = new ArrayList<>();
        for (int i=0;i<args.length;i++){
            if (args[i] instanceof HttpServletRequest){
                continue;
            }
            if (args[i] instanceof HttpServletResponse){
                continue;
            }
            if (args[i] instanceof MultipartFile || args[i] instanceof MultipartFile[]){
                continue;
            }
            params.add(args[i]);
        }
        if (CollectionUtils.isEmpty(params)){
            return null;
        }
        try {
            parameterString = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            logger.warn("请求参数序列化失败，异常信息：{}", ExceptionUtils.getStackTrace(e));
            return null;
        }
        return parameterString;
    }
}
