package com.config;

import java.io.File;
import java.io.IOException;

import sun.net.ftp.FtpClient;

import com.QQPanel.ProgressBar;
import com.topking.ftp.bean.ConfigBean;
import com.topking.ftp.bean.UserInfoBean;
import com.topking.ftp.ui.MainFrame;
//import com.topking.ftp.ui.MainFrame.upLoadToRemote;
import com.topking.ftp.util.GetFTP;
import com.topking.ftp.util.UpLoadToRemote;

public class UploadConfig {
	private UserInfoBean uib;
	private ConfigBean cfb;

	public UploadConfig(UserInfoBean userib, ConfigBean cfbean) {
		this.uib = userib;
		this.cfb = cfbean;
		uploadConfigRun();
	}

	private void uploadConfigRun() {
		String path = System.getProperty("user.dir") + "\\config";
		ParseConfig pc = new ParseConfig(path, uib, cfb);
		Thread config = new Thread(pc);
		config.start();
		new ProgressBar(null, config, "正在生成配置,请稍候……", "新配置生成成功", "配置生成失败");
		try {
			config.join();// 等待线程结束
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		FtpClient myFtp = new GetFTP(uib.getFtpUrl(), uib.getFtpName(),
				uib.getFtpPasswd()).getFtp();
		if (myFtp == null)
			return;
		try {
			myFtp.ascii();
		} catch (IOException e) {
			System.err.println("error in UploadConfig 43:" + e.toString());
			// e2.printStackTrace();
		}
		if (pc.isFinish) { // 上传配置文件
			MainFrame mFrame = new MainFrame(myFtp);
			String remotePath = "/" + uib.getUserID() + "/";
			Thread configUpload = new Thread(new UpLoadToRemote(mFrame,
					remotePath, path, myFtp));
			configUpload.start();
			new ProgressBar(null, configUpload, "正在应用配置,请稍候……", "新配置上传成功", "配置上传失败");
			try {
				configUpload.join();// 等待线程结束
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		File absolutePath = new File(path);
		path = absolutePath.getAbsolutePath();
		path = path.toString();
		File Delfile = new File(path);
		if (Delfile.delete()) {
			System.out.println(path + "删除成功！");
		}
		Delfile.deleteOnExit();
	}
}
