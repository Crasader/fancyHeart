package com.doteyplay.luna.single;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/**
 * �������ӷַ����Ƶ�Controller����Ҫ����ʵ��
 */
public interface SingleActionController {
	/**
	 * ����Decoder��ָ���Ż�ö�Ӧ��Action
	 * @param message
	 * @return
	 */
	public void doAction(IoSession session,IoBuffer buffer);
	/**
	 * ���ͻ��˹رյ�ʱ���������������
	 * @param session
	 */
	public void sessionClose(IoSession session);
}
