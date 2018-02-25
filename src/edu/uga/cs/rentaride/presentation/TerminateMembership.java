package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.persistence.impl.CustomerManager;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/terminate")
public class TerminateMembership extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException
    {
        LogicLayer          logicLayer = null;
        List<Reservation>   rv = null;
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
        
        System.out.println("Status before termination: " + current.getUserStatus());
        
        // Set the user status to terminated
        try {
        	CustomerManager cm = new CustomerManager();
        	cm.terminateCustomers(current);
        }
        catch( Exception e) {
            System.out.println("Error in terminating membership");
            System.out.println(e);
            return;
        }
        
        // logout the user?
        
        // Redirect to index
        res.sendRedirect("index.html");
        
    }
}