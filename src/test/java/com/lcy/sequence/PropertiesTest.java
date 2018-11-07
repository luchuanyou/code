package com.lcy.sequence;

import com.alibaba.fastjson.JSON;
import com.lcy.sequence.common.Constants;
import com.lcy.sequence.common.properties.DynamicPropertySource;
import com.lcy.sequence.config.RedissonConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesTest {


    @Autowired
    private Environment env;


    @Test
    public void tt(){
        String workerId = env.getProperty(Constants.CODE_SNOW_WORKERID_PRE+"sign");
        System.out.println("============================="+workerId);

        workerId = (String) DynamicPropertySource.getValue(Constants.CODE_SNOW_DATACENTERId_PRE+"sign");
        System.out.println("============================="+workerId);
    }
}
