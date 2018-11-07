package com.lcy.sequence.service;

import com.lcy.sequence.common.CustomException;
import com.lcy.sequence.model.Sequence;
import com.lcy.sequence.model.SequenceRuleList;

import java.util.Date;
import java.util.List;

public interface SequenceService {
    Sequence selectSequenceBySeqName(String seqName);

    /**
     * 查询序列列表
     * @return
     */
    List<Sequence> selectSequenceList();
    /**
     * 更新序列
     * @param seqName 序列名称
     * @param cacheSize 序列更新缓存大小
     * @return
     */
    int updateCurrentValBySeqName(String seqName,Integer cacheSize) throws CustomException;

    /**
     * 更新循环日期
     * @param seqName
     * @param currentDate
     * @return
     * @throws CustomException
     */
    int updateCurrentDateBySeqName(String seqName,Date currentDate) throws CustomException;

    public List<SequenceRuleList> selectSequenceRuleListBySeqName(String seqName);
}
