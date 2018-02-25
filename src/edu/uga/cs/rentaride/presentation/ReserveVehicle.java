package edu.uga.cs.rentaride.presentation;

import com.mysql.jdbc.TimeUtil;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/ReserveVehicle")
public class ReserveVehicle extends HttpServlet{
    private static final long serialVersionUID=1L;
    static String templateDir="WEB-INF/Templates";
    static String resultTemplateName="ReserveSuccess.ftl";

    static String errorTemplateName="LoginError.ftl";
    static String errorTemplate2Name="ReserveError.ftl";


    private Configuration cfg;

    public void init() throws ServletException {
        cfg=new Configuration(Configuration.VERSION_2_3_25);
        cfg.setServletContextForTemplateLoading(getServletContext(),"WEB-INF/Templates");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {

        Template resultTemplate=null;
        Template errorTemplate=null;
        Template errorTemplate2=null;

        BufferedWriter toClient=null;
        LogicLayer logicLayer=null;
        HttpSession httpSession=null;
        Session session=null;
        String ssid;


        try{
            resultTemplate=cfg.getTemplate(resultTemplateName);
            errorTemplate=cfg.getTemplate(errorTemplateName);
            errorTemplate2=cfg.getTemplate(errorTemplate2Name);

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

        String pickupDateStr=request.getParameter("pickupDate");
        String returnDateStr=request.getParameter("time");
        String loc=request.getParameter("RL");
        String vt=request.getParameter("VT");

        if(pickupDateStr.equals("") || returnDateStr.equals("")){
            /*request.setAttribute("location",loc);
            request.setAttribute("vehicleType",vt);
            RequestDispatcher rd=request.getRequestDispatcher("/FindVehicle");*/
            try {
                errorTemplate2.process(root,toClient);
            } catch (TemplateException e) {
                e.printStackTrace();
            }


        }
        else {

            long custId = session.getUser().getId();
            int length_in_min = Integer.parseInt(returnDateStr) * 60;
            int hours = Integer.parseInt(returnDateStr);

            DateFormat df = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm");
            try {
                Date pickupD = df.parse(pickupDateStr);
                Timestamp fromTS1 = new Timestamp(pickupD.getTime());

                long pT = pickupD.getTime();
                long now = Calendar.getInstance().getTimeInMillis();

                if (pT - now < 0 || length_in_min <= 0 || hours > 72) {
                    /*request.setAttribute("location", loc);
                    request.setAttribute("vehicleType", vt);
                    RequestDispatcher rd = request.getRequestDispatcher("/FindVehicle");*/
                    errorTemplate2.process(root,toClient);
                }
                else {
                    logicLayer.reserveVehicle(loc, fromTS1, length_in_min, custId, vt);

                    resultTemplate.process(root, toClient);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (RARException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        }
    }
}
