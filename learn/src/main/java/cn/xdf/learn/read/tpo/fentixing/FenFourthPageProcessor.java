package cn.xdf.learn.read.tpo.fentixing;

import java.util.ArrayList;
import java.util.List;

import com.bingo.annotation.ExcelColumn;
import com.bingo.annotation.ExcelEntity;
import com.bingo.annotation.ExcelId;
import com.bingo.annotation.ExcelJoinColumn;
import com.bingo.annotation.ExcelOneToMany;
import com.bingo.annotation.ExcelSheet;
import com.bingo.annotation.ExcelTransient;

import cn.xdf.learn.read.tpo.zhenti.Item;
import cn.xdf.learn.util.HtmlUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
@ExcelEntity
@ExcelSheet(name = "问题表", model = "/cn/xdf/learn/read/tpo/fentixing/fenfourthPageProcessor.xml")
public class FenFourthPageProcessor implements PageProcessor {
	@ExcelId(auto = true)
	private Integer id;
	@ExcelColumn(name="问题")
	private String practices;	//问题
	@ExcelOneToMany
	@ExcelJoinColumn(name = "问题id")
	private List<Item> item;	//选项
	@ExcelColumn(name="答案")
	private String ans;			//答案
	@ExcelColumn(name="解析")
	private String ana;			//解析
	@ExcelColumn(name="标题")
	private String title;		//标题
	@ExcelColumn(name="内容")
	private String neiRong;		//内容
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNeiRong() {
		return neiRong;
	}

	public void setNeiRong(String neiRong) {
		this.neiRong = neiRong;
	}
	public String getPractices() {
		return practices;
	}

	public void setPractices(String practices) {
		this.practices = practices;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public String getAna() {
		return ana;
	}

	public void setAna(String ana) {
		this.ana = ana;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@ExcelTransient
	private Site site = Site.me().setSleepTime(1000).setRetryTimes(100)
			.setTimeOut(15000);
	public Site getSite() {
		return site;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	@Override
	public void process(Page page) {
		List<Item> items1 = new ArrayList<Item>();
		String practice = page.getHtml().$("div.i-practise-body").xpath("h2[1]").toString();
		List<String> select = page.getHtml().$("div.qa").xpath("ul[1]").xpath("li").all();
		String answer = page.getHtml().$("div.select-answer").xpath("span[2]").xpath("em[1]").toString();
		String analyze = page.getHtml().$("div.qa-analyze").xpath("div[2]").toString();
		this.setAna(HtmlUtil.getContent(analyze).trim());
		this.setPractices(HtmlUtil.getContent(practice));
		this.setAns(HtmlUtil.getContent(answer).trim());
		for (int i = 1; i <= select.size(); i++) {
			Item item1 = new Item();
			String item_code = page.getHtml().$("div.qa").xpath("ul[1]").xpath("li["+i+"]").xpath("span[1]").toString().trim();
			
			String item = HtmlUtil.getContent(page.getHtml().$("div.qa").xpath("ul[1]").xpath("li["+i+"]").toString());
			String ite = item.substring(item.lastIndexOf(">")+1);
			item1.setItme_code(HtmlUtil.getContent(item_code));
			item1.setName(ite);
			items1.add(item1);
		}
		this.setItem(items1);
		
	}
	public static void main(String[] args) {
		FenFourthPageProcessor ffpp = new FenFourthPageProcessor();
			Spider.create(ffpp)
			.addUrl("http://toefl.kaomanfen.com/reading/ajaxchildread?unique=u1438650631928841&sheet_id=451&mode=sheet_reading&id=3415").run();
			System.out.println(ffpp.getAna());
			System.out.println(ffpp.getAns());
			System.out.println(ffpp.getPractices());
			System.out.println(ffpp.getItem());
	}

}
