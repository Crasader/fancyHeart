package com.doteyplay.luna.single;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.doteyplay.luna.client.ConnectionInfo;

/**
 * ���Ӷ���
 */
public class SingleConnection
{
	private static Logger logger = Logger.getLogger(SingleConnection.class);
	/**
	 * ��������С
	 */
	private static final int BUFFER_SIZE = 1024 * 8;
	/**
	 * �Ƿ��ӳٷ���
	 */
	private static final boolean TCP_NO_DELAY = true;
	/**
	 * �������ӳ�ʱʱ��
	 */
	private static final long CONNECT_TIMEOUT_MILLIS = 10 * 1000L;
	/**
	 * �첽�����Session����
	 */
	private IoSession ioSession;
	/**
	 * Nio���Ӷ���
	 */
	private NioSocketConnector connector;
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
	 * Ĭ�ϵĹ��캯��
	 * 
	 * @param connectionInfo
	 * @param actionController
	 * @param codecFilter
	 */
	public SingleConnection(ConnectionInfo connectionInfo,
			SingleProtocolHandler actionController,
			ProtocolCodecFilter codecFilter)
	{
		this.connectionInfo = connectionInfo;
		this.handller = actionController;
		this.codecFilter = codecFilter;
		initial();
	}

	/**
	 * ��ʼ����������Ҫ������ͬ����������ָ������������
	 */
	public void initial()
	{
		initConnector();// ��ʼ��������
		try
		{
			if (logger.isDebugEnabled())
				logger.debug("�����µ����Ӷ���"
						+ this.connectionInfo.getServerAddress() + ":"
						+ this.connectionInfo.getServerPort());
			ConnectFuture future = this.connector
					.connect(new InetSocketAddress(this.connectionInfo
							.getServerAddress(), this.connectionInfo
							.getServerPort()));
			;
			future.awaitUninterruptibly();
			this.ioSession = future.getSession();
		} catch (Exception e)
		{
			connector.dispose();
			logger.error("�޷��������ӣ�����Զ������������,�ͷ�������Դ��"
					+ this.connectionInfo.getServerAddress() + ":"
					+ this.connectionInfo.getServerPort());
			this.ioSession = null;
		}
	}

	/**
	 * ������Ϣ
	 * 
	 * @param request
	 */
	public void write(IoBuffer request)
	{
		this.ioSession.write(request);
	}

	/**
	 * ��ʼ����������
	 */
	private void initConnector()
	{
		if (logger.isDebugEnabled())
			logger.debug("��ʼ��������:" + this.connectionInfo.getServerAddress()
					+ ":" + this.connectionInfo.getServerPort());
		this.connector = new NioSocketConnector(1);// Nio���Ӷ���
		this.connector.getFilterChain().addLast("codec", codecFilter);
		this.connector.getSessionConfig().setReceiveBufferSize(BUFFER_SIZE);
		this.connector.getSessionConfig().setTcpNoDelay(TCP_NO_DELAY);
		this.connector.setHandler(handller);
		this.connector.setConnectTimeoutMillis(CONNECT_TIMEOUT_MILLIS);
		this.connector.getSessionConfig().setUseReadOperation(true);// ���ÿ��Զ�ȡ
	}

	/**
	 * ��ȡ��ǰ�Ự��Ϣ
	 * 
	 * @return
	 */
	public IoSession getSession()
	{
		return this.ioSession;
	}
}
