package com.doteyplay.core.server;




public enum ServerStatus {
	EMPTY, 
	NORMAL, 
	BUSY, 
	FULL, 
	CLOSED;
	// ״̬��Ӧ�������������
	private int players;

	private ServerStatus() {
		players = 0;
	}
	
	/**
	 * ��ʼ��ö�����͵���������
	 */
	public static void initStatus(int maxPlayers) {
		for (ServerStatus ss : ServerStatus.values()) {
			int index = ss.ordinal();
			int avg = maxPlayers / 3;
			if (index < 2)
				ss.players = avg * (index + 1);
			else
				ss.players = maxPlayers;
		}
	}

	/**
	 * �������������ȡ��Ӧ�ķ�����״̬
	 * 
	 * @param count
	 * @return
	 */
	public static ServerStatus getByPlayerCount(int count) {
		for (ServerStatus ss : ServerStatus.values()) {
			if (count < ss.players)
				return ss;
		}
		return FULL;
	}

	@Override
	public String toString() {
		return this.name() + "(" + players + ")";
	}
}
