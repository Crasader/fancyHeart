package com.doteyplay.game.constants.skill;

/**
 * Ч������
 */
public enum SkillEffectType
{

	/**
	 * ����
	 */
	EFFECT_TYPE_COMMON_ATK()
	{
		@Override
		public String toString()
		{
			return "����";
		}
	},
	;

	public SkillEffectType get(int order)
	{
		return values()[order];
	}
	
}
