package cn.xdf.learn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import cn.xdf.learn.util.JDBCConnection;			



public class Read12306 {

	/** * 以行为单位读取文件，常用于读面向行的格式化文件 */
	public static void readFileByLines(String fileName) {
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
			reader = new BufferedReader(isr);
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				String[] re = tempString.split("----");
				String sql = "INSERT INTO train_user (login_name,user_name,p_name,p_id,password,phone,email) VALUES('"+
				re[0]+"','"+re[1]+"','"+re[2]+"','"+re[3]+"','"+re[4]+"','"+re[5]+"','"+re[6]+"')";
				java.sql.Statement statement;
				System.out.println(sql);
				try {
					statement = connection.createStatement();
					statement.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

	}
	
	public static void main(String[] args) {
		readFileByLines("E:\\sss.txt");
	}
}
