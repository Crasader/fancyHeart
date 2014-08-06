package com.doteyplay.game.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.doteyplay.constant.DateTimeConstants;
import com.doteyplay.messageconstants.constants.ArrayStringConstants;
import com.doteyplay.messageconstants.constants.SystemMsgConstants;

/**
 * 
 */
public class DateTimeUtil {
	/**
	 * 
	 */
	private DateTimeUtil() {
	}

	/**
	 * һ���ӵĺ����ʾ
	 */
	public final static long SECOND = DateTimeConstants.SECOND;
	/**
	 * һ���ӵĺ����ʾ
	 */
	public final static long MINUTE = 60L * SECOND;
	/**
	 * һСʱ�ĺ����ʾ
	 */
	public final static long HOUR = 60L * MINUTE;
	/**
	 * һ��ĺ����ʾ
	 */
	public final static long DAY = 24L * HOUR;
	/**
	 * һ�ܵĺ����ʾ
	 */
	public final static long WEEK = 7L * DAY;

	/**
	 * һ���µĺ����ʾ(��30�����)
	 */
	public final static long MONTH = 30L * DAY;
	/**
	 * һ��ĺ����ʾ(��365�����)
	 */
	public final static long YEAR = 365L * DAY;

	/**
	 * �û��Ѻõ�����ʱ���ʽ:yyyy-MM-dd HH:mm:ss
	 */
	public final static String FRIENDLY_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * ���ع̶���ʽ����ȷ������
	 */
	private final static String ONLY_MINUTES_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * ���ع̶���ʽ����ȷ����
	 */
	public final static String TIME_FORMAT = "HH:mm:ss";

	/**
	 * ���ع̶���ʽ����ȷ����
	 */
	public final static String TIME_CN_FORMAT = SystemMsgConstants.DAY_TIME_MINUTE_SECOND
			.getMessage();

	/**
	 * ���ع̶���ʽ����ȷ������
	 */
	private final static String ONLY_TIME_FORMAT = "HH:mm";

	/**
	 * ����������
	 */
	private final static String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";

	/**
	 * ���ء����¡�����
	 */
	private final static String MONTH_DAY_FORMAT = SystemMsgConstants.DAY_TIME_MONTY_DAY
			.getMessage();

	/**
	 * ������������
	 */
	private final static String MONTH_DAY_TIME_FORMAT = "MM-dd HH:mm";

	/**
	 * �������ڸ�ʽ��yyyy��MM��dd�� HH:MM
	 */
	private final static String FRIENDLY_DATE_TIME_FORMAT_WITH_CHINESE = SystemMsgConstants.DAY_TIME_FORMAT_1
			.getMessage();

	private final static String[] WEEKLY_DETAIL = ArrayStringConstants.WEEKLY_DETAIL;

	/**
	 * ʱ���ȵ��Ѻø�ʽ��ʾ,��ȷ����, ��:39��4��5��11Сʱ55����7��; 1��; 1Сʱ.
	 * 
	 * @param t
	 *            ʱ���(��λ:����), ����������N/A;
	 * @param shortest
	 *            �Ƿ�ʹ������ַ���ʾ��ʽ, ��:39��; 5��; 36���;
	 * @param ceil
	 *            ��ֵΪtrueʱ������c����ceil(��2Сʱ30�������Ϊ3Сʱ),
	 *            ��ֵΪfalseʱ������c����floor(��2Сʱ30�������Ϊ2Сʱ)
	 * @return
	 */
	public static String buildTimeString(long t, boolean shortest, boolean ceil) {
		if (t < 0)
			return "N/A";

		if (t == 0)
			return "0" + SystemMsgConstants.DAY_TIME_SECOND.getMessage();
		else if (t < SECOND) {
			if (ceil)
				return "1" + SystemMsgConstants.DAY_TIME_SECOND.getMessage();
			else
				return "0" + SystemMsgConstants.DAY_TIME_SECOND.getMessage();
		}

		StringBuilder sb = new StringBuilder();
		buildTimeString(t, sb, shortest, ceil);
		return sb.toString();
	}

