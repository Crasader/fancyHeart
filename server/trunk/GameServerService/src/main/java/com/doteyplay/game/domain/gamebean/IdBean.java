package com.doteyplay.game.domain.gamebean;

public class IdBean extends BaseBean
{
	/**
	 * ��Id
	 */
	private int gameAreaId;
	/**
	 * ÿ������Ӧ��ID��ǰֵ,����ǰ���ֵ
	 */
	private long num;
	/**
	 * ����:��t_pub_role
	 */
	private String table;
	/**
	 * ���ֶ���:��gameAreaId 
	 */
	private String key;
	/**
	 * ID�ֶ���:��PKId
	 */
	private String value;
	
	public int getGameAreaId() {
		return gameAreaId;
	}

	public void setGameAreaId(int gameAreaId) {
		this.gameAreaId = gameAreaId;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getNum()
	{
		return num;
	}

	public void setNum(long num)
	{
		this.num = num;
	}
}
