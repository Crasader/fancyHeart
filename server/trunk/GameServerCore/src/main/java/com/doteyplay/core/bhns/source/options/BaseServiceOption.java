package com.doteyplay.core.bhns.source.options;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.dummy.DummyServiceProxy;
import com.doteyplay.core.bhns.portal.SimpleMethodCollection;

/**
 * ��������ʵ�����jar����classʱ�����ÿ��ǶԸ�������������ֻ��Զ�̷����ṩ�ӿ�
 *
 */
public class BaseServiceOption
{
	protected static final Logger logger = Logger.getLogger(NormalServiceOption.class);

	private int _portalId; // �����ʶ
	private Class<? extends ISimpleService> svrClass;// ����ӿ�
	protected IMethodCollection methodMap;// ����������
	private DummyServiceProxy<? extends ISimpleService> dummyService;// ȱʡ����

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseServiceOption(int ptid, Class<? extends ISimpleService> svrclass)
	{
		this._portalId = ptid;
		this.svrClass = svrclass;
		try
		{
			methodMap = new SimpleMethodCollection(this.svrClass);
			dummyService = new DummyServiceProxy(this._portalId, this.svrClass, methodMap, false);
		} catch (Exception e)
		{
			e.printStackTrace();
			dummyService = null;
		}
	}

	public int portalId()
	{
		return _portalId;
	}

	public IMethodCollection getMethodMap()
	{
		return methodMap;
	}

	public ISimpleService getDummyService()
	{
		return (dummyService != null) ? dummyService.getServiceProxy() : null;
	}

	public Object invokeDummy(String methodSignature, Object[] args)
	{
		if (methodMap != null)
		{
			Method tmpMethod = methodMap.getMeshodOfMethodSignature(methodSignature);
			return dummyService.invokeService(tmpMethod, args);
		} else
		{
			logger.error("dummy service method invoked:method:" + methodSignature + " was ignored");
			return null;
		}
	}

	public Object invokeMethod(ISimpleService service, String methodSignature, Object[] args)
	{
		if (methodMap != null)
		{
			Method tmpMethod = methodMap.getMeshodOfMethodSignature(methodSignature);
			try
			{
				return tmpMethod.invoke(service, args);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isSyncMethod(String methodSignature)
	{
		return this.methodMap.isSyncMethod(methodSignature);
	}
	
	public Class<? extends ISimpleService> getSvrClass()
	{
		return svrClass;
	}
	
}