	/**
	 * ʱ���ȵ��Ѻø�ʽ��ʾ,��ȷ����, ��:39��4��5��11Сʱ55����7��; 1��12��10����; 1��; 1Сʱ.
	 * 
	 * @param t
	 *            ʱ���(��λ:����), ����������N/A;
	 * @return
	 */
	public static String buildTimeString(long t) {
		return buildTimeString(t, false, true);
	}

	/**
	 * ʱ���ȵ��Ѻø�ʽ��ʾ,��ȷ����
	 * 
	 * @param t
	 *            ʱ���(��λ:����)
	 * @param sb
	 * @param shortest
	 *            �Ƿ�ʹ������ַ���ʾ��ʽ
	 * @param ceil
	 *            ��ֵΪtrueʱ������c����ceil(��2Сʱ30�������Ϊ3Сʱ),
	 *            ��ֵΪfalseʱ������c����floor(��2Сʱ30�������Ϊ2Сʱ)
	 */
	private static void buildTimeString(long t, StringBuilder sb,
			boolean shortest, boolean ceil) {
		if (t >= YEAR) {
			long y = t / YEAR;
			t -= y * YEAR;
			if (t > 0 && shortest) {
				if (ceil)
					y++;
				t = 0;
			}

			sb.append(y).append(SystemMsgConstants.DAY_TIME_YEAR.getMessage());
			if (t > 0)
				buildTimeString(t, sb, shortest, ceil);
		} else if (t >= MONTH) {
			long m = t / MONTH;
			t -= m * MONTH;
			if (t > 0 && shortest) {
				if (ceil)
					m++;
				t = 0;
			}

			sb.append(m).append(SystemMsgConstants.DAY_TIME_MONTH.getMessage());
			if (t > 0)
				buildTimeString(t, sb, shortest, ceil);
		} else if (t >= DAY) {
			long d = t / DAY;
			t -= d * DAY;
			if (t > 0 && shortest) {
				if (ceil)
					d++;
				t = 0;
			}

			sb.append(d).append(SystemMsgConstants.DAY_TIME_TIAN.getMessage());
			if (t > 0)
				buildTimeString(t, sb, shortest, ceil);
		} else if (t >= HOUR) {
			long h = t / HOUR;
			t -= h * HOUR;
			if (t > 0 && shortest) {
				if (ceil)
					h++;
				t = 0;
			}
			sb.append(h).append(SystemMsgConstants.DAY_TIME_HOUR.getMessage());
			if (t > 0)
				buildTimeString(t, sb, shortest, ceil);
		} else if (t >= MINUTE) {
			long m = t / MINUTE;
			t -= m * MINUTE;
			if (t > 0 && shortest) {
				if (ceil)
					m++;
				t = 0;
			}
			sb.append(m).append(SystemMsgConstants.DAY_TIME_FZ.getMessage());
			if (t > 0)
				buildTimeString(t, sb, shortest, ceil);
		} else {
			long s = t / SECOND;
			t -= s * SECOND;
			if (t > 0 && ceil)
				s++;
			sb.append(s)
					.append(SystemMsgConstants.DAY_TIME_SECOND.getMessage());
		}
	}

	/**
	 * �����Ѻø�ʽ��-��-��-��-��
	 * 
	 * @param time
	 * @return
	 */
	public static String date2String(long time) {
		Date date = new Date();
		date.setTime(time);
		DateFormat format = new SimpleDateFormat(
				SystemMsgConstants.DAY_TIME_FORMAT_2.getMessage());
		return format.format(date);
	}

