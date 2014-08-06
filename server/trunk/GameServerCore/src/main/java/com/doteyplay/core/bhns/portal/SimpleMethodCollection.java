package com.doteyplay.core.bhns.portal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.service.CoreServiceConstants;

/**
 * �������ÿ�������
 * 
 */
public class SimpleMethodCollection implements IMethodCollection
{
	/**
	 * key :���������֡�ƴ�Ĵ���Value ������
	 */
	private Map<String, Method> methodMap;
	/**
	 * key :������String ���������֡�ƴ�Ĵ�
	 */
	private Map<Method, String> methodSignatureMap;
	/**
	 * ��ͬ�����Ƶķ���
	 */
	private Set<String> syncMethodSet; 

	public SimpleMethodCollection(Class<?> refclass)
	{
		methodMap = new HashMap<String, Method>();
		methodSignatureMap = new HashMap<Method, String>();
		syncMethodSet = new HashSet<String>();
		init(refclass);
	}

	private void init(Class<?> refclass)
	{
		if (refclass != null)
		{
			Method[] tmpMethods = refclass.getMethods();
			if (tmpMethods != null)
			{
				Class<?>[] tmpParams;
				StringBuffer tmpSignatureBuffer;
				String tmpSignature;
				for (Method tmpMethod : tmpMethods)
				{
					tmpSignatureBuffer = new StringBuffer(tmpMethod.getName());
					tmpParams = tmpMethod.getParameterTypes();
					if (tmpParams != null)
					{
						for (Class<?> tmpParamClass : tmpParams)
						{
							tmpSignatureBuffer.append(tmpParamClass.getSimpleName());
						}
					}
					tmpSignature = tmpSignatureBuffer.toString();
					methodMap.put(tmpSignature, tmpMethod);
					methodSignatureMap.put(tmpMethod, tmpSignature);
					
					//ͬ����ʶ
					if(tmpSignature.toLowerCase().indexOf(CoreServiceConstants.SYNC_METHOD_FLAG) >= 0)
						syncMethodSet.add(tmpSignature);
				}
			}
		}
	}
	/**
	 * ���÷��� ����.
	 * methodSignature : ������+����������+����������....���
	 * refobj ����
	 * args ���еĲ�����
	 */
	public Object invokeMethod(String methodSignature, Object refobj, Object[] args)
	{
		if (methodSignature == null || refobj == null)
			return null;

		Method tmpMethod = this.methodMap.get(methodSignature);
		if (tmpMethod == null)
			return null;

		try
		{
			return tmpMethod.invoke(refobj, args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Method getMeshodOfMethodSignature(String methodSignature)
	{
		return (methodSignature != null) ? methodMap.get(methodSignature) : null;
	}

	public String getMethodSignature(Method method)
	{
		return (method != null) ? methodSignatureMap.get(method) : null;
	}

	@Override
	public boolean isSyncMethod(String methodSignature)
	{
		return this.syncMethodSet.contains(methodSignature);
	}

}
