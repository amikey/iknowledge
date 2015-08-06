package cn.xdf.learn.read.og;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
public class ThirdPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {
		//试题部分
		String questionContent = "//div[@class='i-question-type-fullcol']/div[@class='question-body g-clearfix']/div[@class='question-col js-translate-content']";
		
		//题目
		String quesOption = page.getHtml()
				.xpath(questionContent+"/div[@class='inner']/h3[1]").toString();
		String quesInsert = page.getHtml()
				.xpath(questionContent+"/div[@class='inner']/p[@class='insert-desc']/text()").toString();
		String quesDrag = page.getHtml()
				.xpath(questionContent+"/div[@class='inner']/p[@class='que-article']/text()").toString();
		if(quesOption != null){
			quesOption = quesOption.substring(27).replace("</h3>", "");
			System.out.println("题目："+quesOption);
			//选项
			List<String> items = page.getHtml()
					.xpath(questionContent+"/div[@class='inner']/div[@class='question-form']/ul[1]/li[@class='normal']").all();
			System.out.println("选项：");
			for (int i = 1; i <= items.size(); i++) {
				String item = page.getHtml()
						.xpath(questionContent+"/div[@class='inner']/div[@class='question-form']/ul[1]/li["+i+"]/text()").toString();
				System.out.println(item);
			}
			
		}else if(quesInsert != null){
			System.out.println("题目："+quesInsert);
		}else if(quesDrag != null){
			System.out.println("题目："+quesDrag);
			List<String> items = page.getHtml()
					.xpath(questionContent+"/div[@class='inner']/p[@class='sort-answer']").all();
			System.out.println("选项：");
			for (int i = 2; i <= items.size()+1; i++) {
				String item = page.getHtml()
						.xpath(questionContent+"/div[@class='inner']/p["+i+"]/text()").toString();
				System.out.println(item);
			}
		}
		//正确答案
		String answer = page.getHtml()
				.xpath(questionContent+"/div[@class='inner']/div[@class='bottom-side']/div[1]/span[1]/b[1]/text()").toString().trim();
		System.out.println("正确答案："+answer);
		//题目解析
//		String timuAnalysis = page
//				.getHtml()
//				.xpath("//div[@class='g-kmf-wrapper']/div[@class='g-clearfix']/div[@class='kmf-maincol']/div[@class='s-analyse']/div[@class='i-toelf-subject-analyze']/div[@class='js-analysis-container']/div[1]/div[2]/text()").toString();
		
		String timuAnalysis = page.getHtml().$("div.analyze-box").xpath("div[2]").toString();
		timuAnalysis = timuAnalysis.substring(50, timuAnalysis.length()-6);
		System.out.println("题目解析："+timuAnalysis);
	}

	public Site getSite() {
		return site;

	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
	
		Spider.create(new ThirdPageProcessor())
		.addUrl("http://toefl.kaomanfen.com/question/kaomanfen/9437sk.html").run();
		
		
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

	}
}
