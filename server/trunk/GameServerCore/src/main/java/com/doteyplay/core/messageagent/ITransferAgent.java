package com.doteyplay.core.messageagent;

import java.util.List;

import com.doteyplay.net.message.AbstractMessage;

/**
 * ������Ϣ�ӿ� 
 * 
 */
public interface ITransferAgent
{

	public void sendMessage(long roleId, AbstractMessage message);

	public void broadcastMessage(List<Long> roleIdLst,
			AbstractMessage message);
}
