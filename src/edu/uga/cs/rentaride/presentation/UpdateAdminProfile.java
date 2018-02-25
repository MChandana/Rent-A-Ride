package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.AdministratorManager;
import edu.uga.cs.rentaride.persistence.impl.RentalLocationManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class UpdateAdminProfile
 */
@WebServlet("/UpdateAdminProfile")
public class UpdateAdminProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String address;
	private long adminID;
	private String newFirstName, newLastName, newUserName, newEmail, newAddress;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAdminProfile() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
  		super.init(config);
  		processor = new Part2(templateDir, getServletContext());
  		
  	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewPage(request,response);
	}
	
	protected void NewPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 	System.out.println("im here part 1");
		HttpSession httpSession;
		String ssid;
	    httpSession=request.getSession();
		ssid = (String) httpSession.getAttribute( "ssid" );
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder((Configuration.VERSION_2_3_23));
		SimpleHash root = new SimpleHash(db.build());
		Session session = null;
		session = SessionManager.getSessionById( ssid );
		LogicLayer logicLayer = null;
		logicLayer = session.getLogicLayer();
		List<Administrator> allAdmins=null;
        try {
            allAdmins=logicLayer.findAllAdmins();
            System.out.println("it created the allLocation");
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
        
        String fullname = "";
        for(Administrator admin: allAdmins){
            String adminEmail = admin.getEmail();
            String sessionEmail = session.getUser().getEmail();
        	System.out.println("this is the email: "+admin.getEmail());
            System.out.println("this is the session email: "+session.getUser().getEmail());
        	if(adminEmail.equals(sessionEmail)){
                fullname=admin.getFirstName()+" "+admin.getLastName();
                firstname = admin.getFirstName();
                lastname = admin.getLastName();
                username=admin.getUserName();
                email=admin.getEmail();
                address=admin.getAddress();
                System.out.println("this is the admin id: "+adminID);
                break;
            }
           }
        
        AdministratorManager am = new AdministratorManager();
		
		try {
			adminID = am.getAdminID(email);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        root.put("username", username);
        root.put("fullname", fullname);
        root.put("address", address);
        root.put("email", email);
       
		String templateName = "AdminProfile.ftl";
		processor.processTemplate(templateName, root, request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		newFirstName = request.getParameter("firstname");
		newLastName = request.getParameter("lastname");
		newUserName = request.getParameter("username");
		newEmail = request.getParameter("username");
		newAddress = request.getParameter("address");
		
		
		try {
			System.out.println("im going to new page 1");
			NewPage1(request,response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (RARException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void NewPage1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, RARException, ClassNotFoundException, SQLException {
		System.out.println("i'm in New Page 1");
		HttpSession httpSession;
		String ssid;
	    httpSession=request.getSession();
		ssid = (String) httpSession.getAttribute( "ssid" );
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder((Configuration.VERSION_2_3_23));
		SimpleHash root = new SimpleHash(db.build());
		Session session = null;
		session = SessionManager.getSessionById( ssid );
		LogicLayer logicLayer = null;
		logicLayer = session.getLogicLayer();
		AdministratorManager am = new AdministratorManager();
		System.out.println("this is the adminID: "+adminID);
		
		if(!newFirstName.equals("")) {
			am.updateFirstName(adminID, "firstName",newFirstName);
			firstname = newFirstName;
		}
		
		
		if(!newLastName.equals("")) {
			am.updateFirstName(adminID, "lastname",newLastName);
			lastname = newLastName;
		}
		
		if(!newUserName.equals("")) {
		am.updateFirstName(adminID,"username",newUserName);
		username = newUserName;
		}
		
		
		if(!newEmail.equals("")) {
			am.updateFirstName(adminID,"email",newEmail);
			email = newEmail;
		}
	
		
		if(!newAddress.equals("")) {
			System.out.println("this is the adminID again: "+adminID);
			am.updateFirstName(adminID, "address", newAddress);
			address = newAddress;
		}
		System.out.println(firstname);
		System.out.println(lastname);
		System.out.println(username);
		System.out.println(email);
		System.out.println(address);
		
		  root.put("username", username);
	      String fullname = firstname + " "+lastname;  
		  root.put("fullname", fullname);
	        root.put("address", address);
	        root.put("email", email);
	       
			String templateName = "AdminProfile.ftl";
			processor.processTemplate(templateName, root, request, response);
		
		}
	
	

}
