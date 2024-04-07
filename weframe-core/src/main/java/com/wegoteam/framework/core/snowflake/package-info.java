/**
 * 集成分布式雪花算法
 * 容器启动加入@EnableSequenceService
 * 使用结合SequenceService
 *
 * ###sequence 服务 ###
 * sequence:
 *   enable: true
 *   type: snowflake
 *   generate: simple
 */
package com.wegoteam.framework.core.snowflake;