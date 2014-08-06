package com.doteyplay.core.util.servertask;

import java.util.Calendar;

import com.doteyplay.constant.DateTimeConstants;



/**
 * ���������������������
 * 
 */
public enum ServerTaskType
{ 

	/**
	 * ÿСʱ
	 */
	HOURLY,
	/**
	 * ÿ��
	 */
	DAILY,
	/**
	 * ÿ��
	 */
	WEEKLY,
	/**
	 * ÿ��
	 */
	MONTHLY,
	/**
	 * ÿ��
	 */
	YEARLY;
	
	private final static long[] periodics =
	{
		DateTimeConstants.HOUR,
		DateTimeConstants.DAY,
		DateTimeConstants.WEEK,
		DateTimeConstants.MONTH,
		DateTimeConstants.YEAR
	};
	
	/**
	 * ��ȡ�����͵�����(��λ:����)
	 * @return
	 */
	public long getPeriodic()
	{
		return periodics[this.ordinal()];
	}
	
	/**
	 * ��ȡ��Ӧ����ֵ��ServerTaskTypeö�ٶ���, ��������������׳�ArrayIndexOutOfBoundsException
	 * @param value
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static ServerTaskType getByValue(int value)
	{
		return values()[value];
	}
	
	private final static int[] unit =
	{
		Calendar.HOUR_OF_DAY,
		Calendar.DAY_OF_YEAR,
		Calendar.WEEK_OF_YEAR,
		Calendar.MONTH,
		Calendar.YEAR
	};
	
	public void getNextTime(Calendar lastExecuteTime){
		lastExecuteTime.add(unit[this.ordinal()], 1);
	}
}
