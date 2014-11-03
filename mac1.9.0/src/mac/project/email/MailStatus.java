package mac.project.email;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class MailStatus extends JDialog implements Serializable {
	JPanel panel1 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JProgressBar waitBar = new JProgressBar(1, 100);
//	Timer timer = new Timer(50, new Action());
	Timer timer = new Timer(0, new Action());
//	int value = 4;
	int value = 1;

	public MailStatus() {
		Init();
	}

	void Init() {
		panel1.setLayout(borderLayout1);
		waitBar.setStringPainted(true); // 显示进度百分比
		this.getContentPane().setLayout(null);
		panel1.setBounds(new Rectangle(360, 0, 0, 0));
		waitBar.setForeground(UIManager.getColor("TextField.selectionBackground"));
		waitBar.setBounds(new Rectangle(31, 37, 252, 27));
		this.setTitle("正在读取邮件信息，请稍后.....");
		this.getContentPane().add(panel1, null);
		this.getContentPane().add(waitBar, null);
		setSize(340, 109);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		timer.start();

	}


	class Action implements ActionListener, Serializable {
		public void actionPerformed(ActionEvent e) {
			
			if (value >= 100) {
//				value = 4;
				value = 1;

			}
			waitBar.setValue(value);
//			value += 4;
			value += 1;
		}
		
	}

	public void Start() {
		timer.start();
	}

	public void Stop() {
		timer.stop();
	}
}
