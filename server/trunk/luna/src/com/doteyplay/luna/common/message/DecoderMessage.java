package com.doteyplay.luna.common.message;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.luna.common.LunaConstants;

/**
 * ͨ�ý�����Ϣ�Ķ���,�����յ�����Ϣ���н���
 * 
 */
public class DecoderMessage
{
	private static Logger logger = Logger.getLogger(DecoderMessage.class
			.getName());

	/**
	 * ���������ݻ���������
	 */
	private IoBuffer buffer;

	/**
	 * ָ����
	 */
	private short commandId;
	/**
	 * ���ĳ���
	 */
	private int messageLength;
	/**
	 * �ж�Buffer�Ƿ�Ϊ��
	 */
	private boolean bufferIsNull = false;
	/**
	 * ��ɫId
	 */
	private long roleId;

	/**
	 * ����DecoderMessage��ʱ�䣬��Ҫ���������������ڲ������ʱ��
	 */
	private long newTime;

	private long synKey;

	public DecoderMessage(short commandId, int length, long roleId, long synkey)
	{
		this.commandId = commandId;
		this.messageLength = length;
		this.synKey = synkey;
		this.roleId = roleId;
		this.newTime = System.currentTimeMillis();
	}

	public IoBuffer getBuffer()
	{
		return this.buffer;
	}

	public void setBuffer(IoBuffer buffer)
	{
		if ((this.messageLength - LunaConstants.MESSAGE_LENGTH - LunaConstants.ROLEID_LENGTH
				- LunaConstants.COMMAND_LENGTH - LunaConstants.SYNKEY_LENGTH) > 0)
			this.buffer = buffer.getSlice(this.messageLength - LunaConstants.MESSAGE_LENGTH
					- LunaConstants.ROLEID_LENGTH - LunaConstants.SYNKEY_LENGTH
					- LunaConstants.COMMAND_LENGTH);
		else
			this.bufferIsNull = true;
	}

	
	public byte[] getByteArray()
	{
		int length = buffer.getInt();
		byte bytes[] = new byte[length];
		buffer.get(bytes);
		return bytes;
	}
	
	public byte getByte()
	{
		return this.buffer.get();
	}

	public short getShort()
	{
		return this.buffer.getShort();
	}

	public int getInt()
	{
		return this.buffer.getInt();
	}

	public boolean getBoolean()
	{
		return this.buffer.get() == LunaConstants.TRUE;
	}

	public long getLong()
	{
		return this.buffer.getLong();
	}

	public String getString()
	{
		try
		{
			short length = this.buffer.getShort();
			return this.buffer.getString(length, LunaConstants.DECODER);
		} catch (Exception e)
		{
			logger.error("Response��ȡ��Ϣ��������", e);
			return "";
		}
	}

	/**
	 * ���ض���
	 * 
	 * @return
	 */
	public Object getObject()
	{
		try
		{
			return this.buffer.getObject();
		} catch (ClassNotFoundException e)
		{
			logger.error("Response��ȡ��Ϣ��������", e);
			return null;
		}
	}

	public String toString()
	{
		if (this.bufferIsNull)
			return "THIS IS A NULL COMMAND!";
		else
			return this.buffer.getHexDump();
	}

	public short getCommandId()
	{
		return this.commandId;
	}

	public void setCommandId(short commandId)
	{
		this.commandId = commandId;
	}

	public int getMessageLength()
	{
		return this.messageLength;
	}

	public void setMessageLength(int messageLength)
	{
		this.messageLength = messageLength;
	}

	public boolean bufferIsNull()
	{
		return this.bufferIsNull;
	}

	public long getNewTime()
	{
		return this.newTime;
	}

	public void setNewTime(long newTime)
	{
		this.newTime = newTime;
	}

	public long getRoleId()
	{
		return this.roleId;
	}

	public void setRoleId(long roleId)
	{
		this.roleId = roleId;
	}

	public long getSynKey()
	{
		return synKey;
	}

	public void setSynKey(long synKey)
	{
		this.synKey = synKey;
	}
}