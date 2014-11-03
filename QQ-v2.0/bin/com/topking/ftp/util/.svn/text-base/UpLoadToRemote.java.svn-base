package com.topking.ftp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

import com.topking.ftp.bean.FileBean;
import com.topking.ftp.bean.UpLoadBean;
import com.topking.ftp.ui.MainFrame;

/**
 * 文件上传处理类
 * 
 * @author root
 */
public class UpLoadToRemote implements Runnable {
	private String Rpath;
	private String fileName;
	private FtpClient ftp;
	MainFrame mframe;
	private List<UpLoadBean> upList = new ArrayList<UpLoadBean>();

	// 初始化上传列表
	public UpLoadToRemote(MainFrame mf, String Rpath, String fileName,
			FtpClient ftp) {
		this.mframe = mf;
		this.Rpath = Rpath;
		this.fileName = fileName;
		this.ftp = ftp;
	}

	public void run() {
		if (upLoad(Rpath, fileName, ftp)) {
			for (int i = 0; i < upList.size(); i++) {
				UpLoadBean ub = upList.get(i);
				// new Thread(new upLoadThread(ub,ftp)).start();
				new upLoadThread(mframe, ub, ftp).run();
				// mframe.clearInfo(); //如果使用的话，不可以连续上传
			}
		}
	}

	public boolean upLoad(String Rpath, String fileName, FtpClient ftp) {
		System.out.println("Rpath:" + Rpath);
		System.out.println("fileName:" + fileName);
		String localRootPath = PathUtil.rePlace(fileName);
		System.out.println("localRootPath:" + localRootPath);
		String remoteRootPath = Rpath;
		File rootFile = new File(fileName);
		if (rootFile.isDirectory()) {
			if (!mframe.createRemoteDir(remoteRootPath,
					PathUtil.getFolderName(fileName), ftp)) {
				System.out.println("创建远程根文件夹" + remoteRootPath + fileName
						+ "/失败");
				return false;
			}
			remoteRootPath = remoteRootPath + PathUtil.getFolderName(fileName)
					+ "/";
			String[] fs = rootFile.list();
			for (int i = 0; i < fs.length; i++) {
				File subFile = new File(localRootPath + "/" + fs[i]);
				if (subFile.isDirectory()) {
					upLoad(remoteRootPath, localRootPath + "/" + fs[i], ftp);// 递归
				}// end if
				else {
					// 文件上传
					FileBean fb = new FileBean();
					fb.setFileName(subFile.getName());
					fb.setFilePath(subFile.getAbsolutePath());
					fb.setSize(subFile.length());
					fb.setTime(new Long(subFile.lastModified()).toString());
					fb.setType("文件");
					// new Thread(new
					// upLoadThread(fb,remoteRootPath)).start();
					UpLoadBean ub = new UpLoadBean();
					ub.setFb(fb);
					ub.setRemotePath(remoteRootPath);
					upList.add(ub);
					// new Thread(new upLoadThread(ub,ftp)).start();
					// 初始化上传进度条
					int size = (int) fb.getSize();
					int bk = (size / 1024) < 1 ? 1 : (size / 1024);
					String bsize = bk + "K";
					if (bk > 1024) {
						bk = bk / 1024;
						bsize = bk + "M";
					}
					Object[] obj = new Object[] { fb.getFileName(), bsize,
							"0%", "0 (kb/s)", "正在上传" };
					((javax.swing.table.DefaultTableModel) (mframe.tb_progress
							.getModel())).addRow(obj);
					mframe.tb_progress.updateUI();
					ub.setRowIndex(mframe.RowCount++);
				}
			}// end for
		} else {
			// 文件上传
			FileBean fb = new FileBean();
			fb.setFileName(rootFile.getName());
			fb.setFilePath(rootFile.getAbsolutePath());
			fb.setSize(rootFile.length());
			fb.setTime(new Long(rootFile.lastModified()).toString());
			fb.setType("文件");
			UpLoadBean ub = new UpLoadBean();
			ub.setFb(fb);
			ub.setRemotePath(remoteRootPath);
			upList.add(ub);
			// new Thread(new upLoadThread(ub,ftp)).start();
			int size = (int) fb.getSize();
			int bk = (size / 1024) < 1 ? 1 : (size / 1024);
			String bsize = bk + "K";
			if (bk > 1024) {
				bk = bk / 1024;
				bsize = bk + "M";
			}
			Object[] obj = new Object[] { fb.getFileName(), bsize, "0%",
					"0 (kb/s)", "正在上传" };
			((javax.swing.table.DefaultTableModel) (mframe.tb_progress
					.getModel())).addRow(obj);
			mframe.tb_progress.updateUI();
			ub.setRowIndex(mframe.RowCount++);
		}
		System.err.println(mframe.RowCount);
		return true;
	}
}

