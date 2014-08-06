package com.doteplay.editor.tools.htmlEditor;

import java.awt.Image;
import java.net.URL;
/**
 * ��װ�ı�����<br>
 * ����һЩ��������
 * @author Azrael_Guan
 *
 */
public class FaceIcon {
	private Image ico;
	/**
	 * ����
	 */
	private int x,y;
	/**
	 * �ļ���
	 */
	private String fileName;
	/**
	 * ͼƬ��λ��URL
	 */
	private URL url;
	/**
	 * ������
	 */
	private String faceName;
	/**
	 * 
	 * @param ico ͼƬ
	 * @param fileName �ļ���
	 * @param faceName ������
	 */
	public FaceIcon(Image ico,String fileName,String faceName,URL url) {
		this.ico = ico;
		this.fileName = fileName;
		this.faceName = fileName;
		this.url = url;
//		this.faceName = faceName;
	}
	public Image getIco() {
		return ico;
	}
	public void setIco(Image ico) {
		this.ico = ico;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setXY(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFaceName() {
		return faceName;
	}
	public void setFaceName(String faceName) {
		this.faceName = faceName;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
}
