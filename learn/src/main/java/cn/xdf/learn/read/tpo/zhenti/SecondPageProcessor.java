package cn.xdf.learn.read.tpo.zhenti;

import java.util.ArrayList;
import java.util.List;

import com.bingo.annotation.ExcelColumn;
import com.bingo.annotation.ExcelEntity;
import com.bingo.annotation.ExcelId;
import com.bingo.annotation.ExcelJoinColumn;
import com.bingo.annotation.ExcelOneToMany;
import com.bingo.annotation.ExcelSheet;
import com.bingo.annotation.ExcelTransient;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
@ExcelEntity
@ExcelSheet(name = "文章表", model = "/cn/xdf/learn/read/tpo/zhenti/secondPageProcessor.xml")
public class SecondPageProcessor implements PageProcessor {
	@ExcelId(auto = true)
	private Integer id;
	@ExcelColumn(name ="阅读原文")
	private String contentEn;
	@ExcelColumn(name ="中文翻译")
	private String contentCn;
	@ExcelColumn(name ="文章地址")
	private String url;
	@ExcelColumn(name ="TPO编号")
	private String tpoNum;
	@ExcelColumn(name ="英文题目")
	private String enName;
	@ExcelColumn(name ="中文题目")
	private String cnName;
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContentEn() {
		return contentEn;
	}

	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}

	public String getContentCn() {
		return contentCn;
	}

	public void setContentCn(String contentCn) {
		this.contentCn = contentCn;
	}
	@ExcelOneToMany
	@ExcelJoinColumn(name = "文章id")
	List<ThirdPageProcessor> tppList = new ArrayList<ThirdPageProcessor>();
	
	public List<ThirdPageProcessor> getTppList() {
		return tppList;
	}

	public void setTppList(List<ThirdPageProcessor> tppList) {
		this.tppList = tppList;
	}
	
	public String getTpoNum() {
		return tpoNum;
	}

	public void setTpoNum(String tpoNum) {
		this.tpoNum = tpoNum;
	}
	
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	@ExcelTransient
	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {
		String content = "//div[@class='i-question-type-fullcol']/div[@class='question-body g-clearfix']/div[@class='stuff-col']\n" + 
				"/div[@class='stuff-content']";
		
		
		//阅读译文
		String contentCn = page
				.getHtml()
				.xpath(content+"/div[2]/p[1]").toString().substring(19).replace("</p>", "");
		this.setContentCn(contentCn.trim());
		//试题部分
		String questionContent = "//div[@class='i-question-type-fullcol']/div[@class='question-body g-clearfix']/div[@class='question-col js-translate-content']";
		List<String> quesNum = page.getHtml()
				.xpath(questionContent+"/div[@class='tab-question']/div[@class='list js-container']/div[1]/a").all();
		//题目编号
		StringBuffer questionNums = new StringBuffer();
		for (int i = 1; i <= quesNum.size(); i++) {
			questionNums.append(page
				.getHtml()
				.xpath(questionContent+"/div[@class='tab-question']/div[@class='list js-container']/div[1]/a["+i+"]/@href").toString());
			questionNums.append(",");
		}
		String[] qns = questionNums.toString().split(",");
		for (String string : qns) {
			ThirdPageProcessor tpp = new ThirdPageProcessor();
			Spider.create(tpp).addUrl(string).run();
			tppList.add(tpp);
		}
		//阅读英文
			String contentEn = tppList.get(tppList.size()-1).getContent();
			this.setContentEn(contentEn.trim());
	}

	public Site getSite() {
		return site;

	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
			Spider.create(new SecondPageProcessor())
			.addUrl("http://toefl.kaomanfen.com/question/9aa1535f2fe724592be55ab57e68677f/661nqk.html").run();
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

	}

}
