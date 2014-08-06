package com.doteplay.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.common.EditorPanel;
import com.doteplay.editor.common.GroupListPanel;
import com.doteplay.editor.common.GroupPanel;
import com.doteplay.editor.common.PermissionManager;
import com.doteplay.editor.component.ClosableTabbed.ClosableTabbedPane;
import com.doteplay.editor.database.BaseConnectionPool;
import com.doteplay.editor.database.ConnectionInfo;
import com.doteplay.editor.file.exporter.ExcelExporterManager;
import com.doteplay.editor.file.importer.ExcelImporterManager;
import com.doteplay.editor.user.UserCheckDialog;
import com.doteplay.editor.user.UserData;
import com.doteplay.editor.util.Util;

public class XEditor
{

	public static UserData user; 

	public boolean inited = false;
	public static XEditor xEditor;
	public static int globalIndex;

	private JFrame jFrame = null; 
	private JPanel jContentPane = null;
	private JMenuBar jMenuBar = null;
	private JMenu fileMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JDialog aboutDialog = null; 
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JToolBar jToolBar = null;
	private JSplitPane jSplitPane = null;
	private JTabbedPane groupTabbedPane = null;// ���༭����ǩ
	public JTabbedPane editorTabbedPane = null;// �Ҳ����ݱ༭��
	private JToolBar jToolBar1 = null;
	private JProgressBar mainProgressBar = null;
	private JPanel jPanel = null;
	private JLabel jLabelUser = null;
	private JMenu exportMenu = null;
	private JMenuItem excelExportMenuItem = null;
	/**
	 * �����༭����
	 */
	private JMenu importMenu = null;
	private JMenuItem excelImportMenuItem = null;

	public boolean isHaveOpenedDataEditorPanel()
	{
		return editorTabbedPane.getTabCount() > 0;
	}

	/**
	 * ��ȡ��ǰ��Ծ�༭���
	 * 
	 * @return
	 */
	public EditorPanel getActiveEditorPanel()
	{
		EditorPanel panel = (EditorPanel) editorTabbedPane
				.getSelectedComponent();
		return panel;
	}

	public void initProgress(int max)
	{
		mainProgressBar.setMaximum(max);
	}

