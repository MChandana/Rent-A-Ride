package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.UserStatus;
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
import javax.sql.rowset.serial.SerialException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/UpdateUserProfile")
public class UpdateUserProfile extends HttpServlet{

    private static final long serialVersionUID=1L;
    static String templateDir="WEB-INF/Templates";
    static String resultTemplateName="UserProfile-result.ftl";
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

        //email uniquely identifies customer. It's not updated
        String email=session.getUser().getEmail();

        String firstname=req.getParameter("firstname");
        String lastname=req.getParameter("lastname");
        String username=req.getParameter("username");
        //String email=req.getParameter("email");
        String address=req.getParameter("address");
        String licState=req.getParameter("licState");
        String licNumber=req.getParameter("licNumber");
        String CCNumber=req.getParameter("ccNumber");;
        String CCExpirationStr=req.getParameter("ccExpiration");
        DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
        Date CCExpiration=null;
        java.sql.Date CCExpsql=null;
        try {
            System.out.println("date str "+CCExpirationStr);

            CCExpiration = df.parse(CCExpirationStr);
            System.out.println("date "+CCExpiration);

             CCExpsql=new java.sql.Date(CCExpiration.getTime());
            System.out.println("sql date "+CCExpsql);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            logicLayer.updateCustomer(firstname,lastname,username,email,address,licNumber,licState,CCNumber,CCExpsql);
            RequestDispatcher rds=getServletContext().getRequestDispatcher("/UserProfile");
            rds.forward(req,res);
        } catch (RARException e) {
            e.printStackTrace();
        }
        toClient.close();

    }
}
