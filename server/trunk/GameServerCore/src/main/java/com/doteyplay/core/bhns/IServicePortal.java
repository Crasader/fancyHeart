package com.doteyplay.core.bhns;

/**
 * ����ͨ����ȡ
 * 
 * @author 
 * 
 * �˽ӿ��ǹ淶poltal�ڲ��ṹ��Ҳ���Ǵӷ����������Ƕ�(�����ǵ�������Զ�̷���)����������Ӧ���ṩ�Ļ�����֯��̬
 * 
 */

public interface IServicePortal<T extends ISimpleService>
{
	public int getPortalId(); // �˷����ṩ�ķ����ʶ��Ŀǰ���ÿ���������ֻ�ṩһ����Ϊ
	
	public byte getEndpointId();

	public String doPortalCommand(int portalId,String requestCommand);//portal��ָ������
	
	public ISimpleService findService(long svrid); // ����KEYֵ��ȡһ���������
	
	public ISimpleService findService(int portalid,long svrid); // ����KEYֵ��ȡһ���������
	
	public boolean isActive(long serviceId);
	
	public T activeService(long svrid, boolean isOrderActive); // ����KEYֵ����һ���������
	
	public T getPortalService(); // ��ȡ��״̬�������

	public void destroyService(long svrid); // �ͷ�һ���������

	public boolean isProxy(); // �Ƿ�Զ�̴���

}
