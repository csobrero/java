package com.mpx.birjan.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.SerializationUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.mpx.birjan.bean.Wrapper;
import com.mpx.birjan.client.page.ControlView;
import com.mpx.birjan.client.page.CheckCodeView;
import com.mpx.birjan.client.page.DrawView;
import com.mpx.birjan.client.page.MainView;
import com.mpx.birjan.client.page.PrintView;
import com.mpx.birjan.client.page.TicketView;
import com.mpx.birjan.service.impl.BirjanWebService;

@Controller
public class BirjanClient {

	private static final String CONFIG_PATH = "classpath*:applicationContextClient.xml";

	@Autowired
	private BirjanWebService webService;

	@Autowired
	private MainView mainView;

	@Autowired
	private TicketView ticketView;

	@Autowired
	private PrintView printView;
	
	@Autowired
	private CheckCodeView checkCodeView;
	
	@Autowired
	private ControlView controlView;

	@Autowired
	private DrawView drawView;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final ApplicationContext context = new ClassPathXmlApplicationContext(
							CONFIG_PATH);
					context.getBean(BirjanClient.class).init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void init() {
		setView(controlView);
		controlView.reset();
		
		boolean development = webService.isDevelopment();
		if(development){
			ticketView.setDevelopment(development);
			controlView.setDevelopment(development);
		}
	}

	private void setView(JPanel panel) {
		Container container = mainView.getContentPane();
		container.add(panel, BorderLayout.CENTER);
		Component[] components = container.getComponents();
		for (int i = 2; i < components.length; i++) {
			components[i].setVisible(false);
		}
		panel.setVisible(true);
	}

	public String[] populateCombo(String view, String comboName, String day) {
		return webService.populateCombo(view, comboName, day);
	}

	public void printHash() {
		
		@SuppressWarnings("unchecked")
		List<List<Object>> vector = ticketView.getTableModel()
				.getDataVector();
		
		Object[][] data = new Object[vector.size()-2][];
		for (int i = 0; i < vector.size()-2; i++) {
			data[i] = vector.get(i).toArray(new Object[]{});
		}

		
		String lottery = ticketView.getComboBox_1().getSelectedItem()
				.toString();
		String variant = ticketView.getComboBox_2().getSelectedItem()
				.toString();
		String day = ticketView.getComboBox().getSelectedItem().toString().split(" ")[2];
		
		String hash = webService.createGame(lottery, variant, day, data);

		if (hash != null) {
			Ticket ticket = new Ticket(lottery, variant, vector, hash);

			printView.setTicket(ticket);
			setView(printView);
		} else {
			throw new RuntimeException("WEBSERVICE ERROR");
		}

	}

	public void actionMenu(String menu) {
		if(menu.equals("Jugar")){
			setView(ticketView);
			ticketView.reset();
		}
		if(menu.equals("Check")){
			setView(checkCodeView);
		}
		if(menu.equals("Loteria")){
			setView(drawView);
			drawView.reset();
		}
		if(menu.equals("Control")){
			setView(controlView);
			controlView.reset();
		}
	}

	public Object[][] retrieveByCode(String code) {
		Object[][] dataVector = webService.retrieveByHash(code);
		return dataVector;
	}

	public void updateDraw(boolean viewOnly) {
		String[] data = new String[20];

		String lottery = drawView.getComboBox_1().getSelectedItem().toString();
		String variant = drawView.getComboBox_2().getSelectedItem().toString();
		String day = drawView.getComboBox().getSelectedItem().toString()
				.split(" ")[2];

		if (!viewOnly) {
			@SuppressWarnings("unchecked")
			List<List<String>> vector = drawView.getTableModel()
					.getDataVector();

			for (int i = 0; i < 10; i++) {
				data[i] = vector.get(i).get(1);
				data[i + 10] = vector.get(i).get(3);
			}

			webService.createDraw(lottery, variant, day, data);
		}
		
		data = webService.retrieveDraw(lottery, variant, day);

		drawView.updateModel(data);

	}

	public void validateDraw() {
		String lottery = drawView.getComboBox_1().getSelectedItem().toString();
		String variant = drawView.getComboBox_2().getSelectedItem().toString();
		String day = drawView.getComboBox().getSelectedItem().toString()
				.split(" ")[2];
		
		webService.validateDraw(lottery, variant, day);
	}

	public void retriveGames() {
		String lottery = controlView.getComboBox_1().getSelectedItem().toString();
		String variant = controlView.getComboBox_2().getSelectedItem().toString();
		String day = controlView.getComboBox().getSelectedItem().toString()
				.split(" ")[2];
		
		Wrapper[] data = webService.retriveGames(lottery, variant, day);
		
		if(data!=null){
			Workbook wb = new HSSFWorkbook();
//			CreationHelper createHelper = wb.getCreationHelper();
			Sheet sheet = wb.createSheet("new sheet");
			
			Row row ;
			Cell cell;
			
			row = sheet.createRow((short) 0);
			row.createCell(0).setCellValue("Verificador");
			row.createCell(1).setCellValue("Numero");
			row.createCell(2).setCellValue("Posicion");
			row.createCell(3).setCellValue("Apuesta");
			
			Object[][] vector;
			int rowIndex = 1;
			for (int i = 0; i < data.length; i++) {
				vector = (Object[][])SerializationUtils.deserialize(data[i].getDataVector());
				for (int j = 0; j < vector.length; j++) {
					row = sheet.createRow((short) rowIndex++);
					cell = row.createCell(0);
					if(j==0)
						cell.setCellValue(data[i].getHash());
					row.createCell(1).setCellValue((String)vector[j][2]);
					row.createCell(2).setCellValue((Integer)vector[j][3]);
					row.createCell(3).setCellValue((Float)vector[j][1]);
				}
				rowIndex++;
			}
			
			
			controlView.setWorkbook(wb);
		}
	}
}
