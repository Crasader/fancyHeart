/**
 * @package com.doteplay.editor.export
 * @file TextExport.java
 */
package com.doteplay.editor.file.exporter;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;

/**
 */
public class ExcelExporterManager
{

	private File file;
	protected WritableWorkbook book = null;
	protected WritableSheet sheet = null;

	public ExcelExporterManager(File file)
	{
		this.file = file;
	}

	public synchronized void start(int selectIndex)
	{
		AbstractExcelExporter excelExporter = AbstractExcelExporter.createExporter(selectIndex);
		excelExporter.setSheet(selectIndex,file);
		excelExporter.doExport();
	}

	
	public WritableSheet start(String groupName)
	{
		synchronized (this)
		{
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
					if (sheets[i].getName().equals(groupName))
					{
						sheet = sheets[i];
						flag = true;
						break;
					}
				}
				if (!flag)
				{
					sheet = book.createSheet(groupName, sheets.length);
				}
			} catch (BiffException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sheet;
	}

	/**
	 * ��������
	 */
	public void exporter(DataGroup dg)
	{
		try
		{
			WritableCellFormat wcf = getCellFormat();

			BaseData data1 = dg.getDataByIndex(0);
			data1.getWritableSheet(sheet, wcf);

			int i = 0;
			Object[] bds = dg.groupPanel.getSelectObjects();
			if (bds == null || bds.length < 1)
			{// ˵��û��ѡ���κ�����ֱ�ӵ���ȫ��
				Vector<BaseData> vbd = dg.getDataList();
				for (BaseData data : vbd)
				{
					if (data.geneType != 1
							&& data.lastEditorDateTime.getTime() > dg.whatTime)
					{
						data.open();
						String[] cells = data.getExcelData();
						if (cells == null || cells.length <= 0)
						{
							return;
						}
						for (int j = 0; j < cells.length; j++)
						{
							sheet.addCell(new Label(j, i + 1, cells[j]));
						}
						data.close();
						i++;
					}
				}
			} else
			{// ����ѡ����
				for (Object obj : bds)
				{
					data1 = (BaseData) obj;
					if (data1.geneType != 1
							&& data1.lastEditorDateTime.getTime() > dg.whatTime)
					{
						data1.open();
						String[] cells = data1.getExcelData();
						if (cells == null || cells.length <= 0)
						{
							return;
						}
						for (int j = 0; j < cells.length; j++)
						{
							sheet.addCell(new Label(j, i + 1, cells[j]));
						}
						data1.close();
						i++;
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, dg.groupName + "����ʧ��!");
		} finally
		{
			try
			{
				book.write();
				book.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (WriteException e)
			{
				e.printStackTrace();
			}
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
}
