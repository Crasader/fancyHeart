package com.doteyplay.luna.single;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.common.LunaConstants;
import com.doteyplay.luna.common.protocol.DefaultProtocolHandler;

/**
 * ��Ϣ�����Handler
 */
public class SingleProtocolHandler extends IoHandlerAdapter {

	private Logger logger = Logger.getLogger(DefaultProtocolHandler.class
			.getName());
	/**
	 * ִ��Action�ַ��Ŀ����࣬����Handlerʵ����ʱ���������
	 */
	private SingleActionController actionCntroller;

	public SingleProtocolHandler(SingleActionController controller) {
		this.actionCntroller = controller;
	}

	@Override
	public void sessionCreated(IoSession session) {
		if (this.logger.isDebugEnabled())
			this.logger.debug("�����Ự!");
	}

	@Override
	public void sessionOpened(IoSession session) {
		if (this.logger.isDebugEnabled())
			this.logger.debug("�򿪻Ự!");
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE,
				LunaConstants.IDLE_TIME);
	}

	@Override
	public void sessionClosed(IoSession session) {
		if (this.logger.isDebugEnabled())
			this.logger.debug("�رջỰ!");
		if (actionCntroller != null)
			actionCntroller.sessionClose(session);
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		if (this.logger.isDebugEnabled())
			this.logger.debug("���յ�����Ϣ: " + message.toString());
		actionCntroller.doAction(session, (IoBuffer) message);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		if (this.logger.isDebugEnabled())
			this.logger.debug("Idle ״̬:" + status.toString());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		this.logger.error("�Ự�����쳣��" + session.getRemoteAddress(), cause);
	}

	public SingleActionController getActionCntroller() {
		return this.actionCntroller;
	}

	public void setActionCntroller(SingleActionController actionCntroller) {
		this.actionCntroller = actionCntroller;
	}
}
