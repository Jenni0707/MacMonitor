package com.config;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.topking.ftp.bean.ConfigBean;
import com.topking.ftp.bean.UserInfoBean;

public class FtpPanel extends JPanel implements ItemListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JPanel ftpPane;
	private JLabel labelName; // 个人用户名
	private JTextField textName; // FTP用户名主目录，如“No1”
	private JLabel ftpTime;
	private String intervelTime;
	private JLabel timeUnit;
	private JLabel screenTime;
	private String intervelScreenTime;
	private JLabel screentimeUnit;
	private JComboBox ftpTimeList; // ftp上传时间间隔设置的下拉菜单
	private JComboBox screenTimeList; // 截屏时间间隔设置的下拉菜单
	private JCheckBox jck1; // 键盘开关
	private boolean OpenKeyBoard;
	private JCheckBox jck3; // 截屏开关
	private boolean OpenScreenCapture;
	private JCheckBox jck4; // 时间截屏开关
	private boolean OpenScreenTimeCapture;
	private UserInfoBean uib;
	private ConfigBean cfb;

	// 界面初始化并显示
	public FtpPanel(UserInfoBean uibean, ConfigBean cfbean) {//
		this.uib = uibean;
		this.cfb = cfbean;
		ftpPane = new JPanel();
		ftpPane.setLayout(null);
		ftpPane.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));

		labelName = new JLabel("用户名:");
		labelName.setBounds(new Rectangle(20, 20, 50, 25));
		labelName.setFont(new java.awt.Font("Monospaced", 0, 12));
		String userInfo = uib.getUserName() + "(" + uib.getUserID()+ ")";
		textName = new JTextField(userInfo);
		textName.setEditable(false);
		textName.setBounds(new Rectangle(100, 20, 170, 25));
		textName.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));

		ftpTime = new JLabel("FTP通信间隔:");
		ftpTime.setBounds(new Rectangle(20, 55, 100, 25));
		ftpTime.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		timeUnit = new JLabel("秒");
		timeUnit.setBounds(new Rectangle(200, 55, 100, 25));
		timeUnit.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		String[] timePeriod = { "15", "30", "60", "150", "300" };
		ftpTimeList = new JComboBox(timePeriod); // 下拉菜单
		intervelTime = cfb.getIntervelTime();
		ftpTimeList.addItem(intervelTime);
		ftpTimeList.setSelectedItem(intervelTime);
		cfb.setIntervelTime(((String) ftpTimeList.getSelectedItem()));
		ftpTimeList.setEditable(true);
		ftpTimeList.setBounds(new Rectangle(100, 55, 80, 25));
		ftpTimeList.addItemListener(this);
		ftpTimeList.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));

		screenTime = new JLabel("截屏时间间隔:");
		screenTime.setBounds(new Rectangle(20, 90, 100, 25));
		screenTime.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		screentimeUnit = new JLabel("秒");
		screentimeUnit.setBounds(new Rectangle(200, 90, 100, 25));
		screentimeUnit.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		String[] screentimePeriod = {"30", "60", "90", "120", "150", "180"};
		screenTimeList = new JComboBox(screentimePeriod); // 下拉菜单
		intervelScreenTime = cfb.getIntervelScreenTime();
		screenTimeList.addItem(intervelScreenTime);
		screenTimeList.setSelectedItem(intervelScreenTime);
		cfb.setIntervelScreenTime(((String) screenTimeList.getSelectedItem()));
		screenTimeList.setEditable(true);
		screenTimeList.setBounds(new Rectangle(100, 90, 80, 25));
		screenTimeList.addItemListener(this);
		screenTimeList.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		
		jck1 = new JCheckBox("键盘获取");
		OpenKeyBoard = cfb.getKeyBoard();//OpenKeyBoard为真，则开关关闭
		jck1.setSelected(!OpenKeyBoard);
		/*if(OpenKeyBoard){
			jck1.setText("关闭键盘记录(已关闭)");
		} else jck1.setText("打开键盘记录(已打开)");*/
		jck1.setBounds(new Rectangle(30, 125, 180, 25));
		jck1.addItemListener(this);
		
		jck3 = new JCheckBox("按焦点截屏");
		OpenScreenCapture = cfb.getScreenCapture();
		jck3.setSelected(!OpenScreenCapture);
		/*if(OpenScreenCapture) {
			jck3.setText("关闭焦点截屏开关(已关闭)");
		}else {
			jck3.setText("打开焦点截屏开关(已打开)");
		}*/
		jck3.setBounds(new Rectangle(30, 160, 180, 25));
		jck3.addItemListener(this);

		jck4 = new JCheckBox("按时间截屏");
		OpenScreenTimeCapture = cfb.getScreenTimeCapture();
		jck4.setSelected(!OpenScreenTimeCapture);
		if(OpenScreenTimeCapture){
			//jck4.setText("关闭时间截屏开关(已关闭)");
			screenTimeList.setEnabled(false);
		}else {
			//jck4.setText("打开时间截屏开关(已打开)");
			screenTimeList.setEnabled(true);
		}
		jck4.setBounds(new Rectangle(30, 195, 180, 25));
		jck4.addItemListener(this);
		
		ftpPane.add(labelName, null);
		ftpPane.add(textName, null);
		ftpPane.add(ftpTimeList, null);
		ftpPane.add(ftpTime, null);
		ftpPane.add(timeUnit, null);
		ftpPane.add(screenTimeList, null);
		ftpPane.add(screenTime, null);
		ftpPane.add(screentimeUnit, null);
		ftpPane.add(jck1, null);
		ftpPane.add(jck3, null);
		ftpPane.add(jck4, null);
		setConfig();
	}

	public JPanel getPanel() { // 返回该面板
		return ftpPane;
	};

	public void setConfig() {
		cfb.setIntervelTime(intervelTime);
		cfb.setIntervelScreenTime(intervelScreenTime);
		cfb.setKeyBoard(OpenKeyBoard);
		cfb.setScreenCapture(OpenScreenCapture);
		cfb.setScreenTimeCapture(OpenScreenTimeCapture);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (e.getSource() == ftpTimeList) {
				intervelTime = ((String) ftpTimeList.getSelectedItem());
				cfb.setIntervelTime(intervelTime);
				System.out.println("IntervelTime=" + intervelTime);
			}
			if (e.getSource() == screenTimeList) {
				intervelScreenTime = ((String) screenTimeList.getSelectedItem());
				cfb.setIntervelScreenTime(intervelScreenTime);
				System.out.println("IntervelScreenTime=" + intervelScreenTime);
			}
			if (e.getSource() == jck1) {
				//jck1.setText("打开键盘记录(已打开)");
				OpenKeyBoard = false;
				cfb.setKeyBoard(OpenKeyBoard);
				System.out.println("OpenKeyBoard=" + OpenKeyBoard);
			}
			if (e.getSource() == jck3) {
				//jck3.setText("打开焦点截屏开关(已打开)");
				OpenScreenCapture = false;
				cfb.setScreenCapture(OpenScreenCapture);
				System.out.println("OpenScreenCapture=" + OpenScreenCapture);
			}
			if (e.getSource() == jck4) {
				//jck4.setText("打开时间截屏开关(已打开)");
				screenTimeList.setEnabled(true);
				OpenScreenTimeCapture = false;
				cfb.setScreenCapture(OpenScreenTimeCapture);
				System.out.println("OpenScreenTimeCapture=" + OpenScreenTimeCapture);
			}
		}

		if (e.getStateChange() == ItemEvent.DESELECTED) {
			if (e.getSource() == jck1) {
				//jck1.setText("关闭键盘记录(已关闭)");
				OpenKeyBoard = true;
				cfb.setKeyBoard(OpenKeyBoard);
				System.out.println("OpenKeyBoard=" + OpenKeyBoard);
			}
			if (e.getSource() == jck3) {
				//jck3.setText("关闭焦点截屏开关(已关闭)");
				OpenScreenCapture = true;
				cfb.setScreenCapture(OpenScreenCapture);
				System.out.println("OpenScreenCapture=" + OpenScreenCapture);
			}
			if (e.getSource() == jck4) {
				//jck4.setText("关闭时间截屏开关(已关闭)");
				screenTimeList.setEnabled(false);
				OpenScreenTimeCapture = true;
				cfb.setScreenCapture(OpenScreenTimeCapture);
				System.out.println("OpenScreenCapture=" + OpenScreenTimeCapture);
			}
		}
	}
}