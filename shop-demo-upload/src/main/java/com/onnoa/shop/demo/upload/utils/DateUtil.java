package com.onnoa.shop.demo.upload.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DateUtil.class);

    // 系统默认日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_MONTH = "yyyy-MM";

    public static final String DATE_FORMAT_YEAR = "yyyy";

    // 系统默认日期时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_FORMAT_2 = "yyyyMMddHHmmss";

    public static final String DATETIME_FORMAT_4 = "yyyy/MM/dd HH:mm:ss";

    // 10位日期时间格式
    public static final String DATE_TIME_FORMAT_10 = "yyyyMMddHH";

    // 8位日期格式
    public static final String DATE_FORMAT_8 = "yyyyMMdd";

    // 6位日期格式
    public static final String DATE_FORMAT_6 = "yyyyMM";

    // 4位日期格式
    public static final String DATE_FORMAT_4 = "yyyy";

    // 4位日期格式
    public static final String DATE_TEAR = "yyyy";

    // 14位日期时间格式
    public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";

    public static final String DATE_TIME_FORMAT_22 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATE_TIME_FORMAT_22_ = "yyyyMMddHHmmssSSS";

    public static final String DATE_FORMAT_4_ = "yyyy/MM";

    public static final String DATE_FORMAT_6_ = "yyyy/MM/dd";

    public static final String DATE_FORMAT_7 = "yyyyMM";

    public static final String YEAR = "year";

    public static final String MONTH = "month";

    public static final String DAY = "day";

    public static final String HOUR = "hour";

    public static final String MINUTE = "minute";

    public static final String SECOND = "second";

    public static final String WEEK = "week";

    public static final String DATETIME_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";

    public static final long hper = 60 * 60 * 1000;

    public static final long mper = 60 * 1000;

    public static final long sper = 1000;

    private DateUtil() {
    }

    /**
     * <p>
     * Description: 时间以及时间格式相关的处理功能
     * </p>
     * <p>
     * <p>
     * private static Logger logger = Logger.getLogger(DateUtil.class);
     * <p>
     * /** 得到应用服务器当前日期，以默认格式显示。
     *
     * @return
     */
    public static String getFormatedDate() {
        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期时间，以默认格式显示。
     *
     * @return
     */
    public static String getFormatedDateTime() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期时间，以默认格式显示。
     *
     * @return
     */
    public static String getFormatedDateTime(String format) {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(format);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器的当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 得到应用服务器的当前时间，毫秒。
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 得到应用服务器当前日期 按照指定的格式返回。
     *
     * @param pattern 格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
     * @return
     */
    public static String formatDate(String pattern) {

        Date date = new Date();
        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
        String str = dateFormator.format(date);

        return str;
    }

    /**
     * 转换输入日期 按照指定的格式返回。
     *
     * @param date
     * @param pattern 格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
     * @return
     */
    public static String formatDate(Date date, String pattern) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
        String str = dateFormator.format(date);

        return str;
    }

    /**
     * 转换输入日期 按照指定的格式返回。
     *
     * @param date
     * @param pattern 格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
     * @param loc locale
     * @return
     */
    public static String formatDate(Date date, String pattern, Locale loc) {
        if (pattern == null || date == null) {
            return "";
        }
        String newDate = "";
        if (loc == null) {
            loc = Locale.getDefault();
        }
        // if (date != null) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
        newDate = sdf.format(date);
        // }
        return newDate;
    }

    /**
     * 将字符时间从一个格式转换成另一个格式。时间的格式，最好通过系统常量定义。 如： String dateStr = "2006-10-9 12:09:08";
     * DateFormatUtils.convertDateFormat(dateStr, CapConstants.DATE_TIME_FORMAT, CapConstants.DATE_FORMAT_8);
     *
     * @param patternFrom 格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
     * @param patternTo 格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
     * @return
     */
    public static String convertDateFormat(String dateStr, String patternFrom, String patternTo) {

        if (dateStr == null || patternFrom == null || patternTo == null) {
            return "";
        }
        String newDate = "";
        try {
            Date dateFrom = parseStrToDate(dateStr, patternFrom);
            newDate = formatDate(dateFrom, patternTo);
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e.getStackTrace());
        }

        return newDate;
    }

    /**
     * 将时间串按照默认格式CapConstants.DATE_FORMAT，格式化成Date。
     *
     * @param dateStr
     * @return
     */
    public static Date parseStrToDate(String dateStr) {

        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT);

        Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

        return tDate;
    }

    public static String parseDateStrToDateTimeStr(String dateStr) {

        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT);

        Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
        return formatDate(tDate, DateUtil.DATE_TIME_FORMAT);

    }

    /**
     * 将时间串按照默认格式CapConstants.DATE_FORMAT，格式化成Date。
     *
     * @param dateStr
     * @return
     */
    public static Calendar parseStrToCalendar(String dateStr) {

        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT);

        Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

        Locale loc = Locale.getDefault();
        Calendar cal = new GregorianCalendar(loc);
        cal.setTime(tDate);

        return cal;
    }

    public static Calendar parseStrToCalendar(String dateStr, String pattern) {

        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

        Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

        Locale loc = Locale.getDefault();
        Calendar cal = new GregorianCalendar(loc);
        cal.setTime(tDate);

        return cal;
    }

    /**
     * 将时间串按照默认格式CapConstants.DATE_TIME_FORMAT，格式化成Date。
     *
     * @param dateStr
     * @return
     */
    public static Date parseStrToDateTime(String dateStr) {

        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);

        Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

        return tDate;
    }

    /**
     * 将时间串按照指定格式，格式化成Date。
     *
     * @param
     * @param pattern 格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
     * @return
     */

    public static Date parseStrToDate(String dateStr, String pattern) {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

        Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

        return tDate;
    }

    /**
     * 按时间格式相加： 输入要相加的时间基点（字符串型或时间类型），相加的时长（整型），格式（"year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周）
     * 输出相加后的时间（字符串型或时间类型）
     *
     * @param dateStr
     * @param pattern
     * @param step
     * @param type "year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周 通过常量DateFormatUtils.YEAR等来设置.
     * @return
     */
    public static String addDate(String dateStr, String pattern, int step, String type) {
        if (dateStr == null) {
            return dateStr;
        }
        else {
            Date date1 = DateUtil.parseStrToDate(dateStr, pattern);

            Locale loc = Locale.getDefault();
            Calendar cal = new GregorianCalendar(loc);
            cal.setTime(date1);

            if (DateUtil.WEEK.equals(type)) {

                cal.add(Calendar.WEEK_OF_MONTH, step);

            }
            else if (DateUtil.YEAR.equals(type)) {

                cal.add(Calendar.YEAR, step);

            }
            else if (DateUtil.MONTH.equals(type)) {

                cal.add(Calendar.MONTH, step);

            }
            else if (DateUtil.DAY.equals(type)) {

                cal.add(Calendar.DAY_OF_MONTH, step);

            }
            else if (DateUtil.HOUR.equals(type)) {

                cal.add(Calendar.HOUR, step);

            }
            else if (DateUtil.MINUTE.equals(type)) {

                cal.add(Calendar.MINUTE, step);

            }
            else if (DateUtil.SECOND.equals(type)) {

                cal.add(Calendar.SECOND, step);

            }

            return DateUtil.formatDate(cal.getTime(), pattern);
        }
    }

    /**
     * 按时间格式相减： 输入要相加的时间基点（字符串型或时间类型），相加的时长（整型），格式（"year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周）
     * 输出相加后的时间（字符串型或时间类型）
     *
     * @param dateStr
     * @param pattern
     * @param step
     * @param type "year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周
     * @return
     */
    public static String minusDate(String dateStr, String pattern, int step, String type) {

        return addDate(dateStr, pattern, (0 - step), type);

    }

    /**
     * 日期增加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDay(Date date, int days) {
        if (date == null) {
            return date;
        }
        else {
            Locale loc = Locale.getDefault();
            Calendar cal = new GregorianCalendar(loc);
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, days);
            return cal.getTime();
        }
    }

    public static int getDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        else {
            return (int) ((date2.getTime() - date1.getTime()) / 0x5265c00L);
        }
    }

    /**
     * 日期比较大小
     *
     * @param dateStr1
     * @param dateStr2
     * @param pattern
     * @return
     */
    public static int compareDate(String dateStr1, String dateStr2, String pattern) {

        Date date1 = DateUtil.parseStrToDate(dateStr1, pattern);
        Date date2 = DateUtil.parseStrToDate(dateStr2, pattern);

        return date1.compareTo(date2);

    }

    /**
     * @param dateStr
     * @param pattern
     * @return
     */
    public static String getFirstDayInMonth(String dateStr, String pattern) {
        Calendar cal = DateUtil.parseStrToCalendar(dateStr);
        // int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        cal.add(Calendar.DAY_OF_MONTH, (1 - day));

        return DateUtil.formatDate(cal.getTime(), pattern);
    }

    /**
     * @param dateStr
     * @param pattern
     * @return
     */
    public static String getLastDayInMonth(String dateStr, String pattern) {
        Calendar cal = DateUtil.parseStrToCalendar(dateStr);
        // int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int maxDayInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int step = maxDayInMonth - day;

        cal.add(Calendar.DAY_OF_MONTH, step);

        return DateUtil.formatDate(cal.getTime(), pattern);
    }

    /**
     * @param dateStr
     * @param pattern
     * @return
     */
    public static String getFirstDayInWeek(String dateStr, String pattern) {
        Calendar cal = DateUtil.parseStrToCalendar(dateStr);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DAY_OF_MONTH, (1 - day));

        return DateUtil.formatDate(cal.getTime(), pattern);
    }

    /**
     * @param dateStr
     * @param pattern
     * @return
     */
    public static String getLastDayInWeek(String dateStr, String pattern) {
        Calendar cal = DateUtil.parseStrToCalendar(dateStr);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DAY_OF_MONTH, (6 - day));

        return DateUtil.formatDate(cal.getTime(), pattern);
    }

    public static long calendarDayPlus(String dateStr1, String dateStr2) {

        if (dateStr1 == null || dateStr2 == null || "".equals(dateStr1) || "".equals(dateStr2)) {
            return 0;
        }
        Date date1 = DateUtil.parseStrToDate(dateStr1);
        Date date2 = DateUtil.parseStrToDate(dateStr2);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();
        long day = (t2 - t1) / (1000 * 60 * 60 * 24);
        return day;
    }

    /**
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int calendarPlus(String dateStr1, String dateStr2) {

        if (dateStr1 == null || dateStr2 == null || "".equals(dateStr1) || "".equals(dateStr2)) {
            return 0;
        }

        Calendar cal1 = DateUtil.parseStrToCalendar(dateStr1);

        Calendar cal2 = DateUtil.parseStrToCalendar(dateStr2);

        int dataStr1Year = cal1.get(Calendar.YEAR);
        int dataStr2Year = cal2.get(Calendar.YEAR);

        int dataStr1Month = cal1.get(Calendar.MONTH);
        int dataStr2Month = cal2.get(Calendar.MONTH);

        return ((dataStr2Year * 12 + dataStr2Month + 1) - (dataStr1Year * 12 + dataStr1Month));

    }

    /**
     * 得到应用服务器当前日期，以8位日期显示。
     *
     * @return
     */
    public static String getDate() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_8);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期，以四位日期显示。
     *
     * @return
     */
    public static String getYear() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_TEAR);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期六位字符。
     *
     * @return
     */
    public static String getNowDate() {
        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期，以六位日期显示。
     *
     * @return
     */
    public static String getMonth() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat("yyyyMM");
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期，以8位日期显示。
     *
     * @return
     */
    public static String getDateTime() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT_14);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期，以8位日期显示。
     *
     * @return
     */
    public static String getDateTime2() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_6_);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期，以DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss"; 位日期显示。
     *
     * @return
     */
    public static String getVSOPDateTime14() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT_14);
        return dateFormator.format(date);

    }

    /**
     * 得到应用服务器当前日期，以DATE_FORMAT_8 = "yyyyMMdd"; 位日期显示。
     *
     * @return
     */
    public static String getVSOPDate8() {

        Date date = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_8);
        return dateFormator.format(date);

    }

    public static java.sql.Timestamp getTimestamp(String dateStr) {
        DateFormat df = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);
        java.sql.Timestamp ts = null;
        try {
            Date date = df.parse(dateStr);
            ts = new java.sql.Timestamp(date.getTime());
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e.getStackTrace());
        }
        return ts;
    }

    /**
     * 将Date转换成统一的日期时间格式文本。
     *
     * @return
     */
    public static String getFormatedDateTime(java.sql.Date date) {
        if (null == date) {
            return "";
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT_14);
        return dateFormator.format(new java.sql.Date(date.getTime()));
    }

    /**
     * 通过统一的格式将文本转换成Date。输入为日期和时间。
     *
     * @return
     */
    public static java.sql.Date parseDateTime(String sdate) {
        if (null == sdate || "".equals(sdate)) {
            return null;
        }

        // 只有日期类型
        if (sdate.length() <= 10) {
            return parseDate(sdate);
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT_14);

        Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
        if (tDate == null) {
            return null;
        }

        return new java.sql.Date(tDate.getTime());

    }

    /**
     * 通过统一的格式将文本转换成Date。输入为日期。
     *
     * @return
     */
    public static java.sql.Date parseDate(String sdate) {
        if (null == sdate || "".equals(sdate)) {
            return null;
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT);

        Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
        if (tDate == null) {
            return null;
        }

        return new java.sql.Date(tDate.getTime());
    }

    /**
     * 获取14位系统时间
     *
     * @return
     */
    public static String getDateString14() {
        SimpleDateFormat dateFormator = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormator.format(new java.sql.Date(System.currentTimeMillis()));
    }

    /**
     * 日期简单正则校验
     *
     * @return
     */
    public static boolean checkDateValid(String dateStr) {
        boolean flag = false;
        String reg = "(([0-9]{1,4})(0?[1-9]|1[0-2])(0?[1-9]|1[0-9]|2[0-9]|3[0-1]))";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(dateStr);
        while (matcher.find()) {
            flag = true;
            //// System.out.println(matcher.group());
        }
        return flag;
    }

    public static boolean isValidDate(String dataStr, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = dateFormat.parse(dataStr);
            return dataStr.equals(DateUtil.formatDate(date, pattern));
            /*
             * if (dataStr.equals(DateUtil.formatDate(date, pattern))) { return true; } else { return false; }
             */
        }
        catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static Date addMonth(Date date, int months) {
        if (date == null) {
            date = new Date();
        }

        Locale loc = Locale.getDefault();
        Calendar cal = new GregorianCalendar(loc);
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 返回昨天的日期
     *
     * @param pattern 日期的格式
     * @return
     */
    public static String getYesterday(String pattern) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = c.getTime();
        return sdf.format(date);

    }

    /**
     * 返回昨天的日期
     *
     * @return
     */
    public static Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        Date date = c.getTime();
        return date;

    }

    /**
     * 返回指定日期的前一天的日期
     *
     * @param today
     * @param pattern
     * @return
     */
    public static String getYesterday(String today, String pattern) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date2;
        try {
            date2 = s.parse(today);
            c.setTime(date2);
        }
        catch (ParseException e) {
            logger.error("返回指定日期的前一天的日期", e);
        }
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = c.getTime();
        return sdf.format(date);
    }

    /**
     * 返回指定日期的前一天的日期
     *
     * @param today
     * @param pattern
     * @return
     */
    public static String getYesterdayFromPattern(String today, String pattern) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        Date date2;
        try {
            date2 = s.parse(today);
            c.setTime(date2);
        }
        catch (ParseException e) {
            logger.error("返回指定日期的前一天的日期", e);
        }
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = c.getTime();
        return sdf.format(date);
    }

    /**
     * 获取前N个小时
     *
     * @param today
     * @param pattern
     * @return
     * @author Jimmy
     */
    public static String getHour(String today, int n, String pattern) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date;
        try {
            date = formatDate.parse(today);
            calendar.setTime(date);
        }
        catch (ParseException e) {
            logger.error("获取前N个小时出错", e);
        }
        calendar.add(Calendar.HOUR_OF_DAY, -n);
        Date startDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String startHour = sdf.format(startDate);
        return startHour;
    }

    /**
     * 获取当前月份的前N个月的月份
     *
     * @param monthStr 当前月份（比如201305）
     * @param n 前n个月
     * @return YYYYMM 比如201301
     * @throws ParseException
     */
    public static String getMonth(String monthStr, int n) {
        SimpleDateFormat s;
        if (monthStr.length() <= 10) {
            s = new SimpleDateFormat(DATE_FORMAT);
        }
        else {
            s = new SimpleDateFormat(DATE_TIME_FORMAT);
        }
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatDate = new SimpleDateFormat(DATE_FORMAT_6);
        Date date;
        String startMonth = null;
        try {
            date = s.parse(monthStr);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -n);
            Date startDate = calendar.getTime();
            startMonth = formatDate.format(startDate);
        }
        catch (ParseException e) {
            logger.error("获取当前月份的前N个月的月份出错", e);
        }
        return startMonth;
    }

    /**
     * 获取当前月份的前N个月的月份
     *
     * @param monthStr 当前月份（比如201305）
     * @param n 前n个月
     * @return YYYYMM 比如201301
     * @throws ParseException
     */
    public static String getMonth(String monthStr, int n, String format) throws ParseException {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        Date date = formatDate.parse(monthStr);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -n);
        Date startDate = calendar.getTime();
        String startMonth = formatDate.format(startDate);
        return startMonth;
    }

    /**
     * 获取当前月份的前N个月的日期
     *
     * @param dayStr 当前月份（比如20130531）
     * @param n 前n日
     * @return YYYYMMdd 比如20130101
     * @throws ParseException
     */
    public static String getDay(String dayStr, int n, String format) throws ParseException {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        Date date = formatDate.parse(dayStr);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -n);
        Date startDate = calendar.getTime();
        String startMonth = formatDate.format(startDate);
        return startMonth;
    }

    /**
     * 获取当前周是一个月中的第几周，一周中跨两个月的，本周当作下个月的第一周
     *
     * @return
     */
    public static Map<Integer, String> getWeekInfo() {
        Map<Integer, String> map = new HashMap<Integer, String>();

        // 取本周周五的日期
        Calendar friday = Calendar.getInstance();
        friday.setFirstDayOfWeek(Calendar.MONDAY);
        friday.setMinimalDaysInFirstWeek(7);
        friday.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        // 获取本周周五在一个月中的第几周
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        map.put(Calendar.MONTH, format.format(friday.getTime()));
        map.put(Calendar.DAY_OF_WEEK_IN_MONTH, String.valueOf(friday.get(Calendar.DAY_OF_WEEK_IN_MONTH)));

        return map;
    }

    /**
     * 获取当天的小时并转为int类型
     *
     * @return
     */
    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("HH");
        String hour = format.format(calendar.getTime());
        return Integer.parseInt(hour);
    }

    public static String current(String pattern) {
        return formatDate(current(), pattern);
    }

    public static Date current() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * 获取两个日期之间的随机日期(开区间)
     *
     * @param begin
     * @param end
     * @return
     */
    public static Date getRandomDate(Date begin, Date end) {

        // 随机日期
        Date randomDate = new Date();

        long beginTime = begin.getTime();
        long endTime = end.getTime();
        // 随机时间范围不对的时候返回null
        if (beginTime >= endTime) {
            return null;
        }

        long randomTime = beginTime + (long) (Math.random() * (endTime - beginTime));

        // 过滤等于时间范围两头的情况
        if (randomTime == beginTime || randomTime == endTime) {
            return getRandomDate(begin, end);
        }

        randomDate.setTime(randomTime);

        return randomDate;
    }


    public static Date parse(String source) {
        DateFormat format = new SimpleDateFormat(DATETIME_FORMAT_19);
        Date date = null;
        try {
            date = format.parse(source);
        }
        catch (ParseException e) {
            logger.info(e.getMessage(), e.getStackTrace());
        }
        return date;
    }



    public static SimpleDateFormat getDateFormat(String format) {
        /*
         * SimpleDateFormat sdf = (SimpleDateFormat) aDateFormateMap.get(format); if (sdf == null) { sdf = new
         * SimpleDateFormat(format); aDateFormateMap.put(format, sdf); }
         */
        return new SimpleDateFormat(format);
    }

    public static String getSevenDaysBefore() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();

        // 过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }

    public static String getOneMnthBefore() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();

        // 过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }

    public static String getHalfYearBefore() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();

        // 过去半年
        c.setTime(new Date());
        c.add(Calendar.MONTH, -6);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }

    /**
     * 获取当天早上8点到晚上20点的周期，半小时一周期
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    public static List<Long> getHalfHourPeriod(Date begin, Date end) {
        List<Long> dateList = new ArrayList<Long>();
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calBegin.setTime(begin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calEnd.setTime(end);
        // 测试此日期是否在指定日期之后
        String beginDate = formatDate(calBegin.getTime(), "yyyyMMddHH");
        while (end.compareTo(calBegin.getTime()) >= 0) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            beginDate = formatDate(calBegin.getTime(), "yyyyMMddHH");
            String endTime = formatDate(calEnd.getTime(), "yyyyMMddHHmmss");
            int beginHour = Integer.parseInt(beginDate.substring(8, 10));
            int endHour = Integer.parseInt(endTime.substring(8, 10));
            int endMinute = Integer.parseInt(endTime.substring(10, 12));
            if (beginHour == 20) {
                dateList.add(Long.parseLong(beginDate + "00"));
            }
            else {
                dateList.add(Long.parseLong(beginDate + "00"));
                /*
                 * if (beginHour == endHour && endMinute < 30) { } else { dateList.add(Long.parseLong(beginDate +
                 * "30")); }
                 */
                if (!(beginHour == endHour && endMinute < 30)) {
                    dateList.add(Long.parseLong(beginDate + "30"));
                }
            }
            calBegin.add(Calendar.HOUR_OF_DAY, 1);
        }
        return dateList;
    }

    /**
     * 根据开始日期和结束日期查询，一小时一周期
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    public static List<Long> getHourPeriod(Date begin, Date end) {
        List<Long> dateList = new ArrayList<Long>();
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calBegin.setTime(begin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calEnd.setTime(end);
        // 测试此日期是否在指定日期之后
        while (end.compareTo(calBegin.getTime()) >= 0) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            String dates = formatDate(calBegin.getTime(), "yyyyMMddHH") + "00";
            dateList.add(Long.parseLong(dates));
            calBegin.add(Calendar.HOUR_OF_DAY, 1);
        }
        return dateList;
    }

    /**
     * 根据开始日期和结束日期查询，一天一周期
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    public static List<String> getDayPeriod(Date begin, Date end) {
        List<String> dateList = new ArrayList<String>();
        String begins = formatDate(begin, "yyyyMMdd");
        dateList.add(begins);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calBegin.setTime(begin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calEnd.setTime(end);
        // 测试此日期是否在指定日期之后
        while (calEnd.getTime().after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            calBegin.add(Calendar.DAY_OF_WEEK, 1);
            String dates = formatDate(calBegin.getTime(), "yyyyMMdd");
            dateList.add(dates);
        }
        return dateList;
    }

    /**
     * 把毫秒数转换成时分秒
     * 
     * @param millis
     * @return
     */
    public static String millisToStringShort(long millis) {
        StringBuilder strBuilder = new StringBuilder();
        long temp = millis;
        if (temp / hper > 0) {
            strBuilder.append(temp / hper).append(":");
        }
        else {
            strBuilder.append("0").append(":");
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            strBuilder.append(temp / mper).append(":");
        }
        else {
            strBuilder.append("0").append(":");
        }
        temp = temp % mper;
        if (temp / sper > 0) {
            strBuilder.append(temp / sper);
        }
        else {
            strBuilder.append("0");
        }
        return strBuilder.toString();
    }

    public static final Date addHour(Date d, int hour) {
        if (d == null || hour == 0)
            return d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static final String formate(Date date, String format) {
        if (date == null)
            return "";
        format = StringUtils.isBlank(format) ? "yyyy-MM-dd" : format;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

   /* public static void main(String[] args) {
        List<String> monthBetween = getMonthBetween("2020-10", "2021-04");
        for (String month : monthBetween) {
            //结果 2017/03/01
            System.out.println(Integer.valueOf(month.replace("-", "")));
        }
    }*/
    /*
     * public static void main(String[] args) { SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);
     * String times = ""; try { Date date = sdf.parse("2019-04-10 21:22:22"); times = DateUtil.getFormatedDateTime(); }
     * catch (Exception e) { e.printStackTrace(); } //今日的小时分钟 String dayStartTimes = times.substring(0, 10) +
     * " 08:00:00"; String dayEndTimes = times.substring(0, 16) + ":00"; //到分钟 int hour =
     * Integer.parseInt(times.substring(11, 13)); if (hour >= 20) { dayEndTimes = times.substring(0, 10) + " 20:00:00";
     * } //今日的小时分钟 List listD = DateUtil.getHalfHourPeriod(DateUtil.parseStrToDateTime(dayStartTimes),
     * DateUtil.parseStrToDateTime(dayEndTimes)); if (listD != null && listD.size() > 0) { for (int i = 0; i <
     * listD.size(); i++) { System.out.println(listD.get(i)); } } // times = DateUtil.getFormatedDateTime(); //今日的小时分钟
     * dayStartTimes = times.substring(0, 10) + " 08:00:00"; dayEndTimes = times.substring(0, 16) + ":00"; //到分钟 hour =
     * Integer.parseInt(times.substring(11, 13)); if (hour >= 20) { dayEndTimes = times.substring(0, 10) + " 20:00:00";
     * } List list = DateUtil.getHourPeriod(DateUtil.parseStrToDateTime(dayStartTimes),
     * DateUtil.parseStrToDateTime(dayEndTimes)); if (list != null && list.size() > 0) { for (int i = 0; i <
     * list.size(); i++) { System.out.println(list.get(i)); System.out.println(list.get(i)); } } }
     */
}
