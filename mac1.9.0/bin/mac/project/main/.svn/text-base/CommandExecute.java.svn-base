package mac.project.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author root
 * 执行卸载、启动键盘，截屏和程序的命令
 */
public class CommandExecute implements Runnable {
	String cmdStr = "pwd";
	
	public CommandExecute() {
	}
	
	public CommandExecute(String cmdStr) {
		this.cmdStr = cmdStr;
	}

	public void run() {
		try {
			Process proc_tmp = Runtime.getRuntime().exec("id -u");
 			InputStreamReader isr_tmp = new InputStreamReader(proc_tmp.getInputStream());
			BufferedReader br_tmp = new BufferedReader(isr_tmp);
			String str_tmp = br_tmp.readLine();
			if ((str_tmp!=null)&&(!str_tmp.equals("0"))){
				System.err.println("commandline user!");
			} else{
				System.err.println("commandline root!");
			}
			br_tmp.close();
			
			String[] cmd;
			cmd = new String[3];
			cmd[0] = "/bin/sh";
			cmd[1] = " -c";
//			cmd[2] = cmdStr.replace(" ", "\\ ");
			File file = new File(cmdStr);
			if (!file.exists()) {
				return;
			}
			cmd[2] = file.getAbsolutePath();
			System.out.println("cmd runing:" + cmd[2]);
			Process proc = Runtime.getRuntime().exec(cmd[2]);
			InputStreamReader isr = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String str ;
			while ((str = br.readLine())!=null){
				System.err.println(str);
			}
			if (proc.waitFor()!=0) {
				if (proc.exitValue() == 1) {
					System.err.println("exe error!");
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
