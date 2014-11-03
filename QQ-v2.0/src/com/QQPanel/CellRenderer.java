package com.QQPanel;

import java.awt.*;
import java.io.UnsupportedEncodingException;
//import java.nio.charset.Charset;

import javax.swing.*;

/**
 * @author duomeng 处理ICQPOP面板上的每一行
 */
public class CellRenderer extends JLabel implements ListCellRenderer {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	CellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Object[] obja = (Object[]) value;
		if (value != null) {
			String nickName = "";
			nickName = obja[0].toString();
			try {
				nickName = new String(obja[0].toString().getBytes("UTF-8"),
						"UTF-8");
				// String encoding = System.getProperty("file.encoding");
				// System.out.println("系统默认的字符集是:" + encoding);
				// System.out.println(nickName+nickName.getBytes(Charset.forName("UTF-8")).length);
			} catch (UnsupportedEncodingException e) {
				System.err.println("字符编码错误 CellRenderer 36:" + e.getMessage());
				// e.printStackTrace();
			}
			setText(nickName + "(" + obja[1].toString() + ")");// 用户的名字信息放在数组第二列
			// 用户的头像信息放在数组第一列
			String NewStr = ((String) obja[5]).substring(
					((String) obja[5]).indexOf("(") + 1,
					((String) obja[5]).lastIndexOf(")"));
			// System.out.println("NewStr="+NewStr);
			String iconName = NewStr.substring(NewStr.lastIndexOf("/") + 1);
			// setIcon((Icon) obja[0]);
			// System.out.println("iconName="+iconName);
			setIcon(new ImageIcon(this.getClass().getClassLoader()
					.getResource("com/QQPanel/image/"+iconName)));
			// setToolTipText(obja[2].toString());
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());

		}
		return this;
	}
}