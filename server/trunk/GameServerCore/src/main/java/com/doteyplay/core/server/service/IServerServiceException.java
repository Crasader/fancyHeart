package com.doteyplay.core.server.service;

/**
 * ��������ʧ�ܵ�ʱ���׳����쳣
 */
public class IServerServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IServerServiceException(String name){
		super(name);
	}
	public IServerServiceException(String name,Throwable t){
		super(name,t);
	}
}
