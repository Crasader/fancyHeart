package com.doteyplay.game.constants.tollgate;
/**
 * @className:TollgateErrorType.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��7��18�� ����6:24:50
 */
public enum GroupUpdateResultType {

	Success("�ɹ�"),
	ERROR("����"),
	;
	
	private String msg;
	
	private GroupUpdateResultType(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
}

