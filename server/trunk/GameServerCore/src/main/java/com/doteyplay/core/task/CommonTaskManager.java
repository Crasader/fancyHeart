package com.doteyplay.core.task;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.task.impl.CommonTaskService;

public class CommonTaskManager {
	/**
	 * ͨ�����������
	 */
	private AbstractTaskService commonTaskService = CommonTaskService
			.getInstance();
	
	public AbstractTaskService getCommonTaskService() {
		return commonTaskService;
	}

	/**
	 * ��������
	 */
	private static CommonTaskManager manager = new CommonTaskManager();

	private CommonTaskManager() {
	}

	public static CommonTaskManager getInstance() {
		return manager;
	}
	
	public void shutdown() {
		commonTaskService.shutdown();
	}
}
