package com.doteyplay.core.configloader;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.util.PackageScaner;
import com.doteyplay.core.util.StrUtils;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.net.message.AbstractMessage;
import com.doteyplay.net.message.IMessageAction;
import com.doteyplay.net.message.MessageActionHelper;
import com.doteyplay.net.message.MessageFactory;

/**
 * MessageHandler�ļ���
 * 
 * @author
 * 
 * 
 */
public class MessageRegistry
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MessageRegistry.class);

	private static final String[] CONFIG_FILES =
	{ "message-config.xml" };

	private MessageFactory factory;

	public MessageRegistry()
	{
	}

	public void loadConfig(int maxNum) throws FileNotFoundException
	{
		int maxCommandId = 0; // �����Ϣ�ţ����������鳤��Ϊ�����Ϣ��+1
		List<MessageHandlerObject> list = null;

		// ��ȡ�����ļ�����
		SAXReader saxReader = new SAXReader();

		int readFileSize = 0; // ���ٴ���һ�����ϵ������ļ�
		for (String file : CONFIG_FILES)
		{
			try
			{
				Document paramsXMLDoc = saxReader.read(MessageRegistry.class
						.getClassLoader().getResourceAsStream(file));

				// ����package
				list = loadPackage(paramsXMLDoc);
				maxCommandId = list.size();

				readFileSize++;
			} catch (Exception e)
			{
				logger.error("��Ϣע�����,���Ա�����", e);
				// ɶҲ�����
			}
		}

		if (readFileSize < 1)
			throw new FileNotFoundException("�Ҳ����κ���Ϣ�����ļ�");

		// // ��Ϣ����Factory
		if (maxCommandId < maxNum)
			maxCommandId = maxNum - 1;

		factory = new MessageFactory(maxCommandId + 1); // ��ϢFactory
		for (MessageHandlerObject obj : list)
		{
			factory.addMessage(obj.getCommandId(), obj.getClazz());
			if (obj.getHelper() != null)
			{
				factory.addMessageAction(obj.getCommandId(), obj.getHelper());
			}
		}
	}

	private List<MessageHandlerObject> loadPackage(Document paramsXMLDoc)
			throws Exception
	{
		@SuppressWarnings("unchecked")
		List<Node> nodeList = paramsXMLDoc.selectNodes("//message_package");
		String ext = ".class";

		List<MessageHandlerObject> list = new ArrayList<MessageHandlerObject>();

		if (nodeList != null && nodeList.size() > 0)
		{
			for (Node packageNode : nodeList)
			{
				String value = ((Element) packageNode).attributeValue("value");

				// ����Ŀ¼
				String[] include = PackageScaner.scanNamespaceFiles(value, ext,
						false, true);
				if (include != null && include.length > 0)
				{
					String key;
					for (String includeClass : include)
					{
						key = value
								+ "."
								+ includeClass.subSequence(0,
										includeClass.length() - (ext.length()));

						Class<?> actionClass = Class.forName(key);
						ActionAnnotation annotation = (ActionAnnotation) actionClass
								.getAnnotation(ActionAnnotation.class);
						if (annotation != null && annotation.message() != null)
						{
							if (!checkAnnotation(actionClass,
									annotation.message()))
							{
								logger.error("Actionע�����Ϣ�಻һ�£��������:"
										+ actionClass);
								continue;
							}
							processMessage(list, actionClass,
									annotation.message());
						}
					}
				}

			}
		}
		return list;
	}

	private boolean checkAnnotation(Class<?> actionClass, Class<?> messageClass)
	{
		Type[] types = actionClass.getGenericInterfaces();
		for (Type t : types)
		{
			if (t instanceof ParameterizedType)
			{
				Type[] intypes = ((ParameterizedType) t)
						.getActualTypeArguments();
				for (Type tt : intypes)
				{
					if (tt == messageClass)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private void processMessage(List<MessageHandlerObject> list,
			Class<?> actionClass, Class<? extends AbstractMessage> messageClass)
			throws Exception
	{

		try
		{
			Constructor<?> c = messageClass.getDeclaredConstructor(null);
			c.setAccessible(true);
			int commandId = ((AbstractMessage) c.newInstance()).getCommandId(); // ��ȡmessage��commandId;
			MessageHandlerObject obj = new MessageHandlerObject();
			obj.setCommandId(commandId);
			obj.setClazz(messageClass);

			if (actionClass != null)
			{
				c = actionClass.getDeclaredConstructor(null);
				c.setAccessible(true);
				IMessageAction<?, ?> action = (IMessageAction<?, ?>) c
						.newInstance();
				obj.setHelper(new MessageActionHelper(action));
			}

			logger.debug("regist message: name = "
					+ obj.getClazz().getSimpleName() + "; commandId = "
					+ obj.commandId);
			list.add(obj);
		} catch (Exception e)
		{
			throw new MessageProcessException("��������actionClass=" + actionClass
					+ ",messageClass=" + messageClass, e);
		}
	}

	private static MessageRegistry _instance = new MessageRegistry();

	public static MessageRegistry getInstance()
	{
		return _instance;
	}

	/**
	 * ������ʱֻ������
	 */
	public static void reload()
	{
		// int maxCommandId = 0; // �����Ϣ�ţ����������鳤��Ϊ�����Ϣ��+1
		// ArrayList<MessageHandlerObject> list = new
		// ArrayList<MessageHandlerObject>();

		// ��ȡ�����ļ�����
		SAXReader saxReader = new SAXReader();
		for (String file : CONFIG_FILES)
		{
			try
			{
				Document paramsXMLDoc = saxReader.read(MessageRegistry.class
						.getClassLoader().getResourceAsStream(file));

				// ������Ϣ
				@SuppressWarnings("unchecked")
				List<Node> nodeList = paramsXMLDoc.selectNodes("//message");
				if (nodeList != null)
				{
					for (Node messageNode : nodeList)
					{
						Element elm = (Element) messageNode;
						Attribute attribute = elm.attribute("clazz");
						Class<?> c = Class.forName(attribute.getValue()); // Message
						// Class

						int commandId = ((AbstractMessage) c.newInstance())
								.getCommandId(); // ��ȡmessage��commandId;
						MessageActionHelper helper = getInstance()
								.getMessageActionHelper(commandId);
						if (helper != null)
						{
							// �Ƿ񿪷�
							attribute = elm.attribute("closed");
							boolean close = StrUtils.parseBoolean(
									attribute.getValue(), false);
							if (close)
								helper.close();
							else
								helper.open();
						}
					}
				}

			} catch (Exception e)
			{
			}
		}
	}

	private static class MessageHandlerObject
	{
		private int commandId;
		private Class<? extends AbstractMessage> clazz;
		private MessageActionHelper helper;

		public int getCommandId()
		{
			return commandId;
		}

		public void setCommandId(int commandId)
		{
			this.commandId = commandId;
		}

		public Class<? extends AbstractMessage> getClazz()
		{
			return clazz;
		}

		public void setClazz(Class<? extends AbstractMessage> clazz)
		{
			this.clazz = clazz;
		}

		public MessageActionHelper getHelper()
		{
			return helper;
		}

		public void setHelper(MessageActionHelper helper)
		{
			this.helper = helper;
		}
	}

	public void add_message(int commandId,
			Class<? extends AbstractMessage> messageClass)
	{
		// logger.error(commandId);
		factory.addMessage(commandId, messageClass);
	}

	public static void addMessage(int commandId,
			Class<? extends AbstractMessage> messageClass)
	{
		if (getInstance() != null)
			getInstance().add_message(commandId, messageClass);
	}

	public final AbstractMessage getMessage(int commandId)
	{
		return factory.getMessage(commandId);
	}

	public void addMessageHelper(int commandId, MessageActionHelper helper)
	{
		factory.addMessageAction(commandId, helper);
	}

	public final MessageActionHelper getMessageActionHelper(int commandId)
	{
		return factory.getMessageActionHelper(commandId);
	}

	public MessageFactory getFactory()
	{
		return factory;
	}

	public final void freeMessage(AbstractMessage message)
	{
		factory.freeMessage(message);
	}

	public static void main(String[] args)
	{
		IoBuffer buf = IoBuffer.allocate(10);
		buf.put((byte) 1);
		buf.put((byte) 2);
		buf.put((byte) 3);
		buf.put((byte) 4);
		buf.put((byte) 5);
		buf.put((byte) 6);
		buf.put((byte) 7);
		buf.put((byte) 8);
		buf.put((byte) 9);
		buf.put((byte) 10);
		buf.rewind();
		System.out.println(buf.position());
		System.out.println(buf.get(6));
		System.out.println(buf.position());
		System.out.println(Arrays.toString(buf.array()));
	}
}
