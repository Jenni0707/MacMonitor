package com.topking.ftp.util;

import java.io.IOException;
import javax.swing.JOptionPane;
import sun.net.ftp.FtpClient;

public class GetFTP {
	String host;
	String name;
	String pass;

	public GetFTP(String hostID, String ftpName, String passwd) {
		this.host = hostID;
		this.name = ftpName;
		this.pass = passwd;
	}

	public FtpClient getFtp() {
		try {
			FtpClient fc = new FtpClient();
			fc.setConnectTimeout(500);
			fc.openServer(host);
			fc.login(name, pass);
			return fc;
		} catch (IOException e) {
			String strPrompt = "连接主机:" + host + "失败!";
			JOptionPane.showMessageDialog(null, strPrompt, "错误",
					JOptionPane.ERROR_MESSAGE);
		} catch (SecurityException e1) {
			JOptionPane.showMessageDialog(null, "无权限与主机连接", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
		JOptionPane.showMessageDialog(null, "FTP链接错误！", "错误",
				JOptionPane.ERROR_MESSAGE);
		return null;
	}
}
