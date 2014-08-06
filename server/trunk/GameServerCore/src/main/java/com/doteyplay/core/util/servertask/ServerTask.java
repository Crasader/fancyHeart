package com.doteyplay.core.util.servertask;

import com.doteyplay.core.server.servertaskimpl.AbstractTaskItem;
import com.doteyplay.core.server.task.Task;

/**
 * ��������ʱ�ظ���������
 *
 */
public interface ServerTask<T extends ServerTaskInfo, P extends IServerTaskDBManager> extends Task
{
	/**
	 * �־û�ָ������������ִ��ʱ��
	 * @param info ������Ϣ
	 * @param lastUpdate �����ִ��ʱ��
	 */
	public void afterTaskExecuted(T info, long lastUpdate);
	
	/**
	 * ���Ӷ���ִ����
	 */
	public void addTaskItem(AbstractTaskItem item);
	
	/**
	 * �趨�ִ����
	 */
	public boolean setOpen(String itemClassName, boolean open);
}
