package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.RentalManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet("/cancel")
public class Cancel extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
  public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException
    {
    	System.out.println("im here");
    	String reservationID = req.getParameter("id");
    	long id = Long.parseLong(reservationID);
    	
    	Template            resultTemplate = null;
        BufferedWriter      toClient = null;
        LogicLayer          logicLayer = null;
        List<Reservation>   rv = null;
        List<List<Object>>  reservations = null;
        List<Object>        reservation = null;
        Reservation         r  = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        Customer			current;
        
      
      // This section is checking to handle session error
        httpSession = req.getSession();
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
        
        
        
        try {
        	RentalManager rm = new RentalManager();
        	rm.cancel(id);
        	res.sendRedirect("CustomerLoginPageAlt.html");
        }
        catch( Exception e) {
            System.out.println("Error in getting customer's reserations");
            System.out.println(e);
            return;
        }
        
      

        
        
    }
}