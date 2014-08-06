package com.doteyplay.core.bhns.event;

/**
 * �¼�Դ�ӿ�
 * 
 * @author 
 * 
 */
public interface IEventSource
{
	public <T extends IServiceEvent> T createSingletonEvent(int eventId);
	
	public <T extends IMultipleServiceEvent> T createMultipleEvent(int eventId,Object key);
}
