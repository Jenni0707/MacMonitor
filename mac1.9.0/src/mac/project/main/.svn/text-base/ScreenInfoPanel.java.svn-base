package mac.project.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 屏幕信息获取
 * 保存为图片
 * @author root
 */
public class ScreenInfoPanel implements ActionListener, ItemListener {
	private JFrame f;
	private JPanel p;
	private JButton browseButton;
	protected JComboBox imgType;
	protected JComboBox interval;
	protected JTextField tf;

	JTextArea ta;
	JProgressBar progressBar;
	JLabel progressLabel;
	static JButton startButton;
	String ConfigFilePath;
	JButton stopButton;

	protected Set<String> des;
	protected String desDir;
	protected String[] imageType = {"jpg","png","bmp","gif"};
	protected String iT;						//标志图片类型
	protected String[] gapTime = {"1","2","3","5","10"};
	protected String gT;						//标志截取时间间隔
	protected int spaceTime; //截图间隔时间=(int)gT
	private ConfigBean configBean = new ConfigBean();
	ScreenSnap ssp;
	AutoRun autoRun;
//	MainUI mainUI;
	
	public ScreenInfoPanel() {}
	
	public ScreenInfoPanel(AutoRun ar) {
		this.autoRun = ar;
		this.configBean = autoRun.configBean;
		this.ConfigFilePath = autoRun.ConfigFilePath;
		
		imgType = new JComboBox(imageType);//图片格式选择框
		imgType.setEditable(true);
		imgType.addItemListener(this);
		iT = imageType[0];
		interval = new JComboBox(gapTime);//间隔时间选择框
		interval.setEditable(true);
		interval.addItemListener(this);
		gT = gapTime[0];
		
		p = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		p.setLayout(gridbag);
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(5, 1));
		p1.setBorder(BorderFactory.createTitledBorder("自定义屏幕截取："));
		
		Box baseBox1 = Box.createHorizontalBox();
		baseBox1.add(Box.createRigidArea(new Dimension(15, 10)));
		JLabel label1 = new JLabel("请选择存放目录：");
		baseBox1.add(label1);
		baseBox1.add(Box.createRigidArea(new Dimension(15, 10)));
		tf = new JTextField("", 20);
		baseBox1.add(tf);
		baseBox1.add(Box.createRigidArea(new Dimension(15, 10)));
		browseButton = new JButton("浏览");
		browseButton.addActionListener(new ActionListener() {//选择目的目录
			public void actionPerformed(ActionEvent e) {
				JFileChooser dirChooser = new JFileChooser("/");
				dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = dirChooser.showOpenDialog(p);
				if (result == JFileChooser.APPROVE_OPTION) {
					File dir = dirChooser.getSelectedFile();
					tf.setText(dir.getPath());
				} else if (
					result == JFileChooser.CANCEL_OPTION) {
					tf.setText(tf.getText());
				}
			}
		});
		baseBox1.add(browseButton);
		baseBox1.add(Box.createRigidArea(new Dimension(15, 10)));
		p1.add(baseBox1);

		Box baseBox2 = Box.createHorizontalBox();
		baseBox2.add(Box.createRigidArea(new Dimension(15, 10)));
		baseBox2.add(new JLabel(""));
		p1.add(baseBox2);

		/**
		 * 选择配置文件的组件
		 */
		Box baseBox4 = Box.createHorizontalBox();
		baseBox4.add(Box.createRigidArea(new Dimension(15, 10)));
		baseBox4.add(new JLabel(""));
		p1.add(baseBox4);

		Box baseBox5 = Box.createHorizontalBox();
		baseBox5.add(Box.createRigidArea(new Dimension(15, 10)));
		JLabel label2 = new JLabel("请设置图片存储格式：");
		baseBox5.add(label2);
		baseBox5.add(imgType);
		baseBox5.add(Box.createRigidArea(new Dimension(50,10)));
		JLabel label3 = new JLabel("请设置间隔时间：");
		baseBox5.add(label3);
		baseBox5.add(interval);
		JLabel timelabel = new JLabel("分钟");
		baseBox5.add(timelabel);
		baseBox5.add(Box.createRigidArea(new Dimension(50,10)));
		p1.add(baseBox5);

		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setBorder(BorderFactory.createTitledBorder("处理信息显示："));
		ta = new JTextArea("	********Process Messages********	", 5, 40);
		ta.setEditable(false);
		JScrollPane sp = new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		p2.add(sp, BorderLayout.CENTER);

		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.setBorder(BorderFactory.createTitledBorder("所占内存显示"));
		Box vBox1 = Box.createVerticalBox();
		p3.add(vBox1, BorderLayout.NORTH);
		
