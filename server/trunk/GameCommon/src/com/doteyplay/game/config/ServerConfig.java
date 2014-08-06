package com.doteyplay.game.config;

import java.lang.reflect.Field;

import com.doteyplay.game.FieldAnnotation;

/**
 * ���������������Ϣ����server-config.xml�ļ���ȡ������
 * 
 */
public class ServerConfig
{

	@FieldAnnotation(name = "��Ϸ����")
	public final static String SERVER_NAME = "rpgGame";

	@FieldAnnotation(name = "������")
	public static int SERVICE_ID = 0;

	@FieldAnnotation(name = "������")
	public static int SERVER_AREA_ID = 1;

	@FieldAnnotation(name = "��������")
	public static String SERVER_AREA_NAME = "";

	@FieldAnnotation(name = "�������˿�")
	public static int SERVER_PORT = 2888;

	@FieldAnnotation(name = "GM�˿�")
	public static int GM_SERVER_PORT = 9;

	@FieldAnnotation(name = "�Ʒѷ�������ַ")
	public static String FEE_SERVER_IP = "";

	@FieldAnnotation(name = "�Ʒѷ������˿�")
	public static int FEE_SERVER_PORT = 9;
	
	@FieldAnnotation(name = "��֤��������ַ")
	public static String AUTH_SERVER_IP = "";

	@FieldAnnotation(name = "��֤�������˿�")
	public static int AUTH_SERVER_PORT = 9;

	@FieldAnnotation(name = "��־��������ַ")
	public static String LOG_SERVER_IP = "";

	@FieldAnnotation(name = "��־�������˿�")
	public static int LOG_SERVER_PORT = 9;

	@FieldAnnotation(name = "��������߳�����������")
	public static int MAX_PLAYERS = 2000;

	@FieldAnnotation(name = "ResourceBundle�ļ�ǰ׺")
	public static String RESOURCE_BUNDLE_FILE = "";

	@FieldAnnotation(name = "ResourceBundle֧�ֵ�����")
	public static String RESOURCE_BUNDLE_LANGUAGE = "";

	@FieldAnnotation(name = "ResourceBundleĬ�ϵ�����")
	public static String RESOURCE_BUNDLE_DEFAULT = "";

	@FieldAnnotation(name = "�����Ϣ����ͳ�ƿ���")
	public static boolean SERVER_USER_MESSAGE_MONITOR = false;

	@FieldAnnotation(name = "�Ƿ�ʱ��¼�û���Ϣ�����Ϣ")
	public static boolean SHOW_USER_MESSAGE_MONITOR_INFO = false;

	@FieldAnnotation(name = "ͨ�÷���")
	public static String COMMON_SERVICE_PATH = "/common_service.xml";

	@FieldAnnotation(name = "���ط���")
	public static String LOCAL_SERVICE_PATH = "/local_service.xml";

	@FieldAnnotation(name = "���������������������")
	public static int GAME_ROOM_LIMIT = 100;
	
	@FieldAnnotation(name = "protobuf�ļ���·��")
	public static String PROTOBUF_PATH = "D:/myworkspace/����Э��/message.proto";
	
	@FieldAnnotation(name = "�����ļ���·��")
	public static String CONFIG_FILE_PATH = "D:/myworkspace/GameServer/src/main/resources/excel";
	

	public static String info()
	{
		StringBuffer str = new StringBuffer();
		Field[] fields = ServerConfig.class.getDeclaredFields();
		for (Field f : fields)
		{
			try
			{
				str.append(((FieldAnnotation) f.getAnnotations()[0]).name())
						.append(":")
						.append(String.valueOf(f.get(ServerConfig.class)))
						.append("\n");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return str.toString();
	}
}
