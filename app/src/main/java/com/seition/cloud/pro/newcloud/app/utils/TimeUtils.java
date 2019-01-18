package com.seition.cloud.pro.newcloud.app.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    /**
     * 把毫秒值格式化为时：分：秒
     *
     * @param millisecond 毫秒
     * @return 24小时制时：分：秒
     */
    public static String MyFormatTime(long millisecond) {
        Date date = new Date(millisecond/* - TimeZone.getDefault().getRawOffset()*/);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 时:分:秒解析
     *
     * @param millisecond
     * @return
     */
    public static String MyFormatTime11(long millisecond) {
        StringBuilder sb = new StringBuilder();
        if (millisecond > 3600) {
            long h = millisecond / 3600;
            millisecond %= 3600;
            if (h < 10) sb.append("0");
            sb.append(h);
        } else sb.append("00");
        sb.append(":");
        if (millisecond > 60) {
            long m = millisecond / 60;
            millisecond %= 60;
            if (m < 10) sb.append("0");
            sb.append(m);
        } else sb.append("00");
        sb.append(":");
        if (millisecond < 10) sb.append("0");
        sb.append(millisecond);
        return sb.toString();
    }

    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return
     */
    public static String dateDiff(String endTime) {
        String strTime = null;
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = sd.format(curDate);
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(str).getTime() - sd.parse(endTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            if (day >= 3)
                strTime = endTime;
            else if (day >= 1) {
                strTime = day + "天前";
            } else {
                if (hour >= 1) {
                    strTime = /*day + "天" +*/ hour + "时前" /*+ min + "分"*/;

                } else {
                    if (sec >= 1) {
                        strTime =/* day + "天" + hour + "时" +*/ min + "分" + sec + "秒前";
                    } else {
                        strTime = "刚刚";
                    }
                }
            }
            return strTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String timeLongToString(long millisecond) {
        if (millisecond < 60)
            return millisecond + "秒";
        else if (millisecond < 60 * 60)
            return millisecond / 60 + "分" + (millisecond - millisecond / 60 * 60 == 0 ? "" : timeLongToString(millisecond - millisecond / 60 * 60));
        else if (millisecond < 60 * 60 * 24)
            return millisecond / (60 * 60) + "时" + (millisecond - millisecond / (60 * 60) * 60 * 60 == 0 ? "" : timeLongToString(millisecond - millisecond / (60 * 60) * 60 * 60));
        else
            return millisecond / (60 * 60 * 24) + "天" + (millisecond - millisecond / (60 * 60 * 24) * 60 * 60 * 24 == 0 ? "" : timeLongToString(millisecond - millisecond / (60 * 60 * 24) * 60 * 60 * 24));
    }

    public static String MyFormatTime2(long millisecond) {
        Date date = new Date(millisecond /*- TimeZone.getDefault().getRawOffset()*/);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH时mm分ss秒");
        return simpleDateFormat.format(date);
    }

    public static String MyFormatTime3(long millisecond) {
        Date date = new Date(millisecond/* - TimeZone.getDefault().getRawOffset()*/);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String MyFormatTime4(long millisecond) {
        Date date = new Date(millisecond/* - TimeZone.getDefault().getRawOffset()*/);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy.MM.dd");
        return simpleDateFormat.format(date);
    }

    public static String MyFormatTime5(long millisecond) {
        Date date = new Date(millisecond /*- TimeZone.getDefault().getRawOffset()*/);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "HH : mm");
        return simpleDateFormat.format(date);
    }

    private static String getHour(long millisecond) {
        return millisecond / (60 * 60) + "时";
    }

    private static String getMinute(long millisecond) {
        millisecond = millisecond - 60 * 60 * (millisecond / 60 / 60);
        return millisecond / 60 + "分";
    }

    private static String getSecond(long millisecond) {
        return millisecond % 60 % 60 + "秒";
    }

    public static String getHourMinuteSecond(long millisecond) {
        if (millisecond == 0) return "0时0分0秒";
        return getHour(millisecond) + getMinute(millisecond) + getSecond(millisecond);
    }


    public static String Format_TIME = "yyyy.MM.dd HH:mm";
    public static String Format_TIME1 = "yyyy-MM-dd HH-mm-ss";
    public static String Format_TIME2 = "yyyy年MM月dd日";
    public static String Format_TIME3 = "yyyy-MM-dd";
    public static String Format_TIME4 = "yyyy-MM-dd-HH-mm-ss";
    public static String Format_TIME5 = "yyyy-MM-dd HH:mm";
    public static String Format_TIME7 = "yyyy-MM-dd HH-mm";
    public static String Format_TIME8 = "yyyy-MM-dd HH:mm:ss";
    public static String Format_TIME9 = "MM月dd日 HH:mm";
    public static String Format_TIME10 = "HH:mm:ss";
    public static String Format_TIME11 = "HH-mm";

    // * 将时间戳转换为时间  格式为：时:分:秒 //////
    public static String stampToDate10(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format_TIME10, Locale.getDefault());
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    // * 将时间戳转换为时间  格式为：年-月-日 时：分 //////
    public static String stampToDate(String s, String format) {
        String res = "";
        try {
            long ctime = 0;
            if (s != null)
                ctime = Long.parseLong(s);
            if (ctime % 1000 != 0)
                ctime *= 1000;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = new Date(ctime);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * p判断 2个时间戳是否属于同一天
     *
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isTheSameDay(String data1, String data2) {
        if (stampToDate(data1, Format_TIME3).equals(stampToDate(data2, Format_TIME3)))
            return true;
        else
            return false;

    }

    //得到当前时间转换成String
    public static String getCurrentTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    public static String dataToStamp(String time, String format) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
//                  Log.d("--444444---", times);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    // * 将时间戳转换为16进制/////
    public static String stampToHex(int s) {
        String hexString;
        hexString = String.format("%08X", s);
        return hexString;
    }

    public static boolean compireTime(String currentTime, String time) {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        try {
            c1.setTime(df.parse(currentTime));
            c2.setTime(df.parse(time));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result < 0)
            return false;
        else
            return true;//c1>=c2
//		else if(result==0)
//		 	return true;
//		return false;
    }

    public static boolean compireTime2(String currentTime, String time) {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        try {
            c1.setTime(df.parse(currentTime));
            c2.setTime(df.parse(time));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result < 0)
            return false;
        else
            return true;//c1>=c2
//		else if(result==0)
//		 	return true;
//		return false;
    }


    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public static long stringToLong(String str) {
        return new Long(str);
    }
}
