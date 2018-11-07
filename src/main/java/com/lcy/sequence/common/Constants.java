package com.lcy.sequence.common;

/**
 * 常量
 */
public class Constants {

    public static final Integer DEFAULT_SEQ_LENGTH = 11;//序列默认长度
    public static final String SEQ_PRE = ".pre";//序列前缀名
    public static final String SEQ_END = ".end";//序列后缀名

    //序列类型
    public static final Integer SEQ_RULE_TYPE_0 = 0;//序列
    public static final Integer SEQ_RULE_TYPE_1 = 1;//常量
    public static final Integer SEQ_RULE_TYPE_2 = 2;//日期
    public static final Integer SEQ_RULE_TYPE_3 = 3;

    //序列缓存
    public static final String CACHE_SEQ_KEY = "chacheSeq";//序列缓存key
    public static final String CACHE_SEQ_RULE_LIST_KEY = "cacheSeqRuleList";//序列规则列表缓存key
    public static final String CACHE_SEQ_CURRENT_VALUE_KEY = "cacheSeqCurrentVal";//序列当前值缓存key
    public static final String CACHE_SEQ_MAX_VALUE_KEY = "cacheSeqMaxVal";//序列最大值缓存key

    //序列分布锁
    public static final String LOCK_SEQ_CUSTOM_KEY = "lock.seq.custom";//序列锁

    //参数前缀
    public static final String CODE_SNOW_WORKERID_PRE = "code.snow.workerId.";//
    public static final String CODE_SNOW_DATACENTERId_PRE = "code.snow.datacenterId.";//
}
