package com.topking.ftp.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

import com.topking.ftp.bean.DownLoadBean;
import com.topking.ftp.bean.FileBean;
import com.topking.ftp.ui.MainFrame;

/**
 * 文件下载处理类
 * 
 * @author root
 */
public class DownLoadFromRemote implements Runnable {
	private String remotePath;
	private String localRootPath;
	private FileBean fb0;
	private FtpClient ftp;
	private MainFrame mframe;
	private List<DownLoadBean> downList = new ArrayList<DownLoadBean>();

	public DownLoadFromRemote(MainFrame mf, String remotePath,
			String localRootPath, FileBean fb, FtpClient ftpCl) {
		this.mframe = mf;
		this.remotePath = remotePath;
		this.localRootPath = localRootPath;
		this.fb0 = fb;
		this.ftp = ftpCl;
	}

	public void run() {
		if (downLoad(remotePath, localRootPath, fb0)) {
			for (int i = 0; i < downList.size(); i++) {
				DownLoadBean db = downList.get(i);
				// new Thread(new downLoadThread(db,ftp)).start();
				new downLoadThread(mframe, db, ftp).run();
				// mframe.clearInfo(); //如果使用的话，不可以连续下载
			}
		}
		System.out.println("类型=" + fb0.getType());
		if (fb0.getType().equals("文件夹")) {
			if (mframe.deleteDirectory(fb0.getFilePath(), true)) {
				mframe.loadRemoteFile();
			} else {
				JOptionPane.showMessageDialog(null, "删除文件失败 异常信息("
						+ ")", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}// end run
		// 对于文件和文件夹分别加入downlist准备下载

	public boolean downLoad(String remotePath, String localRootPath, FileBean fb) {
		String Rpath = remotePath;
		String Lpath;
		try {
			Lpath = new String(localRootPath.getBytes("UTF-8"), "UTF-8");
			if (Lpath != null && Lpath.length() > 0) {
				System.out.println("Lpath not  null");
			}
			System.out.println("download() " + Rpath + ";" + Lpath + ";"
					+ fb.getFileName());
			if (fb.getType().equals("文件")) {
				DownLoadBean db = new DownLoadBean();
				db.setFb(fb);
				db.setLocalPath(Lpath);
				db.setRemotePath((fb.getFilePath().substring(0, fb
						.getFilePath().length() - fb.getFileName().length()))
						.trim());
				System.out.println(db.getRemotePath());
				downList.add(db);
				// new Thread(new
				// downLoadThread(Lpath,fb.getFileName().trim(),ftp)).start();
				// ****************初始化下载进度条**************//
				int size = (int) fb.getSize();
				int bk = (size / 1024) < 1 ? 1 : (size / 1024);
				String bsize = bk + "K";
				if (bk > 1024) {
					bk = bk / 1024;
					bsize = bk + "M";
				}
				Object[] obj = new Object[] { fb.getFileName(), bsize, "0%",
						"0 (kb/s)", "正在下载" };
				((javax.swing.table.DefaultTableModel) (mframe.tb_progress
						.getModel())).addRow(obj);
				mframe.tb_progress.updateUI();
				db.setRowIndex(mframe.RowCount++);
			} else {
				String dir = Lpath + fb.getFileName().trim();
				String rPath = fb.getFilePath();
				if (!mframe.createLocalDir(dir)) {
					return false;
				}
				String list = mframe.listRemoteFile(Rpath, ftp);// listRemoteFile（下载地址，ftp）
				if (list.equals("")) {
					return false;
				}
				String info[] = list.split("\n");// ？把\n换行赋值给info？标志string的最后一位
				for (int i = 0; i < info.length; i++) {
					if (info[i].contains("\r")) {
						info[i] = info[i].substring(0, info[i].length() - 1);
					}
					info[i] = info[i].replaceAll(" ", "/");
					while (info[i].contains("//")) {
						info[i] = info[i].replaceAll("//", "/");
					}
					String[] s = info[i].split("/");
					String type = s[0];
					String fname = s[s.length - 1];
					if (!".".equals(fname) && !"..".equals(fname)) {
						// FileBean fb = new FileBean();
						String time = s[5] + " " + s[6] + " " + s[7];// 时间=？
						FileBean fb1 = new FileBean();
						fb1.setFileName(fname);// ？修改文件名字
						fb1.setTime(time);// ？修改时间
						fb1.setSize(Long.parseLong(s[4]));
						if (type.contains("drw")) {// 文件夹递归进入下载
							fb1.setType("文件夹");
							fb1.setFilePath(remotePath + fname + "/");// ？修改文件路径
							downLoad(Rpath + fname.trim() + "/", dir + "/", fb1);
							System.out.println("downLoad" + Rpath
									+ fname.trim() + "/," + dir + "/," + fb1);
						} else {// 文件
							fb1.setType("文件");
							fb1.setFilePath(remotePath + fname);// ？修改文件路径
							// new Thread(new
							// downLoadThread(dir,fb.getFileName().trim(),ftp)).start();
							DownLoadBean db = new DownLoadBean();
							db.setFb(fb1);
							db.setLocalPath(dir + "/");
							db.setRemotePath(rPath);
							System.out.println("download() " + rPath
									+ fb1.getFileName() + ";"
									+ db.getLocalPath() + ";"
									+ fb1.getFileName());
							downList.add(db);
							// *************初始化下载进度条*********//
							int size = (int) fb1.getSize();
							int bk = (size / 1024) < 1 ? 1 : (size / 1024);
							String bsize = bk + "K";
							if (bk > 1024) {
								bk = bk / 1024;
								bsize = bk + "M";
							}
							Object[] obj = new Object[] { fb1.getFileName(),
									bsize, "0%", "0 (kb/s)", "正在下载" };
							((javax.swing.table.DefaultTableModel) (mframe.tb_progress
									.getModel())).addRow(obj);
							mframe.tb_progress.updateUI();
							db.setRowIndex(mframe.RowCount++);
						}
					}// end if
				}// end for
			}// end else
		} catch (UnsupportedEncodingException e) {
			System.err.println("错误：下载文件列表初始化错误！！！" + e.getMessage());
		}
		return true;
	}
}

class downLoadThread implements Runnable {
	private String Lpath;
	private String fileName;
	private FtpClient ftp;
	private MainFrame mframe;
	private DownLoadBean db;

	public downLoadThread(MainFrame mf, DownLoadBean db, FtpClient ftp) {
		this.mframe = mf;
		this.Lpath = db.getLocalPath();
		this.fileName = db.getFb().getFileName();
		this.ftp = ftp;
		this.db = db;
	}

	public void run() {
		if (ftp.serverIsOpen()) {
			downLoad();
		}// end if
		else {// 服务器已经关闭
			JOptionPane.showMessageDialog(null, "服务器已经关闭，操作失败", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			Thread.currentThread();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.err.println("错误：线程失败！！！downLoadThread.run" + e.getMessage());
		}
	}

	public void downLoad() {
		StringBuffer buf = new StringBuffer();
		buf.setLength(0);
		FileBean fb = db.getFb();
		System.out.println("--------");
		try {
			File dir = new File(Lpath); // 构造目录
			System.out.println("Lpath=" + Lpath);
			File localFileName;
			if(fileName.endsWith("sec")){		//处理按时间进行截屏的图片名称
				localFileName = new File(dir, fileName+".jpg"); // 通过文件
			}else localFileName = new File(dir, fileName); // 通过文件
			System.out.println("fileName=" + fileName);
			System.out.println("RemotePath=" + db.getRemotePath());
			ftp.cd(db.getRemotePath());
			ftp.binary();
			System.out.println("localfilename=" + localFileName);
			System.out.println("ftp.pwd:" + ftp.pwd());
			System.out.println("下载中……");
			RandomAccessFile file = new RandomAccessFile(localFileName, "rw"); // 构造一个随机访问文件
			TelnetInputStream fget = ftp.get(new String(fileName));// .getBytes("UTF-8"),"UTF-8"
			DataInputStream puts = new DataInputStream(fget);
			// 初始化进度条
			int size = (int) fb.getSize();
			byte[] bytes = new byte[1024];
			long tbt = 0;

			System.out.println("size===" + size);
			int c;
			long s = System.currentTimeMillis();
			long e;
			try {
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				System.err.println("错误：线程错误！！！" + e1.getMessage());
			}
			while ((c = puts.read(bytes)) != -1) {
				file.write(bytes, 0, c);
				e = System.currentTimeMillis() + 10;
				tbt += c;
				System.out
						.print("c=" + c + "|||tbt=" + tbt + "|||size=" + size);
				System.out.println("|||(tbt*100)/size)=" + "    "
						+ ((tbt * 100) / size));
				// 进度条实现
				mframe.tb_progress.getModel().setValueAt(
						size < 1 ? "100%" : ((tbt * 100) / size) + "%",
						db.getRowIndex(), 2);// 设置进度条百分率，
												// ub.getRowIndex(),2对应行和列号
				mframe.tb_progress.getModel().setValueAt(
						e - s < 1 ? "0 (kb/s)" : (1024 * tbt)
								/ ((e - s) * 1000) + " (kb/s)",
						db.getRowIndex(), 3);// 显示已经上传的数据量
				mframe.tb_progress.updateUI();
			}

			mframe.tb_progress.getModel().setValueAt("下载完毕", db.getRowIndex(),
					4);
			mframe.tb_progress.updateUI();

			file.close();
			puts.close();
			mframe.loadLocalFile();
			System.out.println("下载完毕！！！ 开始解密……");

			// ******* START 执行下载后解密 *********//
			String srcFile = localFileName.toString();
			if (srcFile.endsWith("EN") || srcFile.endsWith("log")) {
				mframe.decrypt(srcFile);
				mframe.loadLocalFile();
			}

			// ******* START 执行下载后删除远程目录 *********//
			int result = 0;
			if (result == 0) {
				if (fb.getType().equals("文件夹")) {
					if (mframe.deleteDirectory(fb.getFilePath(), true)) {
						mframe.loadRemoteFile();
						// JOptionPane.showMessageDialog(null, "文件夹已经删除");
					} else {
						JOptionPane.showMessageDialog(null, "删除文件失败 异常信息("
								+ ")", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					if (mframe.deleteFile(fb.getFilePath())) {
						mframe.loadRemoteFile();
						// JOptionPane.showMessageDialog(null, "文件已经删除");
					} else {
						JOptionPane.showMessageDialog(null, "删除文件失败 异常信息("
								+ ")", "错误", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			System.out.println("下载OK!!!");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("错误：文件下载错误！！！" + e1.getMessage());
		}
	}
}
