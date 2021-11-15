package com.onnoa.shop.common.utils;

import com.onnoa.shop.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @Description: 时间转换工具类
 * @Author: onnoA
 * @Date: 2019/11/11 15:57
 */
public class DateUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    // 系统默认日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // 系统默认日期时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回当天最早的事件 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getBeginDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();

    }

    /**
     * 返回当前最晚的事件 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            return c.getTime();
        }
        return null;
    }

    /**
     * 获取两个时间间隔秒数
     */
    public static Long getSecondsBetween(Date d1, Date d2) {
        return Math.abs((d1.getTime() - d2.getTime()) / 1000);
    }

    /**
     * 指定日期加上天数后的日期
     */
    public static Long plusDay(String num, Long NewDate) {
        int day = Integer.parseInt(num);
        Date date = new Date(NewDate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, day);
        return ca.getTime().getTime();
    }

    /**
     * 指定时间加上天数后的日期
     */
    public static Long addDate(Long time, String day) {
        int num = Integer.parseInt(day);
        num = num * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += num; // 相加得到新的毫秒数
        return time; // 将毫秒数转换成日期
    }

    //把字符串解析为日期对象
    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //获取当前时间是星期几
    public static Integer getWeek4Today(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return w == 0 ? 7 : w;
    }

    /**
     * 按格式进行截取时间
     * @param date
     * @param format
     * @return
     */
    public static Date splitByFormat(Date date,String format){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        String str = simpleDateFormat.format(date);
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date tomorrow() {
        return addDay(new Date(), 1);
    }

    public static int getCurrHour() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间戳.1970-1-1 至今的秒数。单位：秒。
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 将1970-1-1 0:0:0 算起的秒数转化为时间
     *
     * @param second
     * @return
     */
    public static Date fromSecond(long second) {
        return new Date(second * 1000);
    }

    /**
     * 格式化日期输出。如果传入data为null，则返回空字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static final String formate(Date date, String format) {
        if (date == null)
            return "";
        format = StringUtils.isBlank(format) ? "yyyy-MM-dd" : format;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static final String formate(Date date) {
        return formate(date, null);
    }

    /**
     * 解析日期
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static final Date parseDate(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            // e.printStackTrace();
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 出错时，抛出异常
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static final Date parseDate2(String dateStr, String format) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateStr);
    }

    /**
     * 获取当天时间 天 开始时间
     *
     * @param d
     * @return
     */
    public static final Date getDayStart(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当天时间 天 开始时间
     *
     * @param d
     * @return
     */
    public static final Date getDayEnd(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public static final Date addDay(Date d, int day) {
        return addHour(d, day * 24);
    }

    public static final Date addHour(Date d, int hour) {
        if (d == null || hour == 0)
            return d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 往后几个月
     *
     * @param d
     * @param count
     * @return
     */
    public static final Date addMonth(Date d, int count) {
        if (d == null || count == 0)
            return d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTime();
    }

    /**
     * 增加或减少分钟
     *
     * @param d
     * @param m
     * @return
     */
    public static final Date addMinute(Date d, int m) {
        if (d == null || m == 0)
            return d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, m);
        return calendar.getTime();
    }

    /**
     * 增加或减少秒
     *
     * @param date
     * @param seconds
     * @return
     */
    public static final Date addSeconds(Date date, int seconds) {
        if (date == null || seconds == 0)
            return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public static final Date minusDayStart(Date d, int day) {

        if (d == null)
            return d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static final Date getYearStart(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static final Date getYearEnd(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MONTH, 12);
        c.set(Calendar.DATE, 31);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取月 的开始时间，结束时间
     *
     * @param d
     * @return
     */
    public static final Date[] getDayStartAndEndDates(Date d) {
        return new Date[] { getDayStart(d), getDayEnd(d) };
    }

    public static final Date getMonthStart(Integer year, Integer month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     *
     * @param year
     * @param month 月，，10代表10月
     * @return
     */
    public static final Date getMonthEnd(Integer year, Integer month) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        return c.getTime();
    }

    public static final Date getMonthStart(Date date) {
        Calendar c = Calendar.getInstance();
        Integer[] yearAndMonth = getYearAndMonth(date);
        c.set(Calendar.YEAR, yearAndMonth[0]);
        c.set(Calendar.MONTH, yearAndMonth[1] - 1);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static final Date getMonthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        Integer[] yearAndMonth = getYearAndMonth(date);
        c.set(Calendar.YEAR, yearAndMonth[0]);
        c.set(Calendar.MONTH, yearAndMonth[1] - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        return c.getTime();
    }

    /**
     * 获取指定时间的年月
     *
     * @param date
     * @return
     */
    public static final Integer[] getYearAndMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new Integer[] { c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1 };
    }

    /**
     * 判断日期是否相等,不算时间。
     *
     * @param paramsNew
     * @param paramsOld
     * @return
     */
    public static boolean sameDate(Date paramsNew, Date paramsOld) {
        try {
            String newDate = formate(paramsNew, "yyyyMMdd");
            String oldDate = formate(paramsOld, "yyyyMMdd");

            if (newDate.equals(oldDate)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * (lateDate - earlyDate).getSeconds()
     *
     * @param earlyDate
     * @param lateDate
     * @return
     */
    public static long secondsBetween(Date earlyDate, Date lateDate) {
        return (lateDate.getTime() / 1000) - (earlyDate.getTime() / 1000);
    }

    public static int compare(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return 0;
        }
        if (d1 == null && d2 != null) {
            return -1;
        }
        if (d1 != null && d2 == null) {
            return 1;
        }
        return new Long(d1.getTime()).compareTo(d2.getTime());
    }

    public static boolean equle(Date d1, Date d2) {
        return compare(d1, d2) == 0;
    }

    public static boolean less(Date d1, Date d2) {
        return compare(d1, d2) < 0;
    }

    public static boolean greater(Date d1, Date d2) {
        return compare(d1, d2) > 0;
    }

    /**
     * 得到本月第一天的日期
     *
     * @Methods Name getFirstDayOfMonth
     * @return Date
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }

    /**
     * 得到本月最后一天的日期
     *
     * @Methods Name getLastDayOfMonth
     * @return Date
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }

    /***
     * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /***
     * 获取指定日期是周几 参数为null时表示获取当前日期是周几
     *
     * @param date
     * @return
     */
    public static String getWeekForDate(Date date) {
        String[] weekOfDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /***
     * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static int getIntWeekOfDate(Date date) {
        int[] weekOfDays = { 7, 1, 2, 3, 4, 5, 6 };
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /***
     * 获取指定日期是当月第几天 参数为null时表示获取当前日期是当月第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /*
     * 获取昨天的时间
     */
    public static Date getYesterdayDate(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }
    public static Calendar getYesterdayCalendar(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar;
    }
    /*
     * 获取明天的时间
     */
    public static Date getTomorrowDate(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }
    public static Calendar getTomorrowCalendar(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar;
    }
    /*
     * 获取当前天的起始时间
     */
    public static Date getStartTime(Calendar day) {
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime();
    }
    /*
     * 获取当前天的结束时间
     */
    public static Date getEndTime(Calendar day) {
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return day.getTime();
    }


    //文件时间转javaDate
    public static Date  switchTime(String day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String fileTime =day.substring(0, 10)+" "+day.substring(11, 19);
        Date date = null;
        try {
            date = format.parse(fileTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 8);// 24小时制
        date = cal.getTime();
        LOGGER.info("转换后的Date时间:" + format.format(date));  //显示更新后的日期
        cal = null;
        return date;
    }

    /**
     * 判断文件时间是否大于某个值.
     * @param filePath 文件路径
     * @param interval 间隔时间
     * @return  true 超过间隔 , fasle 未超过间隔
     */
    public static Boolean compareTime(String filePath,Long interval ){
        //获取文件时间
        FileTime fileTime= null;
        try {
            fileTime = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class).creationTime();
            LOGGER.info("文件时间(有时区问题在转换时会处理: "+fileTime);
            Date date = DateUtils.switchTime(fileTime.toString());

            Long overtopTime =new Date().getTime()-date.getTime();
            if (!(overtopTime/1000>interval)) {
                LOGGER.info("时间未超过设定间隔: " +date);
                return false;
            }
        } catch (IOException e) {
            LOGGER.error("Exception:{}", e);
        }

        LOGGER.info("时间超过设定间隔 重新从ftp拉取");
        return  true;
    }

    public static Date getFormatedDateTime(String text) {
        if (null == text) {
            return null;
        }
        try {
            SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT);
            return dateFormator.parse(text);
        }
        catch (Exception e) {
            // log.log(Level.SEVERE, "Error while switch datatime format " + text,
            // e);
            throw new ServiceException(e.getMessage());
        }

    }

    public static Date getFormatedDate(String text) {
        if (null == text) {
            return null;
        }
        try {
            SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);
            return dateFormator.parse(text);
        }
        catch (Exception e) {
            LOGGER.error("Error while switch data format " + text, e);
        }

        return null;
    }

}
