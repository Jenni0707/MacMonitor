package mac.project.main;

import mac.project.ftp.FtpPanel;

/**
 * 启动FTP上传下载控制进程
 * @author root
 */

public class FtpDownAndUp extends Thread {
	FtpPanel ftpPanel;
	String ConfigFilePath;
	ConfigBean configBean;
	boolean flag = true; 
	int count = 0;
	
	public FtpDownAndUp(String name,FtpPanel fp,String cfp, ConfigBean cfb) {
		super(name);
		this.ftpPanel = fp;
		this.ConfigFilePath = cfp;
		this.configBean = cfb;
		new Notifier(this,configBean,count);
	}

	public synchronized void run() {
		while(flag){
			count++;
			System.err.println("FTP DownLoad exe!"+count);
			ftpPanel.downLoad_action();
			new ChangeConfig(ConfigFilePath,configBean);
			System.err.println("FTP Upload exe!"+count);
			ftpPanel.upLoad_action();
			try {
				System.out.println("wait..."+count);
				this.wait();
			} catch (InterruptedException e) {
				System.err.println("fdau wait err!" + e.toString());
			}
		}
	}
}

class Notifier extends Thread{
	private FtpDownAndUp fdau;
	private ConfigBean cfb;
	int count = 0;
	
	Notifier(FtpDownAndUp ftp,ConfigBean cf,int n){
		fdau = ftp;
		cfb = cf;
		count = n;
		start();
	}
	public void run(){
		while(true){
			int ftpUploadPeriod = 17;		//ftp上传时间设置
			try {
				ftpUploadPeriod = Integer.parseInt(cfb.getUploadPeriod());
				System.out.println("UploadTime："+ftpUploadPeriod+" second");
			} catch (NumberFormatException e) {
				System.err.println("ftpUploadPeriod err!" + e.toString());
//				e1.printStackTrace();
			}
			try{
				sleep(ftpUploadPeriod*1000);
//				sleep(10000);
			}catch (InterruptedException e1) {
				System.err.println("fdau sleep err!" + e1.toString());
			}
			synchronized(fdau){
				count++;
				System.out.println("notify..."+count+":"+ftpUploadPeriod+" second");
				fdau.notify();
			}
		}
	}
}