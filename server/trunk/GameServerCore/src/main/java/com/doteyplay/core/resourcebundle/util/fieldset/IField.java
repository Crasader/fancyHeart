package com.doteyplay.core.resourcebundle.util.fieldset;

import java.lang.reflect.Field;

/**
 * field�ӿڣ���Ҫʵ�ֶ�field���趨
 * 
 * @author 
 * 
 */
public interface IField {
	
	public void setField(Field field, String value) throws Exception;
}
