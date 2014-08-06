package com.doteyplay.luna.common;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * ָ���ʽ����֤��(short)+���ĳ���(short)+ָ���(short)+��������
 * 
 * ��������صĳ���
 */
public class LunaConstants
{

	/**
	 * ��֤��ĳ���
	 */
	public static final byte VALIDATOR_LENGTH = 2;

	/**
	 * ���ĳ���,������У���볤��
	 */
	public static final byte MESSAGE_LENGTH = 4;

	/**
	 * ��ɫ��ŵĳ���4
	 */
	public static final byte ROLEID_LENGTH = 8;
	
	/**
	 * ָ���
	 */
	public static final byte COMMAND_LENGTH = 2;
	
	/**
	 * ����ͬ��������Ψһkey
	 */
	public static final byte SYNKEY_LENGTH = 8;
	/**
	 * ����ͷ����С����,��֤��(short)+���ĳ���(int)+��ɫ���(int)+ָ���(short)+ͬ�����ر�ʾ(long)
	 */
	public static final byte HEADER_LENGTH = VALIDATOR_LENGTH + MESSAGE_LENGTH
			+ ROLEID_LENGTH + COMMAND_LENGTH+SYNKEY_LENGTH;

	/**
	 * ���͸��������˵���֤��
	 */
	public static final short SERVER_VALIDATOR_CODE = 3456;

	/**
	 * �����ͻ��˵���֤��
	 */
	public static final short CLIENT_VALIDATOR_CODE = 8765;

	/**
	 * ���ı��ĳ���
	 */
	public static final int MAX_MESSAGE_LENGTH = Integer.MAX_VALUE;
	/**
	 * ȱʡBuffer�Ĵ�С
	 */
	public static final int DEFAULT_MESSAGE_LENGTH = 2048;
	/**
	 * ��С�ı��ĳ���Ϊֻ�лỰ��źͽ�ɫ��ź�ָ����
	 */
	public static final short MIN_MESSAGE_LENGTH = 6;

	/**
	 * idel����ʱ��
	 */
	public static final byte IDLE_TIME = 60;
	/**
	 * ����
	 */
	public static final CharsetDecoder DECODER = Charset.forName("UTF-8")
			.newDecoder();
	/**
	 * ����
	 */
	public static final CharsetEncoder ENCODER = Charset.forName("UTF-8")
			.newEncoder();

	
	public static final byte FALSE = 0;
	public static final byte TRUE = 1;
}
