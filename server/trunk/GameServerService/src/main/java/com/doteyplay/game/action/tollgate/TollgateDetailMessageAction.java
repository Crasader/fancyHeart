package com.doteyplay.game.action.tollgate;

import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.tollgate.ShowTollgateDetailMessage;
import com.doteyplay.game.service.bo.tollgate.ITollgateInfoService;

/**
 * @className:ShowInstanceDetailMessageAction.java
 * @classDescription: ���û�չʾ��������ϸ��Ϣ��
 * @author:Tom.Zheng
 * @createTime:2014��6��24�� ����4:08:03
 */
public class TollgateDetailMessageAction implements ServiceMessageAction<ShowTollgateDetailMessage, ITollgateInfoService>{

	@Override
	public void processMessage(ShowTollgateDetailMessage message, ITollgateInfoService service)
			throws MessageProcessException {
		// TODO Auto-generated method stub
		ShowTollgateDetailMessage tempMessage =service.showTollgateDetailInfo();
		service.sendMessage(tempMessage);
	}

}
