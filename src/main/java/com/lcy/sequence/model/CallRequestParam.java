package com.lcy.sequence.model;

import java.io.Serializable;

public class CallRequestParam implements Serializable {

    private static final long serialVersionUID = 2062644726626678608L;

    private String seqName;//序列名称
    private String defaultValue = "000000000000000000000000000000";//默认值
    private Integer seqLength = 10;//序列长度

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getSeqLength() {
        return seqLength;
    }

    public void setSeqLength(Integer seqLength) {
        this.seqLength = seqLength;
    }
}
