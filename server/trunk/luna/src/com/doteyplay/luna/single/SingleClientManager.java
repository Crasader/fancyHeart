package com.doteyplay.luna.single;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.doteyplay.luna.client.ConnectionInfo;

/**
 * ��������Ŀ�����������
 */
public class SingleClientManager
{
	/**
	 * ���Ӷ��󣬹ؼ�����
	 */
	private SingleConnection singleConnection;
	/**
	 * ������Ϣ
	 */
	private ConnectionInfo connectionInfo;
	/**
	 * ����Filter
	 */
	private ProtocolCodecFilter codecFilter;
	/**
	 * ִ���߼������Handler
	 */
	private SingleProtocolHandler handller;

	/**
	 * 
	 * @param connectionInfo
	 * @param actionController
	 * @param codecFilter
	 */
	public SingleClientManager(ConnectionInfo connectionInfo,
			SingleProtocolHandler actionController,
			ProtocolCodecFilter codecFilter)
	{
		this.connectionInfo = connectionInfo;
		this.handller = actionController;
		this.codecFilter = codecFilter;
		init();
	}

	/**
	 * ��ʼ�����Ӷ���
	 * 
	 * @return
	 */
	public boolean init()
	{
		singleConnection = new SingleConnection(this.connectionInfo,
				this.handller, this.codecFilter);
		if (singleConnection.getSession() == null)
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param obj
	 */
	public void setAttribute(String key, Object obj)
	{
		singleConnection.getSession().setAttribute(key, obj);
	}

	/**
	 * ������Ϣ��Ŀ�������
	 * 
	 * @param ioBuffer
	 */
	public void sendMessage(IoBuffer ioBuffer)
	{
		singleConnection.getSession().write(ioBuffer);
		System.out.println("=================");
	}
}
