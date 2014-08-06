package com.doteyplay.game.service.bo.virtualworld.chat.handle;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.constants.common.RewardType;
import com.doteyplay.game.domain.gamebean.RoleBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.persistence.serverdata.role.IRoleBeanDao;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.EnergyPatameterObject;

/**
 * @className:EnergyHandlerImpl.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��7��10�� ����4:39:01
 */
public class EnergyHandlerImpl extends AbstractHandlerImpl<EnergyPatameterObject>{

	@Override
	public void onlineHandle(Role role, EnergyPatameterObject t) {
		// TODO Auto-generated method stub
		role.addEnergy( t.getNum(), RewardType.CHAT, true);
	}

	@Override
	public void offlineHandle(long roleId, EnergyPatameterObject t) {
		// TODO Auto-generated method stub
		//�����ʼ�����.��������ֵ.
		IRoleBeanDao dao = DBCS.getExector(IRoleBeanDao.class);
		
		RoleBean roleBean = dao.selectRoleBean(roleId);
		
		if (roleBean == null) {
			throw new RuntimeException("GM do ��ɫ�������,Id��Ӧ�Ľ�ɫ������");
		}
		roleBean.setEnergy(( t.getNum()+roleBean.getEnergy())>0? t.getNum()+roleBean.getEnergy():0);
		
		dao.updateRoleBean(roleBean);
	}

}
