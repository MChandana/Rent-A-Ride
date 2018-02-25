package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.CustomerManager;
import edu.uga.cs.rentaride.persistence.impl.DBTester;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.rentaride.persistence.impl.RentARideParamsManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class CustomerRegister
 */
@WebServlet("/CustomerRegister")
public class CustomerRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String firstName,lastName,username,password,email,address,licState,state,licNumber,creditCardNumber;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerRegister() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
  		super.init(config);
  		processor = new Part2(templateDir, getServletContext());
  		
  	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder((Configuration.VERSION_2_3_23));
		SimpleHash root = new SimpleHash(db.build());
		firstName = request.getParameter("firstName");
		state = request.getParameter("licState");
		lastName = request.getParameter("lastName");
		username = request.getParameter("username");
		password = request.getParameter("password");
		email = request.getParameter("email");
		address = request.getParameter("address");
		Calendar calendar = Calendar.getInstance();
	    java.sql.Date today = new java.sql.Date(calendar.getTime().getTime());
		licNumber = request.getParameter("licState");
		creditCardNumber = request.getParameter("creditCardNumber");
		
		CustomerManager cm = new CustomerManager();
		
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(username);
		System.out.println(password);
		System.out.println(email);
		System.out.println(address);
		System.out.println(licNumber);
		System.out.println(creditCardNumber);
		System.out.println(state);
		
		RentARideParamsManager rpm = new RentARideParamsManager();
		
		try {
			cm.addCustomer(firstName, lastName, username, password, email, address, today, today, state, licNumber, creditCardNumber, today);
			int membershipPrice = rpm.getMembership();
			root.put("membershipPrice",membershipPrice);
			String templateName = "newcustomer.ftl";
			processor.processTemplate(templateName, root, request, response);
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
