package com.lcy.sequence.common.init;

import com.lcy.sequence.service.SeqCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化类
 */
@Component
public class CacheInit implements CommandLineRunner {

    @Autowired
    SeqCacheService seqCacheService;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("==========================初始化了=");
        //序列缓存初始化
        seqCacheService.initSeqCache();

    }
}
