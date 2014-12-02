package com.doteyplay.game.constants.tollgate;

import com.doteyplay.game.constants.IResponseTextId;

/**
 * ����ս���Ľ��
 * 
 * @className:EnterBattleResultType.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��11��4�� ����3:07:22
 */
public enum EnterBattleResultType implements IResponseTextId {
	ENTER_BATTLE(0), // ����ս���ɹ�
	ENTER_NO_Engergy(40091), // ��������
	ENTER_NO_DATA(40092), // �������ݲ�����
	;

	private int textId;

	private EnterBattleResultType(int textId) {
		this.textId = textId;
	}

	@Override
	public int getTextId() {
		// TODO Auto-generated method stub
		return this.textId;
	}

}
