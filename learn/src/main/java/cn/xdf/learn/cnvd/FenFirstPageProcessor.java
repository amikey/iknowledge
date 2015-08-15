package cn.xdf.learn.cnvd;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import cn.xdf.learn.util.HtmlUtil;
import cn.xdf.learn.util.StreamTool;

import com.bingo.annotation.ExcelColumn;
import com.bingo.annotation.ExcelEntity;
import com.bingo.annotation.ExcelId;
import com.bingo.annotation.ExcelJoinColumn;
import com.bingo.annotation.ExcelOneToMany;
import com.bingo.annotation.ExcelSheet;
import com.bingo.annotation.ExcelTransient;
import com.bingo.io.DefaultExcelEntityWriter;
import com.bingo.io.EntityWriter;

/**
 * @author sunxingyang<br>
 */
public class FenFirstPageProcessor implements PageProcessor {
	public void process(Page page) {
//		List<String> titles = page.getHtml().$("li","data-title").regex("TPO.*").all();
//		List<String> urls = page.getHtml().$("li","data-url").regex("/reading/.*").all();
		System.out.println(11);
		String ss= page.getHtml().toString();
		System.out.println(ss);
		
	}
	private Site site = Site.me().setSleepTime(1000).setRetryTimes(100)
			.setTimeOut(150000);
	public Site getSite() {
		return site;

	}

	public static void main(String[] args) throws Exception {
		FenFirstPageProcessor ffp1 = new FenFirstPageProcessor();
		long startTime = System.currentTimeMillis();
			/*Spider.create(new FenFirstPageProcessor()).thread(5).addUrl("http://www.cnvd.org.cn/").run();*/
		ffp1.getAll(null, null);
		System.out.println("爬取用时：" + (System.currentTimeMillis() - startTime)
				/ 1000 / 60 + "分");

		
		
		/*EntityWriter writer = new DefaultExcelEntityWriter("E://test//TPO分题型.xlsx");
		Map<Object, Object> params = new HashMap<Object, Object>();
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(FenFirstPageProcessor.class);
		classes.add(FenSecondPageProcessor.class);
		classes.add(FenFourthPageProcessor.class);
		classes.add(Item.class);
		params.put(DefaultExcelEntityWriter.SHEET_WIDTH_HEADS, classes);
		params.put(DefaultExcelEntityWriter.SHEET_ORDER, classes);
		params.put(DefaultExcelEntityWriter.WRITE_EXCEL_OBJECT, true);
		writer.setParamters(params);
		writer.write(ffpList, FenFirstPageProcessor.class);*/
	}
public String getAll(String action, String form) throws Exception {
		
		String ac = "";
		URL url = new URL("http://www.cnvd.org.cn/flaw/typelist?typeId=29");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");// 提交模式
		conn.setDoOutput(true);// 是否输入参数
		conn.setReadTimeout(1000 * 60 * 10); //设置读取超时
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); //模拟ie浏览器
		conn.setRequestProperty("Accept-Language", "zh-cn");
		conn.setRequestProperty("Connection", "close"); //不进行持久化连接
		StringBuffer params = new StringBuffer();
		params.append(form);
		byte[] bypes = params.toString().getBytes();
		conn.getOutputStream().write(bypes);// 输入参数
		InputStream inStream = conn.getInputStream();
		String html = new String(StreamTool.readInputStream(inStream),"utf-8");
		List<String> liebiao = Html.create(html).$("div.white").xpath("ul[1]").xpath("li[3]").xpath("ul[1]").xpath("li[1]").xpath("ul[1]").xpath("li").all();
		/*for (String string : liebiao) {
			System.out.println(HtmlUtil.getContent(string));
		}*/
		String ss= Html.create(html).xpath("//div[@class='mw Main clearfix']").toString();
		System.out.println(ss);
		/*ac = Html.create(new String(StreamTool.readInputStream(inStream),"utf-8")).$("form", "action").toString();
		if (ac.endsWith(",0.html")) {
			return "http://toefl.kaomanfen.com/" + ac;
		} else {
			return getAll(ac, form);
		}*/
		return "";
	}
}
