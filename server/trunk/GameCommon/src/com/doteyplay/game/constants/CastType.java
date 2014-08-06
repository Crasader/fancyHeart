package com.doteyplay.game.constants;

/**
 * ʩ�ŷ����ķ�ʽ
 */
public enum CastType
{
	/**
	 * ��ͨʩ�ŷ�ʽ
	 */
	NORMAL,
	/**
	 * Ч����������ʩ�ŷ�ʽ
	 */
	TRIGGER,
	/**
	 * BUFF��ʩ��
	 */
	BUFF, ;
	private static final boolean[] installBuff = { true, true, false };
	private static final boolean[] activateSourceEvent = { true, false, false };
	private static final boolean[] activateTargetEvent = { true, true, true };

	/**
	 * ָʾ��ʩ�ŷ�ʽ�Ƿ�ΪĿ�갲װbuff(����еĻ�)
	 */
	public boolean installBuff()
	{
		return installBuff[ordinal()];
	}

//	/**
//	 * ָʾ��ʩ�ŷ�ʽ�Ƿ񴥷�Դ�¼�
//	 */
//	public boolean activateSourceEvent()
//	{
//		return activateSourceEvent[ordinal()];
//	}
//
//	/**
//	 * ָʾ��ʩ�ŷ�ʽ�Ƿ񴥷�Ŀ���¼�
//	 */
//	public boolean activateTargetEvent()
//	{
//		return activateTargetEvent[ordinal()];
//	}
}
