package com.doteyplay.core.dbcs.executor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * DAO�ӿں���ע��
 * ���������б����ݿ�ӳ�������Ϣ
 * 
 * @author 
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DAOInfo
{
	String Params();

}
