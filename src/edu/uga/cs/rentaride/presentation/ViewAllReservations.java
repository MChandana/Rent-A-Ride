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
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.RentalLocationManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet("/ViewAllReservations")
public class ViewAllReservations extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String allReservations;
	private Part2 processor;
	private String templateDir = "/WEB-INF/Templates";
	
	
	public ViewAllReservations(){
		super();
	}
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		processor = new Part2(templateDir, getServletContext());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		allReservations = request.getParameter("allReservations");
		if(allReservations != null){
			allReservations(request, response);
		}
	}
	
	protected void allReservations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession httpSession;
		String ssid;
		httpSession = request.getSession();
		ssid = (String) httpSession.getAttribute("ssid");
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23);
		SimpleHash root = new SimpleHash(db.build());
		Session session = null;
		session = SessionManager.getSessionById(ssid);
		LogicLayer logicLayer = null;
		logicLayer = session.getLogicLayer();
		List<Reservation> allReservation = new ArrayList<Reservation>();
		
		try{
			allReservation = logicLayer.findAllReservations();
		}catch(Exception e){
			System.out.println("Failed to restore all Reservations");
			e.printStackTrace();
		}
			
		root.put("allReservation", allReservation);
		String templateName = "ViewAllReservations-result.ftl";
		processor.processTemplate(templateName, root, request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
