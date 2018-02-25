package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
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
import javax.sql.rowset.serial.SerialException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AlternateRL")
public class AlternateRentalLocation extends HttpServlet {
    private static final long serialVersionUID=1L;

    static String templateDir="WEB-INF/Templates";
    static String resultTemplateName="CustomerLoginPage3-result.ftl";
    static String errorTemplateName="LoginError.ftl";


    private Configuration cfg;

    public void init() throws ServletException {
        cfg=new Configuration(Configuration.VERSION_2_3_25);
        cfg.setServletContextForTemplateLoading(getServletContext(),"WEB-INF/Templates");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
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

        String rentalLoc=req.getParameter("location");
        System.out.println("rentalLoc Alter "+rentalLoc);

      //  String ary[]=rentalLoc.split(" ");
        try {
            List<RentalLocation> rentalLocations=logicLayer.findAllRentalLocations();
            List<VehicleType> vehicleTypes=logicLayer.findAllVehicleTypes();
            List<String> VTnames=new ArrayList<>();

            List<String> RLAddress=new ArrayList<>();
            String locAddress=null;

            for(VehicleType vehicleType:vehicleTypes){
                VTnames.add(vehicleType.getName());
            }

            for(RentalLocation rentalLocation:rentalLocations){
                String rentalLocName=rentalLocation.getName();
                if(rentalLocName.contains(rentalLoc)){
                    locAddress=rentalLocation.getAddress();
                }
            }

             String ary[]=locAddress.split(" ");

            for(RentalLocation rentalLocation:rentalLocations){
                String rentalLocAdrs=rentalLocation.getAddress();

                if(!rentalLocAdrs.equals(locAddress) && (rentalLocAdrs.contains(ary[ary.length-2]))){
                    RLAddress.add(rentalLocation.getName());
                }
            }
            root.put("location",RLAddress);
            root.put("vehicleType",VTnames);

        } catch (RARException e) {
            e.printStackTrace();
        }

        try {
            resultTemplate.process(root,toClient);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        toClient.close();

    }
}
