package com.doteyplay.luna.client;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.client.container.AsynchronismConnectionPool;
import com.doteyplay.luna.client.container.ClientConnection;
import com.doteyplay.luna.common.action.ActionController;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * ִ���첽������Manager,���౻����
 */
public class AsynchronismClientManager
{
	private static Logger logger = Logger
			.getLogger(SynchronicClientManager.class.getName());
	/**
	 * ���ӳض���
	 */
	private AsynchronismConnectionPool pool;
	/**
	 * ������Ϣ
	 */
	private ConnectionInfo info;

	public AsynchronismClientManager()
	{
	}

	/**
	 * ͬ������������Ҫ���еĳ�ʼ����Ϣ
	 * 
	 * @param connectionInfo
	 */
	public void initial(ConnectionInfo connectionInfo,
			ActionController controller)
	{
		this.info = connectionInfo;
		this.pool = new AsynchronismConnectionPool(this.info, controller);
		this.pool.initial();
	}

	/**
	 * ���ص�ǰ���ӳص�״̬
	 * 
	 * @return
	 */
	public Integer getConnectionState()
	{
		return this.pool.getState();
	}

	/**
	 * �������õķ���,���Ӷ���д������д������ͷ�
	 * 
	 * @param buffer
	 * @return
	 */
	public boolean invoke(EncoderMessage request)
	{
		try
		{
			ClientConnection session = this.pool.getConnect();
			if (session != null)
			{
				session.write(request);// д��Ϣ
				return true;
			}
			else
				logger.error("��RPCʧ�ܡ�����Զ�˵ķ����������ṩ����" + this.info.toString());
		} catch (Exception e)
		{
			logger.error(
					"��RPCʧ�ܡ�����Զ�˵ķ����������ṩ���񣬻��߱������Ӻľ���" + this.info.toString(), e);
			
		}
		return false;
	}
	
	/**
	 * �������õķ���,���Ӷ���д������д������ͷ�
	 * 
	 * @param buffer
	 * @return
	 */
	public static boolean invoke(EncoderMessage request,IoSession session)
	{
		try
		{
			if (session != null)
			{
				session.write(request);// д��Ϣ
				return true;
			}
			else
				logger.error("��RPCʧ�ܡ�����Զ�˵ķ����������ṩ����" + session.getRemoteAddress());
		} catch (Exception e)
		{
			logger.error(
					"��RPCʧ�ܡ�����Զ�˵ķ����������ṩ���񣬻��߱������Ӻľ���" + session.getRemoteAddress(), e);
			
		}
		return false;
	}

	/**
	 * ִ�йرղ���
	 */
	public void close()
	{
		this.pool.close();
	}

}
