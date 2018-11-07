package com.lcy.sequence.mapper;


import com.lcy.sequence.model.Sequence;

import java.util.List;

public interface SequenceMapper {
    Sequence selectSequenceBySeqName(String seqName);

    List<Sequence> selectSequenceList();

    int updateCurrentValBySeqName(Sequence sequence);

    int updateCurrentDateBySeqName(Sequence sequence);
}