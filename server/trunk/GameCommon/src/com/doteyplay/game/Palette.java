package com.doteyplay.game;

public class Palette
{
	private Palette()
	{
	}
	
	//ʵ��Ч���μ�: http://en.wikipedia.org/wiki/Web_colors

	public final static byte Black = 0;//��ɫ
	public final static byte Gray = 1;//��ɫ
	public final static byte Silver = 2;//��ɫ
	public final static byte White = 3;//��ɫ 1
	
	public final static byte Red = 4;//��ɫ 1
	public final static byte Maroon = 5;//��ɫ
	public final static byte Purple = 6;//��ɫ
	public final static byte Fuchsia = 7; //��
	public final static byte Green = 8; //����
	public final static byte Lime = 9; //��
	public final static byte Olive = 10; //���
	public final static byte Yellow = 11; //�� 1
	public final static byte Blue = 12; //��
	public final static byte Navy = 13; //������
	public final static byte Teal = 14; //����
	public final static byte Aqua = 15; //�� 1
	
	public final static byte SkyBlue = 16;
	public final static byte DeepSkyBlue = 17;
	public final static byte DodgerBlue = 18;
	public final static byte RoyalBlue = 19;
	
	public final static byte SpringGreen = 20;
	public final static byte LimeGreen = 21;
	public final static byte GreenYellow = 22;
	
	public final static byte Tomato = 23;
	public final static byte OrangeRed = 24;
	public final static byte Orange = 25;
	
	public final static byte Gold = 26;
	public final static byte Goldenrod = 27;
	public final static byte Khaki = 28;
	public final static byte LightYellow = 29;// 1
	
	public final static byte LightBlue = 30;
	public final static byte LightSkyBlue = 31;
	
	private final static int[] COLORS = 
	{ 
		0x000000, //��ɫ
		0x808080, //��ɫ
		0xC0C0C0, //��ɫ
		0xFFFFFF, //��ɫ
		
		0xFF0000, //��ɫ
		0x800000, //��ɫ
		0x800080, //��ɫ
		0xFF00FF, //��
		0x008000, //����
		0x00FF00, //��
		0x808000, //���
		0xFFFF00, //��
		0x0000FF, //��
		0x000080, //������
		0x008080, //����
		0x00FFFF, //��
		
		//��ɫ��
		0x87CEEB,
		0x00BFFF,
		0x1E90FF,
		0x4169E1,
		
		//��ɫ��
		0x00FF7F,
		0x32CD32,
		0xADFF2F,
		
		//��ɫ��
		0xFF6347,
		0xFF4500,
		0xFFA500,
		
		//��ɫ��
		0xFFD700,
		0xDAA520,
		0xF0E68C,
		0xFFFF8C,
		
		//������
		0xADD8E6,
		0x87CEFA,
	};
	
	@Deprecated
	public final static int getColor(byte index)
	{
		return COLORS[index];
	}
}
