package com.totoro.iSecKill.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String TAG = DateUtil.class.getSimpleName();
	
	private static final Calendar mCalendar = Calendar.getInstance();
	private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat hm  = new SimpleDateFormat("HH:mm");

	/**
	 * 日期计算
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addDayCount(Date date, int count) {
		return add(date, Calendar.DATE, count);
	}
	
	public static String addDays(Date date, int count) {
		Date d = addDayCount(date, count);
		return ymd.format(d);
	}
	
	public static Date add(Date date, int field, int value) {
		mCalendar.setTime(date);
		mCalendar.add(field, value);
		return mCalendar.getTime();
	}
	
	public static String addFieldValue(Date date, int field, int value) {
		return ymd.format(add(date, field, value));
	}

	public static String formatYMD(Date date) {
		return ymd.format(date);
	}
	
	public static String formatHMS(Date date) {
		return hms.format(date);
	}
	
	public static String formatYMDHMS(Date date) {
		return ymdhms.format(date);
	}

    public static String formatHM(Date date) {
        return hm.format(date);
    }

    public static String formatCustomDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
	
	public static Date parseStringToYMD(String date) {
		try {
			return ymd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date parseStringToYMDHMS(String date) {
		try {
			return ymdhms.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date formatStartDate(Date date) {
		mCalendar.setTime(date);
		mCalendar.set(Calendar.HOUR_OF_DAY, 0);
		mCalendar.set(Calendar.MINUTE, 0);
		mCalendar.set(Calendar.SECOND, 0);
		mCalendar.set(Calendar.MILLISECOND, 0);
		return mCalendar.getTime();
	}
	
	public static Date formatEndDate(Date date) {
		mCalendar.setTime(date);
		mCalendar.set(Calendar.HOUR_OF_DAY, 23);
		mCalendar.set(Calendar.MINUTE, 59);
		mCalendar.set(Calendar.SECOND, 59);
		mCalendar.set(Calendar.MILLISECOND, 999);
		return mCalendar.getTime();
	}

    public static Date getInitDate() {
        mCalendar.setTime(parseStringToYMD("1970-01-01"));
        return formatStartDate(mCalendar.getTime());
    }

    public static Date setMeasurePointTime(int hour, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        return mCalendar.getTime();
    }

    public static void setTime(Date date) {
        mCalendar.setTime(date);
    }

    public static int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        return mCalendar.get(Calendar.MONTH);
    }

    public static int getDayOfMonth() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getYear(Date date) {
        setTime(date);
        return getYear();
    }

    public static int getMonth(Date date) {
        setTime(date);
        return getMonth();
    }

    public static int getDayOfMonth(Date date) {
        setTime(date);
        return getDayOfMonth();
    }

    public static int getHourOfDay(Date date) {
        setTime(date);
        return mCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Date setYMD(int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return mCalendar.getTime();
    }

}