	/**
	 * ��ȡָ��ʱ���yyyy-MM-dd HH:mm:ss��ʾ��ʽ
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getDayString(long timestamp) {
		return new SimpleDateFormat(YEAR_MONTH_DAY_FORMAT).format(timestamp);
	}

	/**
	 * ��ȡָ��ʱ���MM-dd��ʾ��ʽ
	 * 
	 * @param timestamp
	 * @return String
	 */
	public static String getMonthDayString(long timestamp) {
		return new SimpleDateFormat(MONTH_DAY_FORMAT).format(timestamp);
	}

	/**
	 * ��ȡָ��ʱ��MM-dd HH:mm ��ʽ
	 * 
	 * @param timestamp
	 * @return String author luowei
	 */
	public static String getMonthDayTimeString(long timestamp) {
		return new SimpleDateFormat(MONTH_DAY_TIME_FORMAT).format(timestamp);
	}

	/**
	 * ��ȡָ��ʱ���yyyy-MM-dd HH:mm:ss��ʾ��ʽ
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getTimeString(long timestamp) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT).format(cal
				.getTimeInMillis());
	}

	/**
	 * ����ʱ���ʽΪyyyy-MM-dd HH:mm
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getTimeMinutes(long timestamp) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(ONLY_MINUTES_FORMAT).format(cal
				.getTimeInMillis());
	}

	/**
	 * ����ʱ���ʽΪyyyy-MM-dd HH:mm
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeMinutes(Date time) {
		if (time == null)
			return "";
		return getTimeMinutes(time.getTime());
	}

	/**
	 * ��ȡ��ǰ��ʱ��
	 * 
	 * @return
	 */
	public static String nowDate() {
		// DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT)
				.format(new Date());
	}

	/**
	 * ��ȡ��ǰ��ʱ��(ֻ��ʱ��)
	 * 
	 * @return
	 */
	public static String nowDateOnlyTime() {
		// DateFormat format=new SimpleDateFormat("HH:mm:ss");
		return new SimpleDateFormat(TIME_FORMAT).format(new Date());
	}

	/**
	 * ��ȡָ�����ڵ�ʱ��(HH:mm)
	 * 
	 * @return
	 */
	public static String onlyTime(Date date) {
		return new SimpleDateFormat(ONLY_TIME_FORMAT).format(date);
	}

	/**
	 * ��ȡָ�����ڵ�ʱ��(HH:mm)
	 * 
	 * @return
	 */
	public static String onlyTime(long date) {
		return new SimpleDateFormat(ONLY_TIME_FORMAT).format(date);
	}

	/**
	 * ͨ��ָ��������ȡ���
	 * 
	 * @param cal
	 * @return
	 */
	public static int getYear(Calendar cal) {
		return cal.get(Calendar.YEAR);
	}

