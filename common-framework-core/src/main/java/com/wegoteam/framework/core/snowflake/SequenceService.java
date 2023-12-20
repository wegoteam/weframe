package com.wegoteam.framework.core.snowflake;

/**
 * 描述 ：序列服务类
 * @author xuchang
 */
public interface SequenceService {

    /**
     * 根据分类，获取序列
     *
     * @param category 分类
     * @return Sequence对象
     */
    public Long nextValue(String category);

    /**
     * 如果大于maxValue，则会取整除maxValue后的余数
     * @param category
     * @param maxValue
     * @return
     */
    public Long nextValue(String category, Long maxValue);

}
