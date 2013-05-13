package com.mpx.birjan.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.SerializationUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.mpx.birjan.bean.Wrapper;
import com.mpx.birjan.client.page.CheckCodeView;
import com.mpx.birjan.client.page.ControlView;
import com.mpx.birjan.client.page.DrawView;
import com.mpx.birjan.client.page.MainView;
import com.mpx.birjan.client.page.PrintView;
import com.mpx.birjan.client.page.TicketViewBkp;
import com.mpx.birjan.client.page.TicketView;
import com.mpx.birjan.common.Jugada;
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

	private String user;
	private String password;

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
//		BindingProvider bindingProvider = (BindingProvider) webService;
//
//		Map<String, Object> reqCtx = bindingProvider.getRequestContext();
//
//		reqCtx.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, "true");
//		reqCtx.put(BindingProvider.USERNAME_PROPERTY, "xris");
//		reqCtx.put(BindingProvider.PASSWORD_PROPERTY, "xris");
		
//		CredentialsHolder.set(new CredentialsHolder.Crendentials("xris", "xris"));
		this.user = "xris";
		this.password = "xris";
		
//		Authenticator.setDefault(new Authenticator() {
//	        @Override
//	        protected PasswordAuthentication getPasswordAuthentication() {
//	            return new PasswordAuthentication("xris", "xris".toCharArray());
//	        }
//	    });
		
		setView(ticketView);
		ticketView.reset();
		
		boolean development = webService.isDevelopment();
		if(development){
			ticketView.setDevelopment(development);
			controlView.setDevelopment(development);
			drawView.setDevelopment(development);
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
		String[] result = null;
		try {
			result = webService.populateCombo(view, comboName, day);

		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	public void play() {
		
		
		String day = ticketView.getComboBox().getSelectedItem().toString().split(" ")[2];
		String[] lotteries = ticketView.selectedLotteries().toArray(new String[]{});
		Object[][] data = ticketView.getData();

		
		String hash = webService.createGames(day, lotteries, data);

		if (hash != null) {
			Ticket ticket = new Ticket(lotteries, data, hash);

			printView.setTicket(ticket);
			setView(printView);
			System.out.println(hash);
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

	public Jugada retrieveByCode(String code) {
		Jugada jugada = webService.retrieveByHash(code);
		return jugada;
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

	public String getPassword() {
		return password;
	}

	public final String getUser() {
		return user;
	}

	public Object[][] retrieveAvailability(String day) {
		return webService.retrieveAvailability(day);
	}
}
