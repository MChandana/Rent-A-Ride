package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.VehicleTypeManger;
import edu.uga.cs.rentaride.persistence.impl.VehicleManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class AddVehicle
 */
@WebServlet("/AddVehicle")
public class AddVehicle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String tag, make, model, status, rentalLocation, vehicleType, carCondition, millege, 
	lastService, year, month, day, Syear; 
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddVehicle() {
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
		List<VehicleType> allVehicleTypes = null;
        try {
            allLocation=logicLayer.findAllRentalLocations();
            allVehicleTypes = logicLayer.findAllVehicleTypes();
            System.out.println("it created the allLocation");
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
        
        root.put("allLocation", allLocation);
        root.put("allVehicleTypes", allVehicleTypes);
		String templateName = "addnewVehicle.ftl";
		processor.processTemplate(templateName, root, request, response);
        
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		tag = request.getParameter("tag");
		make = request.getParameter("make");
		model = request.getParameter("model");
		rentalLocation = request.getParameter("rentalLocation");
		vehicleType = request.getParameter("vehicleType");
		carCondition = request.getParameter("carCondition");
		millege = request.getParameter("millege");
		year = request.getParameter("year");
		status = request.getParameter("status");
		month = request.getParameter("month");
		day = request.getParameter("day");
		Syear = request.getParameter("Syear");
		
		VehicleManager vm = new VehicleManager();
		try {
			System.out.println(Syear);
			vm.setVehicle(tag, make, model, year, millege, month, day, Syear, status, rentalLocation, vehicleType, carCondition);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Vehicle> allVehicle = null;
		try {
			allVehicle=logicLayer.findAllVehicles();
		} catch (RARException e) {
			e.printStackTrace();
		}
		
		root.put("allVehicles",allVehicle);
		String templateName = "BrowseVehicles-result.ftl";
		processor.processTemplate(templateName, root, request, response);
		
		
			
	}

}
