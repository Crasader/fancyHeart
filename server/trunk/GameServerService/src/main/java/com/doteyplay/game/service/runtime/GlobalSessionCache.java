package com.doteyplay.game.service.runtime;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.gateway.IGateWayService;
import com.doteyplay.core.server.task.TaskCallbackHandler;
import com.doteyplay.core.service.CoreServiceConstants;
import com.doteyplay.game.domain.common.SessionHolder;
import com.doteyplay.game.message.system.ACKTask;
import com.doteyplay.game.service.bo.virtualworld.IVirtualWorldService;
import com.doteyplay.game.task.TaskManager;
import com.doteyplay.net.message.AbstractMessage;

import common.Logger;

public class GlobalSessionCache
{
	private static final Logger logger = Logger
			.getLogger(GlobalSessionCache.class);

	private GlobalSessionCache()
	{
		// Thread t = new Thread(new Runnable()
		// {
		// @Override
		// public void run()
		// {
		// while(true)
		// {
		// try
		// {
		// Thread.sleep(2000);
		// } catch (InterruptedException e)
		// {
		// e.printStackTrace();
		// }
		// System.out.println("size====="+uIdCache.size()+","+sessionCache.size()+","+rIdCache.size()
		// +","+sessionIdCache.size());
		// Runtime rt = Runtime.getRuntime();
		// long totalMemorySize = rt.totalMemory(); //��ʼ�����ڴ�
		// long maxMemorySize = rt.maxMemory(); //�������ڴ�
		// long freeMemorySize = rt.freeMemory(); //��ǰ�����ڴ�
		//
		// System.out.println("totalMemorySize = "+totalMemorySize);
		// System.out.println("maxMemorySize = " + maxMemorySize);
		// System.out.println("freeMemorySize = "+freeMemorySize);
		// }
		// }
		// });
		// t.start();
	}

	// userId
	private Map<Long, SessionHolder> uIdCache = new ConcurrentHashMap<Long, SessionHolder>();
	// session
	private Map<IoSession, SessionHolder> sessionCache = new ConcurrentHashMap<IoSession, SessionHolder>();
	// roleId
	private Map<Long, SessionHolder> rIdCache = new ConcurrentHashMap<Long, SessionHolder>();
	// sessionId;
	private Map<Long, IoSession> sessionIdCache = new ConcurrentHashMap<Long, IoSession>();

	public void add(long userId, IoSession session, long roleId)
	{
		System.out.println("add:userId:" + userId + ",session:" + session
				+ ",roleId:" + roleId);
		SessionHolder holder = new SessionHolder();
		holder.setSession(session);
		holder.setUserId(userId);
		holder.setHasAuth(true);
		holder.setCurRoleId(roleId);
		holder.setSessionId(getSessionIdByGatewayEndpoint(session));

		uIdCache.put(userId, holder);
		rIdCache.put(roleId, holder);
		sessionCache.put(session, holder);

		TaskCallbackHandler callbackHandler = TaskManager
				.getInstance()
				.getUserTaskService()
				.scheduleAtFixedRate(new ACKTask(holder), 2000L,
						CoreServiceConstants.MILLISECOND_PER_FRAME * 50);
		holder.setCallbackHandler(callbackHandler);
	}

	public void addSession(IoSession session)
	{
		long sessionId = GlobalSessionCache
				.getSessionIdByGatewayEndpoint(session);
		if (sessionIdCache.get(sessionId) == null)
			sessionIdCache.put(sessionId, session);
	}

	public IoSession getSessionBySessionId(long sessionId)
	{
		return sessionIdCache.get(sessionId);
	}

	public void disconnect(IoSession session)
	{
		SessionHolder holder = sessionCache.get(session);
		if (holder != null)
			this.disconnect(holder.getUserId(), true);

		else
		{
			sessionIdCache.remove(getSessionIdByGatewayEndpoint(session));
			session.close(true);
		}
	}

	public void disconnect(long userId, boolean worldLogout)
	{
		SessionHolder holder = uIdCache.remove(userId);
		if (holder == null)
			System.out
					.println("===================holder:null===================");
		if (holder != null && holder.getSession() != null)
		{
			sessionCache.remove(holder.getSession());
			
			sessionIdCache.remove(getSessionIdByGatewayEndpoint(holder.getSession()));
			holder.getSession().close(true);

			if (holder.getCurRoleId() > 0)
			{
				rIdCache.remove(holder.getCurRoleId());

				if (worldLogout)
				{
					IVirtualWorldService virtualWorldService = BOServiceManager
							.findDefaultService(IVirtualWorldService.PORTAL_ID);
					virtualWorldService.doLogout(holder.getCurRoleId());
				}
			}
		} else
		{
			logger.error("�����ڴ�й¶,userId:" + userId + ",holder = "
					+ uIdCache.get(userId));
		}
	}

	public void disconnectAll()
	{
		for (IoSession session : sessionCache.keySet())
		{
			this.disconnect(session);
		}
	}

	public SessionHolder getByUserId(long userId)
	{
		return uIdCache.get(userId);
	}

	public SessionHolder getByRoleId(long roleId)
	{
		return rIdCache.get(roleId);
	}

	public SessionHolder getBySession(IoSession session)
	{
		if (session != null)
			return sessionCache.get(session);
		return null;
	}

	public int getUserCount()
	{
		return uIdCache.size();
	}

	public int getSessionCount()
	{
		return sessionIdCache.size();
	}

	public void sendMessageBySessionId(AbstractMessage message, long sessionId)
	{
		IoSession session = this.getSessionBySessionId(sessionId);
		SessionHolder.sendMsg(message, session);
	}

	public void sendMessageByRoleId(AbstractMessage message, long roleId)
	{
		SessionHolder holder = this.getByRoleId(roleId);
		if (holder != null)
			holder.sendMsg(message);
	}

	// //////////////////////////////////////////////////////////////////
	private static GlobalSessionCache instance = new GlobalSessionCache();

	public static GlobalSessionCache getInstance()
	{
		return instance;
	}

	public static long getSessionIdByGatewayEndpoint(IoSession session)
	{
		// ǰ8λΪ���ط������ڵ�
		long sessionId = ((session.getId() << 8) >>> 8)
				| (long) BOServiceManager
						.findLocalEndpointId(IGateWayService.PORTAL_ID) << 56;
		return sessionId;
	}
	
	public static String printCurrentUidSessionMap()
	{
		String info ="";
		for(Entry<Long, SessionHolder> e:instance.uIdCache.entrySet())
		{
			info +="userId:"+e.getKey();
			info +="sessionInfo:"+e.getValue();
			info +="|";
		}
		return info;
	}

}
