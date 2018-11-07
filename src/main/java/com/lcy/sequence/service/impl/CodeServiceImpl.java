package com.lcy.sequence.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.lcy.sequence.common.Constants;
import com.lcy.sequence.common.CustomException;
import com.lcy.sequence.common.DateUtil;
import com.lcy.sequence.model.Sequence;
import com.lcy.sequence.model.SequenceRuleList;
import com.lcy.sequence.service.CodeService;
import com.lcy.sequence.service.SeqCacheService;
import com.lcy.sequence.service.SequenceService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CodeServiceImpl implements CodeService {
    private static final Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class);

    @Autowired
    SequenceService sequenceService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SeqCacheService seqCacheService;
    @Autowired
    RedissonClient redissonClient;

    @Override
    public  String getNextValue(String seqName) {
        RLock lock = redissonClient.getFairLock(Constants.LOCK_SEQ_CUSTOM_KEY);
        try {
            lock.lock();//加锁
//            logger.info("加锁中...");

            //时时查询序列结果
            Sequence sequence = sequenceService.selectSequenceBySeqName(seqName);
            //检查序列是否更新循环
            checkSequenceIsCycle(sequence);
            //检查是否需要更新缓存
            Long seqNo = checkSequenceUpdateCache(sequence);
            //组合序列
            return getCodeFromRuleList(sequence,seqNo);
        } catch (Exception e) {
            logger.error("获取序列编码系统异常,getNextValue is error seqName"+seqName,e);
            return null;
        }finally {
            lock.unlock();//释放锁
//            logger.info("释放锁....");
        }
    }

    /**
     * 组合序列
     * @param sequence 序列实例
     * @param seqNo 序列号
     * @return
     */
    private String getCodeFromRuleList(Sequence sequence, Long seqNo) {
        try {
            List<SequenceRuleList> ruleList = seqCacheService.getCacheSequenceRuleList(sequence.getSeqName());

            StringBuffer sb = new StringBuffer();
            for(SequenceRuleList dto:ruleList){
                if(Constants.SEQ_RULE_TYPE_0.equals(dto.getType())){//序列类型
                    //获取序列长度
                    int seqLength = getSeqLength(sequence.getCycle(), dto.getContent());
                    sb.append(String.format("%0" + seqLength + "d", seqNo));
                }else if(Constants.SEQ_RULE_TYPE_1.equals(dto.getType())){//常量类型
                    sb.append(dto.getContent());
                }else if(Constants.SEQ_RULE_TYPE_2.equals(dto.getType())){//日期类型
                    String dateStr = DateUtil.getStringFromDate(new Date(),dto.getContent());
                    sb.append(dateStr);
                }else if(Constants.SEQ_RULE_TYPE_3.equals(dto.getType())){

                }
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("组合序列系统异常,getCodeFromRuleList is error seqName:"+ sequence.getSeqName(),e);
            throw new CustomException("9999","组合序列系统异常");
        }
    }

    /**
     * 获取序列生成长度
     * @param cycle 是否循环
     * @param content 序列规则长度
     * @return
     */
    private int getSeqLength(Integer cycle, String content) {
        int seqLength = Constants.DEFAULT_SEQ_LENGTH;
        if(cycle == 0)//不循环
            return seqLength;

        if(!StringUtils.isEmpty(content)){
            seqLength = Integer.valueOf(content);
        }
        return seqLength;
    }

    /**
     * 检查是否需要更新缓存
     * @param sequence
     * @return
     */
    private Long checkSequenceUpdateCache(Sequence sequence) {
        try {
            if(seqCacheService.getCacheSeqCurrentVal(sequence.getSeqName()) < seqCacheService.getCacheSeqMaxVal(sequence.getSeqName())){
                //当前缓存序列值自增
                Long newVal = seqCacheService.incCacheSeqCurrentVal(sequence.getSeqName(),sequence.getIncrementVal());
                return newVal;
            }else{
               //更新序列缓存
               seqCacheService.resetSeqCache(sequence.getSeqName());
                Long newVal = seqCacheService.incCacheSeqCurrentVal(sequence.getSeqName(),sequence.getIncrementVal());
                return newVal;
            }
        } catch (Exception e) {
            logger.error("检查序列是否更新缓存系统异常,checkSequenceUpdateCache is error sequence:"+  JSON.toJSONString(sequence),e);
            throw new CustomException("9999","检查序列是否更新缓存系统异常");
        }
    }

    /**
     * 检查序列是否更新循环
     * @param sequence 序列实体类
     * @throws CustomException
     */
    private void checkSequenceIsCycle(Sequence sequence)  throws CustomException{
        try {
            boolean isUpdate = DateUtil.CompareDatesEqual(sequence.getLastDate(), new Date());
            if(!isUpdate){//更新序列
                sequenceService.updateCurrentDateBySeqName(sequence.getSeqName(),new Date());
                //更新序列缓存
                seqCacheService.resetSeqCache(sequence.getSeqName());
            }
        } catch (Exception e) {
            logger.error("检查序列是否更新循环系统异常,checkSequenceIsCycle is error sequence:"+ JSON.toJSONString(sequence),e);
            throw new CustomException("9999","检查序列是否更新循环系统异常");
        }
    }
}
