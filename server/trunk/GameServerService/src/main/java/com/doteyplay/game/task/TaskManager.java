package com.doteyplay.game.task;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.task.CommonTaskManager;
import com.doteyplay.game.task.impl.SceneTaskService;
import com.doteyplay.game.task.impl.UserTaskService;

/**
 * ϵͳ���еļƻ�������ͨ���������й����
 * 
 */
public class TaskManager
{
	/**
	 * �û����������
	 */
	private AbstractTaskService userTaskService = UserTaskService.getInstance();

	/**
	 * ��������
	 */
	private static TaskManager manager = new TaskManager();

	private TaskManager()
	{
	}

	public static TaskManager getInstance()
	{
		return manager;
	}

	public AbstractTaskService getCommonTaskService()
	{
		return CommonTaskManager.getInstance().getCommonTaskService();
	}

	public AbstractTaskService getUserTaskService()
	{
		return userTaskService;
	}

	/**
	 * ֹͣ���еļƻ��������ִ������˳��ġ�
	 */
	public void shutDownAll()
	{
		CommonTaskManager.getInstance().shutdown();
		userTaskService.shutdown();
	}
}
