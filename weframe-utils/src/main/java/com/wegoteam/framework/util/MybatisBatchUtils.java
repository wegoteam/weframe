package com.wegoteam.framework.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.ToIntFunction;

/**
 * @author xuchang
 * @version 1.0
 * @description: https://juejin.cn/post/7078237987011559460
 * @date 2022/5/4 13:01
 */
@Slf4j
@Component
public class MybatisBatchUtils {
    /**
     * 每次处理1000条
     */
    private static final int BATCH_SIZE = 1000;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 批量处理修改或者插入
     * @Resource
     * private 某Mapper类 mapper实例对象;
     * batchUtils.batchUpdateOrInsert(数据集合, item -> mapper实例对象.insert方法(item));
     * @param data     需要被处理的数据
     * @param function 自定义处理逻辑
     * @return int 影响的总行数
     */
    public  <T> int batchUpdateOrInsert(List<T> data, ToIntFunction<T> function) {
        int count = 0;
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            for (int index = 0; index < data.size(); index++) {
                count += function.applyAsInt(data.get(index));
                if (index != 0 && index % BATCH_SIZE == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.commit();
        } catch (Exception e) {
            batchSqlSession.rollback();
            log.error(e.getMessage(), e);
        } finally {
            batchSqlSession.close();
        }
        return count;
    }
    /**
     * 批量处理修改或者插入
     * batchUtils.batchUpdateOrInsert(数据集合, xxxxx.class, (item, mapper实例对象) -> mapper实例对象.insert方法(item));
     * @param data     需要被处理的数据
     * @param mapperClass  Mybatis的Mapper类
     * @param function 自定义处理逻辑
     * @return int 影响的总行数
     */
    public  <T,U,R> int batchUpdateOrInsert(List<T> data, Class<U> mapperClass, BiFunction<T,U,R> function) {
        int i = 1;
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            U mapper = batchSqlSession.getMapper(mapperClass);
            int size = data.size();
            for (T element : data) {
                function.apply(element,mapper);
                if ((i % BATCH_SIZE == 0) || i == size) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            // 非事务环境下强制commit，事务情况下该commit相当于无效
            batchSqlSession.commit(!TransactionSynchronizationManager.isSynchronizationActive());
        } catch (Exception e) {
            batchSqlSession.rollback();
//            throw new CommonException(e);
        } finally {
            batchSqlSession.close();
        }
        return i - 1;
    }

}
