package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.entity.impl.HourlyPriceImpl;
import edu.uga.cs.rentaride.entity.impl.RentARideParamsImpl;
import edu.uga.cs.rentaride.entity.RentARideParams;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

//import edu.uga.cs.rentaride.entity.RentARideParams;
public class RentARideParamsManager {
	private String driver,url,pass,user;
	
	  public RentARideParamsManager() {
	    	driver = "com.mysql.jdbc.Driver";  
	    	url = "jdbc:mysql://uml.cs.uga.edu/team4";
	    	pass = "seprocess";
	    	 user = "team4";
	    }
	  
	  public List<RentARideParams> getPrices() throws ClassNotFoundException{
		  List<RentARideParams> allPrices = new ArrayList<>();
		  Class.forName("com.mysql.jdbc.Driver");
	     	java.sql.Connection conn = null;
	    	 String sql = "SELECT * from RentARideParams";
	    	 	 ResultSet resultSet = null;
	        	 Statement stmt;
	        	 int membershipPrice = 0; 
	        	 int lateFees = 0;
	        	 
	        		try {
	         			conn = DriverManager.getConnection(url, user, pass);
	         			stmt = conn.createStatement();
	         			 if(stmt.execute(sql)){
	         	                ResultSet rs=stmt.getResultSet();
	         	                while(rs.next()){
	         	                	membershipPrice = rs.getInt("membershipPrice");
	         	                	lateFees = rs.getInt("lateFee");
	         	                	RentARideParamsImpl ri = new RentARideParamsImpl(membershipPrice, lateFees);
	         	                	allPrices.add(ri);
	         	                
	         	                }
	         	       	 }
	         	       	 
	         		} catch (SQLException e) {
	         			// TODO Auto-generated catch block
	         			e.printStackTrace();
	         		}
		  
		  return allPrices;
		  
		  
	  }
	  
		public void updatePrices(String column1,int newPrice) throws ClassNotFoundException {
			Class.forName("com.mysql.jdbc.Driver");
		   	java.sql.Connection conn = null;
			System.out.println("i'm here in update first name");
		 	   
				String sql = "UPDATE RentARideParams SET ";
				sql += column1+" = "+newPrice+" ";
				sql += "WHERE ID = "+1;
				System.out.println(sql);
				Statement stmt;
				
				  try {
					conn = DriverManager.getConnection(url, user, pass);
					stmt = conn.createStatement();
			 	    stmt.executeUpdate(sql);
				} catch (SQLException e) {
					System.out.println("something not working");
					e.printStackTrace();
				}
			}
		
		public int getMembership() throws ClassNotFoundException {
			 Class.forName("com.mysql.jdbc.Driver");
		     	java.sql.Connection conn = null;
		    	 String sql = "SELECT * from RentARideParams";
		    	 	 ResultSet resultSet = null;
		        	 Statement stmt;
		        	 int membershipPrice = 0; 
		        	 
		        		try {
		         			conn = DriverManager.getConnection(url, user, pass);
		         			stmt = conn.createStatement();
		         			 if(stmt.execute(sql)){
		         	                ResultSet rs=stmt.getResultSet();
		         	                while(rs.next()){
		         	                	membershipPrice = rs.getInt("membershipPrice");
		         	                }
		         	       	 }
		         	       	 
		         		} catch (SQLException e) {
		         			// TODO Auto-generated catch block
		         			e.printStackTrace();
		         		}
		        		return membershipPrice;
		}
}
