package cn.xdf.learn.speak.tpo;

import java.util.List;

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
		System.out.println("=======================口语==========================");
		String tpo = page.getHtml().$("div.hd-title").$("span.title").xpath("//i[1]/text()").toString();
		System.out.println(tpo);
		String no = page.getHtml().$("div.hd-title").xpath("//span[@class='title']/text()").toString();
		System.out.println("题号："+no.substring(3, no.length()));
		String label = page.getHtml().$("div.hd-title").xpath("//span[@class='content-title-tpis']/text()").toString();
		System.out.println("标签："+label.substring(1, label.length()-1));
		String question = page.getHtml().$("div.matter-content").xpath("div[@class='content-subject js-translate-content']").xpath("p[1]").toString().replace("<p>", "").replace("</p>", "");
		System.out.println("问题："+question);
		List<String> model = page.getHtml().$("div.content-pageList").$("div.pageList-wrap-box").xpath("div[@class='pageList-box-down js-translate-content']/text()").all();
//		System.out.println("范文："+model);
		for (int i = 0; i < model.size(); i++) {
//			System.out.println("范文"+(i+1)+": "+model.get(i).substring(44, model.get(i).length()-6).toString());
			System.out.println("范文"+(i+1)+": "+model.get(i).toString());
		}
	}

	public Site getSite() {
		return site;
	}
//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		
//		Spider.create(new SecPageProcessor())
//		.addUrl("http://toefl.kaomanfen.com/question/kaomanfen/0a2uck.html").run();
//		
//		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
//				/ 1000 / 60 + "分");
//
//	}

}
