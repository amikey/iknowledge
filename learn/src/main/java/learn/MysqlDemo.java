package learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MysqlDemo {
	private MysqlDemo() {
	};

	private static MysqlDemo mysqlDemo = null;

	public static MysqlDemo getMysqlDemo() {
		if (mysqlDemo == null) {
			mysqlDemo = new MysqlDemo();
		}
		return mysqlDemo;
	}

	public Connection getConnection() {
		Connection conn = null;
		// MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
		// 避免中文乱码要指定useUnicode和characterEncoding
		// 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
		// 下面语句之前就要先创建javademo数据库
		String url = "jdbc:mysql://localhost:3306/xdflearn?"
				+ "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
		// 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
		// 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 动态加载mysql驱动
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url);
			// Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;

	}

	public static void main(String[] args) throws Exception {
		
		StringBuffer sql = new StringBuffer();

		Connection connection =MysqlDemo.getMysqlDemo().getConnection();
		try {

			// or:
			// com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
			// or：
			// new com.mysql.jdbc.Driver();
			System.out.println("成功加载MySQL驱动程序");

			int result = 0;
			// result = stmt.executeUpdate(sql);//
			// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
			if (result != -1) {
				// System.out.println("创建数据表成功");
				ReadExcel read = new ReadExcel();
				List<Words> word = read.readXls();
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
					String ss = "insert into words(WORD,MEANING,ROOT,ROOT_MEANING,HANDOUT_PAGE,PRONUNCATION,MALE_VOIVE) values('" + words.getWord()
							+ "','" + words.getMeaning() + "','"
							+ words.getRoot() + "','" + words.getRootMeaning()
							+ "','" + words.getHandoutPage() + "','"
							+ words.getPronunciation().replace("'", "‘")
							// + "','" + words.getPronunciation()
							+ "','" + words.getMaleVoice() + "');";
					System.out.println(ss);
					try {
						result = connection.createStatement().executeUpdate(ss.toString());
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
			connection.close();
		}

	}

}