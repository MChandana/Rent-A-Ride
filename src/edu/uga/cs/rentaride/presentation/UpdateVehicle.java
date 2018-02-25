package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
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
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.VehicleManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class UpdateVehicle
 */
@WebServlet("/UpdateVehicle")
public class UpdateVehicle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String status, rentalLocation, vehicleType, carCondition, millege, 
	lastService,month, day, Syear, vehicleID,tag; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateVehicle() {
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
		List<Vehicle>allVehicle = null;
        try {
            allVehicle = logicLayer.findAllVehicles();
        	allLocation=logicLayer.findAllRentalLocations();
            allVehicleTypes = logicLayer.findAllVehicleTypes();
            System.out.println("it created the allLocation");
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
        
        root.put("allVehicle", allVehicle);
        root.put("allLocation", allLocation);
        root.put("allVehicleTypes", allVehicleTypes);
		String templateName = "updatevehicle.ftl";
		processor.processTemplate(templateName, root, request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		tag = request.getParameter("tag");
		millege = request.getParameter("millege");
		status = request.getParameter("status");
		rentalLocation = request.getParameter("rentalLocation");
		vehicleType = request.getParameter("vehicleType");
		carCondition = request.getParameter("carCondition");
		
		month = request.getParameter("month");
		day = request.getParameter("day");
		Syear = request.getParameter("Syear");
		
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
		processor.processTemplate(templateName, root, request, response);
		
		
		
	}

}
