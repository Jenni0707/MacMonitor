package com.topking.ftp.validate;

import java.awt.Component;
import javax.swing.JOptionPane;

public class InputValidate {
	public static boolean inputIsNull(Component c, String title, Object value) {
		boolean flag = false;
		if (value instanceof String) {
			if (value == null || "".equals(value)) {
				JOptionPane.showMessageDialog(c, title + "����Ϊ��", "����",
						JOptionPane.ERROR_MESSAGE);
				flag = true;
			}
		}
		return flag;
	}
}
