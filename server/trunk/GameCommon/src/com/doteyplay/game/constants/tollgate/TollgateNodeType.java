package com.doteyplay.game.constants.tollgate;

/**
 * @className:TollgateNodeType.java
 * @classDescription: �ؿ�����.
 * @author:Tom.Zheng
 * @createTime:2014��6��30�� ����5:43:22
 */
public enum TollgateNodeType {
	ENTER_TOLLGATENODE(-1), 
	ENTER_BATTLE(0), ;
	/**
	 * ��ʹ���Ǽ�
	 */
	private int starInitNum;

	private TollgateNodeType(int starInitNum) {
		this.starInitNum = starInitNum;
	}

	public byte getStarInitNum() {
		return (byte)starInitNum;
	}

	
}
