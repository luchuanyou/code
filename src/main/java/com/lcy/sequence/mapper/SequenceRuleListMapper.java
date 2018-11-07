package com.lcy.sequence.mapper;

import com.lcy.sequence.model.SequenceRuleList;

import java.util.List;

public interface SequenceRuleListMapper {
    List<SequenceRuleList> selectSequenceRuleListBySeqName(String seqName);
}