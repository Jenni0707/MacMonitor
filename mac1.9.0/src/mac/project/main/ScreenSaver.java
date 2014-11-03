package mac.project.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 启动后台截屏进程
 * @author root
 *
 */

public class ScreenSaver implements Runnable {
	String localPath;
	String cmdstr = "";
	String path;
	
	public ScreenSaver() {

	}
	
	public ScreenSaver(String lpath,String cmdstr) {
		this.localPath = lpath;
		this.cmdstr = cmdstr;
	}

	public void run() {

		try {
			System.out.println("run ScreenSaver");
			Process proc_tmp = Runtime.getRuntime().exec("id -u");
			InputStreamReader isr_tmp = new InputStreamReader(proc_tmp.getInputStream());
			BufferedReader br_tmp = new BufferedReader(isr_tmp);
			String str_tmp = br_tmp.readLine();
			if ((str_tmp!=null)&&(!str_tmp.equals("0"))){
				System.err.println("exec");
			} else{
				System.err.println("err");
			}
			br_tmp.close();
			
			String[] cmd;
			path = localPath + "ScreenSaver" + "  " + cmdstr;
			
			cmd = new String[3];
			cmd[0] = "/bin/sh";
			cmd[1] = "-c";
			cmd[2] = path;
//			cmd[2] = "nohup " + path + " > /dev/null &";		//nohup ./ScreenSaver > /dev/null &
			System.out.println("cmd[2]:" + cmd[2]);
			Runtime.getRuntime().exec(cmd);
			System.out.println("runing ScreenSaver");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String GetFilePath(){
		return cmdstr;
	}
}