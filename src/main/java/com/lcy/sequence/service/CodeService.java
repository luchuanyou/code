package com.lcy.sequence.service;

/**
 * 编码生成处理接口
 */
public interface CodeService {
    /**
     * 获取编码序列
     * @param seqName 序列名称
     * @return
     */
    public String getNextValue(String seqName);
}
