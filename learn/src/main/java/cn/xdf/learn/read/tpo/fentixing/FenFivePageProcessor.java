package cn.xdf.learn.read.tpo.fentixing;

import java.util.ArrayList;
import java.util.List;

import com.bingo.annotation.ExcelColumn;

import cn.xdf.learn.read.tpo.zhenti.Item;
import cn.xdf.learn.util.HtmlUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class FenFivePageProcessor implements PageProcessor{
	private String title;
	private String neiRong;
	
	private Site site = Site.me().setSleepTime(1000).setRetryTimes(100)
			.setTimeOut(15000);
	public Site getSite() {
		return site;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNeiRong() {
		return neiRong;
	}

	public void setNeiRong(String neiRong) {
		this.neiRong = neiRong;
	}

	public void process(Page page) {
		String title = page.getHtml().xpath("h2[1]").toString();
		String neirong = page.getHtml().xpath("p[1]").toString();
		this.setNeiRong(HtmlUtil.getContent(neirong));
		this.setTitle(HtmlUtil.getContent(title));
		
	}
	public static void main(String[] args) {
		Spider.create(new FenFivePageProcessor())
		.addUrl("http://toefl.kaomanfen.com/reading/ajaxparentread?id=2264").run();
	}
}
