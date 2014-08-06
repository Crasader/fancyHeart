package com.doteyplay.luna.client.container;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.doteyplay.luna.client.ConnectionInfo;
import com.doteyplay.luna.common.action.ActionController;

public class AsynchronismConnectionPool
{

	private static Logger logger = Logger
			.getLogger(AsynchronismConnectionPool.class.getName());

	/**
	 * ���ӳ�����
	 */
	private static int CONNECT_NUM = 1;
	/**
	 * ������Ϣ
	 */
	private ConnectionInfo connectionInfo;
	/**
	 * ����Action�ַ�����
	 */
	private ActionController actionController;

	/**
	 * Ŀǰ��5�����ӣ����ѡ��
	 */
	private List<ClientConnection> connections = new ArrayList<ClientConnection>(
			CONNECT_NUM);

	public AsynchronismConnectionPool()
	{

	}

	/**
	 * 
	 * @param info
	 *            ������������Ϣ
	 * @param controller
	 *            ������Ϣ�ַ���Action �������е���Ϣ�޷��ص�ʱ�򣬿�������ΪNULL
	 */
	public AsynchronismConnectionPool(ConnectionInfo info,
			ActionController controller)
	{
		this.connectionInfo = info;
		this.actionController = controller;
	}

	/**
	 * ��ʼ����������Ҫ������ͬ����������ָ������������
	 */
	public void initial()
	{
		for (int i = 0; i < CONNECT_NUM; i++)
		{
			ClientConnection connection = new ClientConnection(connectionInfo,
					actionController);
			connection.initial();
			connections.add(connection);
		}
	}

	/**
	 * ������Ҫ�ж�һ��,����������ͬ������ֹ��Դ����������Ϣ����,��������Դ������ʱ��ᴴ���µ����ӣ���֤����ͨ�š�
	 * 
	 * @return
	 */
	public ClientConnection getConnect()
	{
		ClientConnection con = connections.get(0);
		if (con != null)
		{
			return con.get();
		} else
		{
			ClientConnection connection = new ClientConnection(connectionInfo,
					actionController);
			connection.initial();
			connections.add(0, connection);
			return connection.get();
		}
	}

	/**
	 * ��ȡ��ǰ��������״̬
	 * 
	 * @return
	 */
	public Integer getState()
	{
		return connections.get(0).getState();
	}

	/**
	 * �ر���������
	 */
	public void close()
	{
		logger.info("�ر�����.");
		for (ClientConnection con : connections)
		{
			con.getSession().close(true);
		}
	}
}
