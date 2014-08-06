package com.doteyplay.game.service.bo.virtualworld.chat.handle;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.domain.gamebean.RoleBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.persistence.serverdata.role.IRoleBeanDao;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.IPatameterObject;
import com.doteyplay.game.service.runtime.GlobalRoleCache;
import common.Logger;

/**
 * @className:AbstractHandlerImpl.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��7��10�� ����5:40:59
 */
public abstract class AbstractHandlerImpl<T extends IPatameterObject > implements IChatHandler<T>{

	private static  Logger logger  = Logger.getLogger(AbstractHandlerImpl.class);
	
	@Override
	public void handle(T t) {
		// TODO Auto-generated method stub
		for(Long roleId:t.getRoleIds()){
			Role role = GlobalRoleCache.getInstance().getRoleById(roleId);
			if(role != null){
				onlineHandle(role, t);
			}else{
				if(!checkRoleExsit(roleId)){
					logger.error("GM do ��ɫ��Ʒ���,Id��Ӧ�Ľ�ɫ������");
				}
				offlineHandle(roleId, t);
			}
			
		}
	}
	
	public abstract void onlineHandle(Role role,T t);
	
	public abstract void offlineHandle(long roleId,T t);
	
	private boolean checkRoleExsit(long roleId){
		//�����ʼ�����.������Ʒ��ֱ�Ӳ���DB
		IRoleBeanDao dao = DBCS.getExector(IRoleBeanDao.class);
		
		RoleBean roleBean = dao.selectRoleBean(roleId);
		
		if(roleBean == null){
//			throw new RuntimeException("GM do ��ɫ��Ʒ���,Id��Ӧ�Ľ�ɫ������");
			return false;
		}
		return true;
	}
	
}
