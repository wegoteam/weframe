package com.wegoteam.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-11-12 14:14
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(String beanId){
        return (T)context.getBean(beanId);
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.context=applicationContext;
    }
}