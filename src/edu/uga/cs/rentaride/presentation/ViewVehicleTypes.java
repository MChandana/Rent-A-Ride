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
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.HourlyPriceManager;
import edu.uga.cs.rentaride.persistence.impl.VehicleTypeManger;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class ViewVehicleTypes
 */
@WebServlet("/ViewVehicleTypes")
public class ViewVehicleTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String name, price, mxHrs;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewVehicleTypes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void init(ServletConfig config) throws ServletException {
  		super.init(config);
  		processor = new Part2(templateDir, getServletContext());
  		
  	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			NewPage(request,response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected void NewPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
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
		List<HourlyPrice> allVehicleTypes = null;
		HourlyPriceManager hpm = new HourlyPriceManager();
        allVehicleTypes = hpm.allPrices();
		//allLocation=logicLayer.findAllRentalLocations();
		//allVehicleTypes = logicLayer.findAllVehicleTypes();
		System.out.println("it created the allLocation");
        
       // root.put("allLocation", allLocation);
        root.put("allVehicleTypes", allVehicleTypes);
		String templateName = "viewVehicleTypes.ftl";
		processor.processTemplate(templateName, root, request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		name = request.getParameter("name");
		price = request.getParameter("price");
		mxHrs = request.getParameter("mxHrs");
		
		System.out.println(name);
		System.out.println(price);
		System.out.println(mxHrs);
		
		VehicleTypeManger vtm = new VehicleTypeManger();
		HourlyPriceManager hpm = new HourlyPriceManager();
		int MaxHrs = Integer.parseInt(mxHrs);
		int Price = Integer.parseInt(price);
		try {
			vtm.setVehicleType(name);
			hpm.setHourlyPrice(name, MaxHrs, Price);
			NewNewPage(request,response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void NewNewPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
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
		List<HourlyPrice> allVehicleTypes = null;
		HourlyPriceManager hpm = new HourlyPriceManager();
        allVehicleTypes = hpm.allPrices();
		//allLocation=logicLayer.findAllRentalLocations();
		//allVehicleTypes = logicLayer.findAllVehicleTypes();
		System.out.println("it created the allLocation");
        
       // root.put("allLocation", allLocation);
        root.put("allVehicleTypes", allVehicleTypes);
		String templateName = "viewVehicleTypes.ftl";
		processor.processTemplate(templateName, root, request, response);
	}
	
	
	
}
		


