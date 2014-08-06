package com.doteplay.editor.common;

import java.sql.Timestamp;

import com.doteplay.editor.EditorConfig;

public abstract class AbstractVersionData extends AbstractData
{

	public int lastEditUserId;// ����ύ�û�ID
	public String lastEditUserName = "";// ����ύ�û���
	public String lastEditorVersion = "";// �༭���汾
	public Timestamp lastEditorDateTime;// �༭��ʱ��
	public int version = 0;// ���ذ汾
	public int db_version = 0;// ���ݿ�汾

	/**
	 * �汾�����Ƿ�ɱ༭ �������Ʊ༭�ͱ��� true : ������ȱʡ�汾���Զ��Ա༭�ͱ�������. false :
	 * ֻ����ȱʡ�汾�±༭�ͱ�������,�����汾û�����ݻ�ϵͳ����.
	 */
	public boolean versionDataEditable;
	// /**
	// * �汾�������ɱ�־
	// * 0:ȱʡ���� 1:�Զ���
	// */
	/**
	 * �汾��ֵת��ϵ��
	 */
	protected static float[][] versionValueModulus = new float[1][2];

	protected void initVersionValueModulus()
	{
		versionValueModulus[1][0] = 1f;
		versionValueModulus[1][1] = 1f;
	}

	public boolean isVersionDataValid(int versionType)
	{
		return true;
	}

	/**
	 * ���ָ���汾��ת����ֵ
	 * 
	 * @param vtype
	 * @param value
	 * @param axisType
	 * @return
	 */
	public static int getVersionValue(int vtype, int value, int axisType)
	{
		return getVersionValue(EditorConfig.VERSION_DEFAULT_TYPE, vtype, value,
				axisType);
	}

	/**
	 * ��ָ���汾�仯Ϊ�����汾
	 * 
	 * @param fromVersionType
	 * @param toVersionType
	 * @param value
	 * @param axisType
	 * @return
	 */
	public static int getVersionValue(int fromVersionType, int toVersionType,
			int value, int axisType)
	{
		if (fromVersionType == toVersionType)
			return value;

		float f = versionValueModulus[toVersionType][axisType];
		float f_default = versionValueModulus[fromVersionType][axisType];
		if (f == f_default)
			return value;
		if (value < 0)
			f = value * f / f_default - 0.5f;
		else
			f = value * f / f_default + 0.5f;
		// f=value*f;
		return (int) f;
	}

	/**
	 * ��õ�ǰ�汾��ת����ֵ
	 * 
	 * @param value
	 * @param axisType
	 * @return
	 */
	public static int getVersionValue(int value, int axisType)
	{
		return getVersionValue(EditorConfig.VERSION_TYPE, value, axisType);
	}

	public int getDefaultVersionValue(int value, int axisType)
	{
		return getDefaultVersionValue(EditorConfig.VERSION_TYPE, value,
				axisType);
	}

	public int getDefaultVersionValue(int vtype, int value, int axisType)
	{
		return getVersionValue(vtype, EditorConfig.VERSION_DEFAULT_TYPE, value,
				axisType);
	}

	public boolean isDeferrentVersion()
	{
		if (isInDB == 1)
		{
			if (version != db_version)
				return true;
		}
		return false;
	}

	public int getLastEditUserId()
	{
		return lastEditUserId;
	}

	public String getLastEditUserName()
	{
		return lastEditUserName;
	}

	public String getLastEditorVersion()
	{
		return lastEditorVersion;
	}

	public Timestamp getLastEditorDateTime()
	{
		return lastEditorDateTime;
	}

	public int getVersion()
	{
		return version;
	}

	public int getDb_version()
	{
		return db_version;
	}

}
