package com.doteyplay.game.constants.chat;
/**
 * @className:ChatUtils.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��7��10�� ����5:04:18
 */
public class ChatUtils {

	/**
	 * ��source�ַ����Ŀ�ͷ,��ȡsuffix��.
	 * @param source
	 * @param suffix
	 * @return ʣ��Ĵ�
	 */
	public static String  doGmPre(String source,String suffix){
		int beginIndex = suffix.length();
		int endIndex = source.length();
		String data=source.substring(beginIndex, endIndex);
		if(data==null){
			return null;
		}
		return data;
	}
}
