package com.lcy.sequence;

import com.lcy.sequence.common.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void incTest(){
        int b = 12;
//        redisTemplate.opsForValue().set("orderKey",b);
        redisTemplate.boundValueOps("orderKey").set(b);
        Long num = redisTemplate.boundValueOps("orderKey").increment(1);
        System.out.println("==================="+num);

    }
    @Test
    public void incTest2(){
        Long num = 12L;
        int size = 2;
        RedisAtomicLong count = new RedisAtomicLong("count",redisTemplate.getConnectionFactory());
        count.set(num);

        long newVal = count.incrementAndGet();
        System.out.println("======================"+newVal);

        long fsdf = count.addAndGet(size);
        System.out.println("==============="+fsdf);
        System.out.println("========================"+count.get());
    }

    @Test
    public void emptyTest(){
        Long maxVal = (Long) redisTemplate.opsForValue().get(Constants.CACHE_SEQ_MAX_VALUE_KEY );
        System.out.println("====================="+maxVal);
        RedisAtomicLong count = new RedisAtomicLong(Constants.CACHE_SEQ_CURRENT_VALUE_KEY + 123,redisTemplate.getConnectionFactory());
        System.out.println("===="+count);
        System.out.println("===================="+count.get());

        Object redisKey = "key";
        long key = redisTemplate.opsForValue().increment(redisKey, 1);
        System.out.println("====================key:"+key);
    }
}
