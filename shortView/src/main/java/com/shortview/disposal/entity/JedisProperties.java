package com.shortview.disposal.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author wanghui
 * @Date 2023/8/30 0030 21:33
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jedis.pool")
public class JedisProperties {
    private int maxTotall;
    private int maxIdle;
    private int minIdle;
    private int maxWaitMillis;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private int timeBetweenEvictionRunsMillis;
    private boolean testWhileIdle;
    private int numTestPerEvictionRun;

    private String host;
    private String password;
    private int port;
    private int timeout;
}
