package cn.xdf.learn.read.tpo.zhenti;

import java.util.ArrayList;
import java.util.List;

import cn.xdf.learn.util.HtmlUtil;

import com.bingo.annotation.ExcelColumn;
import com.bingo.annotation.ExcelEntity;
import com.bingo.annotation.ExcelId;
import com.bingo.annotation.ExcelJoinColumn;
import com.bingo.annotation.ExcelOneToMany;
import com.bingo.annotation.ExcelSheet;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author sunxingyang<br>
 */
@ExcelEntity
@ExcelSheet(name = "问题表", model = "/cn/xdf/learn/read/tpo/zhenti/thirdPageProcessor.xml")
public class ThirdPageProcessor implements PageProcessor {
	@ExcelId(auto = true)
	private Integer id;
	@ExcelColumn(name="问题")
	private String quesOption;
	@ExcelOneToMany
	@ExcelJoinColumn(name = "问题id")
	private List<Item> item;
	@ExcelColumn(name="答案")
	private String answer;
	@ExcelColumn(name="解析")
	private String timuAnalysis;
	@ExcelColumn(name="内容")
	private String content;
	@ExcelColumn(name="注释")
	private String remarks;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTimuAnalysis() {
		return timuAnalysis;
	}

	public void setTimuAnalysis(String timuAnalysis) {
		this.timuAnalysis = timuAnalysis;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public String getQuesOption() {
		return quesOption;
	}

	public void setQuesOption(String quesOption) {
		this.quesOption = quesOption;
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
 

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	public void process(Page page) {
		List<Item> items1 = new ArrayList<Item>();
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
					this.setQuesOption(quesOption.substring(27).replace("</h3>", "").trim());
					System.out.println("题目："+quesOption);
					//选项
					List<String> items = page.getHtml()
							.xpath(questionContent+"/div[@class='inner']/div[@class='question-form']/ul[1]/li[@class='normal']").all();
					for (int i = 1; i <= items.size(); i++) {
						Item item1 = new Item();
						String item_code = page.getHtml()
								.xpath(questionContent+"/div[@class='inner']/div[@class='question-form']/ul[1]/li["+i+"]/div[1]/text()").toString().trim();
						String item = page.getHtml()
								.xpath(questionContent+"/div[@class='inner']/div[@class='question-form']/ul[1]/li["+i+"]/text()").toString();
						item1.setItme_code(item_code);
						item1.setName(item.trim());
						items1.add(item1);
					}
					
				}else if(quesInsert != null){
					Item itemA = new Item();
					itemA.setItme_code("A");
					Item itemB = new Item();
					itemB.setItme_code("B");
					Item itemC = new Item();
					itemC.setItme_code("C");
					Item itemD = new Item();
					itemD.setItme_code("D");
					items1.add(itemA);
					items1.add(itemB);
					items1.add(itemC);
					items1.add(itemD);
					this.setQuesOption(quesInsert);
				}else if(quesDrag != null){
					this.setQuesOption(quesDrag);
					List<String> items = page.getHtml()
							.xpath(questionContent+"/div[@class='inner']/p[@class='sort-answer']").all();
					for (int i = 2; i <= items.size()+1; i++) {
						Item item1 = new Item();
						String item_code = page.getHtml()
								.xpath(questionContent+"/div[@class='inner']/p["+i+"]/span[1]/text()").toString().trim();
						String item = page.getHtml()
								.xpath(questionContent+"/div[@class='inner']/p["+i+"]/text()").toString();
						
						item1.setItme_code(item_code);
						item1.setName(item.trim());
						items1.add(item1);
					}
				}
				this.setItem(items1);
				//内容
				String content = HtmlUtil.getContent(page.getHtml().$("div#js-article").xpath("p[1]").toString().trim());
				this.setContent(content);
				//注释
				String[] s = content.split("♦");
				if(s.length==2){
					String [] ss = content.substring(0,content.indexOf("strong-star js-scrollto")).split("<br /><br />");
					System.out.println("Paragraph "+ss.length+" is marked with ♦");
					this.setRemarks("Paragraph "+ss.length+" is marked with ♦");
				}else if(s.length>2){
					String [] ss = content.substring(0,content.indexOf("strong-star js-scrollto")).split("<br /><br />");
					System.out.println("Paragraph "+ss.length+",Paragraph "+(ss.length+1)+" is marked with ♦");
					this.setRemarks("Paragraph "+ss.length+",Paragraph "+(ss.length+1)+" is marked with ♦");
				}
				//正确答案
				String answer = page.getHtml()
						.xpath(questionContent+"/div[@class='inner']/div[@class='bottom-side']/div[1]/span[1]/b[1]/text()").toString().trim();
				this.setAnswer(answer);
				//题目解析
//				String timuAnalysis = page
//						.getHtml()
//						.xpath("//div[@class='g-kmf-wrapper']/div[@class='g-clearfix']/div[@class='kmf-maincol']/div[@class='s-analyse']/div[@class='i-toelf-subject-analyze']/div[@class='js-analysis-container']/div[1]/div[2]/text()").toString();
				
				String timuAnalysis = page.getHtml().$("div.analyze-box").xpath("div[2]").toString();
				timuAnalysis = timuAnalysis.substring(50, timuAnalysis.length()-6);
				this.setTimuAnalysis(timuAnalysis.trim());
	}

	public Site getSite() {
		return site;
	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
			Spider.create(new ThirdPageProcessor())
			.addUrl("http://toefl.kaomanfen.com/question/0a455bbf20a36b328906804948f16769/891osk.html").run();
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

	}
}
