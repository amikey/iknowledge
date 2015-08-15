package cn.xdf.learn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TEST {

	public static void main(String[] args) {
		  String str="<p style=\"font-size:1.3em;font-weight:bold\">�������Ķ���1</p> " +
		  		"<p style=\"font-size:1.3em;font-weight:bold\">�������Ķ���1000��������</p>";
		  String regex="<p.*?>(.*?)</p> ";
		 

		 String[] strs= str.split("</p>");
		for (String string : strs) {
			System.out.println(string.replace("<p style=\"font-size:1.3em;font-weight:bold\">", ""));
		}
		
	}
}
