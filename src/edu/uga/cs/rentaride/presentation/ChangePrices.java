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
import edu.uga.cs.rentaride.entity.RentARideParams;
import edu.uga.cs.rentaride.entity.impl.RentARideParamsImpl;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.HourlyPriceManager;
import edu.uga.cs.rentaride.persistence.impl.RentARideParamsManager;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class ChangePrices
 */
@WebServlet("/ChangePrices")
public class ChangePrices extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Part2 processor;
	private String templateDir = "/WEB-INF/Templates";
	private String membershipPrice, lateFee;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePrices() {
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
		List<RentARideParams> prices = null;
		RentARideParamsManager rp = new RentARideParamsManager();
        try {
			prices = rp.getPrices();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("it created the allLocation");
        
       // root.put("allLocation", allLocation);
        root.put("prices", prices);
		String templateName = "changeprices.ftl";
		processor.processTemplate(templateName, root, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RentARideParamsManager rm = new RentARideParamsManager();
		membershipPrice = request.getParameter("membershipPrice");
		lateFee = request.getParameter("lateFees");
		
		if(!membershipPrice.equals("")) {
			int mPrice = Integer.parseInt(membershipPrice);
			try {
				rm.updatePrices("membershipPrice", mPrice);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(!lateFee.equals("")) {
			int lFee = Integer.parseInt(lateFee);
			try {
				rm.updatePrices("lateFee", lFee);
			} catch (ClassNotFoundException e) {
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
		List<RentARideParams> prices = null;
		RentARideParamsManager rp = new RentARideParamsManager();
        try {
			prices = rp.getPrices();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("it created the allLocation");
        
       // root.put("allLocation", allLocation);
        root.put("prices", prices);
		String templateName = "changeprices.ftl";
		processor.processTemplate(templateName, root, request, response);
	}

}
