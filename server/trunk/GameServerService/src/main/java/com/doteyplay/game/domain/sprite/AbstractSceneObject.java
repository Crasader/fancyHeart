package com.doteyplay.game.domain.sprite;

/**
 * 
 *�����ɼ�����
 */
public abstract class AbstractSceneObject
{
	protected long id;
	protected String name;
	protected int type;
	
	public AbstractSceneObject(int type)
	{
		this.type = type;
	}
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getType()
	{
		return type;
	}
}
