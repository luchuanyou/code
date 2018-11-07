package com.lcy.sequence.controller;

import com.lcy.sequence.common.Constants;
import com.lcy.sequence.common.SnowflakeUtil;
import com.lcy.sequence.common.properties.DynamicPropertySource;
import com.lcy.sequence.mapper.SequenceLogMapper;
import com.lcy.sequence.model.base.Result;
import com.lcy.sequence.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * code编码接口
 * 请求地址：http://localhost:8089/getCode?seqName=seq_user
 */
@RestController
public class SeqController {

    @Autowired
    SequenceLogMapper sequenceLogMapper;

    @Autowired
    CodeService codeService;

    long t1 = System.currentTimeMillis();
    AtomicInteger count = new AtomicInteger();

    /**
     * 自定义编码接口
     * @param seqName
     * @return
     */
    @RequestMapping("/getCode")
    public Result<String>  getCustomCode(String seqName) {
        Result<String> result = new Result<String>("9999","系统异常",null);

        try {
            if(StringUtils.isEmpty(seqName))
                return new Result<String>("1001","序列名称不能为空",null);

            //获取code编码
            String code = codeService.getNextValue(seqName);

            //添加日志记录
            int id = sequenceLogMapper.insertTestUser(code);
            long t2 = System.currentTimeMillis();
            if((t2-t1) > 1000L){
                t1 = t2;
                System.out.println(count.get());
                count.set(1);
            }else {
                count.incrementAndGet();
            }
            result = new Result<String>("0000","成功",code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成18位ID策略接口
     * @param sign
     * @return
     */
    @RequestMapping("/getFixedCode/{sign}")
    public Result<String>  getFixedCode(@PathVariable String sign) {
        Result<String> result = new Result<String>("9999","系统异常",null);
        try {
            if(StringUtils.isEmpty(sign))
                return new Result<String>("1001","入参不能为空",null);

            String workerId = (String) DynamicPropertySource.getValue(Constants.CODE_SNOW_WORKERID_PRE + sign);
            String datacenterId = (String) DynamicPropertySource.getValue(Constants.CODE_SNOW_DATACENTERId_PRE + sign);

            if(StringUtils.isEmpty(workerId) || StringUtils.isEmpty(datacenterId))
                return new Result<String>("1002","配置参数不能为空",null);

            SnowflakeUtil sfu = new SnowflakeUtil(Long.valueOf(workerId), Long.valueOf(datacenterId));
            long id = sfu.nextId();
            result = new Result<String>("0000","成功",id+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
