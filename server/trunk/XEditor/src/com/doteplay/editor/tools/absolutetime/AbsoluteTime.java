package com.doteplay.editor.tools.absolutetime;

import java.util.Calendar;

/**
 * @author ���� LC
 * @version ����ʱ�䣺2010-11-9 ����11:30:47 ��˵��
 */
public class AbsoluteTime {
	private static final AbsoluteTime instance = new AbsoluteTime();
	private Calendar c;

	public AbsoluteTime() {
		// TODO Auto-generated constructor stub
	}

	public static AbsoluteTime getInstance() {
		return instance;
	}

	public long getAbsoluteTime() throws Exception {
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			c = JDateChooser.showDialog(null, "��ѡ������", null);
			return c.getTimeInMillis();
		
		
	}

	public static void main(String ages[]) throws Exception {
		System.out.println(AbsoluteTime.getInstance().getAbsoluteTime());
	}

}
