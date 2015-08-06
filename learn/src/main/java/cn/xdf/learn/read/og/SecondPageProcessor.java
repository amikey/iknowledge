package cn.xdf.learn.read.og;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
public class SecondPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {
		String content = "//div[@class='i-question-type-fullcol']/div[@class='question-body g-clearfix']/div[@class='stuff-col']\n" + 
				"/div[@class='stuff-content']";
		//阅读英文
		String contentEn = page.getHtml()
				.xpath(content+"/div[@class='inner js-article']/p[1]").toString().substring(40).replace("</p>", "");
		System.out.println("文章："+contentEn);
		//阅读译文
		String contentCn = page.getHtml()
				.xpath(content+"/div[2]/p[1]").toString().substring(19).replace("</p>", "");
		System.out.println("文章译文："+contentCn);
		//试题部分
		String questionContent = "//div[@class='i-question-type-fullcol']/div[@class='question-body g-clearfix']/div[@class='question-col js-translate-content']";
		//题目编号
		List<String> quesNum = page.getHtml()
		.xpath(questionContent+"/div[@class='tab-question']/div[@class='list js-container']/div[1]/a").all();
		StringBuffer questionNums = new StringBuffer();
		for (int i = 1; i <= quesNum.size(); i++) {
			questionNums.append(page.getHtml()
				.xpath(questionContent+"/div[@class='tab-question']/div[@class='list js-container']/div[1]/a["+i+"]/@href").toString());
			questionNums.append(",");
		}
		String[] qns = questionNums.toString().split(",");
		for (String string : qns) {
//			System.out.println(string);
			Spider.create(new ThirdPageProcessor())
			.addUrl(string).run();
		}
	}

	public Site getSite() {
		return site;

	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
	
		Spider.create(new SecondPageProcessor())
		.addUrl("http://toefl.kaomanfen.com/question/kaomanfen/cf368k.html").run();
		
		
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

	}

}
