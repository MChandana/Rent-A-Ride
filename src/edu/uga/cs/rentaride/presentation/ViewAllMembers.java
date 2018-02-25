package edu.uga.cs.rentaride.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet("/ViewAllMembers")
public class ViewAllMembers extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String allMembers;
	private String allMembersFilter;
	private String selectMembers;
	private Part2 processor;
	private String templateDir = "/WEB-INF/Templates";
	
	
	public ViewAllMembers(){
		super();
	}
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		processor = new Part2(templateDir, getServletContext());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		allMembers = request.getParameter("allMembers");
		allMembersFilter = request.getParameter("allMembersFilter");
		selectMembers = request.getParameter("selectMembers");
		System.out.println(allMembers);
		if(allMembers != null){
			allMembers(request, response);
		}
	}
	
	protected void allMembers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession httpSession;
		String ssid;
		httpSession = request.getSession();
		ssid = (String) httpSession.getAttribute("ssid");
		DefaultObjectWrapperBuilder db = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23);
		SimpleHash root = new SimpleHash(db.build());
		Session session = null;
		session = SessionManager.getSessionById(ssid);
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		LogicLayer logicLayer = null;
		logicLayer = session.getLogicLayer();
		List<Customer> allCustomer = null;
		try{
			allCustomer = logicLayer.findAllCustomers();
		}catch(RARException e){
			System.out.println("Getting all members from login new from logic layer");
		}
		
		if(fname != null){
			if(!fname.equals("")){
				System.out.println(allCustomer.size());
				for(int i = 0; i < allCustomer.size(); i++){
					if(!allCustomer.get(i).getFirstName().toLowerCase().equals(fname.toLowerCase())){
						allCustomer.remove(i);
						i--;
					}
				}
			}
		}
		
		if(lname != null){
			if(!lname.equals("")){
				for(int i = 0; i < allCustomer.size(); i++){
					if(!allCustomer.get(i).getLastName().toLowerCase().equals(lname.toLowerCase())){
						allCustomer.remove(i);
						i--;
					}
				}
			}
		}
		
		root.put("allCustomer", allCustomer);
		String templateName = "ViewAllMembers-result.ftl";
		processor.processTemplate(templateName, root, request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
