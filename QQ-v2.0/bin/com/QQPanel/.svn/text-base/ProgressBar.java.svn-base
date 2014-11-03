package com.QQPanel;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBar{
	private JDialog dialog;
	private JProgressBar progressBar;
	private JLabel lbStatus;
	private JButton btnCancel;
	private Window parent;
	private Thread thread; // 处理业务的线程
	private String statusInfo;
	private String resultInfo;
	private String cancelInfo;

	public ProgressBar(Window parent, Thread thread, String statusInfo,
			String resultInfo, String cancelInfo) {
		this.parent = parent;
		this.thread = thread;
		this.statusInfo = statusInfo;
		this.resultInfo = resultInfo;
		this.cancelInfo = cancelInfo;
		initUI();
		startThread();
		dialog.setVisible(true);
	}

	private void initUI() {
		if (parent instanceof Dialog) {
			dialog = new JDialog((Dialog) parent, true);
		} else if (parent instanceof Frame) {
			dialog = new JDialog((Frame) parent, true);
		} else {
			dialog = new JDialog((Frame) null, true);
		}
		final JPanel mainPane = new JPanel(null);
		progressBar = new JProgressBar();
		lbStatus = new JLabel("" + statusInfo);
		btnCancel = new JButton("Cancel");
		progressBar.setIndeterminate(true);
		mainPane.add(progressBar);
		mainPane.add(lbStatus);
		dialog.getContentPane().add(mainPane);
		dialog.setUndecorated(true);// 除去title
		dialog.setResizable(true);
		dialog.setSize(390, 100);
		dialog.setLocationRelativeTo(parent); // 设置此窗口相对于指定组件的位置
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // 不允许关闭

		mainPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				layout(mainPane.getWidth(), mainPane.getHeight());
			}
		});
	}

	private void startThread() {
		new commonthread(this.thread).start();
	}

	private void layout(int width, int height) {
		progressBar.setBounds(20, 20, 350, 15);
		lbStatus.setBounds(20, 50, 350, 25);
		btnCancel.setBounds(width - 85, height - 31, 75, 21);
	}

	class commonthread extends Thread {
		private Thread nowThread;
		
		public commonthread(Thread superthread){
			this.nowThread = superthread;
		}
		
		public void run() {
			try {
				checkthread ck = new checkthread(nowThread); // 处理耗时任务
				// 等待事务处理线程结束
				ck.start();
				ck.join();
				dialog.dispose();
				System.out.println(resultInfo);
			} catch (InterruptedException e) {
				if (cancelInfo != null && !cancelInfo.trim().equals("")) {
					String title = "消息";
					JOptionPane.showMessageDialog(parent, cancelInfo,
							title, JOptionPane.INFORMATION_MESSAGE);
				}
				e.printStackTrace();
			} finally {
				// 关闭进度提示框
				dialog.dispose();
			}
		}
	};
}

class checkthread extends Thread {
	private Thread suthread;
	
	public checkthread(Thread superthread){
		this.suthread = superthread;
	}
	
	public void run() {
		if(this.suthread==null){
			int index = 0;
			while (index<5) {
				try {
					sleep(1000);
					++index;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("滚动条监听线程："+getName());
			while (suthread.isAlive()) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
};
