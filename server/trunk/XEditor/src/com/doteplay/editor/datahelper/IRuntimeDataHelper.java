package com.doteplay.editor.datahelper;

import java.util.List;

// ����������֮��ķ�����Ԫ���ݸ���
// �˴�Ϊ��ʹ�÷��㣬����־�����Ԫ���ݷ���д��һ���ӿ���
public interface IRuntimeDataHelper {

    public void resetCache();// ������ݲ�ѯ�ӿ��ϵ���ʱ����

    public List<MetaData> getRuntimeData(String datagroup, ParamList params);// ���������ݲ�ѯ�ӿ�

    public MetaData findRuntimeData(String datagroup, String dataid);// ������������

    public void debugInfoOutput(String info, int infotype);// ��Ϣչʾ

}
