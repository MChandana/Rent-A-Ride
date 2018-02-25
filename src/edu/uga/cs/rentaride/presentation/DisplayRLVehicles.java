package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/DisplayRLVehicles")
public class DisplayRLVehicles extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static  String  templateDir = "WEB-INF/Templates";
    static  String  resultTemplateName = "CustomerLoginPage-result.ftl";
    static  String  errorTemplateName = "LoginError.ftl";
    static  String  errorTemplate2Name = "CustomerLoginPageAlt-error.ftl";



    private Configuration cfg;

    public void init()
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        //
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(
                getServletContext(),
                "WEB-INF/Templates"
        );
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {

        Template resultTemplate= null;
        Template errorTemplate=null;
        Template errorTemplate2=null;
        BufferedWriter toClient=null;
        LogicLayer logicLayer=null;
        HttpSession httpSession;
        Session session;
        String ssid;

        try{
            resultTemplate=cfg.getTemplate(resultTemplateName);
            errorTemplate=cfg.getTemplate(errorTemplateName);
            errorTemplate2=cfg.getTemplate(errorTemplate2Name);

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

        int found=0;
        String RL=req.getParameter("RLocsearch");

        List<RentalLocation> rentalLocations=new ArrayList<>();
        List<RentalLocation> resultRL=new ArrayList<>();
        List<VehicleType> vehicleTypes=new ArrayList<>();
        List<Vehicle> vehicles=new ArrayList<>();
        List<VehicleType> resultVT=new ArrayList<>();
        int RLfound=1;

        try {
            rentalLocations=logicLayer.findAllRentalLocations();
            vehicleTypes=logicLayer.findAllVehicleTypes();
            vehicles=logicLayer.findAllVehicles();
            if(RL.equals("")){
                System.out.println("%%%%%%%%%%%%here in if %%%%%%% adding all RLs");
                RLfound=0;
                //root.put("location",rentalLocations);
            }
                for (RentalLocation rentalLocation : rentalLocations) {
                    if (rentalLocation.getName().contains(RL)) {
                        resultRL.add(rentalLocation);
                        found = 1;
                        for(Vehicle vehicle:vehicles){
                            if(vehicle.getRentalLocation().getName().equals(rentalLocation.getName())){
                                resultVT.add(vehicle.getVehicleType());
                            }
                        }
                    } else {
                        if (rentalLocation.getAddress().contains(RL)) {
                            resultRL.add(rentalLocation);
                            found = 1;
                            for(Vehicle vehicle:vehicles){
                                if(vehicle.getRentalLocation().getName().equals(rentalLocation.getName())){
                                    resultVT.add(vehicle.getVehicleType());
                                }
                            }
                        }
                    }
                }



                if(found==1){
                    if(RLfound==0){
                        root.put("location",rentalLocations);
                    }else{
                        root.put("location",resultRL);

                    }

                    root.put("vehicleType",vehicleTypes);
                    resultTemplate.process(root,toClient);
                    toClient.flush();

                }else{

                    errorTemplate2.process(root,toClient);
                }

        } catch (RARException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            toClient.close();

        }



    }





}
