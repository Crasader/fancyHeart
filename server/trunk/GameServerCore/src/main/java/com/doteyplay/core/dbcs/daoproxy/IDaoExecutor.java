package com.doteyplay.core.dbcs.daoproxy;

/**
 * DAO�ӿڱ�ǩ
 * 
 * @author 
 * 
 */
public interface IDaoExecutor
{
	// TODO:��������ɾ���ģ������������
	
	public boolean startTransaction();
	
	public boolean commitTransaction();
	
	public boolean endTransaction();
}