	/**
	 * ͨ��ָ��������ȡ�·�(1-12)
	 * 
	 * @param cal
	 * @return 1�µ�12�·ֱ𷵻�1-12
	 */
	public static int getMonth(Calendar cal) {
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * ͨ��ָ��������ȡ��
	 * 
	 * @param cal
	 * @return
	 */
	public static int getDayOfMonth(Calendar cal) {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ͨ��ָ��������ȡ����
	 * 
	 * @param cal
	 * @return ����һ��������ֱ��Ӧ1-7
	 */
	public static int getDayOfWeek(Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * ��ȡСʱ(24Сʱ��)
	 * 
	 * @param cal
	 * @return
	 */
	public static int getHourOfDay(Calendar cal) {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param cal
	 * @return
	 */
	public static int getMinute(Calendar cal) {
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * ��ȡ��
	 * 
	 * @param cal
	 * @return
	 */
	public static int getSecond(Calendar cal) {
		return cal.get(Calendar.SECOND);
	}

	/**
	 * ���ص�ǰ��Сʱ��(24Сʱ 0-23)
	 * 
	 * @return
	 */
	public static int getCurrentHour() {
		return getHourOfDay(Calendar.getInstance());
	}

	/**
	 * ����date1�����ڲ��֣�����Date2��ʱ�䲿��
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date changeTime(Date date1, Date date2) {
		return new Date(date1.getTime() / DAY * DAY + date2.getTime() % DAY);
	}

	/**
	 * �Ƚ��������ڵ�ʱ�䲿���Ƿ�һ��
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareTime(Date date1, Date date2) {
		return date1.getTime() % DAY == date2.getTime() % DAY;
	}

	/**
	 * �Ƚ�����Date���ڲ��ֵĴ�С
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
		if (date1.getTime() / DAY == date2.getTime() / DAY) {
			return 0;
		} else if (date1.getTime() / DAY > date2.getTime() / DAY) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * �����������ڵ�ʱ�䣬�����һ�����е�ʱ��ĵĺ�����
	 * 
	 * @param date
	 * @return
	 */
	public static long dayRunTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime = Calendar.getInstance();
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}

	/**
	 * ���������Ϊ���ڽ���ѭ����������һ��������ʱ��
	 * 
	 * @param date
	 * @param dayOfWeek
	 *            ��1��ʼ��7
	 * @return
	 */
	public static long weekRunTime(Date date, byte dayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime = Calendar.getInstance();
		if ((dayOfWeek + 8) % 7 >= runTime.get(Calendar.DAY_OF_WEEK)) {
			runTime.add(Calendar.DATE,
					(dayOfWeek + 8) % 7 - runTime.get(Calendar.DAY_OF_WEEK));
		} else {
			runTime.add(Calendar.DATE,
					(dayOfWeek + 8) % 7 - runTime.get(Calendar.DAY_OF_WEEK) + 7);
		}
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}

	/**
	 * �������Ϊ����������������һ������ʱ��
	 * 
	 * @param date
	 * @param dayOfMonth
	 * @return
	 */
	public static long monthRunTime(Date date, byte dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime = Calendar.getInstance();
		if (dayOfMonth >= runTime.get(Calendar.DAY_OF_MONTH)) {
			runTime.add(Calendar.DATE,
					dayOfMonth - runTime.get(Calendar.DAY_OF_MONTH));
		} else {
			runTime.add(Calendar.MONTH, 1);
			runTime.set(Calendar.DATE, dayOfMonth);
		}
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}

	/**
	 * ��ָ����ʱ�����ָ��������
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static Date addDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * ��ָ����ʱ�����ָ����Сʱ
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	public static Date addMinute(int minute) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @param minute
	 * @return
	 * @author ChenYongcun
	 * @description ��ָ��ʱ������ָ��������
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	public static Timestamp string2Timestamp(String time) {
		Timestamp date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(time);
			date = new Timestamp(d.getTime());
		} catch (Exception e) {
		}
		return date;
	}

	public static Date string2Date(String time) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(time);
		} catch (Exception e) {
			date = new Date();
		}
		return date;
	}

	public static String time2String(Date time) {
		String timeString = null;
		try {
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeString = new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT)
					.format(time);
		} catch (Exception e) {
			// logger.error("ʱ��ת�ַ�������", e);
		}
		return timeString;
	}

	public static String time2String(long time) {
		Date d = new Date();
		d.setTime(time);
		return time2String(d);
	}

	public static String time2StringWhitChinese(Date time) {
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT_WITH_CHINESE)
				.format(time);
	}

	/**
	 * ����ʣ���ʱ�䣺���ظ�ʽ��������\����Сʱ�����֡� ֻ����ʣ��ʱ������λ��ֵ��
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static String time2StringPrecise2Second(long t) {

		StringBuilder sb = new StringBuilder();
		// long day = 0l;
		long hour = 0l;
		long minute = 0l;

		// day = t / DAY;
		// if(day != 0)
		// {
		// sb.append(day).append("��");
		// return sb.toString();
		// }
		hour = t / HOUR;
		if (hour != 0) {
			sb.append(hour).append(
					SystemMsgConstants.DAY_TIME_HOUR.getMessage());
		}
		minute = t % HOUR / MINUTE;
		if (minute != 0)
			sb.append(minute).append(
					SystemMsgConstants.DAY_TIME_MINUTE.getMessage());
		return sb.toString();
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param dayTime
	 * @param offset
	 * @return
	 */
	public static final Calendar getDayDate(Calendar dayTime, int offset) {
		Calendar calendar = Calendar.getInstance();
		if (dayTime != null)
			calendar.setTimeInMillis(dayTime.getTimeInMillis());

		calendar.add(Calendar.DAY_OF_YEAR, offset);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	/**
	 * ��ȡָ��ʱ�������
	 * 
	 * @param time
	 * @return
	 */
	public static final Calendar getCalendar(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);

		return calendar;
	}

	public static final String getWeeklyDetailByWeek(int week) {
		return WEEKLY_DETAIL[week];
	}

	/**
	 * ��������֮����������Ĺ�ͨ
	 * 
	 * @param startDate
	 *            ��ʼʱ��
	 * @param endDate
	 *            ������ʱ��
	 * @return������
	 */
	public static int getDaysBetweenTwoDates(Date startDate, Date endDate) {
		long begin = startDate.getTime();
		long end = endDate.getTime();
		long inter = end - begin;
		if (inter < 0) {
			inter = inter * (-1);
		}

		long dateMillSec = 24 * 60 * 60 * 1000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;

		if (remainder != 0) {
			dateCnt++;
		}
		return (int) dateCnt;
	}

	public static void main(String... args) throws Exception {
		// Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DAY_OF_MONTH, 4);
		// System.out.println(DateTimeUtil.getDaysBetweenTwoDates(calendar.getTime(),
		// new Date()));
		// System.out.println(DateTimeUtil.time2String(new
		// Date()).split(" ")[1]);
		// Date d = new Date();
		// Calendar cal = Calendar.getInstance();
		// cal.setFirstDayOfWeek(Calendar.MONDAY);
		//
		// System.err.println(cal.get(Calendar.YEAR));
		// System.err.println(cal.get(Calendar.MONTH) + 1);
		// System.err.println(cal.get(Calendar.DATE));
		// System.err.println(cal.get(Calendar.DAY_OF_WEEK) - 1);
		// System.err.println(cal.get(Calendar.HOUR_OF_DAY));
		// System.err.println(cal.get(Calendar.MINUTE));
		// System.err.println(cal.get(Calendar.SECOND));
		//
		//
		//
		// long now = System.currentTimeMillis();
		//
		// System.err.println(getTimeString(now));
		//
		// System.err.println(buildTimeString(now, true, true));
		// System.err.println(buildTimeString(YEAR + 12 * DAY + 10* MINUTE));
		// System.err.println(buildTimeString(DAY));
		// System.err.println(buildTimeString(HOUR));
		// System.err.println(buildTimeString(1200));
		// System.err.println(buildTimeString(200));
		// System.err.println(buildTimeString(0));
		// System.err.println(buildTimeString(-1));
		//
		// Date date=new Date();
		//
		// date.setTime(System.currentTimeMillis()-DateTimeUtil.DAY*5);
		// System.out.println(date);
		// System.out.println(new Date(DateTimeUtil.dayRunTime(date)));
		// System.out.println(new Date(DateTimeUtil.weekRunTime(date,(byte)3)));
		// System.out.println(new Date(DateTimeUtil.weekRunTime(date,(byte)7)));
		// System.out.println(new
		// Date(DateTimeUtil.monthRunTime(date,(byte)12)));
		// System.out.println(new
		// Date(DateTimeUtil.monthRunTime(date,(byte)30)));
		// System.out.println("------------------");
		// System.out.println(changeTime(new Date(),new
		// Date(System.currentTimeMillis()-10000)));
		//
	}

}
