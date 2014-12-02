package com.doteyplay.game.domain.chat;

import com.doteyplay.game.domain.gamebean.BaseBean;

/**
 * @className:ChatMsgBean.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��11��26�� ����6:25:49
 */
public class ChatMsgBean extends BaseBean{

	public final static byte MSG_TYPE_ROLE=0;
	
	public final static byte MSG_TYPE_SYS =1;
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014��11��26�� ����6:25:55
	 */
	private static final long serialVersionUID = 7603027283331071796L;
	
	private long sendRoleId;
	
	private String sendRoleName;
	
	private byte sendType = MSG_TYPE_ROLE;//0���û�.1��ϵͳ.
	
	
	private String msg;//������Ϣ
	
	private long sendTimeLong;//������Ϣ��ʱ���.

	public long getSendRoleId() {
		return sendRoleId;
	}

	public void setSendRoleId(long sendRoleId) {
		this.sendRoleId = sendRoleId;
	}

	public byte getSendType() {
		return sendType;
	}

	public void setSendType(byte sendType) {
		this.sendType = sendType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getSendTimeLong() {
		return sendTimeLong;
	}

	public void setSendTimeLong(long sendTimeLong) {
		this.sendTimeLong = sendTimeLong;
	}

	public String getSendRoleName() {
		return sendRoleName;
	}

	public void setSendRoleName(String sendRoleName) {
		this.sendRoleName = sendRoleName;
	}
	
	
	
	

}
