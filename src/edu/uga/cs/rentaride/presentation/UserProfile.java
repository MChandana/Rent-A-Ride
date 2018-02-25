package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.UserStatus;
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
import java.util.*;

@WebServlet("/UserProfile")
public class UserProfile extends HttpServlet{
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

        List<Customer> customers=new ArrayList<>();
        String name=null;
        String username=null;
        String email=null;
        String address=null;
        Date createdDate=null;
        Date membershipUntil=null;
        String licState=null;
        String licNumber=null;
        String CCNumber=null;
        Date CCExpiration=null;
        UserStatus status=null;

        try {

            customers=logicLayer.findAllCustomers();
        } catch (RARException e) {
            e.printStackTrace();
        }

        for(Customer customer:customers){
            if(customer.getEmail().equals(session.getUser().getEmail())){
                name=customer.getFirstName()+" "+customer.getLastName();
                username=customer.getUserName();
                email=customer.getEmail();
                address=customer.getAddress();
                createdDate=customer.getCreatedDate();
                membershipUntil=customer.getMemberUntil();
                licState=customer.getLicenseState();
                licNumber=customer.getLicenseNumber();
                CCNumber=customer.getCreditCardNumber();
                CCExpiration=customer.getCreditCardExpiration();
                status=customer.getUserStatus();
            }
        }

        root.put("name",name);
        root.put("username",username);
        root.put("email",email);
        root.put("address",address);
        root.put("createdDate",createdDate);
        root.put("memberUntil",membershipUntil);
        root.put("licState",licState);
        root.put("licNumber",licNumber);
        root.put("CCNumber",CCNumber);
        root.put("CCExpiration",CCExpiration);
        root.put("licState",licState);
        root.put("status",status);

        try {
            resultTemplate.process(root,toClient);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        toClient.close();

    }

}
