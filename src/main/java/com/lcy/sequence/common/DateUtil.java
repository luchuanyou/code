package com.lcy.sequence.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取日期：加、减
     * @param date 日期
     * @param day 天数（负数是减）
     * @return
     */
    public static Date getCustomDate(Date date, int day) {
        try {
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            // 时间减一天
            cl.add(Calendar.DAY_OF_MONTH, day);
            date = cl.getTime();
        } catch(Exception e) {
            logger.error("系统异常,getCustomDate is error",e);
        }
        return date;
    }

    /**
     * 比较两个日期是否相等（精确到年月日）
     * @param date1
     * @param date2
     * @return
     *  true：相等
     *  false：不相等
     */
    public static boolean CompareDatesEqual(Date date1,Date date2){
        boolean isEqual = false;
        String dateStr1 = SDF_YYYY_MM_DD.format(date1);
        String dateStr2 = SDF_YYYY_MM_DD.format(date2);
        if(dateStr1.equals(dateStr2))
            isEqual = true;

        return isEqual;
    }
    /**
     * 日期转换
     * @param date
     * @return
     */
    public static String getStringFromDateYYYYMMDD(Date date){
        return SDF_YYYY_MM_DD.format(date);
    }

    /**
     * 日期转换
     * @param date 日期
     * @param format 格式
     * @return
     */
    public static String getStringFromDate(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    /**
     * 日期转换
     * @param date
     * @return
     */
    public static String getStringFromDateYYYYMMDDHHMMSS(Date date){
        return SDF_YYYY_MM_DD_HH_MM_SS.format(date);
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = getCustomDate(new Date(), -1);
        System.out.println(sdf.format(date));
    }
}
