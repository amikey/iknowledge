package cn.xdf.learn.speak.jjlist;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
public class SecPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {
//		System.out.println("===========================满分口语机经 ==============================");
		
		String question = page.getHtml().$("div.matter-content").xpath("div[@class='content-subject js-translate-content']").xpath("p[1]").toString().replace("<p>", "").replace("</p>", "");
		System.out.println("问题："+question);
		
	}

	public Site getSite() {
		return site;
	}
//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		
//		Spider.create(new SecPageProcessor())
//		.addUrl("http://toefl.kaomanfen.com/question/kaomanfen/2a7fok.html").run();
//		
//		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
//				/ 1000 / 60 + "分");
//
//	}

}
