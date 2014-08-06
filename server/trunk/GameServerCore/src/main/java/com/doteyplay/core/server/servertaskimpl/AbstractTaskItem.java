package com.doteyplay.core.server.servertaskimpl;

/**
 * �����������Ŀ
 * 
 */
public abstract class AbstractTaskItem {

	/**
	 * ���������
	 */
	protected String name;
	
	/**
	 * ���������Ƿ�ر�
	 */
	protected boolean open = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOpen(boolean open){
		this.open = open;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	/**
	 * �������õķ���
	 */
	public abstract void invoke();
}
