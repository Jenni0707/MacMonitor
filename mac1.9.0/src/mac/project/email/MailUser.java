package mac.project.email;

import java.io.Serializable;

public class MailUser implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	static String displayName;// 邮件显示名称
	static String userName;// 用户名
	static String passWord;// 口令
	static String pop3Server;// POP3服务器
	static String smtpServer;// SMTP服务器
	static boolean validateNeeded;// 验证信息

	public static void setDisplayName(String DisplayName) {
		displayName = DisplayName;
	}

	public static void setUserName(String UserName) {
		userName = UserName;
	}

	public static void setPassWord(String PassWord) {
		passWord = PassWord;
	}

	public static void setPop3Server(String Pop3Server) {
		pop3Server = Pop3Server;
	}

	public static void setSmtpServer(String SmtpServer) {
		smtpServer = SmtpServer;
	}

	public static void setValidateNeeded(boolean ValidateNeeded) {
		validateNeeded = ValidateNeeded;
	}

	public static String getDisplayName() {
		return displayName;
	}

	public static String getUserName() {
		return userName;
	}

	public static String getPassWord() {
		return passWord;
	}

	public static String getPop3Server() {
		return pop3Server;
	}

	public static String getSmtpServer() {
		return smtpServer;
	}

	public static boolean getValidateNeeded() {
		return validateNeeded;
	}
}
