package com.mpx.lotery;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.SerializationUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Zarasa {
	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");

		String[] s = new String[]{"QWERTY","ASDFGH","ZXCVBN"};
		Object[][] dataVector = new Object[][]{{"1",1f,"xx34",1},{"1",2f,"x2324",20} ,{"1",1f,"x2324",20}};
		
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < dataVector.length; j++) {
				Row row = sheet.createRow((short) j+i*3);
				Cell cell = row.createCell(0);
				if(j==0)
					cell.setCellValue(s[i]);
				row.createCell(1).setCellValue((Float)dataVector[j][1]);
				row.createCell(2).setCellValue((String)dataVector[j][2]);
				row.createCell(3).setCellValue((Integer)dataVector[j][3]);
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Christian\\Desktop\\data.xls");
		wb.write(fileOut);
		fileOut.close();
		
//		new FileOutputStream(file)
		}
		}
