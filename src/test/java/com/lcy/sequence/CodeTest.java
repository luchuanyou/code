package com.lcy.sequence;

import com.lcy.sequence.common.SnowflakeUtil;
import com.lcy.sequence.mapper.SequenceLogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeTest {

    @Autowired
    SequenceLogMapper sequenceLogMapper;

    @Test
    public void randomTest() {
        SnowflakeUtil idWorker = new SnowflakeUtil(0, 0);
        for (int i = 0; i < 10000; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
            sequenceLogMapper.insertTestUser(id+"");
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(1527591735942L - 1527591726221L);
    }
}
