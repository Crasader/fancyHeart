package com.doteyplay.core.monitor;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.doteyplay.core.monitor.manager.SampleManager;

public class MonitorSchedule implements Runnable
{

	private static final Logger logger = Logger.getLogger(MonitorSchedule.class);

	private boolean runing;
	private long sleepInterval;
	private long sampleTime;

	private int sampleCount;
	private int sampleLeftCount;
	
	/**
	 * �Ƿ�������״̬�����ڸտ����ֹر��ֿ�������ֹ����
	 */
	private static AtomicBoolean isRuning = new AtomicBoolean(false);

	public MonitorSchedule()
	{
		runing = false;
	}

	public void forceStop()
	{
		runing = false;
		sampleCount = 0;
		sampleLeftCount = 0;
		SampleManager.getInstance().release();
	}

	public String sampleProcessInfo()
	{
		return "sampleCount=" + sampleCount + ",sampleLeftCount=" + sampleLeftCount;
	}

	/**
	 * ���������ƻ�
	 * 
	 * @Title: beginSampleSchedule
	 * @Description:
	 * @param
	 * @param sampletime
	 * @param
	 * @param sleepinterval
	 * @param
	 * @param samplecount
	 * @param
	 * @return
	 * @return boolean
	 * @throws
	 */
	public boolean beginSampleSchedule(long sampletime, long sleepinterval, int samplecount)
	{
		if (sleepinterval < 60000l || sampletime < 20000l || samplecount < 1)
		{
			logger.error("����ָ��Ƿ�");
			return false;
		}
		SampleManager.getInstance().release();

		if (runing)
		{
			logger.error("�ϴβ������ڽ����У���ȴ�����������ֱ��ֹͣ����");
			return false;
		}

		this.sleepInterval = sleepinterval;
		this.sampleTime = sampletime;
		sampleCount = samplecount;
		new Thread(this).start();
		logger.error("command sample start success...");
		return true;
	}

	@Override
	public void run()
	{
		runing = true;
		sampleLeftCount = sampleCount;
		logger.error("MonitorSchedule start...");

		while (runing && sampleLeftCount > 0)
		{
			isRuning.set(true);
			if (runing)
			{
				try
				{
					Thread.sleep(sleepInterval);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			logger.error("beginSamlping...");
			try
			{
				Monitor.beginSamlping();
				long tmpSampleExpire = System.currentTimeMillis() + this.sampleTime;
				do
				{
					try
					{
						Thread.sleep(5000l);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					logger.error("check Samlping...");
				} while (System.currentTimeMillis() < tmpSampleExpire);
				logger.error("stop samlping and start post data...");
				Monitor.stopSamlping();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(sampleLeftCount>0)
				{
					sampleLeftCount--;
				}
			}
			isRuning.set(false);
		}
		
		logger.error("EndSampling....");
	}

	public long getSleepInterval()
	{
		return sleepInterval;
	}

	public boolean isRuning()
	{
		return runing;
	}

	// *************************************************
	private static MonitorSchedule instance;

	public synchronized static MonitorSchedule getInstance()
	{
		if (instance == null)
		{
			instance = new MonitorSchedule();
		}
		return instance;
	}

	public static boolean startSample(long sampletime, long sleepinterval, int samplecount)
	{
		MonitorSchedule tmpMonitorSchedule = getInstance();
		if (tmpMonitorSchedule != null)
		{
			if(isRuning.get())
			{
				logger.error("��һ��ǿ�ƹرղ�����û��ִ�У����Ժ�ִ��");
				return false;
			}
			return tmpMonitorSchedule.beginSampleSchedule(sampletime, sleepinterval, samplecount);
		}
		else
		{
			logger.error("init MonitorSchedule fail...");
			return false;
		}
	}

	public static void forceStopSampleSchedule()
	{
		MonitorSchedule tmpMonitorSchedule = getInstance();
		if (tmpMonitorSchedule != null)
		{
			logger.error("force stop command monitor...");
			tmpMonitorSchedule.forceStop();
		}
	}

	public static String getSampleProcessInfo()
	{
		MonitorSchedule tmpMonitorSchedule = getInstance();
		if (tmpMonitorSchedule != null)
		{
			return tmpMonitorSchedule.sampleProcessInfo();
		}
		else
		{
			logger.error("init MonitorSchedule fail...");
			return null;
		}
	}

	public static long getIntevalTime()
	{
		MonitorSchedule tmpMonitorSchedule = getInstance();
		if (tmpMonitorSchedule != null)
			return tmpMonitorSchedule.getSleepInterval();
		else
		{
			logger.error("init MonitorSchedule fail...");
			return 0;
		}
	}

	public static boolean hasRun()
	{
		MonitorSchedule tmpMonitorSchedule = getInstance();
		if (tmpMonitorSchedule != null)
			return tmpMonitorSchedule.isRuning();
		else
		{
			logger.error("init MonitorSchedule fail...");
			return false;
		}
	}
	
	public static int getCurrentSampleCount()
	{
		MonitorSchedule tmpMonitorSchedule = getInstance();
		if (tmpMonitorSchedule != null)
			return tmpMonitorSchedule.sampleCount - tmpMonitorSchedule.sampleLeftCount;
		return 0;
	}

}
