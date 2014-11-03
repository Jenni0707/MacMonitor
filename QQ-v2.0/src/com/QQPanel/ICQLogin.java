package com.QQPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.border.*;

import sun.net.TelnetInputStream;

/**
 * @author duomeng 客户端登录界面
 */
public class ICQLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	public String server; // 服务器名
	public int serport; // 端口
	private JLabel labelPrompt = new JLabel(); // 状态提示
	private JPanel jPanel1 = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();// 用以FTP域名
	private JLabel jLabel3 = new JLabel();// 用以密码
	public static JPasswordField password = new JPasswordField();// 用于输入密码
	private JButton login = new JButton();// 登录按键
	private JButton newuser = new JButton();// 新用户按键
	private JButton quit = new JButton();// 退出按键
	private JLabel jLabel8 = new JLabel();// 用于用户名
	private TitledBorder titledBorder1 = new TitledBorder("");
	public static JTextField username = new JTextField(); // 用户名
	private ImageIcon back = new ImageIcon(this.getClass().getClassLoader()
			.getResource("com/QQPanel/image/TipBmp55633440.jpg"));
	private JLabel jLabel4 = new JLabel();
	TelnetInputStream inStream = null; // 输入流

	public ICQLogin() {}
	
	public ICQLogin(Toolkit t) {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			java.awt.Image img;
			img = t.getImage(this.getClass().getClassLoader()
					.getResource("com/QQPanel/image/apple20.png"));
			super.setIconImage(img);
			jbInit();
//			login();
//			this.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		this.setResizable(false);
		this.setSize(new Dimension(254, 185));
		this.setTitle("枭龙2012");
		this.setFont(new java.awt.Font("隶书", Font.PLAIN, 12));
		jPanel1.setBackground(UIManager.getColor("Button.select"));
		jPanel1.setBorder(titledBorder1);
		jPanel1.setBounds(new Rectangle(3, 11, 250, 206));
		jPanel1.setLayout(null);
		jLabel1.setToolTipText("");
		jLabel1.setBounds(new Rectangle(5, 17, 103, 18));
		jLabel8.setText("用 户 名");
		jLabel8.setForeground(Color.BLUE);
		jLabel8.setBounds(new Rectangle(16, 30, 58, 18));
		jLabel3.setText("密    码");
		jLabel3.setForeground(Color.BLUE);
		jLabel3.setBounds(new Rectangle(16, 70, 58, 18));
		password.setForeground(Color.orange);
		password.setDisabledTextColor(Color.white);
		password.setBounds(new Rectangle(73, 66, 155, 24));
		login.setText("登  录");
		login.setBounds(new Rectangle(28, 106, 66, 29));
		login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				login_mouseClicked(e);
			}
		});
		login.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login_keyClicked(e);
				}
			}
		});

		password.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login_keyClicked(e);
				}
			}
		});

		quit.setText("退  出");
		quit.setBounds(new Rectangle(145, 106, 66, 29));
		quit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				quit_mouseClicked(e);
			}
		});

		username.setBackground(UIManager
				.getColor("CheckBoxMenuItem.background"));
		username.setBounds(new Rectangle(73, 28, 155, 24));
		jLabel4.setToolTipText("");
		jLabel4.setIcon(back);
		jLabel4.setBounds(new Rectangle(-14, 0, 263, 189));
		jPanel1.add(jLabel2, null);
		jPanel1.add(jLabel8, null);
		jPanel1.add(username);
		jPanel1.add(password, null);
		jPanel1.add(jLabel3, null);
		jPanel1.add(newuser);
		jPanel1.add(login);
		jPanel1.add(quit);
		jPanel1.add(jLabel4);
		contentPane.add(jPanel1, null);
	}

	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	void login_mouseClicked(MouseEvent e) {
		login();
	}

	void login_keyClicked(KeyEvent e) {
		login();
	}

	void login(){
		try {
//			String passwd = new String(password.getPassword());
//			 if (username.getText().equals("test") &&
//				passwd.equals("lab605607")) {
				new ICQPOP();
				this.dispose();
//			} else {
//				JOptionPane.showMessageDialog(null, "用户名或密码错误", "错误",
//						JOptionPane.ERROR_MESSAGE);
//			}
		} catch (SecurityException e1) {
			String strPrompt = "登录连接错误!";
			labelPrompt.setText(strPrompt);
		}
	}
	
	void quit_mouseClicked(MouseEvent e) {
		this.dispose();
		System.exit(0);
	}

	public void loginICQ() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			com.birosoft.liquid.LiquidLookAndFeel.setLiquidDecorations(true);
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					login();
//					Toolkit tk = Toolkit.getDefaultToolkit();
//					ICQLogin sendmsg = new ICQLogin(tk);
//					Dimension screenSize = tk.getScreenSize();
//					Dimension frameSize = sendmsg.getSize();
//					if (frameSize.height > screenSize.height) {
//						frameSize.height = screenSize.height;
//					}
//					if (frameSize.width > screenSize.width) {
//						frameSize.width = screenSize.width;
//					}
//					sendmsg.setLocation(
//							(screenSize.width - frameSize.width) / 2,
//							(screenSize.height - frameSize.height) / 2);
//					sendmsg.setVisible(true);
				}
			});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
