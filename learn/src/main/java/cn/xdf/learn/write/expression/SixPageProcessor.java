package cn.xdf.learn.write.expression;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 中翻英
 * @author sunxingyang<br>
 */
public class SixPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {
		System.out.println("=============中翻英练习================");
//		List<String> all = page.getHtml().$("div.translate-wrap").$("div.right").all();
//		System.out.println(all);	
		
		String title = page.getHtml().$("div.g-kmf-header").xpath("div[1]").xpath("div[1]").xpath("//h2[1]/text()").toString();
		String category = title.substring(8, title.length()-9);
		System.out.println("类型："+category);
//		System.out.println("类型："+title);
		String part = title.substring(10+category.length(), title.length());
		System.out.println("Part："+part);
		
//		String answers = page.getHtml().$("div.translate-wrap").xpath("//div[2]/div[1]//p[2]/text()").toString();
//		System.out.println(answers);
		
		
		List<String> topic = page.getHtml().$("div.translate-wrap").$("div.right").xpath("//p[@class='topic']/text()").all();
//		List<String> topics = page.getHtml().$("div.translate-wrap").$("div.right").$("p.topic").all();
		for (int i = 1; i <= topic.size(); i++) {
			String answer = page.getHtml().$("div.translate-wrap").xpath("//div[2]/div["+i+"]//p[2]/text()").toString();
//			topic = topic.substring(17, topic.length()-4);
			System.out.println("题目："+topic.get(i-1));
			System.out.println("答案："+answer);
		}
//		topic = topic.substring(17, topic.length()-4);
//		System.out.println("题目："+topic);
//		List<String> answers = page.getHtml().$("div.translate-wrap").$("div.right").xpath("div[1]").xpath("//p[2]/text()").all();
////		List<String> answers = page.getHtml().$("div.translate-wrap").$("div.right").xpath("div[1]").xpath("p[2]").all();
//		for (String answer : answers) {
////			answer = answer.substring(27, answer.length()-4);
//			System.out.println("答案："+answer);
//		}
//		answer = answer.substring(27, answer.length()-4);
//		System.out.println("答案："+answer);
	

	}

	public Site getSite() {
		return site;
	}

//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		Spider.create(new SixPageProcessor())
//		.addUrl("http://toefl.kaomanfen.com/writing/resulttranslate?u=u1438246070517636&sheet_id=246&task_id=0").run();
//		
//		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
//				/ 1000 / 60 + "分");
//	}

}
