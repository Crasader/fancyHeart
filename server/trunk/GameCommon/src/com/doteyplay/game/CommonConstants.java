package com.doteyplay.game;

public class CommonConstants
{
	public static final byte FALSE = 0;
	public static final byte TRUE = 1;
	
	//���ǹ̶�id Ů
	public static final int ROLE_SPRITEID_WOMEN = 0;
	//���ǹ̶�id ��
	public static final int ROLE_SPRITEID_MEN = 1;
	
	public static final int MAX_AREA_NUM = 10000;
	public static final long PER_AREA_ID_DELTA = Long.MAX_VALUE / MAX_AREA_NUM;
	
	//���������
	public static final int MAX_MONEY = Integer.MAX_VALUE;
	public static final int MAX_ENERGY = Integer.MAX_VALUE;
	//���ȼ�
	public static final int ROLE_MAX_LEVEL = 99;
	public static int[] ROLE_LEVEL_EXP = new int[ROLE_MAX_LEVEL];
	public static int[] NPC_LEVEL_EXP = new int[ROLE_MAX_LEVEL];
	
	
	/**
	 * �û�δ��¼
	 */
	public final static byte USER_STATE_UNLOGIN = 0;
	/**
	 * �û��ѵ�¼
	 */
	public final static byte USER_STATE_LOGEDIN = 1;
	
	//��ͨ�û�
	public final static byte USER_TYPE_DEFAULT = 0;
	//gm�û�
	public final static byte USER_TYPE_GM = 1;
	/**
	 * �ı��滻��ʾ
	 */
	public static final String TEXT_REPLACE_REGULAR_DEFAULT = "$";
	/**
	 * �б�ָ��
	 */
	public static final String COMMON_LIST_TO_STRING_SPLIT_FLAG = ",";

	public static final int DECK_NUM = 30;

	public static final int MAX_CARD_NUM = 200;
	public static final int MAX_SAME_CARD_IN_DECK = 2;

	public static final int ADDTYPE_VALUE = 0;
	public static final int ADDTYPE_RATE = 1;
	
	public static final int SCENE_OBJECT_TYPE_SPRITE = 0;

	public static final int SPRITE_TYPE_HERO = 0;
	public static final int SPRITE_TYPE_PET = 1;
	public static final int SPRITE_TYPE_NPC = 2;
	
    

}



