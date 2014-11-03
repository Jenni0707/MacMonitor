package com.config;

import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.topking.ftp.bean.ConfigBean;

public class MailPanel extends JPanel {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mailPane;
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel12 = new JLabel();
	private JLabel jLabel3 = new JLabel();
	private JLabel jLabel6 = new JLabel();
	private JLabel jLabel7 = new JLabel();
	private JLabel jLabel8 = new JLabel();
	private JTextField toField = new JTextField();// 地址文本框
	private JTextField userField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JTextField pop3Field = new JTextField();
	private JTextField smtpField = new JTextField();
	private ConfigBean cfb;

	public MailPanel(ConfigBean cfbean) {
		try {
			this.cfb = cfbean;
			mailPane = new JPanel();
			mailPane.setLayout(null);

			jLabel1.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
			jLabel1.setText("邮箱地址:");
			jLabel1.setBounds(new Rectangle(25, 17, 68, 28));
			toField.setFont(new java.awt.Font("Monospaced", 0, 14));
			toField.setBounds(new Rectangle(100, 20, 170, 28));
			toField.setText(cfb.getEmailURL());
			jLabel6.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
			jLabel6.setText("邮箱帐号:");
			jLabel6.setBounds(new Rectangle(25, 62, 68, 29));
			userField.setFont(new java.awt.Font("Monospaced", 0, 14));
			userField.setBounds(new Rectangle(100, 62, 170, 28));
			userField.setText(cfb.getEmailName());
			jLabel12.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
			jLabel12.setToolTipText("");
			jLabel12.setText("邮箱密码:");
			jLabel12.setBounds(new Rectangle(25, 100, 65, 29));
			passwordField.setFont(new java.awt.Font("Monospaced", 0, 14));
			passwordField.setBounds(new Rectangle(100, 100, 170, 28));
			passwordField.setText(cfb.getEmailPassWD());
			jLabel7.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
			jLabel7.setText("POP3服务器:");
			jLabel7.setBounds(new Rectangle(25, 140, 85, 31));
			pop3Field.setFont(new java.awt.Font("Monospaced", 0, 14));
			pop3Field.setBounds(new Rectangle(100, 140, 170, 28));
			jLabel8.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
			jLabel8.setText("SMTP服务器:");
			jLabel8.setBounds(new Rectangle(25, 180, 93, 29));
			smtpField.setFont(new java.awt.Font("Monospaced", 0, 14));
			smtpField.setBounds(new Rectangle(100, 180, 170, 28));

			mailPane.add(jLabel1, null);
			mailPane.add(toField, null);
			mailPane.add(jLabel3, null);
			mailPane.add(passwordField, null);
			mailPane.add(userField, null);
			mailPane.add(jLabel6, null);
			mailPane.add(jLabel12, null);
			mailPane.add(smtpField, null);
			mailPane.add(jLabel8, null);
			mailPane.add(pop3Field, null);
			mailPane.add(jLabel7, null);
			setConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setConfig() {
		cfb.setEmailURL(toField.getText());
		cfb.setEmailName(userField.getText());
		cfb.setEmailPassWD(new String(passwordField.getPassword()));
	}

	public JPanel getPanel() { // 返回该面板
		return mailPane;
	};
}