package com.doteplay.editor.common;

import java.awt.Component;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

import com.doteyplay.game.editor.ResourceDataConstants;
import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.database.BaseConnectionPool;
import com.doteplay.editor.datahelper.IRuntimeDataHelper;
import com.doteplay.editor.datahelper.MetaData;
import com.doteplay.editor.datahelper.MetaDataScheme;
import com.doteplay.editor.datahelper.ParamList;
import com.doteplay.editor.file.exporter.ExcelExporterManager;
import com.doteplay.editor.util.Util;
import com.doteplay.editor.util.xml.XmlUtil;
import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;
import com.doteplay.editor.util.xml.simpleXML.StrUtils;

public class DataGroup<T extends BaseData> implements IXmlDocument
{

	/**
	 * ��ѯ���� : �޸ı��
	 */
	public boolean modifiedKey;
	/**
	 * ��ѯ���� : ���ƹؼ���
	 */
	public String nameKey = null;
	/**
	 * ��ѯ���� : �������ͱ��
	 */
	public int dataTypeKey = -1;
	/**
	 * ��ѯ���� : ��ϵͳ�������ݱ��
	 */
	public boolean noGenDataKey;
	/**
	 * ��ѯ���� : �������ݱ��
	 */
	public boolean localDataKey;
	/**
	 * ��ѯ���� : �Զ�������
	 */
	public boolean customDataKey;
	/**
	 * ��ѯ���� : ���õ�����
	 */
	public boolean refDataKey;
	/**
	 * ��ѯ���� :��װ�ط�ʽ
	 */
	public int loadType = -1;
	/**
	 * ����������
	 */
	public int resourceType;
	/**
	 * ������Id
	 */
	public String groupId;
	/**
	 * ����������
	 */
	public String groupName;
	/**
	 * ������ID
	 */
	public String groupClassId;
	/**
	 * �����б����� 0:ͨ����� 1:�Զ������
	 */
	public int listType = 0;
	/**
	 * �����б����
	 */
	public GroupPanel groupPanel;
	/**
	 * ������洢·��
	 */
	public String groupPath;
	/**
	 * �����ļ���׺
	 */
	public String fileSuffix;
	/**
	 * ���ݶ���MAP
	 */
	protected Hashtable<String, T> datas = new Hashtable<String, T>();
	/**
	 * ���ݶ����б�
	 */
	protected Vector<T> dataList = new Vector<T>();
	/**
	 * ��ǰ������ݶ�����
	 */
	public int maxId = 0;

	/**
	 * Ԫ����ģ��
	 */
	private MetaDataScheme refScheme;

	/**
	 * ���ݹ�����
	 */
	private IDataFilter<T> dataFilter;
	/**
	 * XML��������ʱ���Ժ������
	 */
	public long whatTime = -1L;

	/**
	 * �Ƿ���Ҫ�������԰�
	 */
	public boolean needLanguage = false;

	public DataGroup()
	{
	}

	public DataGroup(SimpleAttributes attributes)
	{
		init(attributes);
	}

//	protected int getClientResourceType()
//	{
//		return ResourceDataConstants
//				.getClientDataTypeByResourceType(this.resourceType);
//	}

	/**
	 * ���ݹ����������ڲ�ͬ������֮��ķ���ʵ��
	 * 
	 * @param params
	 * @return
	 */
	public List<MetaData> filterData(ParamList params)
	{
		Vector<MetaData> v = new Vector<MetaData>();
		int size = dataList.size();
		T baseData;
		MetaData mdata;
		for (int i = 0; i < size; i++)
		{
			baseData = dataList.get(i);
			baseData.open();
			try
			{
				if (baseData.isMatch(params))
				{
					mdata = new MetaData(this.refScheme);
					baseData.exportMetaData(mdata);
					v.addElement(mdata);
					mdata = null;
				}
			} finally
			{
				baseData.close();
			}
		}
		return v;
	}

	public MetaData createMetaData()
	{
		return new MetaData(this.refScheme);
	}

