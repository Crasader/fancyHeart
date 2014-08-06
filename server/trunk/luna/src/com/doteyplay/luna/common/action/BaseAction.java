package com.doteyplay.luna.common.action;

import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.common.message.DecoderMessage;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * ����������Ϣ��Action
 */
public abstract class BaseAction {

	/**
	 * ���е���Ӧ����ʵ�ֵķ���
	 * 
	 * @param message
	 */
	public abstract void doAction(IoSession session, DecoderMessage message);

	/**
	 * ��Ϸ���߼�,�ṩ�����ֱ�ӵ�����Ϸ�߼��ķ���
	 * 
	 * @param roleId
	 */
	public abstract void doAction(long roleId);

	/**
	 * ������Ϣͨ�õķ���
	 * 
	 * @param session
	 * @param message
	 */
	public void messageSent(IoSession session, EncoderMessage message) {
		session.write(message);
	}

}