		Box baseBox6 = Box.createHorizontalBox();
		baseBox6.add(Box.createRigidArea(new Dimension(30, 20)));
		progressBar = new JProgressBar(); // 实例化进度条，进度值条长度为文件长度
		progressBar.setStringPainted(true); // 描绘文字
		progressBar.setBackground(Color.white); // 设置背景色
		baseBox6.add(progressBar);
		baseBox6.add(Box.createRigidArea(new Dimension(30, 20)));
		p3.add(baseBox6, BorderLayout.CENTER);
		progressLabel = new JLabel("(共截取：     张图片，共占内存      MB)", JLabel.CENTER);
		p3.add(progressLabel, BorderLayout.SOUTH);

		JPanel p4 = new JPanel();
		startButton = new JButton("开始");
		startButton.addActionListener(this);
		stopButton = new JButton("停止");
		stopButton.setEnabled(false);
	    stopButton.addActionListener(this);
		p4.add(startButton);
		p4.add(stopButton);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(p1, gbc);
		p.add(p1);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 10, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(p2, gbc);
		p.add(p2);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 10, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(p3, gbc);
		p.add(p3);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 10, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(p4, gbc);
		p.add(p4);

		p.setVisible(true);
	}
		
	public JPanel getPanel(){          //返回该面板
		return p;
	}
	
	public int StringToInt(String gT){
		int spaceTime;
		if(gT.equals(gapTime[0])) spaceTime = Integer.parseInt(gT);//spaceTime = 2*1000;
		else if(gT.equals(gapTime[1])) spaceTime = Integer.parseInt(gT);
		else if(gT.equals(gapTime[2])) spaceTime = Integer.parseInt(gT);
		else if(gT.equals(gapTime[3])) spaceTime = Integer.parseInt(gT);
		else if(gT.equals(gapTime[4])) spaceTime = Integer.parseInt(gT);
		else gT = (String)interval.getSelectedItem();
		spaceTime = Integer.parseInt(gT);  //默认截图间隔时间是15分钟
		return spaceTime*60*1000;
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			if(e.getSource() == imgType){
				iT = e.getItem().toString();
				System.out.println("Add: iT =" + iT);
			}
			if(e.getSource() == interval){
				gT = e.getItem().toString();
				System.out.println("Add: gT =" + gT);
				spaceTime = Integer.parseInt(gT)*60*1000;
//				spaceTime = StringToInt(gT);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		
		if (e.getSource() == startButton) {
			ta.setText("                                          ********Process Messages********");
			progressLabel.setText("已截取：     张，共      MB");
			progressBar.setValue(0);
			progressBar.setString("");
			if(desDir != ""){				//每次拷贝前都将 desDir 中的内容清零，即初始化
				desDir = "";
			}

			if(!tf.getText().equals("")){                //判断面板目录拷贝目录参数
				desDir = tf.getText();
				File desFile = new File(desDir);
				if(!desFile.exists()){
					String message = "目的文件夹: " + desFile + "  不存在!";
					JOptionPane.showMessageDialog(null, message, "参数错误!", JOptionPane.ERROR_MESSAGE);
					return ;
				}
			}
			
			if((desDir == "")){
				String filePath;
				filePath = configBean.getScreenDes();
				System.out.println("Screen des: "+filePath);
				if(filePath==null){
					String message = "配置文件路径获取为空！";
					JOptionPane.showMessageDialog(null, message, "参数错误!", JOptionPane.ERROR_MESSAGE);
					desDir = "/Volumes/resource/";
					System.out.println("des_Dir(null): "+ desDir);
				}else{
					if(!(new File(filePath).exists())){
						String message = "配置文件中目的目录:  " + filePath + "   不存在!";
						JOptionPane.showMessageDialog(null, message, "参数错误!" ,JOptionPane.ERROR_MESSAGE);
						desDir = "/Volumes/resource/";
					}
					else{
						desDir = configBean.getScreenDes();
					}
				}
				
			}
			
			if(spaceTime == 0){
				gT = (String)interval.getSelectedItem();
				spaceTime = Integer.parseInt(gT)*60*1000;
			}
			
			if((desDir != "")){        // Case 1：参数正确，开始拷贝
				KillProcess.sip_killed = false;
				System.out.println("des_Dir: "+ desDir);
				
				ssp = new ScreenSnap("Screen", desDir, iT, spaceTime, this);  
				ssp.start();
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
			else{
				String message = "请选择屏幕截取结果保存的目的目录!";
				JOptionPane.showMessageDialog(null, message, "参数错误!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// 用户按"取消"后的操作
		if(e.getSource() == stopButton){
			int result;
			result = JOptionPane.showConfirmDialog(f, "操作正在进行中，您确实需要退出吗?", "确认信息", 
					JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);	// 弹出确认框
			if(0 == result){		//如果点击取消
				ssp.cansel();
				ta.append("\n"+"任务已取消!");
				progressBar.setIndeterminate(false);
				progressLabel.setText("Stopped!");
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
			}
			tf.setText("");
		}
	}
}

class SnapShot extends TimerTask{
	private String fileName; 							// 文件的前缀
	private String defaultName = "root";
	private String filePath; 							// 保存路径
	private String defaultPath = ".";
	private String imageFormat; 						// 图像文件的格式
	private String defaultImageFormat = "jpg";
//	private String nameFormat = "yyMMddhhmmss"; 		// 文件命名格式
	private String nameFormat = "MMddhhmmss";
	static File f; // 用来存放 find 的结果
	ScreenInfoPanel sip;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * 默认的文件前缀为root，文件格式为jpg格式
	 */
	public SnapShot()
	{
		fileName = defaultName;
		filePath = defaultPath;
		imageFormat = defaultImageFormat;
	}
	/**
	 * @param s 文件名前缀
	 * @param p 文件存储路径
	 * @param format 文件存储格式
	 */
	public SnapShot(String s, String p, String format, ScreenInfoPanel sip)
	{
		fileName = s;
		filePath = p;
		imageFormat = format;
		this.sip = sip;
	}
	/*************************
	 * 对屏幕进行拍照
	 ************************/
	public void run()
	{
		try
		{
			// *****拷贝屏幕到一个BufferedImage对象screenshot*****//
			BufferedImage screenshot = (new Robot()).createScreenCapture(
					new Rectangle(0, 0,	(int) d.getWidth(), (int) d.getHeight()));
			//***********用当前时间来命名*********//
			Date time = new Date();         // 时间格式
			SimpleDateFormat format = new SimpleDateFormat(nameFormat);         	// 输出文件
			String name = fileName + format.format(time) + "."	+ imageFormat;		// 根据文件前缀变量和文件格式变量，自动生成文件名
			File f = new File(filePath,name);
			System.out.print("Save File " + name);
			ImageIO.write(screenshot, imageFormat, f);								// 将screenshot对象写入图像文件
			System.out.println("..Finished! ");
			sip.ta.append("\n" + "Save File " + name + "..Finished! ");
		} catch (Exception e){
			System.out.println("保存错误！");
		}
	}
	
}

class ScreenSnap extends Thread {
	private String fileName; 							// 文件的前缀
	private String filePath; 							// 保存路径
	private String imageFormat; 						// 图像文件的格式
	private int spaceTime;								//截图间隔时间
	ScreenInfoPanel sip;
	Timer timer = new Timer();
	public ScreenSnap(String s, String p, String format, int time, ScreenInfoPanel sip)
	{
		fileName = s;
		filePath = p;
		imageFormat = format;
		spaceTime = time;
		this.sip = sip;
	}
	public void run() {
		System.out.println("spaceTime: "+spaceTime);
		timer.schedule(new SnapShot(fileName, filePath, imageFormat, sip), 1000, spaceTime); //每spaceTime秒截图一次
//		new SnapShot(fileName, filePath, imageFormat).run();
	}
	public void cansel() {
		timer.cancel();
	}
}
