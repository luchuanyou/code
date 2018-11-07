package com.lcy.sequence;

import com.alibaba.fastjson.JSON;
import com.lcy.sequence.model.Sequence;

public class StringTest {

    public static void main(String[] args) {

        int num = 3;
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        String str = String.format("%0" + num + "d", 2);
        System.out.println(str);

        Sequence sequence = new Sequence();
        sequence.setSeqName("name");
        String jsonStr = JSON.toJSONString(sequence);
        System.out.println(jsonStr);
    }
}
