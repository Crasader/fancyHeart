package com.doteyplay.luna.common.action;

import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.common.message.DecoderMessage;

/**
 * ��Action���зַ���Controller�����еķַ�������ʵ������ӿ�
 * 
 */
public interface ActionController {
	/**
	 * ����Decoder��ָ���Ż�ö�Ӧ��Action
	 * 
	 * @param message
	 * @return
	 */
	public BaseAction getAction(DecoderMessage message);

	/**
	 * ���ͻ��˹رյ�ʱ���������������
	 * 
	 * @param session
	 */
	public void sessionClose(IoSession session);
	
	/**
	 * �������ӵ�ʱ����ô˷���
	 * @param session
	 */
	public void sessionOpen(IoSession session);
}
