package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.CommentManager;
import edu.uga.cs.rentaride.persistence.impl.RentalManager;
import edu.uga.cs.rentaride.persistence.impl.VehicleManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet("/comment")
public class Comment extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Customer current = null;
	


	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String status, rentalLocation, vehicleType, carCondition, millege, 
	lastService,month, day, Syear, vehicleID,tag; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			NewPage(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    protected void NewPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
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
		
		 httpSession = request.getSession();
	        if( httpSession == null ) {       // assume not logged in!                                    
	            System.out.println("Session expired or illegal; please log in");
	            return;
	        }

	        ssid = (String) httpSession.getAttribute( "ssid" );
	        if( ssid == null ) {       // not logged in!                                                  
	        	System.out.println("Session expired or illegal; please log in");
	            return;
	        }

	        session = SessionManager.getSessionById( ssid );
	        if( session == null ) {
	        	System.out.println("Session expired or illegal; please log in");
	            return;
	        }

	        logicLayer = session.getLogicLayer();
	        if( logicLayer == null ) {
	        	System.out.println("Session expired or illegal; please log in");
	            return;
	        }
	        
	        // Get the current user from session
	        User currentUser = session.getUser();
	        
	        // Get the customer from the user
	        if(currentUser instanceof Customer)
	        	current = ((Customer)currentUser);
	        else {
	        	System.out.println("The current user is not a customer.");
	        	return;
	        }
		
		List<Rental> rentals =null;
		
        try {
            
        	RentalManager rm = new RentalManager();
        	rentals = rm.restoreC(current);
            System.out.println("it created the allLocation");
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
        
        root.put("rentals", rentals);
		String templateName = "LeaveComment.ftl";
		processor.processTemplate(templateName, root, request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		long ID = Long.parseLong(id);
		System.out.println(id);
		String comment = request.getParameter("comment");
		System.out.println(comment);
		CommentManager cm = new CommentManager();
		try {
			cm.storeComment(current.getId(), ID, comment);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("CustomerLoginPageAlt.html");
		/*tag = request.getParameter("tag");
		millege = request.getParameter("millege");
		status = request.getParameter("status");
		rentalLocation = request.getParameter("rentalLocation");
		vehicleType = request.getParameter("vehicleType");
		carCondition = request.getParameter("carCondition");
		
		month = request.getParameter("month");
		day = request.getParameter("day");
		Syear = request.getParameter("Syear");*
		
		VehicleManager vm = new VehicleManager();
		
		if(!millege.equals("")) {
			try {
				vm.updateVehicle(tag, "Millege", millege);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(!status.equals("")) {
			try {
				vm.updateVehicle(tag, "Status", status);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		
		if(!rentalLocation.equals("")) {
			try {
				vm.updateVehicle(tag, "RentalLocation", rentalLocation);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(!vehicleType.equals("")) {
			try {
				vm.updateVehicle(tag, "VehicleType", vehicleType);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(!carCondition.equals("")) {
			try {
				vm.updateVehicle(tag, "CarCondition", carCondition);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(!month.equals("") && !Syear.equals("") && !day.equals("")) {
		int Month = Integer.parseInt(month);
		int Syear1 = Integer.parseInt(Syear);
		int Day = Integer.parseInt(day);
		Date lastService = new Date(Syear1, Month, Day);
		System.out.println(Syear1);
		java.util.Date jDate= lastService;
        java.sql.Date sDate=new java.sql.Date(Syear1-1900,Month, Day);
			try {
				vm.updateVehicle(tag, "lastService", sDate);
			}
			catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
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
		List<Vehicle>vehicles = null;
        try {
            vehicles = logicLayer.findAllVehicles();
            System.out.println("it created the allLocation");
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
        
        root.put("allVehicles",vehicles);
		String templateName = "BrowseVehicles-result.ftl";
		processor.processTemplate(templateName, root, request, response);*/
		
		
		
	}

}
