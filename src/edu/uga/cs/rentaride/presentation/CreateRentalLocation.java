package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.logic.impl.CreateRentalLocationCtrl;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.logic.LogicLayer;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/CreateRentalLocation")
public class CreateRentalLocation extends HttpServlet {

    private static final long serialVersionUID=1L;
    static String templateDir="WEB-INF/Templates";
    static String resultTemplateName="RentalLocations-result.ftl";
    private Part2 processor;
    

    private Configuration cfg;

    public void init() throws ServletException{
        cfg=new Configuration(Configuration.VERSION_2_3_25);
        cfg.setServletContextForTemplateLoading(getServletContext(),"WEB-INF/Templates");

    }

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
        System.out.println("in do get create rental location control");
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
    	 System.out.println("in do post create rental location control");
    	Template resultTemplate= null;
        BufferedWriter toClient=null;
        String name=null;
        String address=null;
        int capacity=0;
        long rentalLocation_id=0;
        LogicLayer logicLayer=null;
        HttpSession httpSession;
        Session session;
        String ssid;

        System.out.println("in crete rental loc do post");

        try{
            resultTemplate=cfg.getTemplate(resultTemplateName);
            System.out.println("CRL getting template "+resultTemplate);
        }catch (IOException e) {
            throw new ServletException("Can't load template in: " + templateDir + ": " + e.toString());
        }

        toClient=new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),resultTemplate.getEncoding()));
        response.setContentType("text/html; charset=" + resultTemplate.getEncoding());

        System.out.println("after setting respnse");
        httpSession=request.getSession();
        System.out.println("after getting httpsesion "+httpSession);

        if(httpSession==null){
            System.err.println("httpSession=null not logged in");
        }

      ssid = (String) httpSession.getAttribute( "ssid" );
        System.out.println("after getting session ssid is "+ssid);
        if( ssid == null ) {       // not logged in!
            System.err.println("ssid=null not logged in");
            return;
        }

        session = SessionManager.getSessionById( ssid );
        System.out.println("getting session by id "+session);
        if( session == null ) {
            System.err.println("Session expired or illegal. Please log in");
            return;
        }

        logicLayer = session.getLogicLayer();
        System.out.println("after getting logic layer in CRL "+logicLayer);
        if( logicLayer == null ) {
            System.err.println("Session expired or illegal. Please log in");
            return;
        }


        name=request.getParameter("name");
        address=request.getParameter("address");
        
        boolean nameChecker = logicLayer.isNameValid(name);
        
        if(nameChecker == true) {
        //capacity=Integer.parseInt(request.getParameter("capacity"));
        String capacityString=request.getParameter("capacity");
        capacity=Integer.parseInt(capacityString);
        System.out.println("CRL name address capacity are "+name+" , "+address + " , "+capacity);
        if( name == null || address == null) {
            System.err.println("Unspecified Rental Location name or address values");
            return;
        }

        List<RentalLocation> allLocation=null;
        try {
        	rentalLocation_id = logicLayer.createRentalLocation(name, address, capacity);
            allLocation=logicLayer.findAllRentalLocations();
            System.out.println("it created the allLocation");
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
        catch ( Exception e ) {
            e.printStackTrace();
            return;
        }

        Map<String,Object> root = new HashMap<String,Object>();

        System.out.println("before putting in root");
        root.put("name",name);
        root.put("allLocation",allLocation);
        System.out.println("after root in CRL");

        try {
            resultTemplate.process(root,toClient);
            toClient.flush();
            System.out.println("processing tempalte in CRl");
        } catch (TemplateException e) {
            throw new ServletException( "Error while processing FreeMarker template", e);
        }

        toClient.close();

    }
        else {
        	response.sendRedirect("RentalLocationError.html");
        }
    }
    

}
