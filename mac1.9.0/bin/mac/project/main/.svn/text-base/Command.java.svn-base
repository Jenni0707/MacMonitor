package mac.project.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class Command implements Runnable {
	String cmdStr = "pwd";
	String path = "/";
	
	public Command() {

	}
	
	public Command(String cmdStr, String tempPath) {
		this.cmdStr = cmdStr;
		this.path = tempPath;
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
			isr_tmp.close();
			String[] cmd;
			
			String pathStr = path + "command.result";
			
			cmd = new String[3];
			cmd[0] = "/bin/sh";
			cmd[1] = " -c";
			cmd[2] = cmdStr;// + " > " + pathStr;
			
			System.out.println("cmd runing:" + cmd[2]);
			Process proc = Runtime.getRuntime().exec(cmd[2]);
			InputStreamReader isr = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			
			File f = new File(pathStr);
			if (!f.exists()) {
				RandomAccessFile file = new RandomAccessFile(f, "rw");
				String str ;
				while ((str = br.readLine())!=null){
					System.err.println(">>" + str);
					file.writeBytes(str);		// 将缓冲区中的数据以字符串形式写入文件
					file.writeBytes("\r\n");
				}
				file.close();
			}

			if (proc.waitFor()!=0) {
				if (proc.exitValue() == 1) {
					System.err.println("exe error!");
				}
			}
			br.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String GetFilePath(){
		return path + "command.result";
	}
}
