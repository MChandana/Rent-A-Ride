package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@WebServlet("/Terminate")
public class Terminate extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String allMembers;
	private Part2 processor;
	private String templateDir = "/WEB-INF/Templates";
	private String email;
	private Configuration cfg;
	
	
	public Terminate(){
		super();
	}
	
	public void init(){
		cfg = new Configuration(Configuration.VERSION_2_3_25);
		cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/Templates");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Template errorTemplate = null;
		String errorTemplateName = "LoginError.ftl";
		HttpSession httpSession;
		Session session;
		LogicLayer logicLayer;
		BufferedWriter toClient = null;
		String ssid;
		
		email = request.getParameter("email");
		httpSession = request.getSession();
		try{
			errorTemplate = cfg.getTemplate(errorTemplateName);
		}catch(Exception e){
			System.out.println("Error int TerminateMembership" + e.toString());
		}
		
		Map<String, Object> root = new HashMap<>();
		toClient=new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),errorTemplate.getEncoding()));
        response.setContentType("text/html; charset=" + errorTemplate.getEncoding());
		
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
        
        if(email != null){
        	try {
				boolean worked = logicLayer.terminateMembership(email);
				if(worked == true){
					response.sendRedirect("TerminateSuccess.html");
				}
				else{
					response.sendRedirect("TerminateFailed.html");
				}
			} catch (RARException e) {
				e.printStackTrace();
			}
        }
        
		
	}
}
