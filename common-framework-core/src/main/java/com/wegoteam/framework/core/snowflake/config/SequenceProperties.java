package com.wegoteam.framework.core.snowflake.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: Logable注解的切面实现
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
@ConfigurationProperties(
        prefix = "sequence"
)
public class SequenceProperties {

    private boolean enable = true;

    /**
     * default, snowflake
     */
    private String type;

    /**
     * mac, random, simple
     */
    private String generate;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenerate() {
        return generate;
    }

    public void setGenerate(String generate) {
        this.generate = generate;
    }
}
