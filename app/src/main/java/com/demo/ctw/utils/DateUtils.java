package com.demo.ctw.utils;

import java.util.Calendar;

public class DateUtils {

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
}
