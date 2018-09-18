package com.zzq.zzq.common;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String Format_Date = "yyyy-MM-dd";
    public static final String Format_Time = "HH:mm:ss";
    public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
    }

    public static final String format(Object date) {
        return format(date, "yyyy-MM-dd");
    }

    public static final String format(Object date, String pattern) {
        if (date == null) {
            return null;
        } else {
            return pattern == null ? format(date) : (new SimpleDateFormat(pattern)).format(date);
        }
    }

    public static final String getDate() {
        return format(new Date());
    }

    public static final String getDateTime() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static final String getDateTime(String pattern) {
        return format(new Date(), pattern);
    }

    public static final Date addDate(Date date, int field, int amount) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(field, amount);
            return calendar.getTime();
        }
    }

    public static final Date stringToDate(String date) {
        if (date == null) {
            return null;
        } else {
            String separator = String.valueOf(date.charAt(4));
            String pattern = "yyyyMMdd";
            if (!separator.matches("\\d*")) {
                pattern = "yyyy" + separator + "MM" + separator + "dd";
                if (date.length() < 10) {
                    pattern = "yyyy" + separator + "M" + separator + "d";
                }
            } else if (date.length() < 8) {
                pattern = "yyyyMd";
            }

            pattern = pattern + " HH:mm:ss.SSS";
            pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));

            try {
                return (new SimpleDateFormat(pattern)).parse(date);
            } catch (ParseException var4) {
                return null;
            }
        }
    }

    public static final Integer getDayBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(11, 0);
        start.set(12, 0);
        start.set(13, 0);
        start.set(14, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(11, 0);
        end.set(12, 0);
        end.set(13, 0);
        end.set(14, 0);
        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (n / 86400000L);
    }

    public static final Integer getMonthBetween(Date startDate, Date endDate) {
        if (startDate != null && endDate != null && startDate.before(endDate)) {
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            int year1 = start.get(1);
            int year2 = end.get(1);
            int month1 = start.get(2);
            int month2 = end.get(2);
            int n = (year2 - year1) * 12;
            n = n + month2 - month1;
            return n;
        } else {
            return null;
        }
    }

    public static final Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
        if (startDate != null && endDate != null && startDate.before(endDate)) {
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            int year1 = start.get(1);
            int year2 = end.get(1);
            int month1 = start.get(2);
            int month2 = end.get(2);
            int n = (year2 - year1) * 12;
            n = n + month2 - month1;
            int day1 = start.get(5);
            int day2 = end.get(5);
            if (day1 <= day2) {
                ++n;
            }

            return n;
        } else {
            return null;
        }
    }

    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            smdate = sdf.parse(sdf.format(smdate));
        } catch (ParseException var11) {
            var11.printStackTrace();
        }

        try {
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException var10) {
            var10.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / 86400000L;
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getCurrentDate() {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String getCurrentTime() {
        return (new SimpleDateFormat("HH:mm:ss")).format(new Date());
    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String getCurrentDateTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        return getCurrentDateTime(format);
    }

    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(7);
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(7);
    }

    public static int getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(5);
    }

    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public static int getMaxDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(5);
    }

    public static String getFirstDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        cal.set(5, 1);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }

    public static int getDayOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(6);
    }

    public static int getDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(6);
    }

    public static int getDayOfWeek(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(7);
    }

    public static int getDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(5);
    }

    public static int getDayOfYear(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(6);
    }

    public static String getCurrentDateTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String toString(Date date) {
        return date == null ? "" : (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    public static String toDateTimeString(Date date) {
        return date == null ? "" : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    public static String toString(Date date, String format) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat t = new SimpleDateFormat(format);
            return t.format(date);
        }
    }

    public static String toTimeString(Date date) {
        return date == null ? "" : (new SimpleDateFormat("HH:mm:ss")).format(date);
    }

    public static int compare(String date1, String date2) {
        return compare(date1, date2, "yyyy-MM-dd");
    }

    public static int compareTime(String time1, String time2) {
        return compareTime(time1, time2, "HH:mm:ss");
    }

    public static int compare(String date1, String date2, String format) {
        Date d1 = parse(date1, format);
        Date d2 = parse(date2, format);
        return d1.compareTo(d2);
    }

    public static int compare(Date date1, Date date2, String format) {
        return compare(toString(date1, format), toString(date2, format), format);
    }

    public static int compare(Date date1, Date date2) {
        return compare(toString(date1, "yyyy-MM-dd HH:mm:ss"), toString(date2, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
    }

    public static int compareTime(String time1, String time2, String format) {
        String[] arr1 = time1.split(":");
        String[] arr2 = time2.split(":");
        if (arr1.length < 2) {
            throw new RuntimeException("错误的时间值:" + time1);
        } else if (arr2.length < 2) {
            throw new RuntimeException("错误的时间值:" + time2);
        } else {
            int h1 = Integer.parseInt(arr1[0]);
            int m1 = Integer.parseInt(arr1[1]);
            int h2 = Integer.parseInt(arr2[0]);
            int m2 = Integer.parseInt(arr2[1]);
            int s1 = 0;
            int s2 = 0;
            if (arr1.length == 3) {
                s1 = Integer.parseInt(arr1[2]);
            }

            if (arr2.length == 3) {
                s2 = Integer.parseInt(arr2[2]);
            }

            if (h1 >= 0 && h1 <= 23 && m1 >= 0 && m1 <= 59 && s1 >= 0 && s1 <= 59) {
                if (h2 >= 0 && h2 <= 23 && m2 >= 0 && m2 <= 59 && s2 >= 0 && s2 <= 59) {
                    if (h1 != h2) {
                        return h1 > h2 ? 1 : -1;
                    } else if (m1 == m2) {
                        if (s1 == s2) {
                            return 0;
                        } else {
                            return s1 > s2 ? 1 : -1;
                        }
                    } else {
                        return m1 > m2 ? 1 : -1;
                    }
                } else {
                    throw new RuntimeException("错误的时间值:" + time2);
                }
            } else {
                throw new RuntimeException("错误的时间值:" + time1);
            }
        }
    }

    public static boolean isTime(String time) {
        String[] arr = time.split(":");
        if (arr.length < 2) {
            return false;
        } else {
            try {
                int h = Integer.parseInt(arr[0]);
                int m = Integer.parseInt(arr[1]);
                int s = 0;
                if (arr.length == 3) {
                    s = Integer.parseInt(arr[2]);
                }

                return h >= 0 && h <= 23 && m >= 0 && m <= 59 && s >= 0 && s <= 59;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    public static boolean isValidDate(String str) {
        if (StringUtils.isEmpty(str)) {
            return true;
        } else {
            boolean convertSuccess = true;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                format.setLenient(false);
                format.parse(str);
            } catch (ParseException var4) {
                convertSuccess = false;
            }

            return convertSuccess;
        }
    }

    public static boolean isDate(String date) {
        String[] arr = date.split("-");
        if (arr.length < 3) {
            return false;
        } else {
            try {
                int y = Integer.parseInt(arr[0]);
                int m = Integer.parseInt(arr[1]);
                int d = Integer.parseInt(arr[2]);
                return y >= 0 && m <= 12 && m >= 0 && d >= 0 && d <= 31;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int t = cal.get(7);
        return t == 7 || t == 1;
    }

    public static boolean isWeekend(String str) {
        return isWeekend(parse(str));
    }

    public static Date parse(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            try {
                return (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
            } catch (ParseException var2) {
                return null;
            }
        }
    }

    public static Date parse(String str, String format) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            try {
                SimpleDateFormat t = new SimpleDateFormat(format);
                return t.parse(str);
            } catch (ParseException var3) {
                return null;
            }
        }
    }

    public static Date parseDateTime(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else if (str.length() <= 10) {
            return parse(str);
        } else {
            try {
                return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(str);
            } catch (ParseException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static Date parseDateTime(String str, String format) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            try {
                SimpleDateFormat t = new SimpleDateFormat(format);
                return t.parse(str);
            } catch (ParseException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static Date addMinute(Date date, int count) {
        return new Date(date.getTime() + 60000L * (long) count);
    }

    public static Date addHour(Date date, int count) {
        return new Date(date.getTime() + 3600000L * (long) count);
    }

    public static Date addDay(Date date, int count) {
        return new Date(date.getTime() + 86400000L * (long) count);
    }

    public static Date addWeek(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(3, count);
        return c.getTime();
    }

    public static Date addMonth(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, count);
        return c.getTime();
    }

    public static Date addYear(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(1, count);
        return c.getTime();
    }

    public static String toDisplayDateTime(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        } else {
            try {
                if (isDate(date)) {
                    return toDisplayDateTime(parse(date));
                } else {
                    SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d = t.parse(date);
                    return toDisplayDateTime(d);
                }
            } catch (ParseException var3) {
                var3.printStackTrace();
                return "不是标准格式时间!";
            }
        }
    }

    public static String toDisplayDateTime(Date date) {
        long minite = (System.currentTimeMillis() - date.getTime()) / 60000L;
        if (minite < 60L) {
            return toString(date, "MM-dd") + " " + minite + "分钟前";
        } else {
            return minite < 1440L ? toString(date, "MM-dd") + " " + minite / 60L + "小时前" : toString(date, "MM-dd") + " " + minite / 1440L + "天前";
        }
    }

    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return getYearFirst(currentYear);
    }

    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return getYearLast(currentYear);
    }

    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static Date getMonthBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date getMonthEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    }

}
