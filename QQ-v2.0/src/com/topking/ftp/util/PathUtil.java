package com.topking.ftp.util;

public class PathUtil {

	public static String getLocalPath(String fileName) { // 获取本地路径？
		StringBuffer bf = new StringBuffer(fileName);
		bf.reverse();
		int index = new String(bf).indexOf('\\');
		String temp = new String(bf).substring(index, bf.length());
		bf = new StringBuffer(temp);
		bf.reverse();
		return new String(bf);
	}

	public static String getRemotePath(String fileName) { // 获取远程路径？
		StringBuffer bf = new StringBuffer(fileName);
		bf.reverse();
		int index = new String(bf).indexOf("/");
		String temp = new String(bf).substring(index, bf.length());
		bf = new StringBuffer(temp);
		bf.reverse();
		return new String(bf);
	}

	public static String rePlace(String src) {
		return src.replace('\\', '/');// ‘/’代替‘\\’
	}

	public static String getFolderName(String src) {
		src = rePlace(src);
		String[] s = src.split("/");
		return s[s.length - 1];
	}
}
