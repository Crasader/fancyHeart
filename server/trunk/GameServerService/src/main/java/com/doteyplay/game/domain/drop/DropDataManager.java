package com.doteyplay.game.domain.drop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.game.config.template.DropDataTemplate;
import com.doteyplay.game.service.bo.item.IItemService;
import com.doteyplay.game.util.GameUtil;
import com.doteyplay.game.util.excel.TemplateService;

/**
 * @className:DropDataManager.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��10��14�� ����5:48:50
 */
public class DropDataManager {

	
	private static DropDataManager instantce =null;
	
	/**
	 * ��̬���ݻ���.�����еĵ���������,����Id����.
	 */
	private static Map<Integer,List<DropDataTemplate>> dropGroupMap = new HashMap<Integer,List<DropDataTemplate>>();
	/**
	 * ��̬���ݻ���.���û��ĸ�������ս������Ʒ��������.
	 */
	private static Map<Long,List<DropDataTemplate>>  roleTollgateDropDataCache = new HashMap<Long,List<DropDataTemplate>>();
	
	private DropDataManager(){
		if(dropGroupMap.isEmpty()){
			init();
		}
	}
	
	
	public static DropDataManager getInstance(){
		if(instantce==null){
			synchronized (dropGroupMap) {
				if(instantce==null){
					instantce =  new DropDataManager();
				}
			}
		}
		return instantce;
	}
	
	private void init() {

		Map<Integer, DropDataTemplate> all = TemplateService.getInstance()
				.getAll(DropDataTemplate.class);

		for (int sourceKey : all.keySet()) {
			int key = sourceKey / 1000;//ȡǰ��λ.
			List<DropDataTemplate> list = dropGroupMap.get(key);
			if (list == null) {
				list = new ArrayList<DropDataTemplate>();
				dropGroupMap.put(key, list);
			}
			list.add(all.get(sourceKey));
		}
	}
	/**
	 * ���ظ����ĵ�����Ʒ.
	 * 
	 * @param roleId
	 * @param dropId
	 * @param num
	 * @param isCreate Ϊtrueʱ,�����������,��ȥ����.  Ϊfalseʱ,ȡ����,�����,����,û�м�����,������.
	 * @return
	 */
	public List<DropDataTemplate> getTollgateDropGroup(long roleId,int dropId,int num,boolean isCreate){
		
		if(isCreate){
			roleTollgateDropDataCache.remove(roleId);
		}
		List<DropDataTemplate> list = roleTollgateDropDataCache.get(roleId);
		
		if(list==null){
			list =getRollDropGroup(dropId, num);
			roleTollgateDropDataCache.put(roleId, list);
		}
		
		return list;
	}
	
	/**
	 * ������������е���Ʒ
	 */
	public List<DropDataTemplate> getRollDropGroup(int dropId,int num){
		List<DropDataTemplate>  list = new ArrayList<DropDataTemplate>();
		
		List<DropDataTemplate> dropList = dropGroupMap.get(dropId);
		
		if(dropList==null||dropList.size()==0){
			return null;
		}
		
		while(true){
			for(DropDataTemplate dropItem:dropList){
				
				int rate = dropItem.getRate();
				//���֮����.
				int random = GameUtil.random(0, 10000);
				
				if(random<rate){
					list.add(dropItem);
					if(list.size()>=num){
						break;
					}
				}
				
			}
			if(list.size()>=num){
				break;
			}
		}
		
		return list;
	}
	
	/**
	 * ������������Ʒ.
	 * @param rollDropGroup 
	 * @param roleId 
	 */
	public boolean rewardDropItem(long roleId, List<DropDataTemplate> rollDropGroup){
		
		if(rollDropGroup==null||rollDropGroup.size()==0){
			return false;
		}
		IItemService itemService = BOServiceManager.findService(
				IItemService.PORTAL_ID, roleId);
	
		for (DropDataTemplate dropData : rollDropGroup) {
			try {
				itemService.lock();
				itemService.addOrRemoveItem(dropData.getItemId(), dropData.getNum(), true);
			} finally {
				itemService.unlock();
			}
			
		}
		clearHistory(roleId);
		return true;
	}


	public void clearHistory(long serviceId) {
		// TODO Auto-generated method stub
		if(roleTollgateDropDataCache!=null&&serviceId>0){
			roleTollgateDropDataCache.remove(serviceId);
		}
	}
	
	
	
}
