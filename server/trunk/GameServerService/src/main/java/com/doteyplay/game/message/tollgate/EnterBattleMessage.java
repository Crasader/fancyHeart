package com.doteyplay.game.message.tollgate;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.GateProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @className:NodeEnterMessage.java
 * @classDescription: ����ս��.
 * @author:Tom.Zheng
 * @createTime:2014��7��17�� ����6:42:02
 */
public class EnterBattleMessage extends AbstractMessage{

	/**
	 * @author:Tom.Zheng
	 * @createTime:2014��7��17�� ����6:43:37
	 */
	private static final long serialVersionUID = 6350868158950574592L;

	private int tollgateId;
	
	private int nodeId;
	
	private int groupId;
	
	public EnterBattleMessage() {
		super(MessageCommands.ENTER_BATTLE_MESSAGE);
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decodeBody(IoBuffer in) {
		// TODO Auto-generated method stub
		byte[] protoBufBytes = getProtoBufBytes(in);
		
		try {
			GateProBuf.PNodeReq builder =  GateProBuf.PNodeReq.parseFrom(protoBufBytes);
			tollgateId = builder.getGateId();
			nodeId =builder.getXId();
			groupId =builder.getGroupId();
			
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void encodeBody(IoBuffer out) {
		// TODO Auto-generated method stub
		
	}

	public int getTollgateId() {
		return tollgateId;
	}

	public void setTollgateId(int tollgateId) {
		this.tollgateId = tollgateId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	

}
