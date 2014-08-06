package com.doteyplay.core.bhns.eventservice;

import com.doteyplay.core.bhns.ISimpleService;
/**
 * �¼�����ӿڣ��ṩ�ض��¼�������Ŀ���б�
 * 
 * @author 
 * 
 */
public interface ISvrEventService extends ISimpleService
{
	public final static int PORTAL_ID = 0;

	public String getEventSource(int eventid);

	public String getListenerList(int eventid);

	public void registerListener(int eventid, int portalid);

	public boolean isActiveEvent(int eventid);

}
