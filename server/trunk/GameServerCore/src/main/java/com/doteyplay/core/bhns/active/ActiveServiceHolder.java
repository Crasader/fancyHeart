package com.doteyplay.core.bhns.active;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.util.dependence.DepandenceSorter;
import com.doteyplay.core.util.dependence.DependenceScaner;
import com.doteyplay.core.util.dependence.IDependence;
import com.doteyplay.core.util.dependence.IDependenceAssembly;
import com.doteyplay.core.util.xml.IParamterSupport;
import com.doteyplay.core.util.xml.ISimpleParamters;
import com.doteyplay.core.util.xml.XmlFileSupport;

/**
 * ����ļ������
 * 
 * @author
 * 
 */
public class ActiveServiceHolder implements IParamterSupport,
		IDependenceAssembly
{
	private static final Logger logger = Logger
			.getLogger(ActiveServiceHolder.class);
	/**
	 * ����service�����ñ�.���ڶ�ȡ���е�����.
	 */
	public static String ACTIVESERVICE_CONFIG_FILE = "/active_service.xml";
	/**
	 * ����������Service.<Key��Name,Value��ActiveServiceInfo.��Ϣ�ļ򵥷�װ>
	 */
	private Map<String, ActiveServiceInfo> activeInfoMap = new HashMap<String, ActiveServiceInfo>();
	/**
	 * ����service.<Key�Ƿ�������Id,Value�Ƿ���ļ���Ϣ��װ>
	 */
	private Map<Integer, ActiveServiceInfo> activeIdInfoMap = new HashMap<Integer, ActiveServiceInfo>();
	/**
	 * ������List
	 */
	private List<String> serivceSortedList = new ArrayList<String>();

	public void load_config(String configfile)
	{
		if (configfile != null && configfile.length() > 0)
			ACTIVESERVICE_CONFIG_FILE = configfile;
		// ��ȡ�����ļ�.�����ñ�������Ϊ������,����xml��ÿһ��Ԫ��.ֱ�ӵ���this.putParamter()����.��ȡ����.
		XmlFileSupport.parseXmlFromResource(ACTIVESERVICE_CONFIG_FILE, this);

		boolean check = this.checkDependRelation();
		if (!check)
			throw new RuntimeException("activeService depaendRelation error");

		DepandenceSorter sorter = new DepandenceSorter();
		for (ActiveServiceInfo info : activeInfoMap.values())
			sorter.addElement(String.valueOf(info.getPortalId()),
					info.getDependence());

		serivceSortedList = sorter.sort();
	}

	public void active_all_service(long serviceId)
	{
		if (serviceId <= 0)
			return;

		if (serivceSortedList != null && serivceSortedList.size() > 0)
		{
			int portalId = Integer.parseInt(serivceSortedList.get(0));
			ActiveServiceInfo info = activeIdInfoMap.get(portalId);
			BOServiceManager.activeService(info.getPortalId(), serviceId,
					info.getRule(), true);
		}
	}

	public void active_next_service(int portalId, long serviceId)
	{
		int curIndex = serivceSortedList.indexOf(String.valueOf(portalId));
		int nextIndex = 0;
		if (curIndex < serivceSortedList.size() - 1)
		{
			nextIndex = curIndex + 1;

			int nextPortalId = Integer.parseInt(serivceSortedList
					.get(nextIndex));
			ActiveServiceInfo info = activeIdInfoMap.get(nextPortalId);
			BOServiceManager.activeService(info.getPortalId(), serviceId,
					info.getRule(), true);
		} else
			return;
	}

	public void active_service(int portalId, long serviceId)
	{
		ActiveServiceInfo info = activeIdInfoMap.get(portalId);
		BOServiceManager.activeService(info.getPortalId(), serviceId,
				info.getRule(), false);
	}

	/**
	 * ��ȡactive_service.xml������. ��װ��Ϣ,��װ����Map��ȥ.
	 */
	@Override
	public void putParamter(ISimpleParamters paramter)
	{
		if ("SERVICE".equals(paramter.getDataName()))
		{ // ��ȡName
			String name = paramter.getValue("NAME");
			// portalId ���Id.һ������,��Ϊһ�����
			String portalIdStr = paramter.getValue("PORTALID");
			// ���������������Id.�����Id.�����붺�ŷָ�,д����.
			String dependencesStr = paramter.getValue("DEPENDENCES");
			/**
			 * ����.
			 */
			String ruleStr = paramter.getValue("RULE");
			if (name == null || name.length() <= 0 || portalIdStr == null
					|| portalIdStr.length() <= 0)
			{
				logger.error("Invalid active setting,name=" + name
						+ ",portalId=" + portalIdStr);
				return;
			}
			String[] tmpDependenceList = null;
			if (dependencesStr != null)
				dependencesStr = dependencesStr.trim();

			if (dependencesStr != null && dependencesStr.length() > 0)
				tmpDependenceList = dependencesStr.trim().toUpperCase()
						.split(",");
			else
				tmpDependenceList = null;

			int portalId = Integer.parseInt(portalIdStr);
			// ������ڵ���Ϣ������.
			ActiveServiceInfo activeInfo = new ActiveServiceInfo(name,
					portalId, tmpDependenceList, ruleStr);
			this.activeInfoMap.put(name.toUpperCase(), activeInfo);
			this.activeIdInfoMap.put(portalId, activeInfo);
		}
	}

	@Override
	public void onComplete()
	{
	}

	/**
	 * �������е�xml��Ϣ��,���м��������ϵ.
	 * 
	 * @return false Ϊ��������ϵ���Ϸ�. true ��������.
	 */
	public boolean checkDependRelation()
	{
		Collection<ActiveServiceInfo> tmpBlockInfos = activeInfoMap.values();
		if (tmpBlockInfos != null)
		{
			for (ActiveServiceInfo tmpBlockInfo : tmpBlockInfos)
			{
				if (!DependenceScaner.checkValidation(String.valueOf(tmpBlockInfo.getPortalId()),
						this))
				{
					logger.error("dependence of " + tmpBlockInfo.getName()
							+ " is invalid");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public IDependence getItem(String name)
	{
		if (name != null)
			return activeIdInfoMap.get(Integer.parseInt(name));
		else
			return null;
	}

	// **********************************************************************
	private static ActiveServiceHolder instance;

	private synchronized static ActiveServiceHolder getInstance()
	{
		if (instance == null)
		{
			instance = new ActiveServiceHolder();
		}
		return instance;

	}

	public static void initialize()
	{
		ActiveServiceHolder tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			tmpDataStore.load_config(ACTIVESERVICE_CONFIG_FILE);
		}
	}

	public static void activeAllService(long serviceId)
	{
		ActiveServiceHolder tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			tmpDataStore.active_all_service(serviceId);
		}
	}

	public static void activeNextService(int curPortalId, long serviceId)
	{
		ActiveServiceHolder tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			tmpDataStore.active_next_service(curPortalId, serviceId);
		}
	}
}
