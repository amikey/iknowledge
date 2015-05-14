package cn.xdf.learn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.xdf.learn.entity.Words;
import cn.xdf.learn.util.JDBCConnection;

public class MakeTest {
	

	public static void main(String[] args) {

		MakeTest make = new MakeTest();
		make.makeTestToTimu();
	}

	/**
	 * 根据wd_word_test表生成wd_test_timu表
	 */
	public void makeTestToTimu() {
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		List<WordTest> wordT = getWordTest();
		System.out.println(wordT.size());
		
		for (WordTest wordTest : wordT) {
			List<Integer> ids = getWordId(wordTest.getTestName(),wordTest.getTimuNum());
			int index = 0;
			for (Integer wordId : ids) {
				index++;
				String sql = "INSERT INTO wd_test_timu (test_id,word_id,order_num)values('"
						+ wordTest.getId()
						+ "','"
						+ wordId
						+ "','"
						+ index + "')";
				System.out.println(index);
				java.sql.Statement statement;
				try {
					statement = connection.createStatement();
					statement.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Integer> getWordId(String pages,int timuNum){
		List<Integer> ids = new ArrayList<Integer>();
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		ResultSet rs = null;
		String[] page = pages.split("-");
		String findSql = "SELECT id FROM wd_word WHERE page_no >= "+page[0]+" AND page_no <= "+page[1]+" ORDER BY rand() LIMIT "+timuNum;
		java.sql.Statement statement;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(findSql);
			while (rs.next()) {
				ids.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	

	

	/**
	 * 获取所有页码
	 * 
	 * @return 页码集合
	 */
	public List<Integer> getPage() {
		List<Integer> wordList = new ArrayList<Integer>();
		String sql;
		Connection onnection = JDBCConnection.getJDBCConnection()
				.getConnection();
		ResultSet rs = null;
		try {
			sql = "select DISTINCT(page_no) from wd_word ";
			System.out.println(sql);
			java.sql.Statement statement = onnection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				wordList.add(rs.getInt("page_no"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				onnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wordList;
	}

	/**
	 * 获取数据库中所有出题范围
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<WordTest> getWordTest() {
		List<WordTest> wordList = new ArrayList<WordTest>();
		String sql;
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		java.sql.Statement statement;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM wd_word_test ";
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				WordTest word = new WordTest();
				word.setId(rs.getInt("id"));
				word.setTestName(rs.getString("test_name"));
				word.setTestBookId(rs.getInt("test_book_id"));
				word.setTestBook(rs.getString("test_book"));
				word.setTeacherCode(rs.getString("teacher_code"));
				word.setTeacherName(rs.getString("teacher_name"));
				word.setTimuNum(rs.getInt("timu_num"));
				word.setEndTime(rs.getDate("end_time"));
				word.setCreateTime(rs.getDate("create_time"));
				word.setUpdateTime(rs.getDate("update_time"));
				wordList.add(word);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wordList;
	}

	/**
	 * 递归生成itemCode
	 * 
	 * @param r
	 *            已有序号集合
	 * @param itCode
	 * @return
	 */
	public String getItemCode(List<String> r, String itCode) {
		String[] code = { "A", "B", "C", "D" };
		int random = new Random().nextInt(4);
		String itemCode = code[random];
		if (r.contains(itemCode)) {
			itemCode = getItemCode(r, itemCode);
		}

		return itemCode;

	}

	/**
	 * 获取时间格式
	 * 
	 * @param time
	 * @return
	 */
	public static String string2Date(String time) {
		Date d = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = formatter.format(d);
		return time;
	}

	/**
	 * 根据itemCode生成答案顺序
	 * 
	 * @param itemCode
	 * @return
	 */
	public int getOrderCode(String itemCode) {
		int orderCode = 0;
		if (itemCode.equals("A")) {
			orderCode = 1;
		} else if (itemCode.equals("B")) {
			orderCode = 2;
		} else if (itemCode.equals("C")) {
			orderCode = 3;
		} else {
			orderCode = 4;
		}
		return orderCode;
	}
}
