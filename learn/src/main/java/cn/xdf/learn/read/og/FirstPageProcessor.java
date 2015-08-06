package cn.xdf.learn.read.og;

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
		List<String> categorys = page.getHtml().$("div.kmf-maincol").xpath("div[@class='contents-box clearfix']/div[@class='ltems-boxs g-mod-shadow']").all();
//		System.out.println(categorys.size());
		for (int i = 1; i <= categorys.size(); i++) {
			String category = page.getHtml().$("div.kmf-maincol").xpath("div[@class='contents-box clearfix']/div["+i+"]/div[@class='boxs-scroll']//dl[1]/dt[3]/text()").toString();
			System.out.println("标签："+category);
			String topicEn = page.getHtml().$("div.kmf-maincol").xpath("div[@class='contents-box clearfix']/div["+i+"]/div[@class='boxs-scroll']/div[@class='down-main']/h3[1]/text()").toString();
			System.out.println("题目："+topicEn);
			String topicCn = page.getHtml().$("div.kmf-maincol").xpath("div[@class='contents-box clearfix']/div["+i+"]/div[@class='boxs-scroll']/div[@class='down-main']/h3[2]/text()").toString();
			System.out.println("题目译文："+topicCn);
			String url = page.getHtml().$("div.kmf-maincol").xpath("div[@class='contents-box clearfix']/div["+i+"]/div[@class='boxs-scroll']/p[@class='detailed empty-read']/a/@href").toString();
//			System.out.println("链接："+url);
			Spider.create(new SecondPageProcessor()).addUrl(url).run();
		}
	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
	
		Spider.create(new FirstPageProcessor())
		.addUrl("http://toefl.kaomanfen.com/read/og").run();
		
		
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

	}

}
