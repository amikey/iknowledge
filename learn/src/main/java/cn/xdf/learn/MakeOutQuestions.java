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

public class MakeOutQuestions {
	// 中翻英题目数
	static int CHToENQuIndex = 0;
	// 中翻英答案数
	static int CHToENAnIndex = 0;
	// 英翻中题目数
	static int ENToCHQuIndex = 0;
	// 英翻中答案数
	static int ENToCHAnIndex = 0;
	// 听选英问题数
	static int listenToCheckQuIndex = 0;
	// 听选英答案数
	static int listenToCheckAnIndex = 0;
	// 听写英问题及答案数
	static int listenToWriteQuIndex = 0;

	public static void main(String[] args) {

		MakeOutQuestions make = new MakeOutQuestions();
		make.makeCHToEN();
		make.makeENToCH();
		make.listenToCheck();
		make.listenToWrite();
		System.out.println("中翻英题目数:" + CHToENQuIndex);
		System.out.println("中翻英答案数:" + CHToENAnIndex);
		System.out.println("英翻中题目数:" + ENToCHQuIndex);
		System.out.println("英翻中答案数:" + ENToCHAnIndex);
		System.out.println("听选英问题数:" + listenToCheckQuIndex);
		System.out.println("听选英答案数:" + listenToCheckAnIndex);
		System.out.println("听写英问题及答案数:" + listenToWriteQuIndex);
	}

