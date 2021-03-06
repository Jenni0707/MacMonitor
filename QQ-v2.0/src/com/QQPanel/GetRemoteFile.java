package com.QQPanel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.config.UploadConfig;
import com.QQPanel.ICQPOP;
import com.topking.ftp.bean.ConfigBean;
import com.topking.ftp.bean.UserInfoBean;

@SuppressWarnings("serial")
public class GetRemoteFile extends JPanel {
	private ICQPOP icqPOP;
	private UserInfoBean uib;
	private ConfigBean cfb;

	public GetRemoteFile(ICQPOP icq, UserInfoBean uibean, ConfigBean cfbean) { // 点击自定义文件后缀按钮后的处理
		this.icqPOP = icq;
		this.uib = uibean;
		this.cfb = cfbean;
		final JDialog dialog = new JDialog(icqPOP, "获取远程文件", true);
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(new GridLayout(3, 1));

		JLabel label = new JLabel("   请输入您需要的文件的绝对路径！", JLabel.LEFT);
		final JTextField tf = new JTextField(15);
		Box box = Box.createHorizontalBox();
		box.add(Box.createRigidArea(new Dimension(15,5)));
		box.add(tf);
		box.add(Box.createRigidArea(new Dimension(15,5)));
		JPanel p = new JPanel();
		JButton okButton = new JButton("确定");

		okButton.addActionListener(new ActionListener() { // 按"确定"键后,用户输入的后缀名生效
			public void actionPerformed(ActionEvent e) {
				System.out.println("正在应用配置_获取文件,请稍候……");
				String rfp = tf.getText();
				cfb.setRemoteFilePath(rfp);
				icqPOP.cfgmap.put(uib.getUserID(), cfb.toString());
				new UploadConfig(uib, cfb);
				dialog.dispose();
			}
		});
		p.add(okButton);

		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("取消获取文件……");
				dialog.dispose();
			}
		});
		p.add(cancelButton);

		dialogPane.add(label);
		dialogPane.add(box);
		dialogPane.add(p);
		Toolkit kit = Toolkit.getDefaultToolkit();
		dialog.setBounds(kit.getScreenSize().width / 25 * 14,
				kit.getScreenSize().height / 25 * 7, 400, 135);
		dialog.setVisible(true);
	}

	// public static void main(String[] args) throws Exception{
	// new GetRemoteFile(null,null,null);
	// }
}