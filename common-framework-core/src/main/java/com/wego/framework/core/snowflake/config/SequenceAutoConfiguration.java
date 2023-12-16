package com.wego.framework.core.snowflake.config;


import com.wego.framework.core.snowflake.SequenceService;
import com.wego.framework.core.snowflake.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: Logable注解的切面实现
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
@EnableConfigurationProperties({SequenceProperties.class})
//@AutoConfigureAfter(PageHelperAutoConfiguration.class)
public class SequenceAutoConfiguration {

    /**
     * simple获取节点
     */
    private static final String GENERATE_TYPE_SIMPLE = "simple";

    /**
     * 随机获取节点
     */
    private static final String GENERATE_TYPE_RANDOM = "random";

    /**
     * mac地址获取节点
     */
    private static final String GENERATE_TYPE_MAC = "mac";

    @Autowired
    private SequenceProperties properties;

    @Lazy
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Primary
    @Bean
    @ConditionalOnProperty(
            prefix = "sequence",
            name = "type",
            havingValue = "snowflake")
    public SequenceService snowFlakeService(WorkNodeGenerate workNodeGenerate) {
        if(redisTemplate == null){
            throw  new IllegalStateException("Snowflake sequence com.itools.service need RedisTemplate bean.");
        }
        SnowflakeSequenceService snowflakeSequenceService = new SnowflakeSequenceService();
        snowflakeSequenceService.setRedisTemplate(redisTemplate);
        snowflakeSequenceService.setWorkNodeGenerate(workNodeGenerate);
        return snowflakeSequenceService;
    }


    @Bean
    @ConditionalOnProperty(
            prefix = "sequence",
            name = "type",
            havingValue = "snowflake")
    public WorkNodeGenerate workNodeGenerate() {
        String generate = properties.getGenerate();
        if (GENERATE_TYPE_RANDOM.equals(generate)) {
            return new RandomNodeGenerate();
        } else if (GENERATE_TYPE_SIMPLE.equals(generate)) {
            return new SimpleNodeGenerate();
        } else if (GENERATE_TYPE_MAC.equals(generate)) {
            return new MacNodeGenerate();
        }
        return new RandomNodeGenerate();
    }


}
