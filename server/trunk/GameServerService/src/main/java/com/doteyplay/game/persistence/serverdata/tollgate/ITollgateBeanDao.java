package com.doteyplay.game.persistence.serverdata.tollgate;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.TollgateBean;

/**
 * @className:IRoleInstanceBeanDao.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��6��23�� ����2:55:35
 */
public interface ITollgateBeanDao extends IDaoExecutor {
	// ��ȡ��ǰ�û��Ĺؿ���Ϣ
	@DAOInfo(Params = "")
	public TollgateBean selectTollgateBean(long roleId);
	//���ӵ�ǰ�û��Ĺؿ���Ϣ
	@DAOInfo(Params = "")
	public void insertTollgateBean(TollgateBean tollgateBean);
	//���µ�ǰ�û��Ĺؿ���Ϣ.
	@DAOInfo(Params = "")
	public void updateTollgateBean(TollgateBean tollgateBean);
}
