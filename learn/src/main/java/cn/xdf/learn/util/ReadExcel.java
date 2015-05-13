package cn.xdf.learn.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.xdf.learn.entity.Words;


public class ReadExcel {

	public List<Words> readXls(String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		@SuppressWarnings("resource")
		XSSFWorkbook   hssfWorkbook = new XSSFWorkbook (is);
		Words Words = null;
		List<Words> list = new ArrayList<Words>();
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			System.out.println(hssfSheet.getLastRowNum());
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					Words = new Words();
					XSSFCell word = hssfRow.getCell(0);
					XSSFCell meaning = hssfRow.getCell(1);
					XSSFCell root = hssfRow.getCell(2);
					XSSFCell rootMeaning = hssfRow.getCell(3);
					XSSFCell handoutPage = hssfRow.getCell(4);
					XSSFCell pronunciation = hssfRow.getCell(5);
					XSSFCell maleVoice = hssfRow.getCell(6);
					Words.setWord(getValue(word));
					Words.setMeaning(getValue(meaning));
					try {
						
						Words.setRoot(getValue(root));
					} catch (Exception e) {
						Words.setRoot("");
					}
					try {
						
						Words.setHandoutPage(getValue(handoutPage));
					} catch (Exception e) {
						Words.setHandoutPage("");
					}
					try {
						Words.setRootMeaning(getValue(rootMeaning));
					} catch (Exception e) {
						Words.setRootMeaning("");
					}
					try {
						
						Words.setPronunciation(getValue(pronunciation));
					} catch (Exception e) {
						Words.setPronunciation("");
					}
					try {
						Words.setMaleVoice(getValue(maleVoice));
						
					} catch (Exception e) {
						Words.setMaleVoice("");
					}
						
					
					list.add(Words);
				}
			}
		}
		System.out.println(list.get(1).getWord());
		return list;
	}
	
	 @SuppressWarnings("static-access")
	private String getValue(XSSFCell maleVoice) {
	        if (maleVoice.getCellType() == maleVoice.CELL_TYPE_BOOLEAN) {
	            // 返回布尔类型的值
	            return String.valueOf(maleVoice.getBooleanCellValue());
	        } else if (maleVoice.getCellType() == maleVoice.CELL_TYPE_NUMERIC) {
	            // 返回数值类型的值
	            return String.valueOf(maleVoice.getNumericCellValue());
	        } else {
	            // 返回字符串类型的值
	            return String.valueOf(maleVoice.getStringCellValue());
	        }
	    }
}