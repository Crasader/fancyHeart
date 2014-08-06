package com.doteplay.editor.component.ImageList;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface IImageListItem {

	/**
	 * ��ȡ�߿�:xyΪ���ϽǶ���ԭ���ƫ��
	 * @return
	 */
	public Rectangle getBounds();
	public void draw(Graphics2D g2d,int x,int y,boolean alpha);
	
	public void draw(Graphics2D g2d,int x,int y,boolean alpha,double scale);
	
	public String getImageItemInfo();
	
	public int getImageItemMaxBounds();
}
