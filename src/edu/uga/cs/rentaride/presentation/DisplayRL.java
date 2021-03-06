package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.logic.impl.DisplayRLCtrl;
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
import java.util.List;
import java.util.Map;

@WebServlet("/DisplayRL")
public class DisplayRL extends HttpServlet {

    private static final long serialVersionUID=1L;
    static String templateDir="WEB-INF/Templates";
    static String resultTemplateName="DisplayRLAllDetails.ftl";
    static String errorTemplateName="LoginError.ftl";


    private Configuration cfg;

    public void init() throws ServletException {
        cfg=new Configuration(Configuration.VERSION_2_3_25);
        cfg.setServletContextForTemplateLoading(getServletContext(),"WEB-INF/Templates");


    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
        System.out.println("in browse locations do post");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        Template resultTemplate= null;
        Template errorTemplate=null;
        BufferedWriter toClient=null;
        LogicLayer logicLayer=null;
        HttpSession httpSession;
        Session session;
        String ssid;

        try{
            resultTemplate=cfg.getTemplate(resultTemplateName);
            errorTemplate=cfg.getTemplate(errorTemplateName);
            System.out.println("CRL getting template "+resultTemplate);
        }catch (IOException e) {
            throw new ServletException("Can't load template in: " + templateDir + ": " + e.toString());
        }
        Map<String,Object> root=new HashMap<>();
        toClient=new BufferedWriter(new OutputStreamWriter(res.getOutputStream(),resultTemplate.getEncoding()));
        res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
        try{
            resultTemplate=cfg.getTemplate(resultTemplateName);

            errorTemplate=cfg.getTemplate(errorTemplateName);

        }catch (IOException e) {
            throw new ServletException("Can't load template in: " + templateDir + ": " + e.toString());
        }
        httpSession = req.getSession();
        if( httpSession == null ) {       // not logged in!
            System.err.println("Session expired or illegal; please log in");
            try {
                errorTemplate.process(root,toClient);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            return;
        }

        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid == null ) {       // not logged in!
            System.err.println("Session expired or illegal; please log in");
            try {
                errorTemplate.process(root,toClient);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            return;
        }

        session = SessionManager.getSessionById( ssid );
        if( session == null ) {
            System.err.println("Session expired or illegal; please log in");
            try {
                errorTemplate.process(root,toClient);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            return;
        }

        logicLayer = session.getLogicLayer();
        if( logicLayer == null ) {
            System.err.println("Session expired or illegal; please log in");
            try {
                errorTemplate.process(root,toClient);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            return;
        }


        String RLName=req.getParameter("locName");
        List<Reservation> reservations=null;
        List<Rental> rentals=null;
        List<Vehicle> vehicles=null;
        List<RentalLocation> rentalLocations=null;
        List<RentalLocation> resultRLs=new ArrayList<>();
        try {
           rentalLocations=logicLayer.findAllRentalLocations();
           for(RentalLocation rentalLocation:rentalLocations){
               if(rentalLocation.getName().equals(RLName)){
                   resultRLs.add(rentalLocation);
               }
           }
        } catch (RARException e) {
            e.printStackTrace();
        }

        try {
            reservations=logicLayer.displayRLReservations(RLName);
            vehicles=logicLayer.displayRLVehicles(RLName);
            rentals=logicLayer.displayRLRentals(RLName);
        } catch (RARException e) {
            e.printStackTrace();
        }

        root.put("allLocation",resultRLs);
        root.put("vehicle",vehicles);
        root.put("reservation",reservations);
        root.put("rental",rentals);

        try {
            resultTemplate.process(root,toClient);
            toClient.flush();
        } catch (TemplateException e) {
            System.out.println(e.getMessage());
        }

        toClient.close();


    }

}
