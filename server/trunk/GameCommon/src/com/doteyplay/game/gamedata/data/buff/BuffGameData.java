package com.doteyplay.game.gamedata.data.buff;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.constants.ActivateEvent;
import com.doteyplay.game.constants.DamageType;
import com.doteyplay.game.constants.skill.SkillActionType;
import com.doteyplay.game.constants.skill.SkillAmmoType;
import com.doteyplay.game.constants.skill.SkillEffectRange;
import com.doteyplay.game.constants.skill.SkillTargetSelectType;
import com.doteyplay.game.gamedata.data.IGameData;

public class BuffGameData implements IGameData
{
	
	public DamageType damageType = DamageType.PHYSICS;
	/**
	 * �����˺�
	 */
	public int damage;
	/**
	 * �����ָ�
	 */
	public int health;
	
	/**
	 * ����Ƴ�
	 */
	public boolean canRemove;
	
	/**
	 * �ƶ�
	 */
	public boolean canMove;
	
	/**
	 * ����ʩ��
	 */
	public boolean canUseSkill;
	
	/**
	 * ������
	 */
	public boolean canHealth;
	
	/**
	 * ��������
	 */
	public boolean isPd;
	
	/**
	 * ��������
	 */
	public boolean isMd;
	
	/**
	 * ���ߴ洢
	 */
	public boolean isOfflineSave;
	
	/**
	 * ���߼�ʱ
	 */
	public boolean isOfflineReduce;
	
	/**
	 * ����ʱ��
	 */
	public int time;
	
	/**
	 * ������
	 */
	public int ap;
	/**
	 * �������
	 */
	public int pd;
	
	/**
	 * ��������
	 */
	public int md;
	
	/**
	 * ����
	 */
	public int crh;
	
	/**
	 * ����
	 */
	public int dodge;
	
	
	@Override
	public void load(DataInputStream in) throws IOException
	{
		this.damage = in.readInt();
		this.health = in.readInt();
		this.time = in.readInt();
		this.ap = in.readInt();
		this.pd = in.readInt();
		this.md = in.readInt();
		this.crh = in.readInt();
		this.dodge = in.readInt();
		
		this.canRemove = in.readBoolean();
		this.canMove = in.readBoolean();
		this.canUseSkill = in.readBoolean();
		this.canHealth = in.readBoolean();
		this.isPd = in.readBoolean();
		this.isMd = in.readBoolean();
		this.isOfflineSave = in.readBoolean();
		this.isOfflineReduce = in.readBoolean();
		
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(damage);
		out.writeInt(health);
		out.writeInt(time);
		out.writeInt(ap);
		out.writeInt(pd);
		out.writeInt(md);
		out.writeInt(crh);
		out.writeInt(dodge);
		
		out.writeBoolean(canRemove);
		out.writeBoolean(canMove);
		out.writeBoolean(canUseSkill);
		out.writeBoolean(canHealth);
		out.writeBoolean(isPd);
		out.writeBoolean(isMd);
		out.writeBoolean(isOfflineSave);
		out.writeBoolean(isOfflineReduce);
	}
}
