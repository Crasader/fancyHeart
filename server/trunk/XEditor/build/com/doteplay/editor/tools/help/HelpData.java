package com.doteplay.editor.tools.help;
/**
 * 
 * @author wyc
 * �ڵ����
 */
public class HelpData {
	/**
	 * ����
	 */
	private int PKId = 0;
	/**
	 * ����
	 */
	private String name = "";
	/**
	 * ˵��
	 */
	private String description = "";
	/**
	 * ���ڵ�
	 * û��Ϊ-1
	 */
	private int parentId = -1;
	/**
	 * ����
	 */
	private int type = 0;
	
	public int getPKId() {
		return PKId;
	}
	public void setPKId(int id) {
		PKId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "insert into t_game_help(PKId,name,description,parentId,type) values("+
		PKId+",'"+name+"','"+description+"',"+parentId+","+type+")";
	}
	
	
}
