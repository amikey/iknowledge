package cn.xdf.learn.write.independent;

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
		System.out.println("==============独立写作题目==============");
		String title = page.getHtml().$("div.hd-title").xpath("//span[@class='title']/text()").toString();
		title = title.substring(9, title.length());
		System.out.println("标题："+title);
		String question = page.getHtml().$("div.matter-content").xpath("div[@class='content-subject js-translate-content']").xpath("p[1]").toString().replace("<p>", "").replace("</p>", "").replace("<br />", "").replace("Do you agree or disagree with the following statement?", "").replace("Use specific reasons and examples to support your opinion.", "").replace("Use specific reasons and examples to support your answer.", "").trim();
		System.out.println("题目内容    问题："+question);
		List<String> model = page.getHtml().$("div.content-pageList").xpath("//div[@class='pageList-data']/text()").all();
//		System.out.println(model);
		for (int i = 0; i < model.size(); i++) {
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
//		.addUrl("http://toefl.kaomanfen.com/question/kaomanfen/703bxk.html").run();
//		
//		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
//				/ 1000 / 60 + "分");
//
//	}

}
