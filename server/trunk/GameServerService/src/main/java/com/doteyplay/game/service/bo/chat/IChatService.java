package com.doteyplay.game.service.bo.chat;

import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.game.BOConst;

/**
 * 
 * @className:IChatService.java
 * @classDescription:�������
 * @author:Tom.Zheng
 * @createTime:2014��11��26�� ����5:56:04
 */
public interface IChatService extends ISimpleService {

	public final static int PORTAL_ID = BOConst.BO_CHAT;

	/**
	 * ��ʹ������ģ��
	 */
	public void initlize();

	/**
	 * �ͷ���Դ
	 */
	public void release();
	
	/**
	 * ע�����
	 */
	public void register();
	
	/**
	 * ������Ϣ
	 */
	public void sendMsg(String msgContent);
	
	/**
	 * ȡ��ע��
	 */
	public void cancleRegister();
	/**
	 * ����GM����
	 * @param source
	 */
	public void doGm(String source);

}
