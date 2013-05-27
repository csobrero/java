package com.mpx.birjan.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.mpx.birjan.client.page.BalanceView;
import com.mpx.birjan.client.page.CheckCodeView;
import com.mpx.birjan.client.page.ControlView;
import com.mpx.birjan.client.page.DrawView;
import com.mpx.birjan.client.page.MainView;
import com.mpx.birjan.client.page.PasswordView;
import com.mpx.birjan.client.page.PrintView;
import com.mpx.birjan.client.page.TicketView;
import com.mpx.birjan.common.BalanceDTO;
import com.mpx.birjan.common.Ticket;
import com.mpx.birjan.common.Wrapper;
import com.mpx.birjan.service.BirjanWebService;
import com.mpx.birjan.util.WorkbookHandler;

@Controller
public class BirjanClient extends JApplet {
	
	private static final long serialVersionUID = -7121769101357144892L;

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
	private BalanceView balanceView;

	@Autowired
	private DrawView drawView;

	@Autowired
	private PasswordView passwordView;

	private String user;
	private String password;
	private String[] authorities;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final ApplicationContext context = new ClassPathXmlApplicationContext(
							CONFIG_PATH);
					context.getBean(BirjanClient.class).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	final ApplicationContext context = new ClassPathXmlApplicationContext(
							CONFIG_PATH);
					context.getBean(BirjanClient.class).start();
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't successfully complete");
        }
        
    }
	
	public void start() {
//		BindingProvider bindingProvider = (BindingProvider) webService;
//
//		Map<String, Object> reqCtx = bindingProvider.getRequestContext();
//
//		reqCtx.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, "true");
//		reqCtx.put(BindingProvider.USERNAME_PROPERTY, "xris");
//		reqCtx.put(BindingProvider.PASSWORD_PROPERTY, "xris");
		
//		CredentialsHolder.set(new CredentialsHolder.Crendentials("xris", "xris"));
//		this.user = "xris";
//		this.password = "xris";
		
//		Authenticator.setDefault(new Authenticator() {
//	        @Override
//	        protected PasswordAuthentication getPasswordAuthentication() {
//	            return new PasswordAuthentication("xris", "xris".toCharArray());
//	        }
//	    });
		
		setView(passwordView);
		passwordView.reset();
		
		
	}

	private void setView(JPanel panel) {
		Container container = mainView.getContentPane();
		container.add(panel, BorderLayout.CENTER);
		Component[] components = container.getComponents();
		for (int i = 1; i < components.length; i++) {
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
			TicketPrintable ticket = new TicketPrintable(lotteries, data, hash);

			printView.setTicket(ticket);
			setView(printView);
			System.out.println(hash);
		} else {
			throw new RuntimeException("WEBSERVICE ERROR");
		}

	}

	public Ticket pay(String hash) {
		Ticket jugada = webService.pay(hash);
		return jugada;
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
		if(menu.equals("Balance")){
			setView(balanceView);
			balanceView.reset();
		}
		if(menu.equals("Logout")){
			setView(passwordView);
			passwordView.reset();
		}
	}

	public Ticket retrieveByCode(String hash) {
		Ticket jugada = webService.retrieveByHash(hash);
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
	
	public BalanceDTO performBalance(boolean close) {
		String day = balanceView.getComboBox().getSelectedItem().toString().split(" ")[2];
		
		BalanceDTO balance = webService.performBalance(day, balanceView.getTextCode().getText(), close);
		
		return balance;
		
	}

	public void retriveGames() {
		String day = controlView.getComboBox().getSelectedItem().toString().split(" ")[2];
		String lottery = controlView.getComboBox_1().getSelectedItem().toString();
		String variant = controlView.getComboBox_2().getSelectedItem().toString();
		String state = controlView.states[controlView.getComboBox_3().getSelectedIndex()];
		
		Wrapper[] data = webService.retriveGames(lottery, variant, state, day);
		
		Workbook wb = WorkbookHandler.build(data);
		
		controlView.setWorkbook(wb);
		
	}

	public void login(String userName, String password) {
		this.user = userName;
		this.password = password;
		this.authorities = webService.getAuthorities();
		
		mainView.reset(authorities);
		
		boolean development = webService.isDevelopment();
		if(development){
			ticketView.setDevelopment(development);
			controlView.setDevelopment(development);
			drawView.setDevelopment(development);
		}

		setView(ticketView);
		ticketView.reset();
	}

	public final String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public Object[][] retrieveAvailability(String day) {
		return webService.retrieveAvailability(day);
	}
}
