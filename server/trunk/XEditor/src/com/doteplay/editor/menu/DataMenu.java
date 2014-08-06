/**
 * @package com.doteplay.editor.menu
 * @file DataMenu.java
 */
package com.doteplay.editor.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.doteplay.editor.XEditor;
import com.doteplay.editor.tools.help.HelpDataXmlParser;
import com.doteplay.editor.tools.update.DataUpdateToolDialog;
import com.doteplay.editor.util.Util;

/**
 */
public class DataMenu extends JMenu {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected XEditor xEditor;
	
	private JMenuItem dataUpdateMenuItem = null;
	private JMenuItem helpImportDBMenuItem = null;

	private JMenu importDBMenu = null;

	private JMenuItem cangbaogeActivityDescMenuItem = null;
	
	private JMenuItem fengshenBangMenuItem = null;
	
	private JMenuItem huoDongRedeemMenuItem = null;

	public DataMenu(XEditor xEditor) {
		this.xEditor = xEditor;
		this.initialize();
	}
	
	private void initialize() {
		this.setText("����");
		this.add(getDataUpdateMenuItem());
		this.add(getImportDBMenu());
	}
	
	/**
	 * ����Դ�����趨
	 */
	public void showDataUpdateToolDialog() {
		DataUpdateToolDialog newDialog = new DataUpdateToolDialog(xEditor.getJFrame());
		newDialog.setModal(true);
		newDialog.init();
		Util.showCenteredDialog(newDialog, xEditor.getJFrame());

		newDialog.release();
		newDialog = null;
	}
	
    /**
     * XML�ļ����뵼��
     */
    public void showHelpImportDBDialog(){

		String fileFormat[] = { "xml","XML" };
		JFileChooser chooser = new JFileChooser();
		FileFilter  filter = new FileNameExtensionFilter( "xml�ļ�",fileFormat);
        chooser.setFileFilter(filter);//�����ļ���׺������
        int  retval = chooser.showOpenDialog(xEditor.getJFrame());//��ʾ"�����ļ�"�Ի���
        File f = null;//
        if(retval == JFileChooser.APPROVE_OPTION) {//���ɹ���
            f = chooser.getSelectedFile();//�õ�ѡ����ļ���
        }
        /**
         *  File f = Util.openSelectFileDialog("Excel�ļ�","xlsx");
         */
		if(f == null){
			return;
		}
		
		HelpDataXmlParser parser =  new HelpDataXmlParser(f);
		if(parser.dealWithSql()){//˵���ĵ���ɹ�
			JOptionPane.showMessageDialog(xEditor.getJFrame(), "����ɹ�!");
			
		}else{//����ʧ��
			JOptionPane.showMessageDialog(xEditor.getJFrame(), "����ʧ��!");
		}
		
		
    }

	/**
	 * This method initializes dataUpdateMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDataUpdateMenuItem() {
		if (dataUpdateMenuItem == null) {
			dataUpdateMenuItem = new JMenuItem();
			dataUpdateMenuItem.setName("");
			dataUpdateMenuItem.setText("��Դ����");
			dataUpdateMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showDataUpdateToolDialog();
				}
			});
		}
		return dataUpdateMenuItem;
	}
	

	/**
	 * This method initializes XmlMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getHelpImportDBMenuItem() {
		if (helpImportDBMenuItem == null) {
			helpImportDBMenuItem = new JMenuItem();
			helpImportDBMenuItem.setText("����");
			helpImportDBMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showHelpImportDBDialog();
				}
			});
		}
		return helpImportDBMenuItem;
	}

	
	/**
	 * This method initializes importDBMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getImportDBMenu() {
		if (importDBMenu == null) {
			importDBMenu = new JMenu();
			importDBMenu.setText("�������ݿ�");
			importDBMenu.add(getHelpImportDBMenuItem());
		}
		return importDBMenu;
	}
	
}
