package cn.xdf.learn.write.expression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import us.codecraft.webmagic.Spider;

/**
 * 从excel读取链接
 * @author sunxingyang<br>
 */
public class SevenPageProcessor {

	public static List<String> translateExcelData(String file){
		List<String> resultList = new ArrayList<String>();
		FileInputStream fi = null ;		
		try {
			fi = new FileInputStream(file);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		Workbook wb = null ;		
		try {
			wb = new HSSFWorkbook(fi); // 创建一个excel文件
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Sheet sheet = wb.getSheetAt(0);// 第一张工作表
		int rowNum = sheet.getLastRowNum() + 1;// 第一张工作表包含的数据行数

		for(int i=0;i<rowNum;i++){ // 从第一行开始
			Row row = sheet.getRow(i); // 第i行的数据
			int cellNum = 1;
			String strs = new String();
			for(int j = 0;j<cellNum;j++){
				Cell cell = row.getCell(j);
				Object value = getCellObjectValue(cell);
				if(value != null){
					strs = value.toString().trim();
				} else {
					strs = null;
				}
				System.out.println(strs);
				
				Spider.create(new SixPageProcessor()).addUrl(strs).run();
				
			}
			resultList.add(strs);
			
		}
		
		return resultList;
	}
	
	/**
	 * 区分不同的Cell类型解析出Cell Value。
	 * 
	 * @param cell
	 * @return
	 */
	private static Object getCellObjectValue(Cell cell) {
		Object value = null;
		if (cell == null) {
			return value;
		}
		switch (cell.getCellType()) {
		case 0: // 数据格式
			if(HSSFDateUtil.isCellDateFormatted(cell)){//判断是日期类型
				Date dt = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()); //获取成DATE类型
				value = dt;
			} else {
				value = cell.getNumericCellValue();
			}			
			break;
		case 1: // 字符串格式
			value = cell.getStringCellValue();
			break;
		case 2: // 公式
			value = cell.getCellFormula();
			break;
		case 3: // 空值不处理
			break;
		default: // 不处理
			break;
		}
		return value;
	}
	
	public static void main(String[] args) {
		translateExcelData("F:\\1.xls");
	}
}