	public void setProgress(int value, int max, String s)
	{
		mainProgressBar.setMaximum(max);
		// int max=mainProgressBar.getMaximum();
		// if(value>max)
		mainProgressBar.setValue(value % max);
		mainProgressBar.setString(s);
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar()
	{
		if (jToolBar == null)
		{
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane()
	{
		if (jSplitPane == null)
		{
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setLeftComponent(getGroupTabbedPane());
			jSplitPane.setRightComponent(getEditorTabbedPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes groupTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	public JTabbedPane getGroupTabbedPane()
	{
		if (groupTabbedPane == null)
		{
			groupTabbedPane = new JTabbedPane();
			groupTabbedPane.setPreferredSize(new Dimension(210, 7));
			groupTabbedPane.setTabPlacement(JTabbedPane.LEFT);
		}
		return groupTabbedPane;
	}

	/**
	 * This method initializes editorTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getEditorTabbedPane()
	{
		if (editorTabbedPane == null)
		{
			editorTabbedPane = new ClosableTabbedPane();
			editorTabbedPane.setTabPlacement(JTabbedPane.TOP);
		}
		return editorTabbedPane;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1()
	{
		if (jToolBar1 == null)
		{
			jToolBar1 = new JToolBar();
			jToolBar1.add(getMainProgressBar());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes mainProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getMainProgressBar()
	{
		if (mainProgressBar == null)
		{
			mainProgressBar = new JProgressBar();
			mainProgressBar.setStringPainted(true);
		}
		return mainProgressBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel()
	{
		if (jPanel == null)
		{
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			jLabelUser = new JLabel();
			jLabelUser.setText("��ǰ�û�:");
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(jLabelUser, gridBagConstraints);
		}
		return jPanel;
	}

	/**
	 * This method initializes exportMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getExportMenu()
	{
		if (exportMenu == null)
		{
			exportMenu = new JMenu();
			exportMenu.setText("����");
			exportMenu.setActionCommand("����");
			exportMenu.add(getExcelExportMenuItem());
		}
		return exportMenu;
	}

	/**
	 * This method initializes execlExportMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExcelExportMenuItem()
	{
		if (excelExportMenuItem == null)
		{
			excelExportMenuItem = new JMenuItem();
			excelExportMenuItem.setText("Excel");
			excelExportMenuItem
					.addActionListener(new java.awt.event.ActionListener()
					{
						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							String fileFormat[] =
							{ "xls" };
							JFileChooser chooser = new JFileChooser();
							FileFilter filter = new FileNameExtensionFilter(
									"Excel�ļ�", fileFormat);
							chooser.setFileFilter(filter);
							File f = new File("δ����.xls");
							chooser.setSelectedFile(f);// ����Ĭ���ļ���
							int retval = chooser.showSaveDialog(jFrame);// ��ʾ�������ļ����Ի���
							if (retval == JFileChooser.APPROVE_OPTION)
							{
								f = chooser.getSelectedFile();
							}
							// File f =
							// Util.openSelectFileDialog("Excel�ļ�","xlsx");
							if (f == null)
							{
								return;
							}

							ExcelExporterManager exporter = new ExcelExporterManager(
									f);
							int rel = Util.selectIndex(xEditor.getJFrame(),
									"ѡ�����������", DataManager.getGroupNames(),
									null);
							if (rel == -1)
							{
								return;
							}

							exporter.start(rel);

						}
					});
		}
		return excelExportMenuItem;
	}

	/**
	 * This method initializes importMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getImportMenu()
	{
		if (importMenu == null)
		{
			importMenu = new JMenu();
			importMenu.setText("����");
			importMenu.add(getExcelImportMenuItem());
		}
		return importMenu;
	}

	/**
	 * This method initializes excelImportMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExcelImportMenuItem()
	{
		if (excelImportMenuItem == null)
		{
			excelImportMenuItem = new JMenuItem();
			excelImportMenuItem.setText("Excel");
			excelImportMenuItem
					.addActionListener(new java.awt.event.ActionListener()
					{
						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							JFileChooser chooser = new JFileChooser();
							chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);// ����ѡ��ģʽ���ȿ���ѡ���ļ��ֿ���ѡ���ļ���
							String fileFormat[] =
							{ "xls" };
							FileFilter filter = new FileNameExtensionFilter(
									"XLS�ļ�", fileFormat);
							chooser.setFileFilter(filter);// �����ļ���׺������
							int retval = chooser.showOpenDialog(jFrame);// ��ʾ"�����ļ�"�Ի���
							File f = null;//
							if (retval == JFileChooser.APPROVE_OPTION)
							{// ���ɹ���
								f = chooser.getSelectedFile();// �õ�ѡ����ļ���
							}
							/**
							 * File f =
							 * Util.openSelectFileDialog("Excel�ļ�","xlsx");
							 */
							if (f == null)
							{
								return;
							}

							ExcelImporterManager importer = new ExcelImporterManager(f);
							int rel = Util.selectIndex(xEditor.getJFrame(),
									"ѡ�����������", importer.getOption(), null);
							if (rel == -1)
							{
								return;
							}

							importer.start(rel);
						}
					});
		}
		return excelImportMenuItem;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				xEditor = new XEditor();
				xEditor.getJFrame().setVisible(true);
				xEditor.initEditor();

				// Timer t = new Timer();
				// t.schedule(new TimerTask() {
				// @Override
				// public void run() {
				// if (XEditor.globalIndex < Integer.MAX_VALUE) {
				// XEditor.globalIndex++;
				// }
				// else {
				// XEditor.globalIndex = 0;
				// }
				// xEditor.getJFrame().repaint();
				// }
				// }, 0, EditorConfig.MSPF);

			}
		});
	}

	/**
	 * ��ʼ�����ݿ�
	 * 
	 * @return
	 */
	public boolean initDB()
	{

		// ѡ�����ݿ������ļ�
		String[] tmpConTitles = BaseConnectionPool.connectionTitles();
		if (tmpConTitles == null || tmpConTitles.length <= 0)
		{
			System.exit(0);
		}
		Object selectedValue = JOptionPane.showInputDialog(xEditor.getJFrame(),
				"ѡ�����ݿ�", "ѡ��", JOptionPane.INFORMATION_MESSAGE, null,
				tmpConTitles, tmpConTitles[0]);

		if (selectedValue == null)
		{
			System.exit(0);
		}
		EditorConfig.dbname = selectedValue.toString();

		// ԭ�������ݳس�ʼ��
		ConnectionInfo info = BaseConnectionPool
				.findConnectionInfo((String) selectedValue);
		EditorConfig.isPublisher = info.publisher;

		if (!BaseConnectionPool.init(info))
		{
			System.out.println("Can't init DataSource!");
			EditorConfig.useDataBase = false;
			return false;
		}

		return true;
	}

	/**
	 * �û���¼
	 */
	public void userLogin()
	{

		UserCheckDialog userCheckDialog = new UserCheckDialog();

		Point loc = jFrame.getLocation();
		userCheckDialog.setLocation(loc.x + jFrame.getWidth() / 2
				- userCheckDialog.getWidth() / 2, loc.y + jFrame.getHeight()
				/ 2 - userCheckDialog.getHeight() / 2);

		userCheckDialog.setVisible(true);

		user = userCheckDialog.getUserData();

		if (user == null)
		{
			EditorConfig.useDataBase = false;
			jLabelUser.setText("��ǰ�û�:����");
		} else
		{
			jLabelUser.setText("��ǰ�û�:" + user.name);
			jFrame.setTitle(jFrame.getTitle() + "[" + EditorConfig.dbname + "]");
		}
	}

	public boolean initEditor()
	{
		// load editor config (properties or XML)
		if (!EditorConfig.init())
			return false;

		// ��ʼ�����ݿ����ӳ�
		if (EditorConfig.useDataBase)
		{

			if (!initDB())
			{
				EditorConfig.useDataBase = false;
			} else
			{
				userLogin();
			}
		}
		if (EditorConfig.useDataBase)
		{
			JOptionPane.showMessageDialog(getJFrame(), "���������ݿ⣬���߱༭��", "ȷ�϶Ի���",
					JOptionPane.INFORMATION_MESSAGE);
		} else
		{
			JOptionPane.showMessageDialog(getJFrame(), "�޷��������ݿ⣬���߱༭��",
					"ȷ�϶Ի���", JOptionPane.INFORMATION_MESSAGE);
		}
		// ��ǰ�û���
		PermissionManager.bindUser(user, EditorConfig.useDataBase);

		// open another thread to load the data
		DataManager.init();

		Vector dcs = DataManager.dataGroupList;
		DataGroup dc;
		for (int i = 0; i < dcs.size(); i++)
		{
			dc = (DataGroup) dcs.get(i);
			if (PermissionManager.isPermission(dc.groupId))
			{
				GroupPanel panel;
				if (dc.listType == 0)
				{
					panel = new GroupListPanel();
				} else
				{
					panel = (GroupPanel) Util
							.loadClassByName("com.doteplay.editor.data."
									+ dc.groupId + "." + dc.groupClassId
									+ "ListPanel");
				}
				if (panel != null)
				{
					dc.groupPanel = panel;
					panel.init(this, dc.groupId);
					groupTabbedPane.addTab(dc.groupName, panel);
				}
				
				//��ʱ����
				if(dc.groupId.equals("text"))
				{
					for(Object o:dc.getDataList().toArray())
						((BaseData)o).openForText();
				}
			}
		}
		inited = true;
		return true;
	}

	public void closeDataEditor(EditorPanel panel)
	{
		if (panel == null)
		{
			return;
		}
		BaseData bd = panel.getData();
		if (bd != null && EditorConfig.useDataBase)
		{
			bd.releaseLock();
		}

		editorTabbedPane.remove(panel);
	}

	public void closeDataEditor(int index)
	{
		// System.out.println("XEditor.closeDataEditor: index="+index);
		if (index < 0 || index >= editorTabbedPane.getTabCount())
			return;
		EditorPanel panel = (EditorPanel) editorTabbedPane
				.getComponentAt(index);
		if (panel == null)
			return;
		if (panel.close())
		{
			closeDataEditor(panel);
		}
	}

	public boolean openDataEditor(BaseData bd)
	{

		if (EditorConfig.useDataBase && !bd.isNew)
		{
			// ���ݰ汾��������
			if (Util.indexOf(XEditor.user.id,
					EditorConfig.SPECIAL_RIGHT_OF_USER) < 0
					&& !bd.chechVersion())
			{
				JOptionPane.showMessageDialog(XEditor.xEditor.getJFrame(),
						"ע�⣬��ǰ���ݲ����������ݣ��������أ�");
				return false;
			}
			// ������
			bd.getLock();

		}

		EditorPanel panel;
		int num = editorTabbedPane.getTabCount();
		for (int i = 0; i < num; i++)
		{
			panel = (EditorPanel) editorTabbedPane.getComponentAt(i);
			if (bd == panel.getData())
			{
				editorTabbedPane.setSelectedIndex(i);
				return true;
			}
		}

		DataGroup dc = DataManager.getDataGroup(bd.type);
		if (dc == null)
			return false;
		panel = (EditorPanel) Util.loadClassByName("com.doteplay.editor.data."
				+ dc.groupId + "." + dc.groupClassId + "EditPanel");
		if (panel != null)
		{
			panel.init(this, dc.groupId);
			// bd.editor=panel;
			panel.setData(bd);
			editorTabbedPane.addTab(panel.getTitle(), panel);
			editorTabbedPane.setSelectedComponent(panel);
		}
		return true;
	}

	public void updateDataEditorTitle(EditorPanel ep)
	{
		int i = editorTabbedPane.indexOfComponent(ep);
		if (i == -1)
			return;

		// System.out.println("editor title:"+ep.getTitle());

		editorTabbedPane.setTitleAt(i, ep.getTitle());

	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	public JFrame getJFrame()
	{
		if (jFrame == null)
		{
			try
			{
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/img/icon/icon_16.png")));
			jFrame.setJMenuBar(getJMenuBar());
			jFrame.setSize(1024, 800);
			jFrame.setLocationRelativeTo(null);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("������Ϸ�༭�� ");
			jFrame.addWindowListener(new java.awt.event.WindowAdapter()
			{
				public void windowClosing(java.awt.event.WindowEvent e)
				{
					DataManager.releaseAllGroupDataLock();
					System.out.println(jFrame.getTitle() + " closed!");
				}
			});

		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar1(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJMenuBar()
	{
		if (jMenuBar == null)
		{
			jMenuBar = new JMenuBar();
			jMenuBar.add(getFileMenu());
		}
		return jMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu()
	{
		if (fileMenu == null)
		{
			fileMenu = new JMenu();
			fileMenu.setText("�ļ�");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getImportMenu());
			fileMenu.add(getExportMenu());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem()
	{
		if (exitMenuItem == null)
		{
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("�˳�");
			exitMenuItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes aboutDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog()
	{
		if (aboutDialog == null)
		{
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("����");
			aboutDialog.setSize(new Dimension(251, 169));
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane()
	{
		if (aboutContentPane == null)
		{
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getAboutVersionLabel()
	{
		if (aboutVersionLabel == null)
		{
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("BuildVersion "
					+ EditorConfig.VERSION_BUILD);
			aboutVersionLabel.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/about.png")));
			aboutVersionLabel.setPreferredSize(new Dimension(300, 200));
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCutMenuItem()
	{
		if (cutMenuItem == null)
		{
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("����");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCopyMenuItem()
	{
		if (copyMenuItem == null)
		{
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("����");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPasteMenuItem()
	{
		if (pasteMenuItem == null)
		{
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("ճ��");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveMenuItem()
	{
		if (saveMenuItem == null)
		{
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("����");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
			saveMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
			{
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					EditorPanel panel = (EditorPanel) editorTabbedPane
							.getSelectedComponent();
					if (panel == null)
						return;

					if (panel.save())
					{
						updateDataEditorTitle(panel);
						BaseData bd = panel.getData();

					}
				}
			});
		}
		return saveMenuItem;
	}
}
