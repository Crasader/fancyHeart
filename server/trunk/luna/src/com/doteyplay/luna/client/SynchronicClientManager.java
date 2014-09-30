package com.doteyplay.luna.client;

import org.apache.log4j.Logger;

import com.doteyplay.luna.client.container.SynchronicConnectionPool;
import com.doteyplay.luna.common.message.DecoderMessage;
import com.doteyplay.luna.common.message.EncoderMessage;

public class SynchronicClientManager
{

	private static Logger logger = Logger
			.getLogger(SynchronicClientManager.class.getName());
	private SynchronicConnectionPool pool;
	private ConnectionInfo info;

	public SynchronicClientManager()
	{
	}

	public void initial(ConnectionInfo connectionInfo)
	{
		info = connectionInfo;
		pool = new SynchronicConnectionPool(info);
	}

	public DecoderMessage synInvoke(EncoderMessage request)
	{
		try
		{
			return pool.synInvoke(request);
		} catch (Exception e)
		{
			logger.error(
					(new StringBuilder())
							.append("【RPC失败】由于远端的服务器不能提供服务，或者本地连接耗尽！")
							.append(info.toString()).toString(), e);
			return null;
		}
	}

	public void asynInvoke(EncoderMessage request)
	{
		try
		{
			pool.asynInvoke(request);
		} catch (Exception e)
		{
			logger.error(
					(new StringBuilder())
							.append("【RPC失败】由于远端的服务器不能提供服务，或者本地连接耗尽！")
							.append(info.toString()).toString(), e);
		}
	}

	public boolean relocate(ConnectionInfo connectionInfo)
	{
		if (connectionInfo == null || connectionInfo.getServerAddress() == null
				|| connectionInfo.getServerPort() <= 0
				|| connectionInfo.getServerPort() >= 65535)
			return false;
		this.info = connectionInfo;
		try
		{
			if (pool == null)
				initial(info);
			else
				return pool.relocate(info);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
