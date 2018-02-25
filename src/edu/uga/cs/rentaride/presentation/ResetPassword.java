package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.logic.impl.UpdatePasswordCtrl;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/resetPassword")
public class ResetPassword extends HttpServlet{
    private static final long serialVersionUID = 1L;




    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{




       /* httpSession = req.getSession();
        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid != null ) {
            System.out.println( "Already have ssid: " + ssid );
            session = SessionManager.getSessionById( ssid );
            System.out.println( "Connection: " + session.getConnection() );
        }
        else
            System.out.println( "ssid is null" );

        Map<String,Object> root=new HashMap<>();

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
*/

        String email=req.getParameter("email");
        String firstname=req.getParameter("firstName");
        String lastname=req.getParameter("lastName");
        boolean done=false;

        System.out.println("req email pres "+email);
        System.out.println("req fn pres "+firstname);
        System.out.println("req ln pres "+lastname);


        String changedPassword1=req.getParameter("reset_password1");
        String changedPassword2=req.getParameter("reset_password2");
        if(!changedPassword1.equals(changedPassword2)){
            res.sendRedirect("PasswordMismatch.html");
        }
        else {
            try {
                UpdatePasswordCtrl updatePasswordCtrl = new UpdatePasswordCtrl();
                done=updatePasswordCtrl.updatePassword(email,firstname,lastname,changedPassword1);
            } catch (RARException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(done){
                res.sendRedirect("resetSuccess.html");
            }
            else{
                res.sendRedirect("resetError.html");
            }
        }
    }
}
