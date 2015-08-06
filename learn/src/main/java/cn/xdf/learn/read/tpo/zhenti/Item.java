package cn.xdf.learn.read.tpo.zhenti;

import com.bingo.annotation.ExcelColumn;
import com.bingo.annotation.ExcelEntity;
import com.bingo.annotation.ExcelId;
import com.bingo.annotation.ExcelSheet;

@ExcelEntity
@ExcelSheet(name = "选项表", model = "/cn/xdf/learn/read/tpo/zhenti/item.xml")
public class Item {
	@ExcelId(auto = true)
	private Integer id;
	@ExcelColumn(name="选项标识")
	private String itme_code;
	@ExcelColumn(name="选项")
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItme_code() {
		return itme_code;
	}
	public void setItme_code(String itme_code) {
		this.itme_code = itme_code;
	}
	
}
