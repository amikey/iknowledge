package cn.xdf.learn.read.tpo.fentixing;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class FenThirdPageProcessor implements PageProcessor {
	
	private List<FenFourthPageProcessor> ffpList = new ArrayList<FenFourthPageProcessor>();
	private String neiRong;		//内容
	
	
	public String getNeiRong() {
		return neiRong;
	}

	public void setNeiRong(String neiRong) {
		this.neiRong = neiRong;
	}
	public List<FenFourthPageProcessor> getFfpList() {
		return ffpList;
	}

	public void setFfpList(List<FenFourthPageProcessor> ffpList) {
		this.ffpList = ffpList;
	}

	private Site site = Site.me().setSleepTime(1000).setRetryTimes(100)
			.setTimeOut(15000);
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		
		List<String> qNum = page.getHtml().$("div.qa-sn-list").xpath("ul[1]").xpath("li").all();
		String url = page.getHtml().xpath("script[4]").toString();
		for (int i = 1; i <= qNum.size(); i++) {
			FenFourthPageProcessor ffp = new FenFourthPageProcessor();
			String qid = page.getHtml().$("div.qa-sn-list").xpath("ul[1]").xpath("li["+i+"]").$("a","data-qid").toString();
			String urlAll = "http://toefl.kaomanfen.com/"+url.substring(url.indexOf("questionUrl:")+14,url.indexOf("mode=sheet_reading")+18)+"&id="+qid;
			Spider.create(ffp).addUrl(urlAll).run();
			ffpList.add(ffp);
		}
		
	}
}
