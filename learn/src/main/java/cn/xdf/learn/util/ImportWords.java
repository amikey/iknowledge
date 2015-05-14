package cn.xdf.learn.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.xdf.learn.entity.Words;

public class ImportWords extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		try {

			// or:
			// com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
			// or：
			// new com.mysql.jdbc.Driver();
			System.out.println("成功加载MySQL驱动程序");

			// String path = request.getRealPath("/upload");
			int result = 0;
			// result = stmt.executeUpdate(sql);//
			// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
			if (result != -1) {
				// System.out.println("创建数据表成功");
				ReadExcel read = new ReadExcel();
				// List<Words> word = read.readXls(getFile(path));
				List<Words> word = read.readXls("E:\\iknowledge\\单词导入模版.xlsx");
				int index = 0;
				StringBuffer erro = new StringBuffer();
				System.out.println(word.get(1).getWord());
				for (Words words : word) {
					/*
					 * sql.append("insert into words values('"+UUID.randomUUID()+
					 * "','" + words.getWord() + "','" + words.getMeaning() +
					 * "','" + words.getRoot() + "','" + words.getRootMeaning()
					 * + "','" + words.getHandoutPage() + "','" +
					 * words.getPronunciation().replace("'", "‘") + "','" +
					 * words.getMaleVoice() + "');");
					 */
					String ss = "insert into words(WORD,MEANING,ROOT,ROOT_MEANING,HANDOUT_PAGE,PRONUNCATION,MALE_VOIVE) values('"
							+ words.getWord()
							+ "','"
							+ words.getMeaning()
							+ "','"
							+ words.getRoot()
							+ "','"
							+ words.getRootMeaning()
							+ "','"
							+ words.getHandoutPage()
							+ "','"
							+ words.getPronunciation().replace("'", "‘")
							// + "','" + words.getPronunciation()
							+ "','" + words.getMaleVoice() + "');";
					System.out.println(ss);
					try {
						result = connection.createStatement().executeUpdate(
								ss.toString());
					} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
						index++;
						erro.append(words.getWord() + " \r\n  ");
						e.printStackTrace();
						continue;
					}
				}
				System.out.println("重复单词：" + index + "个.包括\r\n " + erro);
				/*
				 * sql = "select * from student"; ResultSet rs =
				 * stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值 while
				 * (rs.next()) { System.out .println(rs.getString(1) + "\t" +
				 * rs.getString(2));// 入如果返回的是int类型可以用getInt() }
				 */
			}
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getFile(String srcPath) {
		File dir = new File(srcPath);
		File[] files = dir.listFiles();

		String filePath = files[0].getAbsolutePath();
		return filePath;
	}

	public static void main(String[] args) {
		try {
			new ImportWords().doPost(null, null);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}