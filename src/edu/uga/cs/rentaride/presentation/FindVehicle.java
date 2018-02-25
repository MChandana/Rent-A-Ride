package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/FindVehicle")
public class FindVehicle extends HttpServlet {

    private static final long serialVersionUID=1L;
    static String templateDir="WEB-INF/Templates";
    static String resultTemplateName="CustomerLoginPage2-result.ftl";
    //static String resultTemplate2Name="CustomerLoginPage3-result.ftl";

    static String errorTemplateName="LoginError.ftl";

    private Configuration cfg;

    public void init() throws ServletException {
        cfg=new Configuration(Configuration.VERSION_2_3_25);
        cfg.setServletContextForTemplateLoading(getServletContext(),"WEB-INF/Templates");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

        Template resultTemplate=null;
        Template resultTemplate2=null;

        Template errorTemplate=null;
        BufferedWriter toClient=null;
        LogicLayer logicLayer=null;
        HttpSession httpSession=null;
        Session session=null;
        String ssid;

        int found=0;

        try{
            resultTemplate=cfg.getTemplate(resultTemplateName);
        //    resultTemplate2=cfg.getTemplate(resultTemplate2Name);
            errorTemplate=cfg.getTemplate(errorTemplateName);
        }catch (IOException e) {
            throw new ServletException("Can't load template in: " + templateDir + ": " + e.toString());
        }

        toClient=new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),resultTemplate.getEncoding()));
        response.setContentType("text/html; charset=" + resultTemplate.getEncoding());
        Map<String,Object> root=new HashMap<>();

        httpSession = request.getSession();
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

        String RL=request.getParameter("location");
        String VT=request.getParameter("vehicleType");
        List<Vehicle> vehiclesList=new ArrayList<>();
        List<Vehicle> vehicles=new ArrayList<>();
        List<HourlyPrice> hourlyPrices=new ArrayList<>();
        RentalLocation rentalLocation=null;
        List<RentalLocation> rentalLocations=null;
        RentalLocation tempRL=null;


        try {
            vehicles=logicLayer.findAllVehicles();
            rentalLocations=logicLayer.findAllRentalLocations();
            for(Vehicle vehicle:vehicles){
                if(vehicle.getRentalLocation().getName().equals(RL) && vehicle.getVehicleType().getName().equals(VT)){
                    System.out.println("in if");
                    vehiclesList.add(vehicle);
                    hourlyPrices=logicLayer.findHourlyPrices(vehicle.getVehicleType());
                    found=1;
                }
            }

            for(RentalLocation rentalLocation1:rentalLocations){
                 if(rentalLocation1.getName().equals(RL)){
                     //root.put("Rlocation",rentalLocation1);
                    tempRL=rentalLocation1;
                }
            }

        } catch (RARException e) {
            e.printStackTrace();
        }

        if(found==1) {
            root.put("RLocation",tempRL);
            root.put("vehicle", vehiclesList);
            root.put("hourlyPrices", hourlyPrices);
            root.put("selectedLoc",RL);
            root.put("selectedVT",VT);
            try {
                resultTemplate.process(root, toClient);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("redirecting to alternate RL");
            request.setAttribute("location",RL);
            RequestDispatcher rd = request.getRequestDispatcher("/AlternateRL");
            rd.forward(request,response);
            //response.sendRedirect("/AlternateRL");

        }
    }

}
