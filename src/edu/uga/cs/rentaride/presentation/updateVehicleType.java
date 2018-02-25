package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.HourlyPriceManager;
import edu.uga.cs.rentaride.persistence.impl.VehicleTypeManger;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class updateVehicleType
 */
@WebServlet("/updateVehicleType")
public class updateVehicleType extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String name, price, mxHrs, Newname;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateVehicleType() {
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
        try {
			allVehicleTypes = hpm.allPrices();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//allLocation=logicLayer.findAllRentalLocations();
		//allVehicleTypes = logicLayer.findAllVehicleTypes();
		System.out.println("it created the allLocation");
        
       // root.put("allLocation", allLocation);
        root.put("allVehicleTypes", allVehicleTypes);
        root.put("allVehicleTypes", allVehicleTypes);
		String templateName = "updateVehicleTypes.ftl";
		processor.processTemplate(templateName, root, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HourlyPriceManager hpm = new HourlyPriceManager();
		VehicleTypeManger vtm = new VehicleTypeManger();
		name = request.getParameter("name");
		Newname = request.getParameter("Newname");
		mxHrs = request.getParameter("mxHrs");
		price = request.getParameter("price");
		long ID = 0;
		long ID2 = 0;
		
		//int Max = Integer.parseInt(mxHrs);
		//int Price = Integer.parseInt(price);
		try {
			ID = hpm.getID(name);
			ID2 = vtm.getID(name);
			System.out.println(ID);
			System.out.println(ID2);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(!Newname.equals("")) {
			try {
				vtm.updateVehicleType(Newname, ID2);
				hpm.updateHourlyPrice("name", Newname, ID);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(!mxHrs.equals("")) {
			try {
				int Max = Integer.parseInt(mxHrs);
				hpm.updateHourlyPrice("MaxHrs", Max, ID);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(!price.equals("")) {
			try {
				int Price = Integer.parseInt(price);
				hpm.updateHourlyPrice("Price",Price, ID);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
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
	
	

}
