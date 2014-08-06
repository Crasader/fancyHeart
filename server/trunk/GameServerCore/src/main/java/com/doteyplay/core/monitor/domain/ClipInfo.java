package com.doteyplay.core.monitor.domain;

import java.util.Date;

public class ClipInfo
{
	/**
	 * ����Id
	 */
	private int portalId = 0;
	
	/**
	 * ����ķ�����
	 */
	private String methodName;
	/**
	 * ���շ��ʵ��������������С�
	 */
	private int getCallIoSize;
	
	/**
	 * ���յ������������񷵻ص����������С�
	 */
	private int getReturnIoSize;
	
	/**
	 * ������������ʱ���ͳ�ȥ�����������С�;
	 */
	private int sendCallIoSize;
	/**
	 * �����÷���returnʱ�ͳ�ȥ�����������С�
	 */
	private int sendReturnIoSize;
	/**
	 * sendmessageʱ�ͳ�ȥ�����������С�
	 */
	private int sendMessageIoSize;
	
	/**
	 * ���뷽���Ŀ�ʼʱ��
	 */
	private long startTime = new Date().getTime();
	
	/**
	 * �������е����ʱ��
	 */
	private long endTime;
	
	
	public void addGetCallIoSize(long value)
	{
		this.getCallIoSize += value;
	}
	
	public void addSendCallIoSize(long value)
	{
		this.sendCallIoSize += value;
	}
	
	public void addSendReturnIoSize(long value)
	{
		this.sendReturnIoSize += value;
	}
	
	public void addSendMessageIoSize(long value)
	{
		this.sendMessageIoSize += value;
	}
	
	public void addGetReturnIoSize(long value)
	{
		this.getReturnIoSize += value;
	}
	
	public long getProcessCost()
	{
		return endTime - startTime;
	}
	
	/**
	 * @return the portalId
	 */
	public int getPortalId()
	{
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(int portalId)
	{
		this.portalId = portalId;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName()
	{
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	/**
	 * @return the getCallIoSize
	 */
	public int getGetCallIoSize()
	{
		return getCallIoSize;
	}

	/**
	 * @param getCallIoSize the getCallIoSize to set
	 */
	public void setGetCallIoSize(int getCallIoSize)
	{
		this.getCallIoSize = getCallIoSize;
	}

	/**
	 * @return the getReturnIoSize
	 */
	public int getGetReturnIoSize()
	{
		return getReturnIoSize;
	}

	/**
	 * @param getReturnIoSize the getReturnIoSize to set
	 */
	public void setGetReturnIoSize(int getReturnIoSize)
	{
		this.getReturnIoSize = getReturnIoSize;
	}

	/**
	 * @return the sendCallIoSize
	 */
	public int getSendCallIoSize()
	{
		return sendCallIoSize;
	}

	/**
	 * @param sendCallIoSize the sendCallIoSize to set
	 */
	public void setSendCallIoSize(int sendCallIoSize)
	{
		this.sendCallIoSize = sendCallIoSize;
	}

	/**
	 * @return the sendReturnIoSize
	 */
	public int getSendReturnIoSize()
	{
		return sendReturnIoSize;
	}

	/**
	 * @param sendReturnIoSize the sendReturnIoSize to set
	 */
	public void setSendReturnIoSize(int sendReturnIoSize)
	{
		this.sendReturnIoSize = sendReturnIoSize;
	}

	/**
	 * @return the sendMessageIoSize
	 */
	public int getSendMessageIoSize()
	{
		return sendMessageIoSize;
	}

	/**
	 * @param sendMessageIoSize the sendMessageIoSize to set
	 */
	public void setSendMessageIoSize(int sendMessageIoSize)
	{
		this.sendMessageIoSize = sendMessageIoSize;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime()
	{
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime()
	{
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}
	
}
