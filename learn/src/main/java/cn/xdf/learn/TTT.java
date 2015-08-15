package cn.xdf.learn;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TTT {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		
	        String s = "<MTCCommand Time=\"2014-03-22\"><Login UserId=\"admin\" UserPasswd=\"123\" /></MTCCommand><MTCCommand><Login/></MTCCommand>";  
	        String regEx = "<MTCCommand((?!<MTCCommand).)*</MTCCommand>";   
	        Pattern pat = Pattern.compile(regEx);    
	        Matcher mat = pat.matcher(s);    
	          
	          
	        while(mat.find()){  
	            System.out.println(mat.group());  
	        }  
	      }  
	

}