class upLoadThread implements Runnable {
	private UpLoadBean ub;
	MainFrame mframe;
	private FtpClient ftpClient;

	public upLoadThread(MainFrame mf, UpLoadBean ub, FtpClient ftp) {
		mframe = mf;
		this.ub = ub;
		this.ftpClient = new FtpClient();
		ftpClient = ftp;
	}

	public void run() {
		// if
		if (ftpClient.serverIsOpen()) {
			if (ub.getFb().getType().equals("文件")) {
				upLoadFile(ub.getFb(), ub.getRemotePath());
			}
		} else {// 服务器已经关闭
			JOptionPane.showMessageDialog(null, "服务器已经关闭，操作失败", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}// end run

	// 上传文件
	public void upLoadFile(FileBean fb, String path) {// path为服务器的地址，也就相当于上传时候的目的地址
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			ftpClient.cd(path);
			ftpClient.binary();
			String tmpUpFile = new String(fb.getFileName().getBytes("UTF-8"),
					"UTF-8");
			os = ftpClient.put(tmpUpFile);
			System.out.println("upLoadFile-path===" + path);
			is = new FileInputStream(new File(fb.getFilePath()));
			System.out.println("upLoadFile-fb.getFilePath()==="
					+ fb.getFilePath());
			byte[] bytes = new byte[1024];
			long tbt = 0;
			long size = fb.getSize();
			int c;
			long s = System.currentTimeMillis();
			long e;
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				e = System.currentTimeMillis() + 10;
				tbt += c;
				System.out.println("c=" + "    " + c);
				System.out.println("tbt=" + "    " + tbt);
				System.out.println("size=" + "    " + size);
				System.out.println("(tbt*100)/size)=" + "    "
						+ ((tbt * 100) / size));
				// 进度条实现
				mframe.tb_progress.getModel().setValueAt(
						size < 1 ? "100%" : ((tbt * 100) / size) + "%",
						ub.getRowIndex(), 2);// 设置进度条百分率， ub.getRowIndex(),
												// 2对应行和列号
				mframe.tb_progress.getModel().setValueAt(
						e - s < 1 ? "0 (kb/s)" : (1024 * tbt)
								/ ((e - s) * 1000) + " (kb/s)",
						ub.getRowIndex(), 3);// 显示已经上传的数据量
				mframe.tb_progress.updateUI();
			}

			mframe.tb_progress.getModel().setValueAt("上传完毕", ub.getRowIndex(),
					4);
			mframe.tb_progress.updateUI();

			os.close();
			is.close();
			mframe.loadRemoteFile();

		} catch (IOException e1) {
			mframe.tb_progress.getModel().setValueAt("0%", ub.getRowIndex(), 2);
			mframe.tb_progress.getModel().setValueAt("0 (kb/s)",
					ub.getRowIndex(), 3);
			mframe.tb_progress.getModel().setValueAt(
					"上传失败(" + e1.getMessage() + ")", ub.getRowIndex(), 4);
			mframe.tb_progress.updateUI();
			System.err.println("错误：文件上传错误！！！" + e1.getMessage());
		} finally {
			try {
				if (os != null && is != null) {
					os.close();
					is.close();
				}
			} catch (IOException e) {
				System.err.println("错误：文件上传错误！！！" + e.getMessage());
			}
		}
	}
}
