package com.doteplay.editor.tools.tageditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * �����ˣ�wyc             
 * ��������������������ɫ
 *
 * �޸��ˣ�                   
 * �汾�ţ�
 * ����ʱ�䣺May 12, 2010 6:11:05 PM
 */
public class FontColor extends JDialog{ 
	private  String colorIndex = "0";  //  @jve:decl-index=0:
	private JPanel basePanel = null;  //  @jve:decl-index=0:visual-constraint="59,1"
	private JButton yesButton = null;
	private JButton noButton = null;
//	private JFrame jframe = null;
	private JLabel foreLabel = null;
	private JLabel backLabel = null;
	private JScrollPane titleScrollPane = null;
	private JPanel jPanel = null;
	private JTextArea titleTextArea = null;
	
	private String srcColorIndex = "0";//ԭ����ֵȡ��Ҫ��ԭ��  //  @jve:decl-index=0:
	
	private TagEditor tt =  null;//������ȡԭ�����齨�ø������е���ʽ
	private int begin = 0;//�ı�����ѡ������Ŀ�ʼλ��
	private int end = 0;//�ı�����ѡ������Ľ���λ��
	
	
	private JPanel totalPanel = null;
//	private JButton lianghuang = null;
//	private JButton zhhong = null;
//	private JButton zhengbai = null;
//	private JButton hongse = null;
//	private JButton anhuang = null;
//	private JButton zhenghuang = null;
//	private JButton shenhe = null;
//	private JButton zhenglv = null;
//	private JButton qianhe = null;
//	private JButton zhenghong = null;
//	private JButton zhenghui = null;
//	private JButton shenhui = null;
//	private JButton juhuang = null;
//	private JButton qianhui = null;
//	private JButton zhenglan = null;
//	private JButton zhengzi = null;
//	private JButton anhuangse = null;
	private JButton[] jbuttons =  null;
	
	private ButtonAction buttonAction  =  null;
	private JScrollPane titleScrollPane1 = null;
	private JTextArea titleTextArea1 = null;
	private JLabel selectedLabel = null;
	private JLabel selectLabel = null;
	public void setTt(TagEditor tt) {
		this.tt = tt;
	}

	public FontColor(String colorIndex,TagEditor tt){
		this.colorIndex =  colorIndex;
		this.srcColorIndex =  colorIndex;
		buttonAction = new ButtonAction(this);//��ť����
		this.tt =  tt;
		jbuttons = new JButton[tt.colorMap.size()];
		this.setContentPane(getBasePanel());
	}

	/**
	 * This method initializes basePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public  JPanel getBasePanel() {
		if (basePanel == null) {
			selectLabel = new JLabel();
			selectLabel.setText("");
			selectLabel.setBounds(new Rectangle(67, 160, 34, 24));
			selectedLabel = new JLabel();
			selectedLabel.setBounds(new Rectangle(0, 160, 62, 24));
			selectedLabel.setText("��ѡ����:");
			basePanel = new JPanel();
			basePanel.setLayout(null);
			basePanel.setSize(new Dimension(255, 238));
			basePanel.add(getTotalPanel(), null);
			basePanel.add(getYesButton(), null);
			basePanel.add(getNoButton(), null);
			basePanel.add(getJPanel());
			basePanel.add(getTitleScrollPane1(), null);
			basePanel.add(selectedLabel, null);
			basePanel.add(selectLabel, null);
			for(int i = 0;i<jbuttons.length;i++){
				JButton jbutton = new JButton();
				jbutton.setText("");
				jbutton.setPreferredSize(new Dimension(34, 24));
				jbutton.setActionCommand(""+i);
				jbutton.addActionListener(buttonAction);
				jbutton.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
				jbutton.setBackground(tt.colorMap.get(""+i));
				jbuttons[i] =  jbutton;
				totalPanel.add(jbutton,null);
			}
			
			selectLabel.setOpaque(true);
			selectLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
			/*
			 * ��ʼ���ؼ����������-----begin
			 */
			if ((begin>0)&&'>' == tt.wordTextPane.getText().charAt(
					begin - 1)
					&& '$' == tt.wordTextPane.getText().charAt(
							begin - 2)) {// ��Ҫ���ǲ�����ԭ���еı�ǩ��
//				if (tt.resultTextPane.getSelectionStart() != 0) {
					String s = tt.wordTextPane.getText().substring(tt.wordTextPane.getText()
							   .substring(0, begin).lastIndexOf("<#"),begin);
					String[] sArray = s.split("\\|");
					String[] chaolianjie = sArray[1].split("=");
					colorIndex = chaolianjie[1];
					Color c = tt.colorMap.get(colorIndex);
					this.foreLabel.setBackground(c);
					this.titleTextArea1.setForeground(c);
					this.selectLabel.setBackground(c);
//				} 
			}

