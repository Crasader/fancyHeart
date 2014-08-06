package com.doteyplay.core.resourcebundle.util.fieldset;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

/**
 * field type factory
 * 
 * @author 
 * 
 */
public class FieldFactory {
	private static final Logger logger = Logger.getLogger(FieldFactory.class);

	public static void setFieldValue(Field field, String value)
			throws Exception {
		if (FieldUtil.isString(field)) { // �ַ����������
			new StringField().setField(field, value);
		} else if (FieldUtil.isStringArray(field)) { // �ַ�����������
			new StringArrayField().setField(field, value);
		} else {
			logger.error("��֧�ֵĸ�ʽ" + field.getName() + "["
					+ field.getType().getSimpleName() + "]");
		}
	}
}
