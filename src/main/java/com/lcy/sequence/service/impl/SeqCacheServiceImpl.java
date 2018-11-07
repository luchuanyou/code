package com.lcy.sequence.service.impl;

import com.lcy.sequence.common.Constants;
import com.lcy.sequence.common.CustomException;
import com.lcy.sequence.model.Sequence;
import com.lcy.sequence.model.SequenceRuleList;
import com.lcy.sequence.service.SeqCacheService;
import com.lcy.sequence.service.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeqCacheServiceImpl implements SeqCacheService {
    private static final Logger logger = LoggerFactory.getLogger(SeqCacheServiceImpl.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SequenceService sequenceService;


    @Override
    public void initSeqCache() {
        //查询序列列表
        List<Sequence> seqList = sequenceService.selectSequenceList();
        for (Sequence sequence : seqList) {
            //初始化序列值
            resetCacheSeqCurrentValAndMaxVal(sequence.getSeqName());
            //初始化序列规则列表缓存
            resetCacheSequenceRuleList(sequence.getSeqName());
        }
    }

    @Override
    public void resetSeqCache(String seqName) {
        //更新重置序列化值
        resetCacheSeqCurrentValAndMaxVal(seqName);
        //初始化序列规则列表缓存
//        resetCacheSequenceRuleList(seqName);
    }
    /**
     * 更新缓存序列当前值和最大值
     * @param seqName
     */
    private synchronized void resetCacheSeqCurrentValAndMaxVal(String seqName) {
        try {
            Sequence sequence = sequenceService.selectSequenceBySeqName(seqName);
            RedisAtomicLong count = new RedisAtomicLong(Constants.CACHE_SEQ_CURRENT_VALUE_KEY + seqName,redisTemplate.getConnectionFactory());
            count.set(sequence.getCurrentVal());
            //更新序列数据库
            sequenceService.updateCurrentValBySeqName(seqName,sequence.getCacheSize());

            sequence = sequenceService.selectSequenceBySeqName(seqName);
            redisTemplate.opsForValue().set(Constants.CACHE_SEQ_MAX_VALUE_KEY + seqName,(sequence.getCurrentVal()));
        }  catch (Exception e) {
            logger.error("更新缓存序列当前值和最大值系统异常,resetCacheSeqCurrentValAndMaxVal is error seqName:"+ seqName,e);
            throw new CustomException("9999","更新缓存序列当前值和最大值系统异常");
        }
    }

    /**
     * 重置序列规则列表缓存
     * @param seqName
     */
    private void resetCacheSequenceRuleList(String seqName) {
        List<SequenceRuleList> ruleList = sequenceService.selectSequenceRuleListBySeqName(seqName);
        redisTemplate.opsForValue().set(Constants.CACHE_SEQ_RULE_LIST_KEY + seqName,ruleList);
    }

    @Override
    public List<SequenceRuleList> getCacheSequenceRuleList(String seqName) {
        List<SequenceRuleList> ruleList = (List<SequenceRuleList>) redisTemplate.opsForValue().get(Constants.CACHE_SEQ_RULE_LIST_KEY + seqName);
        if(ruleList == null || ruleList.size() == 0){
            resetCacheSequenceRuleList(seqName);
            //重新赋值
            ruleList = (List<SequenceRuleList>) redisTemplate.opsForValue().get(Constants.CACHE_SEQ_RULE_LIST_KEY + seqName);
        }
        return ruleList;
    }

    @Override
    public Long getCacheSeqCurrentVal(String seqName) {
        RedisAtomicLong count = new RedisAtomicLong(Constants.CACHE_SEQ_CURRENT_VALUE_KEY + seqName,redisTemplate.getConnectionFactory());
        Long currentVal = count.get();
        if(currentVal < 0){
            resetCacheSeqCurrentValAndMaxVal(seqName);
            currentVal = count.get();//重新获取值
        }
        return currentVal;
    }

    @Override
    public Long getCacheSeqMaxVal(String seqName) {
        Long maxVal = (Long) redisTemplate.opsForValue().get(Constants.CACHE_SEQ_MAX_VALUE_KEY + seqName);
        if(maxVal == null){
            resetCacheSeqCurrentValAndMaxVal(seqName);
            //重新赋值
            maxVal = (Long) redisTemplate.opsForValue().get(Constants.CACHE_SEQ_MAX_VALUE_KEY + seqName);
        }
        return maxVal;
    }

    @Override
    public Long incCacheSeqCurrentVal(String seqName, int incrementVal) {
        RedisAtomicLong count = new RedisAtomicLong(Constants.CACHE_SEQ_CURRENT_VALUE_KEY + seqName,redisTemplate.getConnectionFactory());
        Long newVal = count.addAndGet(incrementVal);
        return newVal;
    }
}
