package com.minminaya.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间处理的工具类
 * Created by Niwa on 2017/10/3.
 */

public class DateUtils {

    /**
     * 获取当前年月日的偏移
     * <p>当前偏移量为0时，表示当前时间，为负数时，表示前几天，为正数时，表示后几天</p>
     *
     * @param index 偏移量，单位为Day
     */
    public static String getBeforeDayTime(int index) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, index);
        date = calendar.getTime();
        String str = dateFormat.format(date);
        Logger.e("DateUtils", "当前时间：" + str);
        return str;
    }


    public static String convertDate(String indexDate) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outDateFormat = new SimpleDateFormat("MM月dd日");
        Date date = null;
        try {
            date = inputDateFormat.parse(indexDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = outDateFormat.format(date);
        Logger.e("DateUtils", "当前时间：" + str);
        return str;
    }


}
