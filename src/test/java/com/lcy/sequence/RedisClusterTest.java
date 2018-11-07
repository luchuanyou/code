package com.lcy.sequence;

import com.lcy.sequence.common.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

/**
 * redis集群测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterTest {
    @Autowired
    JedisCluster jedisCluster;

    @Test
    public void simpleTest(){
        String name = jedisCluster.set("name", "tom");
        System.out.println("=============name:"+name);
        System.out.println("=============getName:"+jedisCluster.get("name"));

    }

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void templTest(){
        redisTemplate.opsForValue().set("hello", "hello world");
        System.out.println("============result："+redisTemplate.opsForValue().get("hello"));
    }
    @Test
    public void templTest2(){
        String seqName = "user";
        RedisAtomicLong count = new RedisAtomicLong(Constants.CACHE_SEQ_CURRENT_VALUE_KEY + seqName,redisTemplate.getConnectionFactory());
        count.set(1);
        System.out.println("====================="+count.get());
    }
}
