package com.doteplay.editor.data.text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataManager;
import com.doteyplay.game.gamedata.data.TextGameData;

public class TextData extends BaseData<TextGameData>
{

	public TextData()
	{
		super(new TextGameData());
		type = "text";
		dataGroup = DataManager.getDataGroup(type);
	}

	
	@Override
	public String toString()
	{
		String s = super.toString();
		s += "(" + dataType + ")";
		return s;
	}

	// // TODO:=======Excel=====================================================
	// /**
	// * ��ȡExcel���ݣ�������ʵ��
	// */
	public String[] getExcelData()
	{
		String[] ss = new String[]
		{ id, gameData.name, gameData.text};
		return ss;
	}
	
	/**
	 * ����Excel���ݣ�������ʵ��
	 */
	public void setExcelData(String[] ss)
	{
		id = ss[0];
		if(gameData == null)
			gameData = new TextGameData();
		gameData.name = ss[1];
		gameData.text = ss[2];
	}
}