			/*
			 * ��ʼ���ؼ����������-----end
			 */
			
		}
		return basePanel;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			backLabel = new JLabel();
			backLabel.setOpaque(true);
			backLabel.setBackground(Color.white);
			backLabel.setBounds(new Rectangle(15, 15, 68, 68));
			backLabel.setText("");
			foreLabel = new JLabel();
			foreLabel.setOpaque(true);
			foreLabel.setBackground(Color.BLACK);
			foreLabel.setBounds(new Rectangle(2, 2, 68, 68));
			foreLabel.setText("");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(0, 58, 91, 91));
			jPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			backLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			foreLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jPanel.add(foreLabel, null);
			jPanel.add(backLabel, null);
		}
		return jPanel;
	}
	/**
	 * This method initializes yesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getYesButton() {
		if (yesButton == null) {
			yesButton = new JButton();
			yesButton.setBounds(new Rectangle(40, 200, 61, 29));
			yesButton.setText("ȷ��");
			yesButton.setActionCommand("yesButton");
			yesButton.addActionListener(buttonAction);
		}
		return yesButton;
	}

	/**
	 * This method initializes noButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNoButton() {
		if (noButton == null) {
			noButton = new JButton();
			noButton.setBounds(new Rectangle(140, 200, 61, 29));
			noButton.setText("ȡ��");
			noButton.setActionCommand("noButton");
			noButton.addActionListener(buttonAction);
		}
		return noButton;
	}

	class ButtonAction implements ActionListener{
		private FontColor fc = null;
		public ButtonAction(FontColor fc){
			this.fc =  fc;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("yesButton")){//���ȷ����ť
				String  str = "";
				//���������һ����ԭ�е�tag��ǩ�б༭
				
				//�ڶ��ֽ����µ�tag��ǩ
				if(begin == 0){//˵������ʼλ��---����˵�����Բ�������ԭ�еĻ����ϱ༭
						str += "<#|c="+fc.colorIndex+"|a=0|$>";
						str += fc.tt.resultTextPane.getSelectedText();
						str += "</#>";
//						str += fc.tt.wordTextPane.getText().substring(end); 
						if('>'==fc.tt.wordTextPane.getText().charAt(end-1)&&'$'==fc.tt.wordTextPane.getText().charAt(end-2)){
							str += fc.tt.wordTextPane.getText().substring(fc.tt.wordTextPane.getText().substring(0,end).lastIndexOf("<#"));
						} else {
							str += fc.tt.wordTextPane.getText().substring(end);
						} 
						fc.tt.setContent(str);
						fc.tt.dealWith(str);
					
					
				} else  {
					
					if('>' == fc.tt.wordTextPane.getText().charAt(begin - 1)&&'$' == fc.tt.wordTextPane.getText().charAt(begin - 2)){//��Ҫ���ǲ�����ԭ���еı�ǩ��
						if(fc.tt.resultTextPane.getSelectionStart() != 0){
							String s = fc.tt.wordTextPane.getText().substring(fc.tt.wordTextPane.getText().substring(0,begin).lastIndexOf("<#"),begin);
							String []sArray =  s.split("\\|");
							sArray[1] = "c="+colorIndex;
							
							String ss = "";
							for(int i = 0 ;i < sArray.length ; i++){
								ss += sArray[i];
								if( i != sArray.length - 1){
									ss += "|";
								}
							}
	//						ss += fc.tt.resultTextPane.getSelectedText() + "</#>";
							String str2 = "";
							str2 +=  fc.tt.wordTextPane.getText().substring(0,fc.tt.wordTextPane.getText().substring(0,begin).lastIndexOf("<#"));
							str2 += ss;
							str2 += fc.tt.wordTextPane.getText().substring(begin);	
							fc.tt.setContent(str2);
							fc.tt.dealWith(str2);
						
						}else{//�ڿ�ʼλ��Ϊ0�ĵط��༭
							String s = fc.tt.wordTextPane.getText().substring(fc.tt.wordTextPane.getText().substring(0,begin).lastIndexOf("<#"),begin);
							String []sArray =  s.split("\\|");
							sArray[1] = "c="+colorIndex;
							
							String ss = "";
							for(int i = 0 ;i < sArray.length ; i++){
								ss += sArray[i];
								if( i != sArray.length - 1){
									ss += "|";
								}
							}
							String str2 = "";
							str2 += ss;
							str2 += fc.tt.resultTextPane.getSelectedText()+"</#>";
//							str2 += fc.tt.wordTextPane.getText().substring(end);
							if('>'==fc.tt.wordTextPane.getText().charAt(end-1)&&'$'==fc.tt.wordTextPane.getText().charAt(end-2)){
								str2 += fc.tt.wordTextPane.getText().substring(fc.tt.wordTextPane.getText().substring(0,end).lastIndexOf("<#"));
							} else {
								str2 += fc.tt.wordTextPane.getText().substring(end);
							} 
							fc.tt.setContent(str2);
							fc.tt.dealWith(str2);
						}
						
						
					} else{//���ǵĻ�ֱ��ƴ���ַ���
						str += fc.tt.wordTextPane.getText().substring(0,begin);
						str += "<#|c="+fc.colorIndex+"|a=0|$>";
						str += fc.tt.resultTextPane.getSelectedText();
						str += "</#>";
						if('>'==fc.tt.wordTextPane.getText().charAt(end-1)&&'$'==fc.tt.wordTextPane.getText().charAt(end-2)){
							str += fc.tt.wordTextPane.getText().substring(fc.tt.wordTextPane.getText().substring(0,end).lastIndexOf("<#"));
						} else {
							str += fc.tt.wordTextPane.getText().substring(end);
						} 
						fc.tt.setContent(str);
						fc.tt.dealWith(str);
						
					}
					
					
				}
				
				 
				
//				fc.tt.content += "+1";
//				fc.tt.dealWith(fc.tt.content);
//				jframe.setVisible(false);
//				jframe = null;
				dispose();
				
			} else if(e.getActionCommand().equals("noButton")){//���ȡ��
				dispose();
//				jframe.setVisible(false);
//				jframe = null;
				
			} else {//�����ɫ��ť
				colorIndex =  e.getActionCommand();
				Color c = fc.tt.colorMap.get(e.getActionCommand());
				JButton jb = (JButton)e.getSource();
				jb.setBackground(c);
				selectLabel.setBackground(c);
				foreLabel.setBackground(c);
				titleTextArea1.setForeground(c);
				
				for(int i = 0 ; i <jbuttons.length ; i++){
					jbuttons[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
					jbuttons[i].setBackground(tt.colorMap.get(""+i));
					
				}
			}
			
		}
		
	}
	/**
	 */
