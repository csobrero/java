package com.mpx.birjan.util;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.mpx.birjan.bean.Wrapper;
import com.mpx.birjan.common.Status;

public class WorkbookHandler {

	public static Workbook build(Wrapper[] data) {
			Workbook wb = null;
			if(data!=null){
				wb = new HSSFWorkbook();

				Sheet summary = wb.createSheet("Resumen");
				Sheet details = wb.createSheet("Detalle");
				
				Map<String, Accumulator> map = new HashMap<String, Accumulator>();
				map.put(Status.VALID.name(), new Accumulator());
				map.put(Status.INVALID.name(), new Accumulator());
				map.put(Status.LOSER.name(), new Accumulator());
				map.put(Status.WINNER.name(), new Accumulator());
				map.put(Status.PAID.name(), new Accumulator());
				
				Row row ;
				
				row = details.createRow((short) 0);
				row.createCell(0).setCellValue("Ticket");
				row.createCell(1).setCellValue("Ubicacion");
				row.createCell(2).setCellValue("Numero");
				row.createCell(3).setCellValue("Importe");
				row.createCell(4).setCellValue("Estado");
				row.createCell(5).setCellValue("Premio");
				row.createCell(6).setCellValue("User");
				row.createCell(7).setCellValue("Creado");
				
				
				Object[][] vector;
				int rowIndex = 1;
				for (int i = 0; i < data.length; i++) {
					vector = data[i].getDataVector();
					String status = data[i].getStatus();
					Float prize = data[i].getPrize();
					row = details.createRow((short) rowIndex++);
					row.createCell(0).setCellValue(data[i].getHash());
					row.createCell(1).setCellValue((Integer)vector[0][0]);
					row.createCell(2).setCellValue((String)vector[0][1]);
					map.get(status).add((Float)vector[0][2], prize);
					row.createCell(3).setCellValue((Float)vector[0][2]);
					row.createCell(4).setCellValue(status);
					row.createCell(5).setCellValue((prize!=null)?prize.toString():"");
					row.createCell(6).setCellValue(data[i].getUsername());
					row.createCell(7).setCellValue(DateFormat.getDateTimeInstance(
				            DateFormat.SHORT, DateFormat.SHORT).format(data[i].getCreatedDate()));
					for (int j = 1; j < vector.length; j++) {// if more than 1 line.
						row = details.createRow((short) rowIndex++);
						row.createCell(0);
						row.createCell(1).setCellValue((Integer)vector[j][0]);
						row.createCell(2).setCellValue((String)vector[j][1]);
						map.get(status).addWithoutIncrementItem((Float)vector[j][2]);
						row.createCell(3).setCellValue((Float)vector[j][2]);
					}
					rowIndex++;
				}
				

				
				row = summary.createRow((short) 0);
				row.createCell(0).setCellValue("Estado");
				row.createCell(1).setCellValue("Cantidad");
				row.createCell(2).setCellValue("Recaudado");
				row.createCell(3).setCellValue("Premio");
				
				rowIndex = 1;
				for (Entry<String, Accumulator> entry : map.entrySet()) {
					if (entry.getValue().getQuantity() > 0) {
						row = summary.createRow((short) rowIndex++);
						row.createCell(0).setCellValue(entry.getKey());
						row.createCell(1).setCellValue(entry.getValue().getQuantity());
						row.createCell(2).setCellValue(String.format("%.2f", entry.getValue().getTotalAmount()));
						row.createCell(3).setCellValue(String.format("%.2f", entry.getValue().getTotalPrize()));
					}
				}
			}
			
			return wb;
		}
	
	protected static class Accumulator {
		private int quantity= 0;
		private float totalAmount = 0f;
		private float totalPrize = 0f;
		
		public void addWithoutIncrementItem(float amount){
			totalAmount+=amount;
		}
		
		public void add(float amount, Float prize){
			quantity++;
			totalAmount+=amount;
			if(prize!=null)
				totalPrize+=prize;
		}

		public int getQuantity() {
			return quantity;
		}

		public float getTotalAmount() {
			return totalAmount;
		}

		public float getTotalPrize() {
			return totalPrize;
		}
	}

}
