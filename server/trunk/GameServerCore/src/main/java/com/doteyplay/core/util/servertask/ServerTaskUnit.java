package com.doteyplay.core.util.servertask;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.doteyplay.constant.DateTimeConstants;
import com.doteyplay.core.configloader.TaskHandlerRegistry;
import com.doteyplay.core.server.servertaskimpl.AbstractTaskItem;
import com.doteyplay.core.server.task.Task;
import com.doteyplay.core.util.CheckTime;

/**
 * ServerTask�ĵ��ȵ�����ִ�е�Ԫ
 * 
 */
class ServerTaskUnit implements Task
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ServerTaskUnit.class);

	private ServerTaskInfo info;

	private final ServerTaskType taskType;

	private final CheckTime checkTime;

	private long lastUpdate;

	private long nextUpdateTime;

	private final ServerTask<ServerTaskInfo, IServerTaskDBManager> task;

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public ServerTaskUnit(ServerTaskInfo info)
	{
		this.info = info;
		this.taskType = info.getServerTaskType();
		this.checkTime = new CheckTime(info.getMonth(), info.getDay(),
				info.getWeek(), info.getHour(), info.getMinute());

		try
		{
			this.task = (ServerTask) TaskHandlerRegistry.getInstance()
					.getTaskHandlerClz().newInstance();
		} catch (Exception e)
		{
			throw new IllegalArgumentException(info.getName() + "��ʼ������ִ����ʧ��", e);
		}

		IServerTaskDBManager dbManager = TaskHandlerRegistry.getInstance()
				.getDbManager();
		try
		{
			ServerTaskInfo tmpInfo = dbManager.getServerTaskInfo(info.getId());
			if (tmpInfo != null)
				this.nextUpdateTime = tmpInfo.getNextExecuteTime();
			else
			{
				this.setupNextUpdateTime();
				this.info.setNextExecuteTime(this.nextUpdateTime);
				dbManager.saveServerTaskInfo(this.info);
			}

		} catch (Exception e)
		{
			throw new IllegalArgumentException(info.getName() + "��ʼ�����������ʧ��", e);
		}

		if (this.nextUpdateTime <= 0)
			this.setupNextUpdateTime();

		/**
		 * ���ؾ�����Ŀ
		 */
		if (info.getItemClassName() != null
				&& info.getItemClassName().length > 0)
		{

			try
			{
				for (String className : info.getItemClassName())
				{
					Class c = Class.forName(className);
					task.addTaskItem((AbstractTaskItem) c.newInstance());
				}
			} catch (Exception e)
			{
				throw new IllegalArgumentException(info.getName()
						+ "��ʼ�����������ʧ��", e);
			}

		}

		if (logger.isInfoEnabled())
			logger.info("��������ʱ��������[" + info.getName() + "]�´ε���ʱ��:"
					+ DateTimeConstants.getTimeString(this.nextUpdateTime));
	}

	private void setupNextUpdateTime()
	{
		long now = System.currentTimeMillis();
		Calendar cal = this.checkTime.setupFor(now);
		lastUpdate = cal.getTimeInMillis();
		if (this.lastUpdate > now)
			this.lastUpdate -= taskType.getPeriodic();
		else
			taskType.getNextTime(cal);
		nextUpdateTime = cal.getTimeInMillis();
	}

	@Override
	public void run()
	{
		final Calendar now = Calendar.getInstance();
		final long nowTime = now.getTimeInMillis();
		if (nowTime > nextUpdateTime)
			doTask();
	}

	// ִ���������������
	private void doTask()
	{
		try
		{
			task.run();

			// �������һ�θ��µ�ʱ��
			this.setupNextUpdateTime();

			// ���˸���ʱ����־û�
			task.afterTaskExecuted(info, this.nextUpdateTime);

			if (logger.isInfoEnabled())
			{
				logger.info("���ȷ�������ʱ�������:[" + info.getName() + "], �´�ִ��ʱ������Ϊ:"
						+ DateTimeConstants.getTimeString(this.nextUpdateTime));
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return the info
	 */
	ServerTaskInfo getInfo()
	{
		return info;
	}

	long getLastExecuteTime()
	{
		return lastUpdate;
	}

	long getNextExecuteTime()
	{
		return nextUpdateTime;
	}

	public void setOpen(String itemClassName, boolean open)
	{
		this.task.setOpen(itemClassName, open);
	}
}