//	public void setJframe(JFrame  jframe){
//		this.jframe =  jframe;
//	}
	
	/**
	 * This method initializes totalPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTotalPanel() {
		if (totalPanel == null) {
			GridLayout gridLayout3 = new GridLayout();
			gridLayout3.setRows(2);
			gridLayout3.setColumns(9);
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(4);
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(4);
			gridLayout1.setColumns(4);
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			totalPanel = new JPanel();
			totalPanel.setBounds(new Rectangle(0, 0, 255, 48));
			totalPanel.setLayout(gridLayout3);
		}
		return totalPanel;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * This method initializes titleScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getTitleScrollPane1() {
		if (titleScrollPane1 == null) {
			titleScrollPane1 = new JScrollPane();
			titleScrollPane1.setBounds(new Rectangle(96, 58, 153, 93));
			titleScrollPane1.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			titleScrollPane1.setViewportView(getTitleTextArea1());
		}
		return titleScrollPane1;
	}

	/**
	 * This method initializes titleTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTitleTextArea1() {
		if (titleTextArea1 == null) {
			titleTextArea1 = new JTextArea();
			titleTextArea1.setText("\u6548\u679c\u9884\u89c8");
			titleTextArea1.setLineWrap(true);
		}
		return titleTextArea1;
	}


}
