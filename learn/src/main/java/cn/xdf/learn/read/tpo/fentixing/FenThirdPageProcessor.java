package cn.xdf.learn.read.tpo.fentixing;

import java.util.ArrayList;
import java.util.List;

import com.bingo.annotation.ExcelJoinColumn;
import com.bingo.annotation.ExcelOneToMany;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class FenThirdPageProcessor implements PageProcessor {
	@ExcelOneToMany
	@ExcelJoinColumn(name = "文章id")
	private List<FenFourthPageProcessor> ffpList = new ArrayList<FenFourthPageProcessor>();
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
			FenFivePageProcessor ffivep = new FenFivePageProcessor();
			String qid = page.getHtml().$("div.qa-sn-list").xpath("ul[1]").xpath("li["+i+"]").$("a","data-qid").toString();
			String urlAll = "http://toefl.kaomanfen.com/"+url.substring(url.indexOf("questionUrl:")+14,url.indexOf("mode=sheet_reading")+18)+"&id="+qid;
			String fiveUrl = "http://toefl.kaomanfen.com/reading/ajaxparentread?id="+qid;
			Spider.create(ffp).addUrl(urlAll).run();
			Spider.create(ffivep).addUrl(fiveUrl).run();
			ffp.setNeiRong(ffivep.getNeiRong().trim());
			ffp.setTitle(ffivep.getTitle());
			ffpList.add(ffp);
		}
		
	}
}
