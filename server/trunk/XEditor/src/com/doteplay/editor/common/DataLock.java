/**
 * 
 */
package com.doteplay.editor.common;

import com.doteplay.editor.XEditor;

public class DataLock
{

	public boolean lock = false;
	public String lockUser = null;
	public int lockUserId = -1;

	public DataLock()
	{

	}

	public DataLock(boolean lock, int lockUserId, String lockUser)
	{
		this.lock = lock;
		this.lockUserId = lockUserId;
		this.lockUser = lockUser;
	}

	/**
	 * �Ƿ�����
	 * 
	 * @return
	 */
	public boolean hasLock()
	{
		return lock;
	}

	/**
	 * �Լ��ϵ���
	 * 
	 * @return
	 */
	public boolean isLockedByMe()
	{
		if (lockUserId != -1 && lockUserId == XEditor.user.id)
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * �����ϵ���
	 * 
	 * @return
	 */
	public boolean isLockedByOther()
	{
		return (!isLockedByMe());
	}

	@Override
	public String toString()
	{
		return "hasLock:" + hasLock() + " LockByMe=" + isLockedByMe() + " "
				+ lockUserId + " " + lockUser;
	}
}
