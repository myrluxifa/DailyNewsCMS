package cc.mrbird.common.util;

public class Util {
	public static String translate(String s) {
		String ss=s.replace("&gt;", ">").replace("&lt;", "<");
		return ss;
	}
}
