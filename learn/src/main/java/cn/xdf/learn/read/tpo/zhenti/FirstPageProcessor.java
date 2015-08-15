package cn.xdf.learn.read.tpo.zhenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.xdf.learn.util.HtmlUtil;

import com.bingo.annotation.ExcelColumn;
import com.bingo.annotation.ExcelEntity;
import com.bingo.annotation.ExcelId;
import com.bingo.annotation.ExcelJoinColumn;
import com.bingo.annotation.ExcelOneToMany;
import com.bingo.annotation.ExcelSheet;
import com.bingo.annotation.ExcelTransient;
import com.bingo.io.DefaultExcelEntityWriter;
import com.bingo.io.EntityWriter;
import com.bingo.model.Excel;
import com.bingo.utils.FileUtil;

/**
 * @author sunxingyang<br>
 */
@ExcelEntity
@ExcelSheet(name = "类型表", model = "/cn/xdf/learn/read/tpo/zhenti/firstPageProcessor.xml")
public class FirstPageProcessor implements PageProcessor,Serializable {
	@ExcelId(auto = true)
	private Integer id;
	@ExcelColumn(name="类型")
	private String category;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@ExcelOneToMany
	@ExcelJoinColumn(name = "类型id")
	List<SecondPageProcessor> sppList = new ArrayList<SecondPageProcessor>();
	
	public List<SecondPageProcessor> getSppList() {
		return sppList;
	}

	public void setSppList(List<SecondPageProcessor> sppList) {
		this.sppList = sppList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	@ExcelTransient
	private Site site = Site.me().setSleepTime(500).setRetryTimes(100)
			.setTimeOut(15000);
	@ExcelTransient
	static int index = 1;

	public void process(Page page) {
		String maincol = "//div[@class='g-kmf-wrapper']/div[@class='i-toefl-listen']/div[@class='g-clearfix']\n"
				+ "/div[@class='kmf-maincol']";
		// 类别 例子：地理/环境/能源 (21)
		this.setCategory(page.getHtml().xpath(maincol+ "/div[@class='wrap-left-top new-list-left g-mod-shadow']/div[2]/ul[1]/li["
				+ index + "]/a[1]/text()").toString().trim());
		List<String> qn = page.getHtml().$("div.ltems-boxs").all();
		// 每种类型的数量
		/*int qNum = Integer.parseInt(category.substring(
				category.indexOf("(") + 1, category.indexOf(")")));*/
		int qNum = qn.size();
		// 阅读试题链接
//		StringBuffer readLinks = new StringBuffer();
		List<ImgInfo> iiList = new ArrayList<FirstPageProcessor.ImgInfo>(); 
		for (int i = 1; i <= qNum; i++) {
			ImgInfo imf = new ImgInfo();
			StringBuffer readLinks = new StringBuffer();
			readLinks
					.append(page
							.getHtml()
							.xpath(maincol
									+ "/div[@class='wrap-content-list']/div[@class='box-wrap-content']\n"
									+ "/div[@class='box-items clearfix']/div[@class='contents-box clearfix']/div["
									+ i
									+ "]\n"
									+ "/div[@class='boxs-scroll']/p[1]/a[1]/@href")
							.toString());
//			readLinks.append(",");
			String tpoNumT = page
					.getHtml()
					.xpath(maincol
							+ "/div[@class='wrap-content-list']/div[@class='box-wrap-content']\n"
							+ "/div[@class='box-items clearfix']/div[@class='contents-box clearfix']/div["
							+ i
							+ "]\n"
							+ "/div[@class='boxs-scroll']/div[@class='top-imgs']/div[1]/dl[1]/dt[1]")
					.toString();
			String tpoNum = HtmlUtil.getContent(tpoNumT).trim();
			String temp = maincol
					+ "/div[@class='wrap-content-list']/div[@class='box-wrap-content']\n"
					+ "/div[@class='box-items clearfix']/div[@class='contents-box clearfix']/div["
					+ i
					+ "]\n"
					+ "/div[@class='boxs-scroll']/div[@class='down-main']";
			imf.setCnName(HtmlUtil.getContent(page.getHtml().xpath(temp+"/h3[1]").toString()));
			imf.setEnName(HtmlUtil.getContent(page.getHtml().xpath(temp+"/h3[2]").toString()));
			imf.setReadLinks(readLinks.toString());
			imf.setTpoNum(tpoNum);
			iiList.add(imf);
		}
		for (ImgInfo imgInfo : iiList) {
			SecondPageProcessor spp = new SecondPageProcessor();
			Spider.create(spp).addUrl(imgInfo.getReadLinks()).run();
			spp.setTpoNum(imgInfo.getTpoNum());
			spp.setUrl(imgInfo.getReadLinks());
			spp.setCnName(imgInfo.getCnName());
			spp.setEnName(imgInfo.getEnName());
			sppList.add(spp);
		}
		this.setSppList(sppList);
	}

	public Site getSite() {
		return site;
	}
	public static void main(String[] args) {
		List<FirstPageProcessor> fppList = new ArrayList<FirstPageProcessor>();
		for (int i = 1; i <= 7; i++) {
			FirstPageProcessor fp = new FirstPageProcessor();
			Spider.create(fp).addUrl("http://toefl.kaomanfen.com/read/tpo?t=tpo&c=2&s="+ i).run();
			fppList.add(fp);
			index++;
		}
		
		

		EntityWriter writer = new DefaultExcelEntityWriter("E://test//TPO真题.xlsx");
		Map<Object, Object> params = new HashMap<Object, Object>();
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(FirstPageProcessor.class);
		classes.add(SecondPageProcessor.class);
		classes.add(ThirdPageProcessor.class);
		classes.add(Item.class);
		params.put(DefaultExcelEntityWriter.SHEET_WIDTH_HEADS, classes);
		params.put(DefaultExcelEntityWriter.SHEET_ORDER, classes);
		params.put(DefaultExcelEntityWriter.WRITE_EXCEL_OBJECT, true);
		writer.setParamters(params);
		writer.write(fppList, FirstPageProcessor.class);
		
	}
	
	class ImgInfo{
		String tpoNum;
		String readLinks;
		String enName;
		String cnName;
		public String getTpoNum() {
			return tpoNum;
		}
		public void setTpoNum(String tpoNum) {
			this.tpoNum = tpoNum;
		}
		public String getReadLinks() {
			return readLinks;
		}
		public void setReadLinks(String readLinks) {
			this.readLinks = readLinks;
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
		
	}
	
}

