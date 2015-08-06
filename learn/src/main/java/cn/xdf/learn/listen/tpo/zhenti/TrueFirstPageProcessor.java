package cn.xdf.learn.listen.tpo.zhenti;

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
import us.codecraft.webmagic.selector.Html;

/**
 * @author sunxingyang<br>
 */
public class TrueFirstPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
    static int index = 1;
	public void process(Page page) {
		String titles = page.getHtml().$("div.kmf-maincol").xpath("div[1]").xpath("div[1]").xpath("ul[1]").all().toString();
		System.out.println(titles);

	}

	public Site getSite() {
		return site;

	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
			Spider.create(new TrueFirstPageProcessor())
			.addUrl("http://toefl.kaomanfen.com/listen/tpo").run();
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
