package learn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);

	
	static List<Words> dataset = new ArrayList<Words>();
	public void process(Page page) {
		
		//根据页面位置获取美式音标
		String pronunciation = page.getHtml().xpath("//div[@id='content']/div[@class='main']/div[@class='word']/div[@class='phonetic']/span[1]/bdo/text()")
				.toString();
		//MP3的URL地址 示例：muTd300h2230716d0f31673e090a17af8de9d91f.mp3?t=telefax
		String mp3Url = page.getHtml().xpath("//div[@id='content']/div[@class='main']/div[@class='word']/div[@class='phonetic']/span[2]/i[2]/@naudio")
				.toString();
		String word = page.getUrl().toString().split("/")[3];
		//特殊音标替换
//		String newPronunciation = conversionSpecialCharacters(word,pronunciation);
		String newPronunciation = null;
		/*try {
			newPronunciation = new String(pronunciation.getBytes("ISO8859-1"), "GBK");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		//下载MP3
		String maleVoice = downloadVioce(mp3Url);
		
		//将转换完成的单词放到集合中
		Words wordEntity = new Words();
		wordEntity.setWord(word == null?"":word);
		wordEntity.setMeaning("");
		wordEntity.setRoot("");
		wordEntity.setRootMeaning("");
		wordEntity.setHandoutPage("");
		wordEntity.setPronunciation(pronunciation == null?"":pronunciation);
		wordEntity.setMaleVoice(maleVoice == null?"":maleVoice);
		dataset.add(wordEntity);

		System.out.println("单词：" + word
				+ "发音" + pronunciation );
		
	}

	public Site getSite() {
		return site;

	}

	public static void main(String[] args) {
		//待爬取单词
		/*String[] words = { "asymmetrical", "bicameral", "complacence",
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
				"anticlockwise","decimeter" };*/
		long startTime = System.currentTimeMillis();
		List<String> wordL=null;
		try {
			wordL = getAllWords();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//***************爬取单词*****************
		int count = wordL.size();
		int counts = wordL.size();
		for (String string : wordL) {

			Spider.create(new WordsPageProcessor()).addUrl("http://dict.cn/" + string).run();
			System.out.println("未转/总数："+--count+"/"+counts);
		}
		//System.out.println("待转换单词个数："+words.length);
		System.out.println("爬取用时："+(System.currentTimeMillis()-startTime)/1000/60+"分");
//		Spider.create(new WordsPageProcessor()).addUrl("http://dict.cn/" + "decimeter").run();
		
		//****************保存******************
		System.out.println("保存");
		try {
			saveWords();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("共计用时："+(System.currentTimeMillis()-startTime)/1000/60+"分");
		System.out.println("保存完成");
		
		//***************导出********************
		System.out.println("导出开始");
		try {
			exportExcel();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
        //*************************************  
    }
	/**
	 * 转换特殊字符
	 * @param word 单词 
	 * @param pronunciation 发音
	 */
	public String  conversionSpecialCharacters(String words,String pronunciation){
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
				
				//转换特殊字符
				for (int i = 0; i < pronunciation.length(); i++) {
					for (String key : changes.keySet()) {
						char item = pronunciation.charAt(i);
						if(key.equals(item+"")){
							String newP = pronunciation.replace(key, changes.get(key));
							pronunciation = newP;
						}
					}
				}
		return pronunciation;
	}
	/**
	 * 保存单词到数据库补全缺失的音标
	 * @throws SQLException
	 */
	public static void saveWords() throws SQLException{
		String sql ;
		Connection onnection = MysqlDemo.getMysqlDemo().getConnection();
		String wordss = null;
		try {
			for (Words word : dataset) {
				wordss = word.getWord();
				//由于 音标中的  ' 符号在 数据库操作时会产生错误，所以将其替换成 ’ 后续导出时 再 替换回来
				//sql="update words set PRONUNCATION = '"+word.getPronunciation().replace("'", "‘")+"' , MALE_VOIVE = '"+word.getMaleVoice()+"' where (WORD = '"+word.getWord()+"' and PRONUNCATION ='')";
				//sql="update words set PRONUNCATION = '"+word.getPronunciation().replace("'", "‘")+"' where WORD = '"+word.getWord()+"'";
				sql="update words set  PRONUNCATION = '"+word.getPronunciation().replace("'", "‘")+"' , MALE_VOIVE = '"+word.getMaleVoice()+"' where WORD = '"+word.getWord()+"'";
				System.out.println(sql);
				java.sql.Statement statement = onnection.createStatement();
				statement.executeUpdate(sql);
			}
		} catch (SQLException e) {
			System.out.println("错误单词："+wordss);
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
	/**
	 * 下载并保存MP3文件
	 * @param mp3Url
	 */
	public String  downloadVioce(String mp3Url){
		//截取纯MP3路径
				String[] mp3U = mp3Url.split("\\?");
				String mp3Name = "dict_"+mp3U[1].split("=")[1];

				InputStream in = null;
				FileOutputStream f = null;
				try {
				//经验证第一次请求MP3时 需要在地址后加入单词 即  变量 mp3Url
				in=new URL("http://audio.dict.cn/"+mp3Url).openConnection().getInputStream();  //创建连接、输入流
				//创建文件输出流   重置MP3 文件名称  文件格式为  dict_单词.mp3  
				/*File file = new File("E:/iknowledgeVoice/"+mp3Name+".mp3");
				if(!file.exists()){
					file.mkdir();
				}*/
				f = new FileOutputStream("E:/iknowledgeVoice/"+mp3Name+".mp3");
				byte [] bb=new byte[1024];  //接收缓存
				int len;
				while( (len=in.read(bb))>0){ //接收
				  f.write(bb, 0, len);  //写入文件
				}
				f.close();
				in.close();
				
				} catch (Exception e) {
					System.out.println(mp3Url);
					e.printStackTrace();
				}finally {
					try {
						if (f != null) {
							f.close();
						}
						if(in !=null){
							in.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return mp3Name;
	}
	/**
	 * 获取数据库中所有单词
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getAllWords() throws SQLException {
		List<String> wordList = new ArrayList<String>();
        String sql ;
		Connection onnection = MysqlDemo.getMysqlDemo().getConnection();
		ResultSet rs = null;
		try {
				sql="select WORD from words WHERE PRONUNCATION='' ";
				System.out.println(sql);
				java.sql.Statement statement = onnection.createStatement();
				rs=statement.executeQuery(sql);
				while (rs.next()) {
				    wordList.add(rs.getString("WORD"));
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			onnection.close();
		}
        return wordList;
	}
}
