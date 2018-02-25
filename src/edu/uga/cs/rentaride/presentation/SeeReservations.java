package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.RentalManager;
import edu.uga.cs.rentaride.persistence.impl.ReservationManager;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet("/seeReservations")
public class SeeReservations extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    static  String  templateDir = "WEB-INF/Templates";
    static  String  resultTemplateName = "Reservations.ftl";

    private Configuration cfg;

    public void init()
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(getServletContext(),"WEB-INF/Templates");
    }
    
    public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException
    {
    	Template            resultTemplate = null;
        BufferedWriter      toClient = null;
        LogicLayer          logicLayer = null;
        List<Reservation>   rv = null;
        List<Rental>   		rentals = null;
        List<List<Object>>  reservations = null;
        List<List<Object>>  pickedUp = null;
        List<Object>        reservation = null;
        Reservation         r  = null;
        Rental 				rental = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        Customer			current;
        Rental				rented = null;
        
        // Load templates from the WEB-INF/templates directory of the Web app.                                                                                                                    
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        }
        catch( IOException e ) {
            throw new ServletException("Can't load template in: " + templateDir + ": " + e.toString());
        }
        
        // Prepare the HTTP response:                                                                 
        // - Use the charset of template for the output                                               
        // - Use text/html MIME-type                                                                                                                                                              
        toClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
        res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
        
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
        
        // Get a list of the customers reservations
        Map<String,Object> root = new HashMap<String,Object>();
        
        try {
        	long CID = current.getId();
        	ReservationManager rm = new ReservationManager();
        	RentalManager rem = new RentalManager();
        	
        	rv = rm.restoreID(CID);
        	List<Reservation> rev = new ArrayList<Reservation>();
        	
        	rentals = rem.restoreC(current);
        	
        	
        	List<Rental> riri = new ArrayList<Rental>();
        	for(Rental roro : rentals) {
        		if(roro.getPickupTime() != null) {
        			riri.add(roro);
        		}
        	}
        	
        	for(Reservation meme : rv) {
        		if(!meme.getCanceled()) {
        			rev.add(meme);
        		}
        	}
        	
        	root.put("rev", rev);
        	
        	root.put("rentals", rentals);
        	
        	root.put("riri", riri);
        	
        	
        	
            
        	                                                                                        
       /*     reservations = new LinkedList<List<Object>>();
            pickedUp = new LinkedList<List<Object>>();
            root.put( "reservations", reservations );
            root.put( "pickedUp", pickedUp );
            System.out.println("i made it 2");*/
            
            
            
          /* for( int i = 0; i < rv.size(); i++ ) {
            	System.out.println("i made it 3");
            	r = (Reservation) rv.get( i );
            	System.out.println("i made it 4");
                reservation = new LinkedList<Object>();
                reservation.add( r.getPickupTime() );
                reservation.add( r.getRentalLocation().getName() );
                reservation.add( r.getVehicleType().getName() );
                reservation.add( r.getId() );
                rented = null;
                if(!r.getCanceled()) // check to make sure the reservation isn't cancelled
                {
                	for (int j = 0; j< rentals.size(); j++)
                	{
                		rental = (Rental) rentals.get(j);
                		
                		if (rental.getReservation() != null && rental.getReservation().getId() == r.getId())
                			rented = rental;
                	}
                	if (rented == null)
                		reservations.add( reservation );
                	else
                	{
                		if(rented.getReturnTime() == null)
                			pickedUp.add(reservation);
                	}
                }
            }*/
        }
        catch( Exception e) {
            System.out.println("Error in getting customer's reserations");
            e.printStackTrace();
            return;
        }
        
        // Merge the data-model and the template                                                      
        //                                                                                            
        try {
            resultTemplate.process( root, toClient );
            toClient.flush();
        }
        catch (TemplateException e) {
            throw new ServletException( "Error while processing FreeMarker template", e);
        }

        toClient.close();
        
    }
}
