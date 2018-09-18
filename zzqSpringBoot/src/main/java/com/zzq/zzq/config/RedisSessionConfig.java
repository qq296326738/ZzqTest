package com.zzq.zzq.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 如果不需要做特殊处理，只需直接使用注解@EnableRedisHttpSession即可，
 * 打开@EnableRedisHttpSession源码，
 * 发现maxInactiveIntervalInSeconds  session的过期时间默认是1800秒即30分钟，
 * 如果需要修改，注解时进行修改即可。如果想对redisSession做一些特殊处理。
 * 看@EnableRedisHttpSession源码，头部的注释
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class RedisSessionConfig {
}
