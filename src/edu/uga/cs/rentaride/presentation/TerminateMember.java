package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.RentalLocationManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet("/TerminateMember")
public class TerminateMember extends HttpServlet{
	
	public static final long serialVersionUID = 1L;
	private String terminateMember;
	private Part2 processor;
	private String templateDir = "/WEB-INF/Templates";
	
	
	public TerminateMember(){
		super();
	}
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		processor = new Part2(templateDir, getServletContext());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
			terminateMember(request, response);
		
	}
	
	protected void terminateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession httpSession;
		String ssid;
		httpSession = request.getSession();
		ssid = (String) httpSession.getAttribute("ssid");
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23);
		SimpleHash root = new SimpleHash(db.build());
		Session session = null;
		session = SessionManager.getSessionById(ssid);
		LogicLayer logicLayer = null;
		logicLayer = session.getLogicLayer();
		List<Customer> c = null;
		List<Customer> allCustomer = new ArrayList<>();
		try{
			c = logicLayer.findAllCustomers();
			UserStatus stl = UserStatus.ACTIVE;
			for(Customer cici : c) {
				UserStatus s = cici.getUserStatus();
				if(stl.equals(s)) {
					allCustomer.add(cici);
					System.out.println(cici.getFirstName()+" "+cici.getUserStatus());
				}
			}
			
		} catch(RARException e){
			System.out.println("Failed to retrieve all members");
		}
		
		root.put("allCustomer", allCustomer);
		String templateName = "terminateMember.ftl";
		processor.processTemplate(templateName, root, request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}

}
