package cn.xdf.learn.write.tpo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
public class FirstPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {

		List<String> category = page.getHtml().$("div.kmf-maincol").$("a","href").regex(".*html").all();
//		List<String> Writing = page.getHtml().$("div.kmf-maincol").$("a","text").regex(".*Task").all();
//		System.out.println(category);

//		for (String string : category) {
////			System.out.println(string);
//			Spider.create(new SecPageProcessor())
//			.addUrl(string).run();
//		}
		for (int i = 0; i < category.size();i++) {
			if(i % 2 == 0){
				System.out.println("==============综合写作==============");
				System.out.println("类型：Integrated Writing Task");
				Spider.create(new SecPageProcessor()).addUrl(category.get(i)).run();
			}else{
				System.out.println("==============独立写作==============");
				System.out.println("类型：Independented Writing Task");
				Spider.create(new ThiPageProcessor()).addUrl(category.get(i)).run();
			}
			
		}
	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= 20/3+1; i++) {
			Spider.create(new FirstPageProcessor())
			.addUrl("http://toefl.kaomanfen.com/writing/tpo-1"+"/"+i).run();
		}
		for (int i = 1; i <= 14/3+1; i++) {
			Spider.create(new FirstPageProcessor())
			.addUrl("http://toefl.kaomanfen.com/writing/tpo-2"+"/"+i).run();
		}
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

	}

	/**
	 * 下载并保存MP3文件
	 * 
	 * @param mp3Url
	 */
	public static boolean downloadVioce(String mp3Url) {

		InputStream in = null;
		FileOutputStream f = null;
		try {
			// 经验证第一次请求MP3时 需要在地址后加入单词 即 变量 mp3Url
			in = new URL(mp3Url).openConnection().getInputStream(); // 创建连接、输入流
			// 创建文件输出流 重置MP3 文件名称 文件格式为 dict_单词.mp3
			/*
			 * File file = new File("E:/iknowledgeVoice/"+mp3Name+".mp3");
			 * if(!file.exists()){ file.mkdir(); }
			 */
			String[] mp3Names = mp3Url.split("/");

			File f1 = new File("E:/考满分音频/" + mp3Names[mp3Names.length - 1]);
			System.out.println(mp3Names[mp3Names.length - 1]);
			// f1.mkdirs();
			f = new FileOutputStream(f1);
			byte[] bb = new byte[1024]; // 接收缓存
			int len;
			while ((len = in.read(bb)) > 0) { // 接收
				f.write(bb, 0, len); // 写入文件
			}
			f.close();
			in.close();

		} catch (Exception e) {
			System.out.println(mp3Url);
			e.printStackTrace();
		} finally {
			try {
				if (f != null) {
					f.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
