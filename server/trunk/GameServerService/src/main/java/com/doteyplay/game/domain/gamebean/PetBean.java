package com.doteyplay.game.domain.gamebean;

public class PetBean extends SpriteBean
{
	private long roleId;
	/**
	 * �Ƿ�����Ӣ��
	 */
	private int selected;
	/**
	 * Ӣ�۴�����ϵ.
	 * 0,1,2,3,4��ʾ�����������еļ���.
	 */
	private String groupStr="";
	
	
	public long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(long roleId)
	{
		this.roleId = roleId;
	}

	public int getSelected()
	{
		return selected;
	}
	
	public boolean isSelected(){
		return selected==1;
	}

	public void setSelected(int selected)
	{
		this.selected = selected;
	}

	public String getGroupStr() {
		return groupStr;
	}

	public void setGroupStr(String groupStr) {
		this.groupStr = groupStr;
	}

	

	
}
