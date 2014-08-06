package com.doteplay.editor.util.clipremap;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Remapper
{
	private int computeW;
	private int computeH;
	private Rectangle[] rectangleSrc;
	private Rect[][] rects;
	private Vector<Rect> vRectsW; //��������
	private Vector<Rect> vRectsH; //��������
	private Vector<Rect> vRectsA; //���������
	private Vector<Rect> vUsedRects; //�Ѿ��źõľ���
	private Vector<Vector<Rect>> vSortResult = new Vector<Vector<Rect>>(); //�洢���еĽ��
	private Vector<Integer> vSmallResult = new Vector<Integer>(); //�洢���еĽ��

	private boolean isExit;

	public Remapper(int width, int imgArea, Rectangle[] rectangle)
	{
		if (width <= 0)
		{
			return;
		}
		this.rectangleSrc = rectangle;
		
		computeW = width;
		computeH = imgArea / width * 3;//���������ⲻ��
		
		vRectsW = new Vector<Rect>();	//��������
		vRectsH = new Vector<Rect>();	//��������
		vRectsA = new Vector<Rect>();	//���������
		vUsedRects = new Vector<Rect>();	//�Ѿ��źõ�
		
		for (int i = 0; i < rectangleSrc.length; i++)
		{
			Rect r = new Rect(rectangleSrc[i].width, rectangleSrc[i].height);
			r.id = i;
			
			vRectsW.add(r);
			vRectsH.add(r);
			vRectsA.add(r);
		}

		RectSorter.sort(vRectsW, RectSorter.SORT_BY_WIDTH);
		RectSorter.sort(vRectsH, RectSorter.SORT_BY_HEIGHT);
		RectSorter.sort(vRectsA, RectSorter.SORT_BY_AREA);

		rects = new Rect[computeH][computeW];
		for (int i = 0; i < rects.length; i++)
		{
			for (int j = 0; j < rects[i].length; j++)
			{
				rects[i][j] = new Rect();
			}
		}
	}

	public void start()
	{
		try
		{
			if (vRectsW.get(0).w > computeW)	//������Ŀ�����ӿ�
			{
				JOptionPane.showMessageDialog(null, "��Ȳ���,�����п��Ϊ" + vRectsW.get(0).w + ",������Ŀ�Ϊ" + computeW + "!!!");
				return;
			}

			//��ʼ �Ȱ���ߵĿ�Ž�ȥ
			Rect rtH = vRectsH.get(0);
			rtH.x = 0;
			rtH.y = 0;
			addUseRect(rtH);
			
			//��������ķŽ�ȥ
			Rect rtA = vRectsA.get(0);
			findCanAddPos(rtA, getFristPointCanUse());
			addUseRect(rtA);
			
			while (!vRectsW.isEmpty())
			{
				if (isExit)
				{
					return;
				}
				Rect r = vRectsA.get(0);	//�����������
				findCanAddPos(r, getFristPointCanUse());
				addUseRect(r);
			}
			addSortResult(vUsedRects);
		} catch (StackOverflowError e)
		{
			JOptionPane.showMessageDialog(null, e.toString() + "�ݹ��쳣,�뽫��ȵ���!!!" + "    ");
			return;
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "������� �� " + e.getMessage() + "\n�뽫��ȵ���");
			return;
		}
		
		for (int i = 0; i < vSortResult.get(0).size(); i++)
		{
			Rect r = vSortResult.get(0).get(i);
			rectangleSrc[r.id].x = r.x;
			rectangleSrc[r.id].y = r.y;
		}
	}

	/** ����Ӿ���֮�����ش��� */
	private void addUseRect(Rect rect)
	{
		setRectIsUsed(rect, true);
		vUsedRects.add(rect);
		vRectsW.remove(rect);
		vRectsH.remove(rect);
		vRectsA.remove(rect);
	}
	
	/**
	 * @param rect
	 * @param p
	 *
	 * �ݹ���÷��������Ż�
	 */
	private void findCanAddPos(Rect rect, Point p)
	{
		while (true)
		{
			//����ɨ�裬���rect�ɷ��õ�λ��
			rect.x = p.x;
			rect.y = p.y;
			if (rects[p.y][p.x].isUsed)
			{
				p.x += rects[p.y][p.x].w;
			} else if (computeW - p.x < rect.w)
			{
				p.x = 0;
				p.y++;
			} else
			{
				p.x++;
			}
			if (p.x >= computeW)
			{
				p.x = 0;
				p.y++;
			}
			if (isCanAddRect(rect))
			{
				break;
			}
		}
	}

	/**
	 * @return ��ԭ�㿪ʼ��⣬�õ���һ��ûʹ�õĵ㣨���Ż���
	 */
	private Point getFristPointCanUse()
	{
		boolean isFind = false;
		
		Point p = new Point();//ûʹ�õĵ�
		p.x = 0;
		p.y = 0;
		while (!isFind)
		{
			if (rects[p.y][p.x].isUsed)	//��������Ѿ�ʹ��
			{
				p.x += rects[p.y][p.x].w;	//������ʹ�õĲ���
				
				if (p.x >= computeW)	//��ȹ��磬����
				{
					p.x = 0;
					p.y++;
				}
			} else
			{
				isFind = true;
			}
		}
		return p;
	}

	/**
	 * @param rect
	 * ���Ż�
	 */
	private boolean isCanAddRect(Rect rect)
	{
		if (rect.x + rect.w > computeW)	//����
		{
			return false;		//���ɷ���
		}
		
		//����Ѿ�ʹ��
		for (int y = rect.y; y < rect.y + rect.h; y++)
		{
			for (int x = rect.x; x < rect.x + rect.w; x++)
			{
				if (rects[y][x].isUsed)
				{
					return false;
				}
			}
		}
		
		//���������Χ
		if (rect.x + rect.w > computeW)
		{
			return false;
		}
		
		return true;
	}

	/** ���·��õľ�����ռ�õ����ȫ�����Ϊ���� */
	private void setRectIsUsed(Rect rect, boolean b)
	{
		for (int y = rect.y; y < rect.y + rect.h; y++)
		{
			for (int x = rect.x; x < rect.x + rect.w; x++)
			{
				rects[y][x].isUsed = b;
				rects[y][x].id = rect.id;
				rects[y][x].w = rect.w;
				rects[y][x].h = rect.h;
			}
		}
	}

	private void addSortResult(Vector<Rect> v)
	{
		Vector<Rect> vTem = new Vector<Rect>();
		int mw = 0;
		int mh = 0;
		for (int i = 0; i < v.size(); i++)
		{
			Rect r = v.get(i);
			vTem.add(r);
			if (r.x + r.w > mw)
			{
				mw = r.x + r.w;
			}
			if (r.y + r.h > mh)
			{
				mh = r.y + r.h;
			}
		}
		
		int nowArea = mw * mh;
		if (vSmallResult.size() > 0)
		{
			int smallArea = vSmallResult.get(0);
			if (nowArea < smallArea)
			{
				vSmallResult.add(0, nowArea);
				vSortResult.add(0, vTem);
			} else
			{
				vSmallResult.add(nowArea);
				vSortResult.add(vTem);
			}
		} else
		{
			vSmallResult.add(nowArea);
			vSortResult.add(vTem);
		}
	}
}