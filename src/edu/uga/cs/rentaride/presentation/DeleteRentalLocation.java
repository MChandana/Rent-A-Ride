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

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class DeleteRentalLocation
 */
@WebServlet("/DeleteRentalLocation")
public class DeleteRentalLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String templateDir = "/WEB-INF/Templates";
	private Part2 processor;
	private String name = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRentalLocation() {
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
		System.out.println("greetings from delete rental location");
		NewPage(request,response);
	}
	
	protected void NewPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("greetings from delete rental location New Page");
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
		String templateName = "delete-rentalLocation.ftl";
		processor.processTemplate(templateName, root, request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
