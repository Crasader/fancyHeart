package com.doteyplay.game.message.utils;

import java.util.List;

import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.message.common.CommonTipsMessage;

/**
 * @className:TipsMessageUtils.java
 * @classDescription: ����ͨ����ʾ��Ϣ
 * @author:Tom.Zheng
 * @createTime:2014��7��11�� ����3:13:53
 */
public class TipsMessageUtils {

	/**
	 * ����������.0:��ֵ����.1:��Դ��ַ����.��Ҫ���η���.
	 */
	public final static byte param_data = 0;
	
	public final static byte param_resurceId = 1;
	
	/*
	 * ��Ϣ��״̬��
	 */
	public final static byte state_failure = 0;
	public final static byte state_success = 1;
	public final static byte state_tip = 2;
	
	/**
	 * �ı���,������������õı��.
	 */
	public final static String aimStr = "$";
	
	/**
	 * parm���ַ�����,�������ݺ����͵��м����.����.
	 */
	public final static String connectStr =",";

	public static CommonTipsMessage newMsg(byte state,int msgId){
		CommonTipsMessage msg = new CommonTipsMessage(state,msgId);
		return msg;
	}
	public static CommonTipsMessage newFailureMsg(int msgId){
		return newMsg(state_failure, msgId);
	}
	public static CommonTipsMessage newSuccessMsg(int msgId){
		return newMsg(state_success, msgId);
	}
	public static CommonTipsMessage newTipMsg(int msgId){
		return newMsg(state_tip, msgId);
	}

	public static CommonTipsMessage newMsg(byte state, int msgId,
			List<String> params) {
		CommonTipsMessage msg = new CommonTipsMessage(state,msgId,params);
		return msg;
	}

	
	public static void sendMsg(CommonTipsMessage msg,Role... roles){
		for (Role role : roles) {
			role.sendMsg(msg);
		}
	}
	
	public static void main(String[] args) {
		String x = "$$$$Xxxx$xxx$$";
		
		int count=count(x, "$$");
		
		System.out.println(count);
	}
	
	public static String connectStr(String p,byte type){
		return p+connectStr+type;
	}
	
	public static String connectDataStr(String p){
		return connectStr(p,param_data);
	}
	
	public static String connectResourceIdStr(String p){
		return connectStr(p,param_resurceId);
	}
	
	
	
	/**
	 * �����ַ�����,��׼�ַ��ĸ���.
	 * @param source
	 * @return
	 */
	public static int count(String source,String aimStr){
		int count = 0;
		int i = source.indexOf(aimStr);// ���ҵ�һ���ַ�������λ��
		if (i != -1)
			count = 1;

		for (int j = i; j < source.length();) {
			int index = source.indexOf(aimStr, j + aimStr.length());
			if (index != -1) {
				count++;
				j = index;
			} else {
				break;
			}
		}

		return count;
	}
}
