package com.doteyplay.luna.common.protocol.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * ��Ҫ���͵Ķ���ִ�б��룬���з���
 * @param <T>
 */
public class DefaultMessageEncoder<T extends EncoderMessage> implements MessageEncoder<T> {

	@Override
	public void encode(IoSession session, T message,
			ProtocolEncoderOutput out) throws Exception {
		out.write(message.toBuffer());
	}
}