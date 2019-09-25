package cto.github.rent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description redis db 连接
 * @Date 2019-09-25
 * @author ybbzbb
 */
@Configuration
public class JedisDataSource {

    @Value("${redis.ip}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.database}")
    private int database;

    @Value("${redis.pwd}")
    private String pwd;

    @Bean
    public JedisPool initJedis() {
        pwd = "".equals(pwd) ? null : pwd;

        JedisPoolConfig config = new JedisPoolConfig();

        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(true);
        config.setMaxIdle(10);
        config.setMaxTotal(50);
        config.setMinEvictableIdleTimeMillis(1800000);
        config.setMinIdle(3);
        config.setMaxWaitMillis(20000);
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);


        JedisPool jedisPool = new JedisPool(config, host, port, timeout, pwd, database, null);
        return jedisPool;
    }
}
