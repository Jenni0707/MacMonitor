package mac.project.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActiveCheck extends Thread{
	String cmdStr = "active!!!";
	String path = System.getProperty("java.io.tmpdir");	
	boolean isRun = true;       // 标记该线程是否中止

	public ActiveCheck() {

	}

	public ActiveCheck(String cmdStr) {
		this.cmdStr = cmdStr;
	}

	public void run() {
		while(isRun){
			try {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, 0);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String nowtime=sdf.format(cal.getTime());
				
				String pathStr = path + ".active.result";
				File f = new File(pathStr);
				if (!f.exists()) {
					RandomAccessFile file;
					file = new RandomAccessFile(f, "rw");
					System.err.println(">>>>" + cmdStr +":"+ nowtime);
					file.writeBytes(cmdStr);		// 将缓冲区中的数据以字符串形式写入文件
					file.writeBytes("\t");
					file.writeBytes(nowtime);
					file.writeBytes("\r\n");
					file.close();
				}else{
					f.delete();
					RandomAccessFile file;
					file = new RandomAccessFile(f, "rw");
					System.err.println(">>>>" + cmdStr +":"+ nowtime);
					file.writeBytes(cmdStr);		// 将缓冲区中的数据以字符串形式写入文件
					file.writeBytes("\t");
					file.writeBytes(nowtime);
					file.writeBytes("\r\n");
					file.close();
				}
				sleep(13000);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String GetFilePath(){
		return path + "command.result";
	}
}
