package com.doteyplay.core.task.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.service.CoreServiceConstants;

/**
 * ͨ�õ���������ṩ��
 * 
 */
public class CommonTaskService extends AbstractTaskService {

	/**
	 * ����ִ�е��̳߳ض���
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(16);
	/**
	 * ��һ��ִ�е��ӳ�ʱ��
	 */
	private static long initialDelay = 20001;

	/**
	 * ��������
	 */
	private static CommonTaskService instance = new CommonTaskService();

	private CommonTaskService() {
	}

	public static CommonTaskService getInstance() {
		return instance;
	}

	@Override
	protected long getDelay() {
		return initialDelay;
	}

	@Override
	protected ScheduledThreadPoolExecutor getExecutor() {
		return scheduler;
	}

	@Override
	protected String getName() {
		return "ͨ�����������";
	}

	@Override
	protected long getPeriod() {
		return CoreServiceConstants.MILLISECOND_PER_FRAME;
	}

}
