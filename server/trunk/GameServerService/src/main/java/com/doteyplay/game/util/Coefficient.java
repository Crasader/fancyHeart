package com.doteyplay.game.util;

/**
 * ϵ���࣬���е�ϵ������ͨ�������������û�õ�
 */
public class Coefficient {
	/**
	 * Ĭ�ϵĽ�Ǯ����ϵ��
	 */
	private static float  dropMoneyRate=1.0f;
	
	/**
	 * ɱ�־������ϵ��
	 */
	private static float dropExpRate=1.0f;
	
	

	public static float getDropMoneyRate() {
		return dropMoneyRate;
	}

	public static void setDropMoneyRate(float dropMoneyRate) {
		Coefficient.dropMoneyRate = dropMoneyRate;
	}

	public static float getDropExpRate() {
		return dropExpRate;
	}

	public static void setDropExpRate(float dropExpRate) {
		Coefficient.dropExpRate = dropExpRate;
	}
}
