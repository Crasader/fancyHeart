package com.doteyplay.core.bhns.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * �¼�Դ�������
 * 
 * @author 
 * 
 */
public class EventProxyEntrance implements InvocationHandler
{
	private EventProxy<?> serviceProxy;

	public EventProxyEntrance(EventProxy<?> serviceproxy)
	{
		this.serviceProxy = serviceproxy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		return serviceProxy.doService(method, args);
	}
}
