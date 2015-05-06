package learn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
public class WordsPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(100).setRetryTimes(3)
			.setTimeOut(15000);

	static List<Words> dataset = new ArrayList<Words>();
	public void process(Page page) {
		//需要替换的字符
		Map<String, String> changes = new HashMap<String, String>();
		changes.put("æ", "1");
		changes.put("ɑː", "2");
		changes.put("ɔː", "3");
		changes.put("ə", "4");
		changes.put("ʃ", "5");
		changes.put("ŋ", "6");
		changes.put("θ", "7");
		changes.put("ʌ", "8");
		changes.put("ʒ", "9");
		changes.put("e", "A");// 音标有误 separate excel发音['sAp4`ret] 网站发音['sepərət]
		// segregate excel发音 ['sAgrI`get] 网站发音['seɡrɪɡeɪt] [ˈsɛɡrɪˌɡet]
		changes.put("ər", "B");
		changes.put("ɜː", "C");
		//根据页面位置获取美式音标
		String pronunciation = page
				.getHtml()
				.xpath("//div[@id='content']/div[@class='main']/div[@class='word']/div[@class='phonetic']/span[1]/bdo/text()")
				.toString();
		for (int i = 0; i < pronunciation.length(); i++) {
			for (String key : changes.keySet()) {
				char item = pronunciation.charAt(i);
				if(key.equals(item+"")){
					String newP = pronunciation.replace(key, changes.get(key));
					pronunciation = newP;
				}
			}
		}
		Words word = new Words();
		word.setWord(page.getUrl().toString().split("/")[3]);
		word.setMeaning("");
		word.setRoot("");
		word.setRootMeaning("");
		word.setHandoutPage("");
		word.setPronunciation(pronunciation);
		word.setMaleVoice("");
		dataset.add(word);

		System.out.println("单词：" + page.getUrl().toString().split("/")[3]
				+ "发音" + pronunciation);
	}

	public Site getSite() {
		return site;

	}

	public static void main(String[] args) {
		String[] words = { "asymmetrical", "bicameral", "complacence",
				"consulting", "deserted", "descending", "Decameron",
				"Decennial", "ecliptic", "heterosexual", "homologue",
				"hypotension", "metastasis", "monotheism", "polynomial",
				"primacy", "rebirth", "Renaissance", "telecom", "telefax",
				"aerodynamics", "inauguration", "accede", "centipede",
				"chronology", "scissors", "accredit", "crucifixion",
				"cruciform", "chromatography", "fluorochrome", "dentistry",
				"seduction", "abduction", "adduction", "esse", "refrigerant",
				"geocentric", "geomagnetic", "geopolitics", "hemostasis",
				"inherited", "adjoining", "adjunction", "astronautics",
				"Neolithic", "pharmaceutics", "exponential", "prescriptive",
				"asperse", "alienable", "non-linear", "poetess", "rhetorics",
				"poetics", "roomie", "birdie", "doggie",
				"piggie", "fatalism", "Judaism", "divinity",
				"hypothetically", "madness", "penmanship", "dictatorship",
				"Helium", "Calcium", "Titanium", "Potassium", "Sodium",
				"anticlockwise" };
		
		//***************爬取单词*****************
		/*for (String string : words) {

			Spider.create(new WordsPageProcessor()).addUrl("http://dict.cn/" + string).run();
		}
		System.out.println("待转换单词个数："+words.length);*/
		
		
		//****************保存******************
		/*try {
			saveWords();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	
		
		//***************导出********************
		try {
			exportExcel();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        //*************************************  
    }
	/**
	 * 保存单词到数据库
	 * @throws SQLException
	 */
	public static void saveWords() throws SQLException{
		String sql ;
		Connection onnection = MysqlDemo.getMysqlDemo().getConnection();
		try {
			for (Words word : dataset) {
				sql="update words set PRONUNCATION = '"+word.getPronunciation().replace("'", "‘")+"' where (WORD = '"+word.getWord()+"' and PRONUNCATION ='')";
				System.out.println(sql);
				java.sql.Statement statement = onnection.createStatement();
				statement.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			onnection.close();
		}
	}
	/**
	 * 将数据库中的数据导出到Excel
	 * @throws SQLException
	 */
	public static void exportExcel() throws SQLException{
		ExportExcel<Words> ex = new ExportExcel<Words>();
        String[] headers = { "单词","词性和词义","词根","词根含义","讲义页码","美音音标","美音男声"};
        try {

        	String sql ;
    		Connection onnection = MysqlDemo.getMysqlDemo().getConnection();
    		ResultSet rs = null;
    		List<Words> wordList = new ArrayList<Words>();
    		try {
    			
    				sql="select * from words";
    				System.out.println(sql);
    				java.sql.Statement statement = onnection.createStatement();
    				rs=statement.executeQuery(sql);
    				while (rs.next()) {
    					Words word = new Words();
    				    word.setWord(rs.getString("WORD"));
    				    word.setMeaning(rs.getString("MEANING"));
    				    word.setRoot(rs.getString("ROOT"));
    				    word.setRootMeaning(rs.getString("ROOT_MEANING"));
    				    word.setHandoutPage(rs.getString("HANDOUT_PAGE"));
    				    word.setPronunciation(rs.getString("PRONUNCATION").replace("‘", "'"));
    				    word.setMaleVoice(rs.getString("MALE_VOIVE"));
    				    wordList.add(word);
    				   }
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}finally {
    			onnection.close();
    		}
        	
            OutputStream out = new FileOutputStream("E://单词导出.xls");
            ex.exportExcel(headers, wordList, out);
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
