package com.doteplay.editor.datahelper;

import java.util.HashMap;

import com.doteplay.editor.util.xml.simpleXML.ISimpleXmlSupport;
import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;
import com.doteplay.editor.util.xml.simpleXML.SimpleXmlParser;
import com.doteplay.editor.util.xml.simpleXML.StrUtils;

/*
 * Ԫ����ģ�嶨�塣 ��������������Ԫ���ݣ����Ŀ�꣺Ϊ�𲽽��͸�������֮�����϶ȣ�����ṩһ�ָ�Ч�ı�����ģʽԪ�������ͣ�����ȡ����ͬ����ֱ�ӵ�ֱ�ӷ���
 * ���Ҫ�� 1��ҵ���޹أ���ȫ����Ԫ����Ҫ��; 2����Ч����֤�����Ĺ���Ч��;
 * 3�����������ܼ�鵽������Դ�ͱ༭ģʽ;4���ϵ��ڴ�ʹ�ã���֤��ͨPC��ֱ�ӿ���
 */
public class MetaDataScheme implements ISimpleXmlSupport
{

	public final static int COUNT_DATA_TYPE = 3;// ������������
	public final static int DATA_TYPE_STRING = 0;// �ַ�������
	public final static int DATA_TYPE_INT = 1;// ��������
	public final static int DATA_TYPE_DATE = 2;// �ַ�������

	private String dataGroup;
	private HashMap<String, MetaDataField> dataMap;
	private int[] fieldCounts;

	public MetaDataScheme()
	{
		dataGroup = "unknown";
		dataMap = new HashMap<String, MetaDataField>();
		fieldCounts = new int[COUNT_DATA_TYPE];
	}

	public void reset()
	{
		dataMap.clear();
		for (int i = 0; i < COUNT_DATA_TYPE; i++)
			fieldCounts[0] = 0;
	}

	public void regField(String dataname, String datadesc, int datatype)
			throws MetaDataException
	{
		if (dataname != null && dataname.length() > 0 && datatype >= 0
				&& datatype < COUNT_DATA_TYPE && !dataMap.containsKey(dataname))
		{
			MetaDataField newField = null;
			if (datatype >= 0 && datatype < COUNT_DATA_TYPE)
				newField = new MetaDataField(dataname, datadesc, datatype,
						fieldCounts[datatype]++);
			if (newField != null)
			{
				dataMap.put(dataname, newField);
				newField = null;
				return;
			} else
				throw new MetaDataException("Ԫ�����ֶ�" + dataname + "������Ч");
		}
		throw new MetaDataException("Ԫ�����ֶ�" + dataname + "ע��ʧ��");
	}

	public void loadDataScheme(String schemecontent)
	{
		SimpleXmlParser.parseXmlData(schemecontent, this);
	}

	public void loadDataSchemeFile(String fname)
	{
		SimpleXmlParser.parseXmlFramFile(fname, this);
	}

	public boolean fieldExist(String fname)
	{
		return (fname != null && dataMap.containsKey(fname));
	}

	public int getTypeFieldCount(int datatype)
	{
		if (datatype >= 0 && datatype < COUNT_DATA_TYPE)
			return fieldCounts[datatype];
		else
			return 0;
	}

	public MetaDataField fieldInfo(String fieldname)
	{
		if (fieldname != null)
			return (MetaDataField) this.dataMap.get(fieldname);
		else
			return null;
	}

	@Override
	public void readXmlData(String nodename, SimpleAttributes attributes)
	{
		if ("field".equals(nodename))
		{
			try
			{
				String tmpName = attributes.getValue("name");
				String tmpDesc = attributes.getValue("desc");
				int tmpDataType = StrUtils.parseint(
						attributes.getValue("datatype"), DATA_TYPE_STRING);
				this.regField(tmpName, tmpDesc, tmpDataType);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else if ("datagroup".equals(nodename))
		{
			try
			{
				this.dataGroup = attributes.getValue("name");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setDataGroup(String dataGroup)
	{
		this.dataGroup = dataGroup;
	}

	public String getDataGroup()
	{
		return dataGroup;
	}

}
