package com.lcy.sequence.service;

import com.lcy.sequence.model.SequenceRuleList;

import java.util.List;

/**
 * 缓存接口
 */
public interface SeqCacheService {
    /**
     * 初始化序列缓存
     */
    public void initSeqCache();

    /**
     * 更新重置序列缓存
     * @param seqName
     */
    public void resetSeqCache(String seqName);

    /**
     * 获取序列规则列表缓存
     * @param seqName
     * @return
     */
    public List<SequenceRuleList> getCacheSequenceRuleList(String seqName);

    /**
     * 获取缓序序列当前值
     * @param seqName
     * @return
     */
    public Long getCacheSeqCurrentVal(String seqName);

    /**
     * 获取缓存序列最大值
     * @param seqName
     * @return
     */
    public Long getCacheSeqMaxVal(String seqName);

    /**
     * 自增缓存序列当前值
     * @param seqName 序列名称
     * @param incrementVal 自增步长值
     * @return
     */
    public Long incCacheSeqCurrentVal(String seqName, int incrementVal);
}
