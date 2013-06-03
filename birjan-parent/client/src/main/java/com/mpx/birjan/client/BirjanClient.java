package com.mpx.birjan.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.mpx.birjan.Exception.BusinessException;
import com.mpx.birjan.client.page.BalanceView;
import com.mpx.birjan.client.page.CierreView;
import com.mpx.birjan.client.page.ControlView;
import com.mpx.birjan.client.page.ExceptionView;
import com.mpx.birjan.client.page.JugadaView;
import com.mpx.birjan.client.page.MainView;
import com.mpx.birjan.client.page.PagoView;
import com.mpx.birjan.client.page.PasswordView;
import com.mpx.birjan.client.page.PremiosView;
import com.mpx.birjan.client.page.PrintView;
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
	private JugadaView jugadaView;

	@Autowired
	private PrintView printView;
	
	@Autowired
	private PagoView pagoView;
	
	@Autowired
	private ControlView controlView;
	
	@Autowired
	private CierreView cierreView;

	@Autowired
	private PremiosView premiosView;

	@Autowired
	private BalanceView balanceView;

	@Autowired
	private PasswordView passwordView;
	
	@Autowired
	private ExceptionView exceptionView;

	private String user;
	private String password;
	private String[] authorities;
	private DateTime serverDateTime;

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
	
	public void play(String day, String[] lotteries, Object[][] data) {

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
		if(menu.equals("Jugada")){
			setView(jugadaView);
			jugadaView.reset();
		}
		if(menu.equals("Pago")){
			setView(pagoView);
			pagoView.reset();
		}
		if(menu.equals("Premios")){
			setView(premiosView);
			premiosView.reset();
		}
		if(menu.equals("Control")){
			setView(controlView);
			controlView.reset();
		}
		if(menu.equals("Cierre")){
			setView(cierreView);
			cierreView.reset();
		}
		if(menu.equals("Balance")){
			setView(balanceView);
			balanceView.reset();
		}
		if (menu.equals("Logout") || menu.equals("ChangeTime")) {
			password = null;
			authorities = null;
			if (menu.equals("Logout")) {
				serverDateTime = null;
			}
			setView(passwordView);
			passwordView.reset();
		}
	}

	public Ticket retrieveByCode(String hash) {
		Ticket jugada = webService.retrieveByHash(hash);
		return jugada;
	}

	public void updateDraw(String lottery, String variant, String day, boolean viewOnly) {
		String[] data = new String[20];

		if (!viewOnly) {
			@SuppressWarnings("unchecked")
			List<List<String>> vector = premiosView.getTableModel().getDataVector();

			for (int i = 0; i < 10; i++) {
				data[i] = vector.get(i).get(1);
				data[i + 10] = vector.get(i).get(3);
			}

			webService.createDraw(lottery, variant, day, data);
		}

		data = webService.retrieveDraw(lottery, variant, day);

		premiosView.updateModel(data);

	}

	public void validateDraw(String lottery, String variant, String day) {
		webService.validateDraw(lottery, variant, day);
	}
	
	public BalanceDTO performBalance(boolean close) {
		String day = cierreView.getDay();		
		String userName = cierreView.getSelectedUser();
		BalanceDTO balance = webService.performBalance(day, userName, close);	
		return balance;	
	}

	public void retriveGames(String lottery, String variant, String day, String state) {
		
		Wrapper[] data = webService.retriveGames(lottery, variant, state, day);
		
		Workbook wb = WorkbookHandler.build(data);
		
		controlView.setWorkbook(wb);
		
	}

	public void login(String userName, String password, DateTime date) {
		this.user = userName;
		this.password = password;
		try {
			this.authorities = webService.getAuthorities();
			
//			boolean development = webService.isDevelopment();
			this.serverDateTime = new DateTime(webService.updateServerDateTime(date==null?null:date.toDate()));
			DateTimeUtils.setCurrentMillisOffset(serverDateTime.getMillis()-new Date().getTime());
			mainView.reset();
			
			if(isManager()){
				setView(balanceView);
				balanceView.reset();
			} else {
				setView(jugadaView);
				jugadaView.reset();
			}
			
		} catch (BusinessException e) {
			setView(exceptionView);
			exceptionView.reset(e);
		}
		
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

	public BalanceDTO[] balance(boolean close) {
		return webService.closeBalance(balanceView.getDay(), close);
	
	}

	
	public boolean isAdmin() {
		return Arrays.asList(authorities).contains("ROLE_ADMIN");
	}
	
	public boolean isManager() {
		return Arrays.asList(authorities).contains("ROLE_MANAGER");
	}
	
	public boolean isUser() {
		return Arrays.asList(authorities).contains("ROLE_USER");
	}

	public String[] getAllUser() {
		return webService.getUsers();
	}

	public void closeUser() {
		mainView.closeUser();
		
	}

	public DateTime getServerDateTime() {
		return serverDateTime;
	}
}
