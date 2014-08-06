package com.doteplay.editor.file.exporter;

import java.io.File;
import java.io.IOException;

import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.file.exporter.impl.SpriteExcelExporter;
import com.doteplay.editor.file.exporter.impl.TextExcelExporter;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public abstract class AbstractExcelExporter
{
	protected WritableWorkbook book = null;
	protected WritableSheet sheet = null;
	
	
	public abstract void doExport();
	
	/**
	 * Ϊsheet��ֵ
	 */
	public void setSheet(int selectIndex,File file)
	{
		String[] dgNames = DataManager.getGroupNames();
		try
		{
			if (file.exists())
			{// ˵�����½����ļ�
				Workbook workBook = Workbook.getWorkbook(file);// ��õ�һ�����������
				book = Workbook.createWorkbook(file, workBook); // ͨ������Ĺ������øø���
			} else
			{
				book = Workbook.createWorkbook(file);
			}

			WritableSheet[] sheets = book.getSheets();
			boolean flag = false;
			for (int i = 0; i < sheets.length; i++)
			{
				if (sheets[i].getName().equals(dgNames[selectIndex]))
				{
					sheet = sheets[i];
					flag = true;
					break;
				}
			}
			if (!flag)
			{
				sheet = book.createSheet(dgNames[selectIndex], sheets.length);
			}
		} catch (BiffException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ����Excel��Ԫ���и�ʽ������
	 */
	protected WritableCellFormat getCellFormat() throws WriteException
	{
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 14,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // �����ʽ ���� �»��� б�� ���� ��ɫ
		WritableCellFormat wcf = new WritableCellFormat(wf); // ��Ԫ����
		wcf.setBackground(jxl.format.Colour.GRAY_25); // ���õ�Ԫ��ı�����ɫ
		wcf.setAlignment(jxl.format.Alignment.CENTRE); // ���ö��뷽ʽ
		return wcf;
	}
	
	public static AbstractExcelExporter createExporter(int idx)
	{
		switch (idx)
		{
			case 0:
				return new TextExcelExporter();
			case 1:
				return new SpriteExcelExporter();
	
			default:
				return null;
		}
	}

}
