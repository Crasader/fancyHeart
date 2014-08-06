package com.doteyplay.game.service.bo.virtualworld.chat.handle;

import com.doteyplay.game.service.bo.virtualworld.chat.patameter.IPatameterObject;

/**
 * @className:IGMProcess.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014��7��10�� ����11:31:32
 */
public interface IChatHandler<T extends IPatameterObject> {
	public void handle(T t);
	
}
