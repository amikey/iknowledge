package cn.xdf.learn.write.independent;

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

		List<String> category = page.getHtml().$("div.col-2").$("a","href").regex(".*html").all();
//		System.out.println(category);

		for (String string : category) {
//			System.out.println(string);
			Spider.create(new SecPageProcessor()).addUrl(string).run();
		}
	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= 10; i++) {
			Spider.create(new FirstPageProcessor())
			.addUrl("http://toefl.kaomanfen.com/writing/independent185-"+i).run();
		}
		
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

	}

}
