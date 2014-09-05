package com.doteyplay.game.constants.tollgate;
/**
 * @className:NodeUpdateState.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��8��14�� ����3:24:48
 */
public enum NodeUpdateState {
	ERROR("��������!"),
	UPDATE("��Ҫ���µ�DB"),
	NOUPDATE("���ø��µ�DB"),
	;
	
	private String msg;
	
	private NodeUpdateState(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
}
