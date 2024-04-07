package com.wegoteam.framework.core.snowflake.impl;

import com.wegoteam.framework.core.snowflake.SequenceService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * ClassName: SnowflakeSequenceService <br/>
 * Description: 基于snowFlake算法实现序列号生成
 * @author xuchang
 */
@Service
public class SnowflakeSequenceService implements SequenceService {

    /** 最小节点编号 */
    private final Integer MIN_NODE_ID = 0;

    /** id worker*/
    private SnowFlakeIdWorker idWorker;

    /** redis client*/
    private RedisTemplate<Object, Object> redisTemplate;

    private WorkNodeGenerate workNodeGenerate;

    private ClusterNode node;

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setWorkNodeGenerate(WorkNodeGenerate workNodeGenerate) {
        this.workNodeGenerate = workNodeGenerate;
    }

    @PostConstruct
    public void init() {
        if (null == redisTemplate) {
            this.idWorker = new SnowFlakeIdWorker(MIN_NODE_ID, MIN_NODE_ID);
        } else {
            node = workNodeGenerate.generate(redisTemplate);
            this.idWorker = new SnowFlakeIdWorker(node.getWorkId(), node.getCenterId());
        }
    }

    @Override
    public Long nextValue(String category) {
        return idWorker.nextValue();
    }

    @Override
    public Long nextValue(String category, Long maxValue) {
        return idWorker.nextValue();
    }

    @PreDestroy
    public void destroy(){
        if(node != null){
            workNodeGenerate.release(redisTemplate, node);
        }
    }
}
