package com.topking.ftp.ui;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.topking.ftp.bean.UserInfoBean;

public class LocalPath extends JPanel {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	static TextField initialLPath = new TextField();
	public JFrame frame;

	public LocalPath(UserInfoBean uib) {
		frame = new JFrame("设置本地路径");
		frame.setLayout(null);
		UserInfoBean configUib = uib;
		JLabel initLocalPath = new JLabel("选择本地目录");
		JButton browseButton1 = new JButton("浏览");
		JButton jbOK = new JButton("确 定");
		JButton jbCancel = new JButton("取 消");

		initLocalPath.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		initialLPath.setFont(new java.awt.Font("Monospaced", 0, 12));
		jbOK.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		jbCancel.setFont(new java.awt.Font("Monospaced", 0, 12));

		initLocalPath.setBounds(new Rectangle(5, 50, 100, 25));
		initialLPath.setBounds(new Rectangle(100, 50, 290, 25));
		browseButton1.setBounds(new Rectangle(390, 50, 60, 25));
		jbOK.setBounds(120, 100, 70, 25);// 横坐标，纵坐标，长，宽
		jbCancel.setBounds(300, 100, 70, 25);

		if (configUib != null) {
			LocalPath.initialLPath.setText(configUib.getLocalPath());
		}

		frame.add(initLocalPath, null);
		frame.add(initialLPath, null);
		frame.add(browseButton1, null);
		frame.add(jbOK);
		frame.add(jbCancel);

		browseButton1.addActionListener(new ActionListener() {// 选择目的目录
					public void actionPerformed(ActionEvent e) {
						JFileChooser dirChooser = new JFileChooser("/");
						dirChooser
								.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int result = dirChooser.showOpenDialog(frame);
						if (result == JFileChooser.APPROVE_OPTION) {
							File dir = dirChooser.getSelectedFile();
							initialLPath.setText(dir.getPath());
						} else if (result == JFileChooser.CANCEL_OPTION) {
							initialLPath.setText(initialLPath.getText());
						}
					}
				});

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});

		jbOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		jbCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		frame.setSize(500, 200);
		frame.setLocation(200, 200);
		frame.setVisible(true);
	}

}
