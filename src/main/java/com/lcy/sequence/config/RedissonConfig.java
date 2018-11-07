package com.lcy.sequence.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "spring.redisson")
@Configuration
public class RedissonConfig {

    private String address;
    private String password;


    /**
     * 单例模式
     * @return
     */
    /*@Bean
    public RedissonClient RedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379")
                .setPassword("123456")
                ;
        return Redisson.create(config);
    }*/

    /**
     * 主从模式
     * @return
     */
    /*@Bean
    public RedissonClient RedissonClient() {
        Config config = new Config();
        config.useMasterSlaveServers()
                .setMasterAddress("127.0.0.1:6379")
                .addSlaveAddress("127.0.0.1:6379", "127.0.0.1:6379", "127.0.0.1:6379")
                .addSlaveAddress("127.0.0.1:6379")
                .setPassword("123456")
                //同任何节点建立连接时的等待超时。时间单位是毫秒。默认：10000
                .setConnectTimeout(30000)
                //当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。默认:3000
                .setReconnectionTimeout(10000)
                //等待节点回复命令的时间。该时间从命令发送成功时开始计时。默认:3000
                .setTimeout(10000)
                //如果尝试达到 retryAttempts（命令失败重试次数） 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时） 计时。默认值：3
                .setRetryAttempts(5)
                //在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒。     默认值：1500
                .setRetryInterval(3000)
        ;
        return Redisson.create(config);
    }*/

    @Value("${spring.redis.cluster.nodes}")
    private String nodeList;
    /**
     * 集群
     * @return
     */
    @Bean
    public RedissonClient RedissonClient() {
        Config config = new Config();
        /*config.useClusterServers().addNodeAddress("127.0.0.1:6379")
                .addNodeAddress("127.0.0.1:6381","127.0.0.1:6382","127.0.0.1:6383","127.0.0.1:6384","127.0.0.1:6385")
        ;*/
        String[] nodes = nodeList.split(",");
        ClusterServersConfig serversConfig = config.useClusterServers();
        for (String node : nodes) {
            serversConfig.addNodeAddress(node)            ;
        }
        return Redisson.create(config);
    }
}
