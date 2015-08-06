package cn.xdf.learn.write.tpo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
public class ThiPageProcessor implements PageProcessor {

	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {
//		System.out.println("=============独立写作================");
//		String content = page.getHtml().$("div.content-read-data").xpath("p[1]").toString().replace("<p>", "").replace("</p>", "");
//		System.out.println("阅读材料："+content);
//		String model = page.getHtml().$("div.content-pageList").xpath("div[1]").toString();
//		model = model.substring(42, model.length()-6).replace("&quot;", "");
//		System.out.println("范文："+model);
		String question = page.getHtml().$("div.matter-content").xpath("div[@class='content-subject js-translate-content']").xpath("p[1]").toString().replace("<p>", "").replace("</p>", "").replace("<br />", "").replace("Do you agree or disagree with the following statement?", "").replace("Use specific reasons and examples to support your answer.", "").trim();
		System.out.println("问题："+question);
//		String[] qns = category.toString().split(",");
//		for (String string : qns) {
//			Spider.create(new ThirdPageProcessor())
//			.addUrl(string).run();
//		}
	}

	public Site getSite() {
		return site;

	}
//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		
//		Spider.create(new ThiPageProcessor())
//		.addUrl("http://toefl.kaomanfen.com/question/kaomanfen/942zwk.html").run();
//		
//		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
//				/ 1000 / 60 + "分");
//
//	}

}
