package com.wegoteam.framework.util;
 
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description:
 * @author: XUCHANG
 * @time: 2020/12/4 10:51
 */
@Slf4j
public class PropertyUtils {

    /**
     * 加载property文件到io流里面
     * @param propertyFile
     * @return
     */
    public static Properties loadProperties(String propertyFile) {
    	Properties properties = new Properties();
        try {
            InputStream inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyFile);
            if(inputStream == null){
            	inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream("properties/"+propertyFile);
            }
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            log.error("加载属性文件{}失败，失败原因{}",propertyFile,e);
        }
        return properties;
    }
}
