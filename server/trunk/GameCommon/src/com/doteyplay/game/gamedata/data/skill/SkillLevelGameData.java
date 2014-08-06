package com.doteyplay.game.gamedata.data.skill;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.doteyplay.game.constants.skill.SkillEffectType;
import com.doteyplay.game.gamedata.data.IGameData;

public class SkillLevelGameData implements IGameData
{
	
	public int level;
	/**
	 * �������ܴ�������
	 */
	public int skillTriggerPercent;
	/**
	 * Ӱ�췶Χ����
	 */
	public String effectRangeParam = "";
	
	/**
	 * Ч������
	 */
	public SkillEffectType effectType = SkillEffectType.EFFECT_TYPE_COMMON_ATK;
	
	/**
	 * Ч������
	 */
	public String effectDataParam = "";
	
	/**
	 * bufid
	 */
	public int buffId;
	/**
	 * ʹ�ü���ʱ���ж����ʴ������buf
	 */
	public int buffTriggerPercent;
	

	@Override
	public void load(DataInputStream in) throws IOException
	{
		level = in.readInt();
		skillTriggerPercent = in.readInt();
		effectRangeParam = in.readUTF();
		effectType = SkillEffectType.values()[in.readByte()];
		effectDataParam = in.readUTF();
		buffId = in.readInt();
		buffTriggerPercent = in.readInt();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(level);
		out.writeInt(skillTriggerPercent);
		out.writeUTF(effectRangeParam);
		out.writeByte(effectType.ordinal());
		out.writeUTF(effectDataParam);
		out.writeInt(buffId);
		out.writeInt(buffTriggerPercent);
	}

}
