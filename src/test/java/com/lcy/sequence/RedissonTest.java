package com.lcy.sequence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void getList(){
        try {
            System.out.println("=========================="+redissonClient);
            System.out.println("=================result==========="+redissonClient.getConfig().toJSON().toString());
            //测试 list
            List<String> strList = redissonClient.getList("strList");
            System.out.println("===========str"+strList);
            strList.clear(); //清除所有数据
            strList.add("测试中文1");
            strList.add("test2");

            strList = redissonClient.getList("strList");
            System.out.println("===========str"+strList);
            redissonClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void lockTest() throws IOException {
        System.out.println("=================result==========="+redissonClient.getConfig().toJSON().toString());
        RLock lock = redissonClient.getFairLock("test");

        try {
            lock.lock();
            System.out.println("====================================");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
