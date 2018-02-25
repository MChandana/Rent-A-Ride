package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
import java.sql.SQLException;
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
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.RentalLocationManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;




/**
 * Servlet implementation class UpdateRentalLocation
 */
@WebServlet("/UpdateRentalLocation")
public class UpdateRentalLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String name = null;
	private String newname = null;
	private String newaddress = null;
	private String newcapacity = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRentalLocation() {
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
		NewPage(request,response);
	}
	
	protected void NewPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			List<RentalLocation> allLocation=null;
	        try {
	            allLocation=logicLayer.findAllRentalLocations();
	            System.out.println("it created the allLocation");
	        } catch (RARException e) {
	            System.out.println("getting all rental locations in login new from logic layer");
	        }
	        
	        root.put("allLocation", allLocation);
			String templateName = "admin-update-location.ftl";
			processor.processTemplate(templateName, root, request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    name = request.getParameter("name");
	    newname = request.getParameter("newname");
	    newaddress = request.getParameter("newaddress");
	    newcapacity = request.getParameter("newcapacity");
	    
	    LogicLayer logicLayer=null;
        HttpSession httpSession;
        Session session = null;
        String ssid;
	    httpSession=request.getSession();
	    ssid = (String) httpSession.getAttribute( "ssid" );
	    session = SessionManager.getSessionById( ssid );
	    logicLayer = session.getLogicLayer();
	    
	    boolean nameChecker = logicLayer.isNameValid(newname);
	    System.out.println(nameChecker);
	    if(nameChecker == false) {
	    	ErrorPage(request,response);
	    }
	    else{
	    	try {
				NewNewPage(request,response);
			} catch (RARException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	
	protected void ErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		List<RentalLocation> allLocation=null;
        try {
            allLocation=logicLayer.findAllRentalLocations();
            System.out.println("it created the allLocation");
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
        
        root.put("allLocation", allLocation);
		String templateName = "update-error.ftl";
		processor.processTemplate(templateName, root, request, response);
}
	
	protected void NewNewPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, RARException, ClassNotFoundException, SQLException {
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
		RentalLocationManager rlm = new RentalLocationManager();
		
		if(newcapacity != null) {
			int capacity = Integer.parseInt(newcapacity);
			rlm.updateRentalLocation("capacity", capacity, name);
		}
		
		if(newaddress != null) {
			rlm.updateRentalLocation("address",newaddress,name);
		}
		
		if(newname != null) {
		rlm.updateRentalLocation("name",newname,name);
		}
		
		  List<RentalLocation> allLocation=null;
	        try {
	            allLocation=logicLayer.findAllRentalLocations();
	            System.out.println("it created the allLocation");
	        } catch (RARException e) {
	            System.out.println("getting all rental locations in login new from logic layer");
	        }
	        catch ( Exception e ) {
	            e.printStackTrace();
	        }
	        
	      root.put("allLocation", allLocation);
	      if(newname != null) {
	      root.put("name", newname);
	      }
	      else {
	    	  root.put("name",name);
	      }
	  	String templateName = "updateLocationResult.ftl";
		processor.processTemplate(templateName, root, request, response);
	      
		
		
		
		}
	
	

}
