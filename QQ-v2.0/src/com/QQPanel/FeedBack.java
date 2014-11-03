package com.QQPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

import com.topking.ftp.bean.ConfigBean;
import com.topking.ftp.bean.UserInfoBean;
import com.topking.ftp.util.GetFTP;

public class FeedBack implements Runnable {

	UserInfoBean uib;
	ConfigBean cfb;
	FtpClient myFtp;
	CommandWindow cw;
	ICQPOP icqPop;
	private int ch;
	private String Path;
	private String userName;
	private String srcFile;
	TelnetInputStream inStream = null;
	private StringBuffer buf = new StringBuffer();
//	public static String c[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
//			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	public FeedBack(CommandWindow commandWindow, UserInfoBean userib,ConfigBean cfgb) {
		this.cw = commandWindow;
		this.uib = userib;
		this.cfb = cfgb;
	}

	public void run() {
		Path = "/";
		System.out.println("FeedBack.run");
		userName = uib.getUserID();
		try {
			buf.setLength(0);
			myFtp = new GetFTP(uib.getFtpUrl(), uib.getFtpName(),
					uib.getFtpPasswd()).getFtp();
			if (myFtp == null)
				return;
			String ftppwd = myFtp.pwd();
			System.out.println("pwd:" + ftppwd);
			myFtp.cd("/" + userName + "/");
			if (loadFile(Path + userName + "/", myFtp)) {
				cw.count = 0;
				srcFile = "command.result";
				inStream = myFtp.get("/" + userName + "/" + srcFile); // +得到文件的输入流,No1/Config是配置文件在服务器上所在的位置
				InputStreamReader isr = new InputStreamReader(inStream);
				BufferedReader br = new BufferedReader(isr);
				String s = new String();
				/*while ((ch = inStream.read()) >= 0) { // 读取数据流
					buf.append((char) ch); // 将读取的数据存在缓冲区中
				}*/
				String str ="";
				while ((s = br.readLine()) != null) { // 读文件的每一行
					str +=s+"\r\n";
				}
				br.close();
				isr.close();
				inStream.close();
				String Str;
				if(cfb.getCommandStr()!=null){
					Str = cfb.getCommandStr()+"\r\n"+str;
				}else
					Str = "ls -al /User"+"\r\n"+str;				 
				cw.appendTextInPanel(Str);
				cw.writeCmdStr2File(Str);
				
//				if (myFtp.serverIsOpen()) {
//					String cmd = "DELE " + "/" + userName + "/" + srcFile
//							+ "\r\n";
//					myFtp.sendServer(cmd);
//					myFtp.binary();
//				}
			} else {
				cw.count++;
				System.out.println("cw.count: " + cw.count);
				if(cw.count==4)
				{
					/*JOptionPane.showMessageDialog(null, "命令行执行结果尚未返回", "提示",
						JOptionPane.INFORMATION_MESSAGE);*/
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	// 刷新指定文件夹
	public String listFile(String path, FtpClient ftp) {
		String list = null;
		try {
			ftp.cd(path);
			System.out.println("FeedBack.listFile()");
			TelnetInputStream is = ftp.list();
			int c;
			StringBuffer sb = new StringBuffer();
			while ((c = is.read()) != -1) {
				sb.append((char) c);
			}
			is.close();
			String list2 = new String(sb);
			list = new String(list2.getBytes("iso8859-1"), "UTF-8");
		} catch (IOException e) {
			System.err.println("Error:刷新远程目录失败 异常信息: " + e.getMessage());
			// e.printStackTrace();
		}
		return list;
	}

	// 导入目标文件夹获得文件的创建时间并比较
	public boolean loadFile(String path, FtpClient ftp) {// 比较FTP上文件List与command.result为真则返回ture，假则返回false
		String filename = "cmd";
		Long filesize = 0L;
		String list = listFile(path, ftp);
		if (list == null || list.equals("")) {
			return false;
		}
		
		String[] info = list.split("\n");
		for (int i = 0; i < info.length; i++) {
			if (info[i].length() > 8) {
				info[i] = info[i].replaceAll(" ", "/");
				while (info[i].contains("//")) {
					info[i] = info[i].replaceAll("//", "/");
				}
				String[] s = info[i].split("/");
				filename = s[s.length - 1];
				filesize = Long.parseLong(s[4]);
			}
			if (filename.equals("command.result")&&(filesize-cw.fileSize)!=0L) {
				cw.fileSize = filesize;
				return true;
			}
		}
		System.out.println("loadFile in FeedBack false");
		return false;
	}
}