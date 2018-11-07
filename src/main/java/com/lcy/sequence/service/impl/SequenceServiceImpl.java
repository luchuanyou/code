package com.lcy.sequence.service.impl;

import com.lcy.sequence.common.CustomException;
import com.lcy.sequence.mapper.SequenceMapper;
import com.lcy.sequence.mapper.SequenceRuleListMapper;
import com.lcy.sequence.model.Sequence;
import com.lcy.sequence.model.SequenceRuleList;
import com.lcy.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    SequenceMapper sequenceMapper;
    @Autowired
    SequenceRuleListMapper sequenceRuleListMapper;

    @Override
    public Sequence selectSequenceBySeqName(String seqName) {
        return sequenceMapper.selectSequenceBySeqName(seqName);
    }

    @Override
    public List<Sequence> selectSequenceList() {
        return sequenceMapper.selectSequenceList();
    }

    @Override
    public int updateCurrentValBySeqName(String seqName, Integer cacheSize) throws CustomException{
        try {
            if(StringUtils.isEmpty(seqName))
                throw new CustomException("1001","序列名称不能为空");
            if(cacheSize == null  || cacheSize <= 0)
                throw new CustomException("1001","缓存大小不能为空或者小于等于0");
            Sequence dto = new Sequence();
            dto.setSeqName(seqName);
            dto.setCacheSize(cacheSize);
            return sequenceMapper.updateCurrentValBySeqName(dto);
        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode(),e.getErrorMessage());
        } catch (Exception e) {
            throw new CustomException("9999","更新序列系统异常");
        }
    }

    @Override
    public int updateCurrentDateBySeqName(String seqName, Date currentDate) throws CustomException {
        try {
            if(StringUtils.isEmpty(seqName))
                throw new CustomException("1001","序列名称不能为空");
            if(currentDate == null)
                throw new CustomException("1001","更新时间不能为空");
            Sequence dto = new Sequence();
            dto.setSeqName(seqName);
            dto.setLastDate(currentDate);
            return sequenceMapper.updateCurrentDateBySeqName(dto);
        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode(),e.getErrorMessage());
        } catch (Exception e) {
            throw new CustomException("9999","更新序列日期系统异常");
        }
    }

    @Override
    public List<SequenceRuleList> selectSequenceRuleListBySeqName(String seqName) {
        return sequenceRuleListMapper.selectSequenceRuleListBySeqName(seqName);
    }
}
