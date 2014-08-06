package com.doteyplay.game.task.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.service.CoreServiceConstants;

/**
 * �����ƻ�����
 * 
 */
public class SceneTaskService extends AbstractTaskService
{

	/**
	 * ����ִ�е��̳߳ض���
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(16);

	/**
	 * ��������
	 */
	private static SceneTaskService service = new SceneTaskService();

	/**
	 * ��һ��ִ�е��ӳ�ʱ��
	 */
	private final static long initialDelay = 20001;

	private SceneTaskService()
	{
	}

	public static SceneTaskService getInstance()
	{
		return service;
	}

	@Override
	protected long getDelay()
	{
		return initialDelay;
	}

	@Override
	protected ScheduledThreadPoolExecutor getExecutor()
	{
		return scheduler;
	}

	@Override
	protected String getName()
	{
		return "�����ƻ�����";
	}

	@Override
	protected long getPeriod()
	{
		return CoreServiceConstants.MILLISECOND_PER_FRAME;
	}

}
