package com.doteyplay.game.message.tollgate;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.GateProBuf;
import com.doteyplay.game.message.proto.GateProBuf.PUpdateGates;
import com.doteyplay.net.message.AbstractMessage;

/**
 *
* @className:TollgateAndNodeChangeInfoMessage.java
* @classDescription:��ͻ���չʾ�������ڵ�������ı���Ϣ
* @author:Tom.Zheng
* @createTime:2014��6��24�� ����6:52:10
 */
public class TollgateChangeMessage extends AbstractMessage {
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014��6��24�� ����6:52:28
	 */
	private static final long serialVersionUID = -2782476069461493749L;
	
	private Map<Integer,Boolean> operateTollgateMap;


	public TollgateChangeMessage()
	{
		super(MessageCommands.SEND_TOLLGATE_CHANGE_MESSAGE);
	}
	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	@Override
	public void decodeBody(IoBuffer in) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void encodeBody(IoBuffer out) {
		// TODO Auto-generated method stub
		//����byte����.����ȥ.
		GateProBuf.PUpdateGates.Builder builder = GateProBuf.PUpdateGates.newBuilder();
		if(this.operateTollgateMap==null){
			throw new RuntimeException("��ϢΪ��,���÷���.");
		}
		for (Integer key : this.operateTollgateMap.keySet()) {
			GateProBuf.PGateItem.Builder itemBuilder = GateProBuf.PGateItem.newBuilder();
			itemBuilder.setIsLock(true);
			itemBuilder.setGateID(key);
			builder.addGates(itemBuilder);
		}
		PUpdateGates build = builder.build();
		out.put(build.toByteArray());
	}
	public Map<Integer, Boolean> getOperateTollgateMap() {
		return operateTollgateMap;
	}
	public void setOperateTollgateMap(Map<Integer, Boolean> operateTollgateMap) {
		this.operateTollgateMap = operateTollgateMap;
	}

	public void addOperateTollgate(int tollgateId,boolean isLock){
		if (this.operateTollgateMap==null) {
			this.operateTollgateMap = new HashMap<Integer,Boolean>();
		}
		
		this.operateTollgateMap.put(tollgateId, isLock);
	}
	
	

}
