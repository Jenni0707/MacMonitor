package mac.project.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 检查 pName 进程是否在运行
public class CheckThread extends Thread{
	private String pName;
	private boolean isExist = false;
	boolean isContinue = true;       // 标记该线程是否中止
	
	public CheckThread(String name){
		pName = name;
	}
	
	public void run(){
		String[] cmd = new String[3];
		cmd = new String[3];
		cmd[0] = "/bin/sh";
		cmd[1] = "-c";
		cmd[2] = "ps -e";
		
		try {
	      	while (isContinue){
	      		Process proc = Runtime.getRuntime().exec(cmd);
				InputStreamReader isr = new InputStreamReader(proc.getInputStream());
		      	BufferedReader br = new BufferedReader(isr);
		      	String line = null;
	      		while ((line=br.readLine()) != null){
	      			if (line.contains(pName)){
	      				isExist = true;
	      				System.out.println("cst: " + line + "  isExist: " + isExist);
	      				return;
              		}
		      	}
	      		if (null == line)
	      			isExist = false;

	      		if (!isExist){
	      			isContinue = false;
	      			return;	
	      		} else
		      		sleep(2000);
	      	}
	      	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean processIsExist() {
		return isExist;
	}
}
