package com.QQPanel;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.config.UploadConfig;
import com.topking.ftp.bean.ConfigBean;
import com.topking.ftp.bean.UserInfoBean;

@SuppressWarnings("serial")
public class CommandWindow extends JFrame {
	private String userName; // 登陆成功的用户名
	private JTextArea jta_recive; // 显示接受到消息的组件
	private JTextField jtf_send; // 发送输出框
	private File myfile;
	private ICQPOP icqPOP;
	private UserInfoBean uib;
	private ConfigBean cfb;
	public Long fileSize = 0L;
	public int count = 0;
	Timer checkFeedbackTimer;

	public CommandWindow(ICQPOP icqpop, UserInfoBean userib, ConfigBean cfbean) {
		this.icqPOP = icqpop;
		this.uib = userib; // 得到用户名
		this.cfb = cfbean; // 设置所执行的命令
		this.setName("Macbook命令行窗口");
		this.setVisible(true);
		userName = uib.getUserName();
		init();
	}

	public void init() {
		this.setSize(670, 445);
		this.setLocationRelativeTo(this);
		Toolkit kit = Toolkit.getDefaultToolkit();
		this.setLocation(kit.getScreenSize().width / 25 * 8,
				kit.getScreenSize().height / 25 * 7);
		this.setLayout(null);
		this.setTitle("命令执行窗口");

		jtf_send = new JTextField();
		jta_recive = new JTextArea();// 显示接受到消息的组件
		jta_recive.setLineWrap(true);
		jta_recive.setEditable(false);
		JButton bu_send = new JButton("发    送");
		JButton bu_history = new JButton("聊天记录");

		JScrollPane sp = new JScrollPane(jta_recive);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setBounds(10, 10, 640, 300); // 设置 JScrollPane 宽100,高200
		JLabel jb = new JLabel("输入命令");

		jb.setBounds(15, 325, 100, 25);
		jtf_send.setBounds(150, 325, 450, 25);
		jta_recive.setBounds(new Rectangle(10, 10, 640, 300));
		bu_send.setBounds(new Rectangle(10, 370, 90, 29));
		bu_history.setBounds(new Rectangle(150, 370, 90, 29));

		ActionListener sendListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProgressBar(null, null, "正在发送命令,请稍候……", "命令发送成功", "命令发送失败！");
				/*
				 * Calendar date = Calendar.getInstance(); int day =
				 * date.get(Calendar.DAY_OF_MONTH); int month =
				 * date.get(Calendar.MONTH) + 1; int year =
				 * date.get(Calendar.YEAR); int hours =
				 * date.get(Calendar.HOUR_OF_DAY); int minutes =
				 * date.get(Calendar.MINUTE);
				 */
				SimpleDateFormat sm = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				sm.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
				String content = "\r\n" + userName + ">>>> "
						+ jtf_send.getText() + "	" + sm.format(new Date())
						+ ";" + "\r\n";
				writeCmdStr2File(content);
				String commandStr = jtf_send.getText();
				if ((commandStr != null) && (!commandStr.equals(""))) {
					cfb.setCommandStr(commandStr);
				}
				icqPOP.cfgmap.put(uib.getUserID(), cfb.toString());
				new UploadConfig(uib, cfb);
				jta_recive.append(content);
				jtf_send.setText("");
			}
		};
		bu_send.addActionListener(sendListener);
		jtf_send.addActionListener(sendListener);
		bu_history.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(new File(userName + "command.txt")).exists()) {
					/*
					 * Calendar date = Calendar.getInstance(); int day =
					 * date.get(Calendar.DAY_OF_MONTH); int month =
					 * date.get(Calendar.MONTH) + 1; int year =
					 * date.get(Calendar.YEAR); int hours =
					 * date.get(Calendar.HOUR_OF_DAY); int minutes =
					 * date.get(Calendar.MINUTE);
					 */
					SimpleDateFormat sm = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String content = "聊天记录" + sm.format(new Date()) + ";"
							+ "\r\n";
					writeCmdStr2File(content);
				}
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					File file = new File(userName + "command.txt");
					try {
						desktop.open(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		Container c = this.getContentPane();
		c.add(jb);
		c.add(sp);
		c.add(jtf_send);
		c.add(bu_send);
		c.add(bu_history);
		this.setVisible(true);
		checkFeedbackTimer = new Timer();
		checkFeedbackTimer.schedule(new FeedBackTask(this, uib, cfb), 3 * 1000,
				30 * 1000);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				this_windowClosed(e);
			}
		});
	}

	// 将单选框封装成为一个方法
	public JButton createJbutton(String name, int x, int y, int width,
			int height) {
		JButton jb = new JButton(name);
		jb.setBounds(x, y, width, height);
		return jb;
	}

	public void this_windowClosed(WindowEvent e) {
		System.out.println("退出命令执行！");
		checkFeedbackTimer.cancel();
		this.setVisible(false);
	}

	public void appendTextInPanel(String buf) {
		String str = buf.toString();
		jta_recive.append(str);
		jta_recive.setCaretPosition(jta_recive.getDocument().getLength()
				- str.length());
	}

	public void writeCmdStr2File(String buf) {
		String str = buf.toString();

		try {
			myfile = new File(".\\" + userName + "command.txt");
			FileOutputStream fout = new FileOutputStream(myfile, true);
			OutputStreamWriter output = new OutputStreamWriter(fout, "UTF-8");
			BufferedWriter bw = new BufferedWriter(output);
			bw.write(str + "\r\n");
			bw.flush();
			bw.close();
			output.close();
			fout.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class FeedBackTask extends TimerTask {
	UserInfoBean uib;
	CommandWindow cw;
	ConfigBean cfb;

	public FeedBackTask(CommandWindow commandWindow, UserInfoBean userib,
			ConfigBean cfgb) {
		this.cw = commandWindow;
		this.uib = userib;
		this.cfb = cfgb;
	}

	public void run() {
		System.out.println("action:检查反馈");
		/*
		 * FeedBack fudb= new FeedBack(cw, uib); Thread FUDB = new Thread(fudb);
		 * FUDB.start();
		 */
		new FeedBack(cw, uib, cfb).run();
	}
}