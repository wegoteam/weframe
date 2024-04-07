package com.wegoteam.framework.core.bean;

import com.wegoteam.framework.core.context.CommonCallContext;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: XUCHANG
 * @time: 2019/12/4 10:51
 */
public class BeanCopyUtils {

    /**
     * 将对象属性拷贝到目标类型的同名属性字段中
     * @param <T>
     * @param source
     * @param targetClazz
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClazz) {

        T target = null;
        try {
            target = targetClazz.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return target;
    }

    /**
     * 将对象属性拷贝到目标类型的同名属性字段中
     * @param source
     * @param target
     * @return
     */
    public static <T> T copyProperties(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 将对象属性拷贝给目标对象
     * @param source
     * @param context
     * @param target
     * @return
     */
    public static <T> T copyProperties(Object source, CommonCallContext context, T target) {

        BeanUtils.copyProperties(source, target);
        if (CommonBaseTimes.class.isAssignableFrom(target.getClass())) {
            BeanCopyUtils.copyProperties((CommonBaseTimes) target, context);
        }

        return target;
    }

    /**
     * 拷贝通用属性
     * @param dto
     * @param context
     */
    public static void copyProperties(CommonBaseTimes dto, CommonCallContext context) {
        if (context == null || dto == null) {
            return;
        }

        dto.setCreateTime(context.getCallTime().getTime());
        dto.setCreateUserId(context.getUser().getCaller());

        dto.setLastUpdateTime(context.getCallTime().getTime());
        dto.setLastUpdateUserId(context.getUser().getCaller());
    }

    /**
     * 将list的对象拷贝到目标类型对象中
     * @param list
     * @param clazz
     * @return
     */
    public static <V, E> List<E> copy(Collection<V> list, Class<E> clazz) {
        List<E> result = new ArrayList<>(12);

        if (!CollectionUtils.isEmpty(list)) {
            for (V source : list) {
                E target = null;
                try {
                    target = (E) clazz.newInstance();
                    BeanUtils.copyProperties(source, target);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                result.add(target);
            }
        }

        return result;
    }
}