	/**
	 * ͨ�������ļ���ʼ��
	 * 
	 * @param groupinfo
	 */
	public void init(SimpleAttributes groupinfo)
	{
		resourceType = Integer.parseInt(groupinfo.getValue("id"));
		groupId = groupinfo.getValue("class");
		groupName = groupinfo.getValue("name");
		groupClassId = groupinfo.getValue("prefix");
		listType = StrUtils.parseint(groupinfo.getValue("listtype"), 0);
		fileSuffix = groupinfo.getValue("suf");
		groupPath = EditorConfig.dataPath + groupId + File.separatorChar;
//		needLanguage = groupinfo.getValue("needLanguage").equals("1") ? true
//				: false;

		File f = new File(groupPath);
		if (!f.exists())
		{
			try
			{
				f.mkdir();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		// ����Ԫ����ģ��
		String tmpDataSchemeFile = groupinfo.getValue("datascheme");
		refScheme = new MetaDataScheme();
		refScheme.setDataGroup(groupName);
		if (tmpDataSchemeFile == null || tmpDataSchemeFile.length() <= 0)
		{
			refScheme.regField("id", "����ID", MetaDataScheme.DATA_TYPE_STRING);
			refScheme.regField("dataid", "�������ݿ�ID",
					MetaDataScheme.DATA_TYPE_STRING);
			refScheme.regField("name", "����", MetaDataScheme.DATA_TYPE_STRING);
			refScheme.regField("desc", "����", MetaDataScheme.DATA_TYPE_STRING);
		} else
		{
			refScheme.loadDataSchemeFile(EditorConfig.confPath
					+ tmpDataSchemeFile);
		}

		// �Ǽ�Ȩ����
		PermissionManager.regPermissionPoint(groupId);
	}

	/**
	 * �Ƴ�����ϵͳ��������
	 */
	public void removeAllGeneData()
	{
		int size = dataList.size();
		T bd;
		Enumeration<T> enu = datas.elements();// dataList.elements();
		int num = 0;
		while (enu.hasMoreElements())
		// for(int i=0;i<size;i++)
		{
			// bd=dataList.get(i);
			bd = enu.nextElement();
			if (bd.geneType == 0)
				continue;
			dataList.removeElement(bd);
			datas.remove(bd.id);
			num++;
		}
		// System.out.println("removeAllGeneData:"+size+","+num);
	}

	private void clear()
	{
		datas.clear();
		dataList.clear();
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public int getDataCount()
	{
		return dataList.size();
	}

	/**
	 * ����������ȡ����
	 * 
	 * @param idx
	 * @return
	 */
	public T getDataByIndex(int idx)
	{
		if (idx >= 0 && idx < dataList.size())
			return (T) this.dataList.get(idx);
		else
			return null;
	}

	/**
	 * ��������id��ȡ����
	 * 
	 * @param idx
	 * @return
	 */
	public T getData(String id)
	{
		return (id != null) ? (T) this.datas.get(id) : null;
	}

	/**
	 * ����id�Ƿ��Ѿ�����
	 * 
	 * @param id
	 * @return
	 */
	public boolean isDataExist(String id)
	{
		return (id != null) ? datas.containsKey(id) : false;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 */
	public Vector<T> getDataList()
	{
		return this.dataList;
	}

	/**
	 * �������ƻ�ȡ����
	 * 
	 * @param nameId
	 * @return
	 */
	public T getDataByName(int nameId)
	{
		// Vector<T> v=new Vector<T>();
		int size = dataList.size();
		T bd;
		for (int i = 0; i < size; i++)
		{
			bd = dataList.get(i);
			if (bd.nameId == nameId)
				return bd;
			// v.addElement(bd);
		}
		return null;
	}

	/**
	 * �����������ͻ�ȡ�����б�
	 * 
	 * @param type
	 * @return
	 */
	public Vector<T> getDataListByType(int type)
	{
		if (type == -1)
			return dataList;
		Vector<T> v = new Vector<T>();
		int size = dataList.size();
		T bd;
		for (int i = 0; i < size; i++)
		{
			bd = dataList.get(i);
			if (bd.dataType == type)
				v.addElement(bd);
		}
		return v;
	}

	/**
	 * �½�����
	 * 
	 * @return
	 */
	public T newData()
	{
		// if(type==1)
		// return null;
		String gclass = "com.doteplay.editor.data." + groupId + "." + groupClassId
				+ "Data";
		T bd = (T) Util.loadClassByName(gclass);
		// bd.id=String.valueOf(++gdata.maxId);

		return bd;
	}

	/**
	 * �������
	 * 
	 * @param ss
	 * @return
	 */
	public T addData(String[] ss)
	{
		String gclass = "com.doteplay.editor.data." + groupId + "." + groupClassId
				+ "Data";
		T bd = (T) Util.loadClassByName(gclass);
		if (bd != null)
		{
			bd.initByIndexFile(this, ss);
			addData(bd);
		}
		return bd;
	}

	/**
	 * ��������
	 * 
	 * @param bd
	 */
	public void setData(T bd)
	{
		datas.put(bd.id, bd);

		int index = -1;
		for (int i = 0; i < dataList.size(); i++)
		{
			T bd1 = dataList.get(i);
			if (bd.id.equals(bd1.id))
			{
				index = i;
				break;
			}
		}

		if (index != -1)
		{
			dataList.set(index, bd);
		}
	}

	/**
	 * �������ݶ���
	 * 
	 * @param bd
	 */
	public void addData(T bd)
	{
		if (datas.containsKey(bd.id))
			return;
		int j;
		datas.put(bd.id, bd);

		int a = Integer.parseInt(bd.id);
		int b;
		boolean added = false;
		T bd1;
		// ���ձ��˳������б�
		for (j = dataList.size() - 1; j >= 0; j--)
		{
			bd1 = dataList.get(j);
			b = Integer.parseInt(bd1.id);
			if (a >= b)
			{
				//��ֹ��������
				if (j < dataList.size() - 1)
					dataList.insertElementAt(bd, j + 1);
				else
					dataList.addElement(bd);
				added = true;
				break;
			}
		}
		if (!added)
		{
			dataList.addElement(bd);
		}
		if (a > maxId)
			maxId = a;
	}

	/**
	 * ɾ�����ݶ���
	 * 
	 * @param bd
	 */
	public void removeData(T bd)
	{
		if (bd.geneType == 1)
			return;
		datas.remove(bd.id);
		dataList.remove(bd);

		// if(type==1)
		// return;

		// ɾ���ͻ����ļ�
		String fileName = groupPath + bd.id + "." + fileSuffix;
		File file = new File(fileName);
		if (file.exists())
			file.delete();
		// ɾ���ͻ���176�ļ�
		fileName = groupPath + bd.id + "." + fileSuffix + "1";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// ɾ���ͻ���1000*600�ļ�
		fileName = groupPath + bd.id + "." + fileSuffix + "2";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// ɾ���༭���ļ�
		fileName = groupPath + bd.id + "." + fileSuffix + "x";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// ɾ�����������ļ�
		fileName = groupPath + bd.id + "." + fileSuffix + "s";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// if(groupPanel!=null)
		// groupPanel.removeData(bd);

		// save();
	}

	/**
	 * ��ȡ���������id
	 * 
	 * @return
	 */
	public synchronized int getMaxId()
	{
		int b;
		T bd1;
		maxId = 0;
		for (int j = 0; j < dataList.size(); j++)
		{
			bd1 = dataList.get(j);
			b = Integer.parseInt(bd1.id);
			if (b > maxId)
				maxId = b;
		}
		return maxId;
	}

	/**
	 * ��ȡ���������ݿ������id
	 * 
	 * @return
	 */
	public synchronized String getMaxDataId()
	{
		int b;
		T bd1;
		maxId = 0;
		for (int j = 0; j < dataList.size(); j++)
		{
			bd1 = dataList.get(j);
			b = Integer.parseInt(bd1.id);
			if (b > maxId)
				maxId = b;
		}

		return String.valueOf(maxId + 1);
	}

	/**
	 * ���汾�����������ļ�
	 * 
	 * @return
	 */
	public boolean saveIndexFile()
	{
		// if(type==1)
		// return true;
		try
		{
			String path = groupPath + "index.txt";
			int i, num = 0;
			int size = dataList.size();
			StringBuffer s = new StringBuffer();
			T bd;
			for (i = 0; i < size; i++)
			{
				bd = (T) dataList.get(i);
				if (bd.geneType == 1)
					continue;
				num++;
			}
			s.append(num + "\r\n");
			for (i = 0; i < size; i++)
			{
				bd = (T) dataList.get(i);
				if (bd.geneType == 1)
					continue;
				s.append(bd.getIndexString() + "\r\n");
			}
			Util.saveFile(path, s.toString().getBytes());

			if (groupPanel != null)
				groupPanel.updateView();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * �������������ļ���ʼ��������
	 * 
	 * @return
	 */
	public boolean initBaseDataByIndexFile()
	{

		String gclass = "com.doteplay.editor.data." + groupId + "." + groupClassId
				+ "Data";
		T bd, bd1;

		// ��ȡ�����ļ�
		String fn_index = groupPath + "index.txt";
		byte[] buf = Util.loadFile(fn_index);
		if (buf == null)
			return false;
		String s_index = new String(buf);
		int i = 0, j;
		String temp;
		String[] sss;

		Class tmpGroupClass = null;
		try
		{
			tmpGroupClass = Class.forName(gclass);
		} catch (Exception e)
		{
			e.printStackTrace();
			tmpGroupClass = null;
		}
		String[] ss = s_index.split("\r\n");
		// 1
		int size = Integer.parseInt(ss[i++]);
		// 1 ɳĮ 2008-04-30 12:00:00
		for (j = 0; j < size; j++)
		{
			temp = ss[i++];
			sss = temp.split("\t");
			try
			{
				bd = (T) tmpGroupClass.newInstance();
			} catch (Exception e)
			{
				e.printStackTrace();
				bd = null;
			}
			if (bd == null)
				continue;
			bd.initByIndexFile(this, sss);
			addData(bd);
			bd.isInDB = 0;
			bd.db_version = 0;
		}
		tmpGroupClass = null;
		return true;
	}

	/**
	 * �������ݿ�����ˢ�¸���������
	 * 
	 * @param thread
	 */
	public void update(Thread thread)
	{
		if (EditorConfig.useDataBase)
		{

			String gclass = "com.doteplay.editor.data." + groupId + "."
					+ groupClassId + "Data";
			T bd;

			// �����ʷ״̬
			for (int j = 0; j < dataList.size(); j++)
			{
				bd = dataList.get(j);
				bd.isInDB = 0;
				bd.db_version = 0;
			}
			// �����ݿ���������ļ�
			// System.out.println(gclass);
			bd = (T) Util.loadClassByName(gclass);
			bd.dataGroup = this;

			db_loadList(gclass);

			saveIndexFile();

		}
	}

	/**
	 * ���ݸ����¼���Ӧ
	 * 
	 * @param bd
	 */
	public void onDataSaved(T bd)
	{
		if (groupPanel != null)
			groupPanel.updateView();
	}

	/**
	 * ���ҷ����������ݶ��� ���ݶ�����open״̬
	 * 
	 * @param param
	 * @return
	 */
	public Vector<T> findData(Hashtable<String, Object> param)
	{
		Vector<T> v = new Vector<T>();
		int size = dataList.size();
		T baseData;
		for (int i = 0; i < size; i++)
		{
			baseData = dataList.get(i);
			baseData.open();
			if (baseData.isMatch(param))
			{
				v.addElement(baseData);
			} else
				baseData.close();
		}
		return v;
	}

	/**
	 * ��������ˢ�£���Ҫ����ʵ��
	 * 
	 * @param component
	 * @param selectedObjs
	 * @return
	 */
	public boolean specialUpdate(Component component, Object[] selectedObjs)
	{
		JOptionPane.showConfirmDialog(component, "û���������!");
		return false;
	}

	/**
	 * �ͷŸ����������ݵ���
	 */
	public void releaseAllDataLock()
	{
		if (!EditorConfig.useDataBase)
		{
			return;
		}
		for (T d : datas.values())
		{
			// System.out.println(d.dataId+" "+d.getDataLock());
			d.releaseLock();
		}
	}

	/**
	 * �����������Ч��
	 */
	public void checkData(IRuntimeDataHelper datahelper)
	{
		int size = dataList.size();
		T baseData;
		for (int i = 0; i < size; i++)
		{
			baseData = dataList.get(i);
			baseData.open();
			try
			{
				baseData.checkRuntimeData(datahelper);
			} finally
			{
				baseData.close();
			}
		}

	}

	/**
	 * ��ȡ���ݹ���������Ҫ����ʵ��
	 * 
	 * @return
	 */
	public IDataFilter<T> getDataFilter()
	{
		return this.dataFilter;
	}

	/**
	 * �������ݹ���������Ҫ����ʵ��
	 * 
	 * @param dataFilter
	 */
	public void setDataFilter(IDataFilter<T> dataFilter)
	{
		this.dataFilter = dataFilter;
	}

	// TODO:===���ݿ�========================================

	/**
	 * �������ݿ�װ������
	 * 
	 * @param gclass
	 */
	public void db_loadList(String gclass)
	{

		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return;

		System.out.println("===" + groupName + " " + "===");

		String sql;
		PreparedStatement pstat;
		ResultSet rs;

		String tableName = EditorConfig.RES_DATA_TABLE_EDITOR;
		try
		{

			sql = "select resourceId,PKId,name,gd_version,type,gd_editorUserId,gd_editorUserName,gd_editorVersion,gd_dateTime,preload,preloadOrder,openFlag from "
					+ tableName + " where resourceType=? order by resourceId";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, this.resourceType);

			rs = pstat.executeQuery();
			while (rs.next())
			{
				String id = String.valueOf(rs.getInt("resourceId"));
				// String id = String.valueOf(rs.getInt("PKId"));
				T bd1 = datas.get(id);
				if (bd1 == null)
				{
					bd1 = (T) Util.loadClassByName(gclass);
					bd1.init(this, id);
					if (!EditorConfig.isPublisher)
					{
						if (bd1.db_load(conn))
						{
							this.addData(bd1);
						}
					} else
					{
						this.addData(bd1);
					}
				} else
				{
					// bd1.dataId=""+rs.getInt("dataId");
					// System.out.println(ss[2]);
					// if(bd1.dataId!=null && !bd1.dataId.equals("-1")){
					// bd1.db_version=rs.getInt("gd_version");
					// }

					bd1.PKId = rs.getInt("PKId");
					bd1.db_version = rs.getInt("gd_version");
					bd1.isInDB = 1;

					bd1.lastEditorVersion = rs.getString("gd_editorVersion");

					// ������Ƿ������ݣ������ݿ��ȡ����
					if (!EditorConfig.isPublisher)
					{
						bd1.nameId = rs.getInt("name");

						bd1.preload = rs.getInt("preload");
						bd1.preloadOrder = rs.getInt("preloadOrder");
						bd1.openFlag = rs.getInt("openFlag");

						// ÿ����¼��������û���ʱ����ʾ
						bd1.lastEditUserId = rs.getInt("gd_editorUserId");
						bd1.lastEditUserName = rs
								.getString("gd_editorUserName");
						bd1.lastEditorDateTime = rs.getTimestamp("gd_dateTime");
					}

				}
				// System.out.println("\t" + id + "," +bd1.PKId+","+ bd1.name +
				// ",version:" + bd1.version + ",db_version:"
				// + bd1.db_version + " " + bd1.lastEditUserName + " " +
				// bd1.lastEditorDateTime + " "
				// + bd1.lastEditorDateTime);

			}
			rs.close();
			pstat.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����ݿ���ɾ��ϵͳ��������
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected boolean db_deleteGeneData()
	{
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return false;

		String sql;
		PreparedStatement pstat;
		boolean ok = false;
		try
		{
			// sql = "delete from " + groupTable + " where geneType=1";

			sql = "delete from " + EditorConfig.RES_DATA_TABLE_EDITOR
					+ " where geneType=1 and resourceType=?";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, this.resourceType);

			int result = pstat.executeUpdate();
			if (result == 1)
			{
				ok = true;
			}
			pstat.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			ok = false;
		} finally
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ok;
	}

	/**
	 * ���������ݵ�ͨ����Ϣ�������ݿ�����Ҫ��������ʵ��
	 */
	public void db_saveAllCommonData()
	{
	}

	// TODO: ======================XML============================

	@Override
	public Document getDocument()
	{
		Document doc = new DefaultDocument();
		doc.add(this.getElement());
		return doc;
	}

	public void setDocument(Document doc)
	{
		Element root = doc.getRootElement();
	}

	@Override
	public String getXmlFileName()
	{
		return groupPath + groupId + ".xml";
	}

	@Override
	public boolean importXml()
	{
		Document doc = XmlUtil.ReadXml(this.getXmlFileName());
		if (doc == null)
		{
			return false;
		}
		Element root = doc.getRootElement();
		List<Element> list = root.selectNodes(groupClassId + "Data");
		for (Element e : list)
		{

			try
			{
				Attribute attribute = e.attribute("id");
				T data = datas.get(attribute.getValue());
				data.open();
				data.setElement(e);
				data.save();
				data.close();
			} catch (Exception e1)
			{
				System.err.println(e.attribute("id") + ":" + e1.getMessage());
				e1.printStackTrace();
			}
		}
		this.saveIndexFile();
		return true;
	}

	@Override
	public boolean exportXml()
	{
		Document doc = this.getDocument();
		String fileName = this.getXmlFileName();
		// System.out.println("writeXml:\r\n" + fileName + "\r\n" +
		// doc.asXML());
		return XmlUtil.WriteXml(doc, fileName);
	}

	/**
	 * ��ȡ����Map
	 * 
	 * @return
	 */
	public Map<String, Object> getAttributeMap()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceType", resourceType);
		map.put("groupId", groupId);
		map.put("groupName", groupName);
		map.put("groupClassId", groupClassId);
		map.put("listType", listType);
		map.put("fileSuffix", fileSuffix);
		return map;
	}

	public Element getElement()
	{
		Element element = null;
		try
		{
			element = new DefaultElement(groupId.toUpperCase());
			Map<String, Object> map = this.getAttributeMap();
			XmlUtil.ElementAddAttributes(element, map);
			Object[] bds = groupPanel.getSelectObjects();
			if (bds == null || bds.length < 1)
			{// ˵��û��ѡ���κ�����ֱ�ӵ���ȫ��
				for (T data : dataList)
				{
					if (data.geneType != 1
							&& data.lastEditorDateTime.getTime() > whatTime)
					{
						data.open();
						element.add(data.getElement());
						data.close();
					}
				}

			} else
			{// ����ѡ����
				for (Object obj : bds)
				{
					T data = (T) obj;
					if (data.geneType != 1
							&& data.lastEditorDateTime.getTime() > whatTime)
					{
						data.open();
						element.add(data.getElement());
						data.close();
					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return element;
	}

	// TODO:====CVS============================
	/**
	 * �Ƿ�֧��CSV����/��������Ҫ����ʵ��
	 * 
	 * @return
	 */
	public boolean isSupportCSV()
	{
		return false;
	}

	/**
	 * ��ȡCSV����
	 * 
	 * @return
	 */
	public String getCSVTitle()
	{
		return "���,����";
	}

	// TODO:====================excel===============================
	public String getExcelFileName()
	{
		return groupPath + groupId + ".xls";
	}

	public boolean exportExcel()
	{
		ExcelExporterManager exporter = new ExcelExporterManager(new File(
				this.getExcelFileName()));
		exporter.start(groupName);
		exporter.exporter(this);
		return true;
	}
}
