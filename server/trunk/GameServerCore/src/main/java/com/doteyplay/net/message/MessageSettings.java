package com.doteyplay.net.message;

import com.doteyplay.game.MessageCommands;

/**
 * ��Ϣ���������Ϣ����: �����������ȼ�
 */
public class MessageSettings
{
	private MessageSettings()
	{
	}
	
	/**
	 * ��ȡָ����Ϣ�Ļ������ȼ�
	 * @param commandId ��ϢID
	 * @return
	 * @throws ArrayIndexOutOfBoundsException �����ϢID��Ч,���׳����쳣
	 */
	public static int getPriority(int commandId)
	{
		return priorities[commandId];
	}
	
	private final static int[] priorities = new int[MessageCommands.MESSAGE_NUM];
	
	static
	{
		for(int i=0; i < MessageCommands.MESSAGE_NUM; ++i)
		{
			priorities[i] = 8;
		}
		
		initPriorities();
		checkPriorities();
	}
	
	/**
	 * ��Ϣ�������ȼ��Լ�
	 */
	private static void checkPriorities()
	{
		for(int i=0; i < MessageCommands.MESSAGE_NUM; ++i)
		{
			int value = priorities[i];
			if(value < 0 || value > 9)
				throw new IllegalArgumentException("��Ϣ:" + i + "�����ȼ�������Ч:" + value);
		}
	}

	/**
	 * ������Ϣ�������ȼ�
	 */
	private static void initPriorities()
	{
		priorities[	MessageCommands.ACK_MESSAGE.COMMAND_ID ] = 9;
	}
}
