package com.doteyplay.core.behavior;

/**
 * ����Ϊ�ۺϽӿ�
 * 
 */
public interface IBehaviorAssembly
{
	/**
	 * ����Ϊ�ۺ��л��һ����Ϊ�ӿ�
	 * 
	 * @return IBehavior
	 */
	public <T extends IBehavior> T getBehavior(int behaviorid);

}
