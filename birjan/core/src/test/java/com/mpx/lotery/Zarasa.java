package com.mpx.lotery;

import java.io.FileOutputStream;
import java.io.IOException;

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
		Object[][] dataVector = new Object[][]{{"1",1f,"xx34",1},{"1",1f,"x2324",20}};
		
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < dataVector.length; j++) {
				Row row = sheet.createRow((short) j);
				Cell cell = row.createCell(0);
				if(j==0)
					cell.setCellValue(s[i]);
				
			}
		}
			
		
		
		
		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow((short) 0);
		// Create a cell and put a value in it.
		Cell cell = row.createCell(0);
		cell.setCellValue(1);

		// Or do it on one line.
		row.createCell(1).setCellValue(1.2);
		row.createCell(2).setCellValue(
		createHelper.createRichTextString("This is a string"));
		row.createCell(3).setCellValue(true);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("C:\\data.xls");
		wb.write(fileOut);
		fileOut.close();
		}
		}
