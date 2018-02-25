package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/returnRental")
public class ReturnRental extends HttpServlet {
private static final long serialVersionUID = 1L;

	
	private String rentalID = "";
	 private Configuration cfg;

	 
    @SuppressWarnings("deprecation")
	public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException
    {
    	Template            resultTemplate = null;
    	LogicLayer          logicLayer = null;
    	BufferedWriter      toClient = null;
        List<Reservation>   rv = null;
        Reservation         r  = null;
        List<Rental>		rentals = null;
        Rental				rental = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        Customer			current;
        
       
        
        // Prepare the HTTP response:                                                                 
        // - Use the charset of template for the output                                               
        // - Use text/html MIME-type                                                                                                                                                              

 
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
        Map<String,Object> root = new HashMap<String,Object>();
        // Get the id of the reservation to be turned into a rental
    	rentalID = req.getParameter("id");
    	long id = Long.parseLong(rentalID);
    	List<Rental> riri = new ArrayList<>();
        
        try {
        		RentalManager rm = new RentalManager();
        		rm.updateCharges(id, current.getId());
        		riri = rm.restoreC(current);
        		res.sendRedirect("CustomerLoginPageAlt.html");
        		
            }
        
        	
        
       catch( Exception e) {
            System.out.println("Error in getting customer's reserations");
            e.printStackTrace();
            return;
        }
        
        // Merge the data-model and the template                                                      
        //                                                                                            
        
       
        
    }
}
