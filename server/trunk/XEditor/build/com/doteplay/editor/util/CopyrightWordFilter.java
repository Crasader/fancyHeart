/**
 * @package com.doteplay.editor.util
 * @file StringReplacer.java
 */
package com.doteplay.editor.util;

import com.doteplay.editor.EditorConfig;


/**
 * ����涨�Ĺؼ��ֹ���
 */
public class CopyrightWordFilter {
	/**
	 * �滻�б�
	 * ע����б�˳���
	 */
	public final static String[][] Replace_Table = new String[][] { 

		{ "ɱ", "����", }, 
	};
	
	private static CopyrightWordFilter instance;

	private CopyrightWordFilter() {}

	public static CopyrightWordFilter getInstance() {
		if (instance == null) {
			instance = new CopyrightWordFilter();
		}
		return instance;
	}

	public String replace(String src) {
		if(EditorConfig.IS_OPEN_COPYRIGHT_WORD_FILTER == false){
			return src;
		}
		String s = src;
		for (int i = 0; i < Replace_Table.length; i++) {
			String regex = Replace_Table[i][0];
			String replacement = Replace_Table[i][1];
			s = s.replaceAll(regex, replacement);
		}
		
		if (!s.equals(src)) {
			System.out.println(src + " ---> " + s);
		}
		
		return s;
	}

	public static void main(String[] args) {
		String s = "������ú�Ѫɷ����ɱ������";
		s = CopyrightWordFilter.getInstance().replace(s);
	}
}
