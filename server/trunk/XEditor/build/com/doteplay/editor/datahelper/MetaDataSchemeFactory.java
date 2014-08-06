package com.doteplay.editor.datahelper;

import java.util.HashMap;

public class MetaDataSchemeFactory {

    private HashMap<String, MetaDataScheme> schemeMap;

    public MetaDataSchemeFactory() {
        schemeMap = new HashMap<String, MetaDataScheme>();
    }

    public synchronized void reg_scheme(MetaDataScheme scheme) throws MetaDataException {
        if (scheme != null && scheme.getDataGroup() != null && !schemeMap.containsKey(scheme.getDataGroup()))
            schemeMap.put(scheme.getDataGroup(), scheme);
        else {
            if (scheme == null)
                throw new MetaDataException("Ԫ����ģ��ΪNULL");
            else if (scheme.getDataGroup() == null)
                throw new MetaDataException("Ԫ������ΪNULL���޷�ע��");
            else if (schemeMap.containsKey(scheme.getDataGroup()))
                throw new MetaDataException("Ԫ�������Ѿ��Ǽǣ��޷�ע��");
        }
    }

    public synchronized MetaDataScheme get_scheme(String datagroup) {
        if (datagroup != null)
            return (MetaDataScheme) schemeMap.get(datagroup);
        else
            return null;
    }

    private static MetaDataSchemeFactory _instance;

    private synchronized static MetaDataSchemeFactory getInstance() {
        if (_instance == null) {
            _instance = new MetaDataSchemeFactory();
        }
        return _instance;
    }

    public static void regScheme(MetaDataScheme scheme) {
        MetaDataSchemeFactory tmpFactory = getInstance();
        if (tmpFactory != null)
            tmpFactory.reg_scheme(scheme);
    }

    public static void regSchemeFromFile(String sfile) {
        MetaDataSchemeFactory tmpFactory = getInstance();
        if (tmpFactory != null) {
            MetaDataScheme tmpScheme = new MetaDataScheme();
            tmpScheme.loadDataSchemeFile(sfile);
            tmpFactory.reg_scheme(tmpScheme);
        }
    }

    public static MetaDataScheme getScheme(String datagroup) {
        MetaDataSchemeFactory tmpFactory = getInstance();
        if (tmpFactory != null)
            return tmpFactory.get_scheme(datagroup);
        else
            return null;
    }
}
