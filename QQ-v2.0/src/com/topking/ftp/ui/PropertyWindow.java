package com.topking.ftp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import com.topking.ftp.bean.FileBean;

/**
 * PropertyWindow
 * 
 * @author root
 */
public class PropertyWindow extends javax.swing.JFrame {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JLabel L_date;
	private javax.swing.JLabel L_name;
	private javax.swing.JLabel L_size;
	private javax.swing.JLabel L_type;
	private javax.swing.JTextField T_date;
	private javax.swing.JTextField T_name;
	private javax.swing.JTextField T_size;
	private javax.swing.JTextField T_type;
	private javax.swing.JButton bt_ok;

	/** Creates new form PropertyWindow */
	public PropertyWindow(FileBean fb, int x, int y) {
		this.setIconImage(this.getToolkit().createImage(
				this.getClass().getClassLoader()
						.getResource("com/topking/ftp/ui/images/property.gif")));
		this.setTitle("文件属性");
		this.setResizable(false);
		this.setLocation(x, y);
		initComponents(fb);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents(FileBean fb) {

		L_name = new javax.swing.JLabel("文件名", new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("com/topking/ftp/ui/images/name.gif")),
				SwingConstants.LEFT);
		L_type = new javax.swing.JLabel("类型", new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("com/topking/ftp/ui/images/type.gif")),
				SwingConstants.LEFT);
		L_size = new javax.swing.JLabel("大小", new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("com/topking/ftp/ui/images/size.gif")),
				SwingConstants.LEFT);
		L_date = new javax.swing.JLabel("创建时间", new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("com/topking/ftp/ui/images/date.gif")),
				SwingConstants.LEFT);
		bt_ok = new javax.swing.JButton();
		T_name = new javax.swing.JTextField(fb.getFileName());
		T_type = new javax.swing.JTextField(fb.getType());
		// 设置显示的文件大小
		if (fb.getSize() < 1024) {
			T_size = new javax.swing.JTextField(
					new Long(fb.getSize()).toString() + "B");
		}
		if (fb.getSize() > 1024 && fb.getSize() < 1024 * 1024) {
			long size = fb.getSize();
			double size2 = size / 1024;
			T_size = new javax.swing.JTextField(size2 + "KB");
		}
		if (fb.getSize() > 1024 * 1024) {
			long size = fb.getSize();
			double size2 = size / (1024 * 1024);
			T_size = new javax.swing.JTextField(size2 + "M");
		}
		T_date = new javax.swing.JTextField(fb.getTime());
		T_name.setEditable(false);
		T_type.setEditable(false);
		T_size.setEditable(false);
		T_date.setEditable(false);

		// setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		bt_ok.setText("确定");
		bt_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destoryWindow();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																								.addComponent(
																										L_size,
																										javax.swing.GroupLayout.Alignment.LEADING,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										L_type,
																										javax.swing.GroupLayout.Alignment.LEADING,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										L_name,
																										javax.swing.GroupLayout.Alignment.LEADING,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										74,
																										javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addComponent(
																						L_date,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						74,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						T_type,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						190,
																						Short.MAX_VALUE)
																				.addComponent(
																						T_name,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						190,
																						Short.MAX_VALUE)
																				.addComponent(
																						T_size,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						190,
																						Short.MAX_VALUE)
																				.addComponent(
																						T_date,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						190,
																						Short.MAX_VALUE))
																.addContainerGap())
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		bt_ok)
																.addGap(109,
																		109,
																		109)))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(L_name)
												.addComponent(
														T_name,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(L_type)
												.addComponent(
														T_type,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(L_size)
												.addComponent(
														T_size,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(L_date)
												.addComponent(
														T_date,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addComponent(bt_ok)));

		pack();
	}// </editor-fold>

	public void destoryWindow() {
		this.dispose();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	// public static void main(String args[]) {
	// java.awt.EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// new PropertyWindow(new FileBean(),20,50).setVisible(true);
	// }
	// });
	// }
}
