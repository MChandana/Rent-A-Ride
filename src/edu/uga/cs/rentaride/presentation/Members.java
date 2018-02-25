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
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.RentalLocationManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class Members
 */
@WebServlet("/Members")
public class Members extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String allMembers;
	private String updatePrice;
	private Part2 processor;
	private String templateDir = "/WEB-INF/Templates";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Members() {
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
		allMembers = request.getParameter("allMembers");
		updatePrice = request.getParameter("update");
		System.out.println(allMembers);
		System.out.println(updatePrice);
		
		if(allMembers != null) {
			allMembers(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void allMembers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
