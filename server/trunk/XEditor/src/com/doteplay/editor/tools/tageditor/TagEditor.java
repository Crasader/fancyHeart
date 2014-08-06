package com.doteplay.editor.tools.tageditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.doteyplay.game.editor.ResourceDataConstants;


public class TagEditor extends JPanel {

	private JPanel basePanel = null;  //  @jve:decl-index=0:visual-constraint="26,19"
	private JToolBar toolsToolBar = null;
	private JButton colorButton = null;
	private JButton linkButton = null;
	public String content = "";// ���� // @jve:decl-index=0:
	private JSplitPane totalSplitPane = null;
	private JScrollPane resultScrollPane = null;
	private JScrollPane wordScrollPane = null;
	/*
	 * ����һ����ɫ��������ɫ���map�ṹ
	 */
	public Map<String, Color> colorMap = new Hashtable<String, Color>(); // @jve:decl-index=0:
	public  JTextPane resultTextPane = null;
	public  JTextPane wordTextPane = null;
	
	public Map<Integer,Integer>numberIndex = new Hashtable<Integer,Integer>();  //  @jve:decl-index=0:
	int srcNumber = 0;
	int descNumber = 0;
	public Map<String ,Icon>imageMap =  new Hashtable<String,Icon>();  //  @jve:decl-index=0:
	/**
	 * ��Ӱ�ť����
	 */
	private ButtonAction buttonAction = null;  //  @jve:decl-index=0:
	private TeatPaneListener tpl = null;
	private JButton icoButton = null;
	private JButton openButton = null;
	private JButton saveButton = null;
	private boolean flag = false;
	private File file = null;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		wordTextPane.addFocusListener(tpl);
	}

	/**
	 * This method initializes
	 * 
	 */
	public TagEditor(String content,boolean flag) {
		super();
		/**
		 * ʵ������ɫ
		 */
		for (int i = 0; i < ResourceDataConstants.TAG_COLORS.length; i++) {
			int color = ResourceDataConstants.TAG_COLORS[i];
			colorMap.put(""+i, new Color(color));
		}
		
		/**
		 * ʵ����ͼ��
		 */
		imageMap.put("/em01", new ImageIcon(getClass().getResource("ico/01.GIF")));
		imageMap.put("/em02", new ImageIcon(getClass().getResource("ico/02.GIF")));
		imageMap.put("/em03", new ImageIcon(getClass().getResource("ico/03.GIF")));
		imageMap.put("/em04", new ImageIcon(getClass().getResource("ico/04.GIF")));
		imageMap.put("/em05", new ImageIcon(getClass().getResource("ico/05.GIF")));
		imageMap.put("/em06", new ImageIcon(getClass().getResource("ico/06.GIF")));
		imageMap.put("/em07", new ImageIcon(getClass().getResource("ico/07.GIF")));
		imageMap.put("/em08", new ImageIcon(getClass().getResource("ico/08.GIF")));
		imageMap.put("/em09", new ImageIcon(getClass().getResource("ico/10.GIF")));
		imageMap.put("/em10", new ImageIcon(getClass().getResource("ico/11.GIF")));
		imageMap.put("/em11", new ImageIcon(getClass().getResource("ico/12.GIF")));
		imageMap.put("/em12", new ImageIcon(getClass().getResource("ico/13.GIF")));
		imageMap.put("/em13", new ImageIcon(getClass().getResource("ico/14.GIF")));
		imageMap.put("/em14", new ImageIcon(getClass().getResource("ico/15.GIF")));
		imageMap.put("/em15", new ImageIcon(getClass().getResource("ico/16.GIF")));
		imageMap.put("/em16", new ImageIcon(getClass().getResource("ico/17.GIF")));
		imageMap.put("/em17", new ImageIcon(getClass().getResource("ico/18.GIF")));
		imageMap.put("/em18", new ImageIcon(getClass().getResource("ico/19.GIF")));
		imageMap.put("/em19", new ImageIcon(getClass().getResource("ico/20.GIF")));
		imageMap.put("/em20", new ImageIcon(getClass().getResource("ico/21.GIF")));
		imageMap.put("/em21", new ImageIcon(getClass().getResource("ico/22.GIF")));
		imageMap.put("/em22", new ImageIcon(getClass().getResource("ico/23.GIF")));
		imageMap.put("/em23", new ImageIcon(getClass().getResource("ico/24.GIF")));
		imageMap.put("/em24", new ImageIcon(getClass().getResource("ico/25.GIF")));
		imageMap.put("/em25", new ImageIcon(getClass().getResource("ico/26.GIF")));
		imageMap.put("/jin", new ImageIcon(getClass().getResource("ico/jin.GIF")));
		imageMap.put("/yin", new ImageIcon(getClass().getResource("ico/yin.GIF")));
		imageMap.put("/tong", new ImageIcon(getClass().getResource("ico/tong.GIF")));
		imageMap.put("/j$", new ImageIcon(getClass().getResource("ico/jinyb.GIF")));
		imageMap.put("/y$", new ImageIcon(getClass().getResource("ico/yinyb.GIF")));
		imageMap.put("/jy$", new ImageIcon(getClass().getResource("ico/j$.GIF")));
		imageMap.put("/yy$", new ImageIcon(getClass().getResource("ico/y$.GIF")));
		imageMap.put("/jb$", new ImageIcon(getClass().getResource("ico/jinq.GIF")));

		this.flag = flag; 
		buttonAction =  new ButtonAction(this);
		this.content = content;
		initialize();
		this.dealWith(this.content);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setBounds(new Rectangle(0, 0, 393, 177));
		this.add(getBasePanel(), BorderLayout.CENTER);

	}

	/**
	 * This method initializes basePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getBasePanel() {
		if (basePanel == null) {
			basePanel = new JPanel();
			basePanel.setLayout(new BorderLayout());
			basePanel.setBounds(new Rectangle(1, 1, 352, 129));
			basePanel.add(getToolsToolBar(), BorderLayout.NORTH);
			basePanel.add(getTotalSplitPane(), BorderLayout.CENTER);
		}
		return basePanel;
	}

	/**
	 * This method initializes toolsToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getToolsToolBar() {
		if (toolsToolBar == null) {
			toolsToolBar = new JToolBar();
			toolsToolBar.setPreferredSize(new Dimension(87, 24));
			if(flag){
				toolsToolBar.add(getOpenButton());
				toolsToolBar.add(getSaveButton());
			}
			toolsToolBar.add(getColorButton());
			toolsToolBar.add(getLinkButton());
			toolsToolBar.add(getIcoButton());
		}
		return toolsToolBar;
	}

	/**
	 * This method initializes colorButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getColorButton() {
		if (colorButton == null) {
			colorButton = new JButton(); 
			colorButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/rtf_choosecolor.gif")));
			colorButton.setPreferredSize(new Dimension(23, 23));
			colorButton.setBounds(new Rectangle(16, 1, 23, 20));
			colorButton.setToolTipText("��ɫ�༭");
			colorButton.setActionCommand("colorButton");
			colorButton.addActionListener(this.buttonAction);
		}
		return colorButton;
	}

	/**
	 * This method initializes linkButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLinkButton() {
		if (linkButton == null) {
			linkButton = new JButton(); 
			linkButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/discrete_attribute.gif")));
			linkButton.setPreferredSize(new Dimension(23, 23));
			linkButton.setBounds(new Rectangle(44, 1, 23, 20));
			linkButton.setToolTipText("�����ӱ༭");
			linkButton.setActionCommand("linkButton");
			linkButton.addActionListener(this.buttonAction);
			
		}
		return linkButton;
	}

	/**
	 * This method initializes totalSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getTotalSplitPane() {
		if (totalSplitPane == null) {
			totalSplitPane = new JSplitPane();
			totalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			totalSplitPane.setContinuousLayout(false);
			totalSplitPane.setPreferredSize(new Dimension(5, 130));
			totalSplitPane.setOneTouchExpandable(true);
			totalSplitPane.setBottomComponent(getWordScrollPane());
			totalSplitPane.setTopComponent(getResultScrollPane());
		}
		return totalSplitPane;
	}

	/**
	 * This method initializes resultScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getResultScrollPane() {
		if (resultScrollPane == null) {
			resultScrollPane = new JScrollPane();
			resultScrollPane.setPreferredSize(new Dimension(3, 60));
			resultScrollPane.setViewportView(getResultTextPane());
		}
		return resultScrollPane;
	}

	/**
	 * This method initializes wordScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getWordScrollPane() {
		if (wordScrollPane == null) {
			wordScrollPane = new JScrollPane();
//			wordScrollPane.setPreferredSize(new Dimension(2, 80));
			wordScrollPane.setViewportView(getWordTextPane());
		}
		return wordScrollPane;
	}

	/**
	 * �����ַ���
	 */
	public void dealWithString(String src ) {
		SimpleAttributeSet attrSet  =  new SimpleAttributeSet();
		String str = src;
		if (str.startsWith("<#")) {
			descNumber = descNumber + str.substring(0,str.indexOf("$>")).length() + 2;
			String s = str.substring(2, str.indexOf(">"));
			String[] attributes = s.split("\\|");// ��һ�������һ����Ϊ��ǩ����
			// �ľ�ֻ���м䲿��
			Color col = colorMap.get(attributes[1].split("=")[1]);
			StyleConstants.setForeground(attrSet, col);
			StyleConstants.setUnderline(attrSet, true);

			int beginindex = str.indexOf("$>") + 2;
			int endindex = str.indexOf("</#>");
//			System.out.println(src + "\t:\t" + beginindex+"\t::"+endindex);
			dealWithResult(str.substring(beginindex, endindex), attrSet);
			descNumber += 4;
			str = str.substring(str.indexOf("</#>") + 4);
			this.dealWithString(str);
			

		} else {// ˵����ͷ���Ǳ�ǩ
			String s = "";
			if (str.indexOf("<#") > 0) {
				s = str.substring(0, str.indexOf("<#") > 0 ? str.indexOf("<#")
						: str.length());
				dealWithResult(s, attrSet);// �����ʾ���

			} else {
				
				dealWithResult(str, attrSet);// �����ʾ���
			}
			if (str.indexOf("<#") >= 0) {
				str = str.substring(str.indexOf("<#"));
				this.dealWithString(str);
			}
		}
		

	}

	/**
	 * �ѽ�������Ч����ʾ����
	 */
	public void dealWithResult(String str, SimpleAttributeSet attrSet) {
		Document doc = resultTextPane.getDocument();
		try {

			
			while( str!= null && !"".equals(str)){//ѭ������ͼƬ

				String key =  this.isIcoBegin(str);
				if(!"".equals(key)){//˵������ͼƬ��ͷ��
					Icon ico =  imageMap.get(key);
					resultTextPane.setCaretPosition(doc.getLength());
					this.resultTextPane.insertIcon(ico);
//					str =  str.replace(key, "");
					str = str.substring(key.length());
					descNumber = descNumber+key.length();
					this.numberIndex.put(srcNumber++, descNumber);
				} else {//�����������ֿ�ͷ��
					
					String s =  "";
					if(str.indexOf("/")>0){
						s = str.substring(0,str.indexOf("/"));
					} else {
						s = str;
						
					}
					String ss = s.substring(0) ; 
//					if(ss.indexOf("\r\n") > 0){
//						while(ss != null && !"".equals(ss.trim())){
//							
//							String sss = "";
//							String ssss =  "";
//							if(ss.indexOf("\r\n")>0){
//								sss = ss.substring(0,ss.indexOf("\r\n"));
//								ssss = sss+"\r\n";
//							} else {
//								sss =  ss;
//								ssss = sss;
//								
//							}
////							descNumber = descNumber+2;
////							System.out.println("����ѭ����");
////							descNumber++;
//							for(int i = 0 ;i<sss.length() ; i ++){
//								if(i!=sss.length()-1){
//									this.numberIndex.put(srcNumber++, descNumber++);	
//								} else {
////									System.out.println("--------555555--->");
////									descNumber = descNumber ++;
//									this.numberIndex.put(srcNumber, descNumber++);
//								}
//								
//							}
//							doc.insertString(doc.getLength(), ssss, attrSet);
//							this.numberIndex.put(srcNumber, descNumber);
//							if(ss.indexOf("\r\n")>0){
//								ss =  ss.substring(sss.length()+2);
//							} else {
//								ss = "";
//							}
//							
//							
//						}
//					} 
//			else {
						for(int i = 0 ;i<s.length() ; i ++){
							this.numberIndex.put(srcNumber++, descNumber++);
						}

						doc.insertString(doc.getLength(), s, attrSet);
//					}	
					if(s.indexOf("\r\n") >=0){
						descNumber = descNumber + (s.split("\\\r\\\n").length -1) ;
//						srcNumber = srcNumber + s.split("\\\r\\\n").length -1 ;
						this.numberIndex.put(srcNumber, descNumber);
					} 
//					str = str.replace(s, "");
					str = str.substring(s.length());
					
				}
				
			}
			
			
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:   " + e);
		}
	}
	
	/**
	 * �жϸ��ַ����Ƿ�����ͼ�꿪ͷ,����ǻ��򷵻ظ��ַ������򷵻�һ���մ�
	 */
	public String isIcoBegin(String str){
		String s = "";
		Set set = imageMap.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			Map.Entry<String,ImageIcon> me =  (Map.Entry<String,ImageIcon>)it.next();
			String key = me.getKey();
			if(str.startsWith(key)){//˵�����Ը�ͼƬ��ͷ��
				s =  key;
				return s;
			}
		}
		
		return "";
	}
	
	public void dealWith(String str){
		if(str == null){
			return;
		}
		str = str.trim();
		this.numberIndex =  null;
		this.numberIndex =  new Hashtable<Integer, Integer>();
		
		this.descNumber = 0;
		this.srcNumber = 0;
		wordTextPane.setText("");
		resultTextPane.setText("");
		this.dealWithString(str);
		this.dealWithWord(str);
		this.numberIndex.put(srcNumber, descNumber);
	}
	

	public void dealWithWord(String str) {
		SimpleAttributeSet attrSet  =  new SimpleAttributeSet();
		Document doc = wordTextPane.getDocument();
		try {
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:   " + e);
		}
	}

	/**
	 * This method initializes resultTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	public JTextPane getResultTextPane() {
		if (resultTextPane == null) {
			resultTextPane = new JTextPane();
			resultTextPane.setBackground(Color.WHITE);
			
		}
		return resultTextPane;
	}

	/**
	 * This method initializes wordTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	public JTextPane getWordTextPane() {
		if (wordTextPane == null) {
			wordTextPane = new JTextPane();
			tpl =  new TeatPaneListener(this);
			wordTextPane.addFocusListener(tpl);
		}
		return wordTextPane;
	}

	
	class ButtonAction implements ActionListener{
		private TagEditor tt =  null;
		public ButtonAction(TagEditor tt){
			this.tt = tt;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if("colorButton".equals(e.getActionCommand())){//ʹ��ɫ��ť
				String ct = tt.resultTextPane.getSelectedText();
				if(ct != null&&!"".equals(ct.trim())){//�ж��Ƿ��Ѿ�ѡȡ������
					FontColor fc = new FontColor("0",this.tt);
					fc.setResizable(false);
					fc.setTitle("��ɫ�༭��");
					int end = tt.resultTextPane.getSelectionEnd();
					int begin = tt.resultTextPane.getSelectionStart();
					Map<Integer,Integer> map  = tt.numberIndex;
					fc.setBegin(map.get(begin));//ѡ������Ŀ�ʼλ��
					fc.setEnd(map.get(end));//ѡ������Ľ���λ��
					fc.setBounds(250, 200, 255, 265);
					
					Dimension d=fc.getSize();
					Point loc = basePanel.getLocationOnScreen();
		        	loc.translate(d.width/3,0);
		        	fc.setLocation(loc);
		        	
					
					fc.setModal(true);
					fc.setVisible(true);
					
				} else {//û��ѡȡ����
					
					JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�༭������","��ʾ",1);
					
				}
			}
			
			if("linkButton".equals(e.getActionCommand())){//�ǳ����Ӱ�ť
				String ct = tt.resultTextPane.getSelectedText();
				if(ct != null&&!"".equals(ct.trim())){//�ж��Ƿ��Ѿ�ѡȡ������
					int end = tt.resultTextPane.getSelectionEnd();
					int begin = tt.resultTextPane.getSelectionStart();
					Map<Integer,Integer> map  = tt.numberIndex;
	
					UnderLine ul =  new UnderLine(map.get(begin),map.get(end),tt);
					ul.setResizable(false);
					ul.setTitle("�����ӱ༭");
					ul.setContentPane(ul.getTotalPanel());
					ul.setBounds(250, 200, 255, 265);
					
					Dimension d=ul.getSize();
					Point loc = basePanel.getLocationOnScreen();
		        	loc.translate(d.width/3,0);
		        	ul.setLocation(loc);
					
					ul.setModal(true);
					ul.setVisible(true);
					
				} else {//û��ѡ��༭������
					
					JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�༭������","��ʾ",1);
				}
			}
			
			if("icoButton".equals(e.getActionCommand())){//�����ͼƬѡ��ť
//				System.out.println(resultTextPane.get);
				
				int end = tt.resultTextPane.getSelectionEnd();
				int begin = tt.resultTextPane.getSelectionStart();
				Map<Integer,Integer> map  = tt.numberIndex;
				
				Ico ico =  new Ico(tt,map.get(begin));
				ico.setTitle("ͼ��༭");
				ico.setResizable(false);
				ico.setContentPane(ico.getTotalPanel());
				ico.setBounds(250, 200, 255, 265);

				Dimension d=ico.getSize();
				Point loc = basePanel.getLocationOnScreen();
	        	loc.translate(d.width/3,0);
	        	ico.setLocation(loc);
	        	
	        	ico.setModal(true);
				ico.setVisible(true);
			}

		}

	}
	
	class TeatPaneListener implements FocusListener{
		private TagEditor tt = null;
		public TeatPaneListener(TagEditor tt){
			this.tt = tt;
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			String content = tt.wordTextPane.getText();
			tt.setContent(content);
			tt.dealWith(content);
		}
		
	}
	
	
	
	
	
	
	/**
	 * This method initializes icoButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIcoButton() {
		if (icoButton == null) {
			icoButton = new JButton();
			icoButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/tileimg.png")));
			icoButton.setPreferredSize(new Dimension(23, 23));
			icoButton.setLocation(new Point(72, 1));
			icoButton.setSize(new Dimension(23, 23));
			icoButton.setToolTipText("ͼƬ�༭");
			icoButton.setActionCommand("icoButton");
			buttonAction = new ButtonAction(this);
			icoButton.addActionListener(buttonAction);
		}
		return icoButton;
	}

	/**
	 * This method initializes openButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/openFile.png")));
			openButton.setToolTipText("��");
			openButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
			        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//����ѡ��ģʽ���ȿ���ѡ���ļ��ֿ���ѡ���ļ���
					String fileFormat[] = { "txt" };
					FileFilter  filter = new FileNameExtensionFilter( "txt�ļ�",fileFormat);
			        chooser.setFileFilter(filter);//�����ļ���׺������
			        int  retval = chooser.showOpenDialog(null);//��ʾ"�����ļ�"�Ի���
			        file = null;//
			        if(retval == JFileChooser.APPROVE_OPTION) {//���ɹ���
			        	file = chooser.getSelectedFile();//�õ�ѡ����ļ���
		            }
			        /**
			         *  File f = Util.openSelectFileDialog("Excel�ļ�","xlsx");
			         */
					if(file == null){
						return;
					}
					InputStream inputStream = null;
					try {
						inputStream = new FileInputStream(file);
						byte a[] = new byte[1024];
						content = "";
						while(true){
							int t = inputStream.read(a);
							content += new String(a);
							if(t<1024){
								break;
							}
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}finally{
						if(inputStream != null){
							try {
								inputStream.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}							
						}
					}
					dealWith(content);
				
				}
			});
		}
		return openButton;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/saveFile.png")));
			saveButton.setToolTipText("����");
			saveButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(file == null){
						JFileChooser chooser = new JFileChooser();
				        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//����ѡ��ģʽ���ȿ���ѡ���ļ��ֿ���ѡ���ļ���
						String fileFormat[] = { "txt" };
						FileFilter  filter = new FileNameExtensionFilter( "TXT�ļ�",fileFormat);
				        chooser.setFileFilter(filter);//�����ļ���׺������
				        int  retval = chooser.showOpenDialog(null);//��ʾ"�����ļ�"�Ի���
				        if(retval == JFileChooser.APPROVE_OPTION) {//���ɹ���
				        	file = chooser.getSelectedFile();//�õ�ѡ����ļ���
			            }
						if(file == null){
							return;
						}
					}
				    OutputStream os;
					try {
						os = new FileOutputStream(file);
					    byte[]  b =  content.getBytes();
					    os.write(b);
					    os.flush();
					    os.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			});
		}
		return saveButton;
	}

	public static void main(String[] args) {

		final JFrame jframe = new JFrame("<tag>�༭��");
		jframe.setBounds(200, 150, 404, 200);
		Container c = jframe.getContentPane();
		final TagEditor tt = new TagEditor(
				"��ȫ:Java���������·�ͷֲ�ϵͳ����������µİ�ȫ���⣬Java�������������������ͷ�������System.��ʵ֤��Java�ڷ�����һ�������ıȽϺ�",true);
		///em1613�����Բ��������Բ��š� �����ӣ�/em0114�����⸣֮���У������֮������ �����ӣ�15���ϱ�֮ľ��/em11/em18���ں�ĩ���Ų�̨֮������/em20������ǧ��֮�У�ʼ�����¡������ӣ� /em09/em13/em13/em13
		// Ц/em01��10/jin20/yin90/tong
		// ssd/r/nddssd/r/nddssd/r/nddssd/r/nddssd/r/nddssd/r/ndd
//		JPanel jp =  new JPanel(null);
		jframe.getContentPane().add(tt);
		jframe.setVisible(true);
		
		jframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
//				if (JOptionPane.showConfirmDialog(jframe, "����ľ����˳���") == JOptionPane.YES_OPTION) {
				System.out.println("������Ϊ��"+tt.content);
					System.exit(0);
//				}

			}
		});
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
