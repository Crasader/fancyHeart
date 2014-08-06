package com.doteyplay.core.util;

/**
 * ������
 * 
 * @author 
 * 
 */
public class StrUtils {
	/**
	 * �ж��Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean empty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * �ַ����滻
	 * 
	 * @param text
	 * @param repl
	 * @param with
	 * @return
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * �ַ����滻
	 * 
	 * @param text
	 * @param repl
	 * @param with
	 * @param max
	 * @return
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (text == null || repl == null || with == null || repl.length() == 0
				|| max == 0)
			return text;
		StringBuffer buf = new StringBuffer(text.length());
		int start = 0;
		int end = 0;
		do {
			if ((end = text.indexOf(repl, start)) == -1)
				break;
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();
		} while (--max != 0);
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * �趨�ַ���Ϊ����
	 */

	public static int parseInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * �趨�ַ���Ϊboolean
	 */

	public static boolean parseBoolean(String value, boolean defaultValue) {
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