	/**
	 * 生成看中文含义选择英文单词试题
	 */
	public void makeCHToEN() {
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		List<Words> words = getAllWords();
		String[] code = { "A", "B", "C", "D" };
		int itemid = 0;
		for (Words words2 : words) {
			int right = new Random().nextInt(4);
			String sql = "INSERT INTO wd_timu (timu_type,audio,timu,right_ans,word_id,word,create_time,update_time)values(1,'"
					+ words2.getMaleVoice()
					+ "','"
					+ words2.getMeaning()
					+ "','"
					+ code[right]
					+ "','"
					+ words2.getId()
					+ "','"
					+ words2.getWord()
					+ "','"
					+ string2Date(new Date().toString())
					+ "','"
					+ string2Date(new Date().toString()) + "')";
			CHToENQuIndex++;
			java.sql.Statement statement;
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 查询题干ID
			ResultSet rs = null;

			String itemSql = "SELECT MAX(id) id FROM wd_timu ";
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(itemSql);
				while (rs.next()) {
					itemid = rs.getInt("id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			List<Words> word1 = getWordGroup(
					(int) Integer.parseInt(words2.getHandoutPage()), words2);
			// 存放选项
			List<String> r = new ArrayList<String>();
			r.add(code[right]);
			for (Words words3 : word1) {
				int random = new Random().nextInt(4);
				String itemCode = code[random];
				if (!r.contains(itemCode)) {

					r.add(itemCode);

				} else {
					itemCode = getItemCode(r, itemCode);
					r.add(itemCode);
				}

				
				String sql2 = "INSERT INTO wd_timu_item (item_code,item_name,order_num,timu_id,create_time,update_time)values('"
						+ itemCode
						+ "','"
						+ words3.getWord()
						+ "',"
						+ getOrderCode(itemCode)
						+ ","
						+ itemid
						+ ",'"
						+ string2Date(new Date().toString())
						+ "','"
						+ string2Date(new Date().toString()) + "')";
				CHToENAnIndex++;

				try {
					statement = connection.createStatement();
					statement.executeUpdate(sql2);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			String sql3 = "INSERT INTO wd_timu_item (item_code,item_name,order_num,timu_id,create_time,update_time)values('"
					+ code[right]
					+ "','"
					+ words2.getWord()
					+ "',"
					+ getOrderCode(code[right])
					+ ","
					+ itemid
					+ ",'"
					+ string2Date(new Date().toString())
					+ "','"
					+ string2Date(new Date().toString()) + "')";
			CHToENAnIndex++;
			System.out.println(sql3);
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql3);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 生成看英文单词选择中文含义试题
	 */
	public void makeENToCH() {
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		List<Words> words = getAllWords();
		String[] code = { "A", "B", "C", "D" };
		int itemid = 0;
		for (Words words2 : words) {
			int right = new Random().nextInt(4);
			String sql = "INSERT INTO wd_timu (timu_type,audio,timu,right_ans,word_id,word,create_time,update_time)values(2,'"
					+ words2.getMaleVoice()
					+ "','"
					+ words2.getWord()
					+ "','"
					+ code[right]
					+ "','"
					+ words2.getId()
					+ "','"
					+ words2.getWord()
					+ "','"
					+ string2Date(new Date().toString())
					+ "','"
					+ string2Date(new Date().toString()) + "')";
			ENToCHQuIndex++;
			java.sql.Statement statement;
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 查询题干ID
			ResultSet rs = null;

			String itemSql = "SELECT MAX(id) id FROM wd_timu ";
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(itemSql);
				while (rs.next()) {
					itemid = rs.getInt("id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			List<Words> word1 = getWordGroup(
					(int) Integer.parseInt(words2.getHandoutPage()), words2);
			// 存放选项
			List<String> r = new ArrayList<String>();
			r.add(code[right]);
			for (Words words3 : word1) {
				int random = new Random().nextInt(4);
				String itemCode = code[random];
				if (!r.contains(itemCode)) {

					r.add(itemCode);

				} else {
					itemCode = getItemCode(r, itemCode);
					r.add(itemCode);
				}
				
				String sql2 = "INSERT INTO wd_timu_item (item_code,item_name,order_num,timu_id,create_time,update_time)values('"
						+ itemCode
						+ "','"
						+ words3.getMeaning()
						+ "',"
						+ getOrderCode(itemCode)
						+ ","
						+ itemid
						+ ",'"
						+ string2Date(new Date().toString())
						+ "','"
						+ string2Date(new Date().toString()) + "')";
				ENToCHAnIndex++;

				try {
					statement = connection.createStatement();
					statement.executeUpdate(sql2);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			String sql3 = "INSERT INTO wd_timu_item (item_code,item_name,order_num,timu_id,create_time,update_time)values('"
					+ code[right]
					+ "','"
					+ words2.getMeaning()
					+ "',"
					+ getOrderCode(code[right])
					+ ","
					+ itemid
					+ ",'"
					+ string2Date(new Date().toString())
					+ "','"
					+ string2Date(new Date().toString()) + "')";
			ENToCHAnIndex++;
			System.out.println(sql3);
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql3);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成听发音选择单词试题
	 */
	public void listenToCheck() {
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		List<Words> words = getAllWords();
		String[] code = { "A", "B", "C", "D" };
		int itemid = 0;
		for (Words words2 : words) {
			int right = new Random().nextInt(4);
			String sql = "INSERT INTO wd_timu (timu_type,audio,timu,right_ans,word_id,word,create_time,update_time)values(3,'"
					+ words2.getMaleVoice()
					+ "','"
					+ words2.getMaleVoice()
					+ "','"
					+ code[right]
					+ "','"
					+ words2.getId()
					+ "','"
					+ words2.getWord()
					+ "','"
					+ string2Date(new Date().toString())
					+ "','"
					+ string2Date(new Date().toString()) + "')";
			listenToCheckQuIndex++;
			java.sql.Statement statement;
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 查询题干ID
			ResultSet rs = null;

			String itemSql = "SELECT MAX(id) id FROM wd_timu ";
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(itemSql);
				while (rs.next()) {
					itemid = rs.getInt("id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			List<Words> word1 = getWordGroup(
					(int) Integer.parseInt(words2.getHandoutPage()), words2);
			// 存放选项
			List<String> r = new ArrayList<String>();
			r.add(code[right]);
			for (Words words3 : word1) {
				int random = new Random().nextInt(4);
				String itemCode = code[random];
				if (!r.contains(itemCode)) {

					r.add(itemCode);

				} else {
					itemCode = getItemCode(r, itemCode);
					r.add(itemCode);
				}
				
				String sql2 = "INSERT INTO wd_timu_item (item_code,item_name,order_num,timu_id,create_time,update_time)values('"
						+ itemCode
						+ "','"
						+ words3.getWord()
						+ "',"
						+ getOrderCode(itemCode)
						+ ","
						+ itemid
						+ ",'"
						+ string2Date(new Date().toString())
						+ "','"
						+ string2Date(new Date().toString()) + "')";
				listenToCheckAnIndex++;

				try {
					statement = connection.createStatement();
					statement.executeUpdate(sql2);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			String sql3 = "INSERT INTO wd_timu_item (item_code,item_name,order_num,timu_id,create_time,update_time)values('"
					+ code[right]
					+ "','"
					+ words2.getWord()
					+ "',"
					+ getOrderCode(code[right])
					+ ","
					+ itemid
					+ ",'"
					+ string2Date(new Date().toString())
					+ "','"
					+ string2Date(new Date().toString()) + "')";
			listenToCheckAnIndex++;
			System.out.println(sql3);
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql3);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成听发音写单词试题
	 */
	public void listenToWrite() {
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		List<Words> words = getAllWords();
		for (Words words2 : words) {
			String sql = "INSERT INTO wd_timu (timu_type,audio,timu,right_ans,word_id,word,create_time,update_time)values(4,'"
					+ words2.getMaleVoice()
					+ "','"
					+ words2.getMaleVoice()
					+ "','"
					+ words2.getWord()
					+ "','"
					+ words2.getId()
					+ "','"
					+ words2.getWord()
					+ "','"
					+ string2Date(new Date().toString())
					+ "','"
					+ string2Date(new Date().toString()) + "')";
			System.out.println(sql);
			listenToWriteQuIndex++;
			java.sql.Statement statement;
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据页码及单词生成选项
	 * 
	 * @param page
	 * @param wordf
	 * @return
	 */
	public List<Words> getWordGroup(int page, Words words) {
		List<Words> wordList = new ArrayList<Words>();
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		java.sql.Statement statement;
		ResultSet rs = null;

		String sql = "SELECT * FROM words where HANDOUT_PAGE = '" + page + "'";
		if (words.getWord() != null) {
			sql += "AND WORD != '" + words.getWord() + "' ";
		} else if (words.getMeaning() != null) {
			sql += "AND MEANING != '" + words.getMeaning() + "' ";
		} else {
			sql += "AND PRONUNCATION != '" + words.getPronunciation() + "' ";
		}
		sql += " ORDER BY rand() LIMIT 3";
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				Words word = new Words();
				word.setWord(rs.getString("WORD"));
				word.setMeaning(rs.getString("MEANING"));
				word.setRoot(rs.getString("ROOT"));
				word.setRootMeaning(rs.getString("ROOT_MEANING"));
				word.setHandoutPage(rs.getString("HANDOUT_PAGE"));
				word.setPronunciation(rs.getString("PRONUNCATION").replace("‘",
						"'"));
				word.setMaleVoice(rs.getString("MALE_VOIVE"));
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
			sql = "select DISTINCT(HANDOUT_PAGE) from words ";
			System.out.println(sql);
			java.sql.Statement statement = onnection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				wordList.add(rs.getInt("HANDOUT_PAGE"));
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
	 * 获取数据库中所有单词
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<Words> getAllWords() {
		List<Words> wordList = new ArrayList<Words>();
		String sql;
		Connection connection = JDBCConnection.getJDBCConnection()
				.getConnection();
		java.sql.Statement statement;
		ResultSet rs = null;
		try {
			sql = "select * from words ";
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				Words word = new Words();
				word.setId(rs.getInt("id"));
				word.setWord(rs.getString("WORD"));
				word.setMeaning(rs.getString("MEANING"));
				word.setRoot(rs.getString("ROOT"));
				word.setRootMeaning(rs.getString("ROOT_MEANING"));
				word.setHandoutPage(rs.getString("HANDOUT_PAGE"));
				word.setPronunciation(rs.getString("PRONUNCATION").replace("‘",
						"'"));
				word.setMaleVoice(rs.getString("MALE_VOIVE"));
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
	 * @param r  已有序号集合
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
	 * @param itemCode
	 * @return
	 */
	public int getOrderCode(String itemCode){
		int orderCode = 0;
		if(itemCode.equals("A")){
			orderCode =1;
		}else if(itemCode.equals("B")){
			orderCode =2;
		}else if(itemCode.equals("C")){
			orderCode =3;
		}else{
			orderCode =4;
		}
		return orderCode;
	}
}
