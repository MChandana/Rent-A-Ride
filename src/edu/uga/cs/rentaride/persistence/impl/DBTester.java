package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.uga.cs.rentaride.RARException;

public class DBTester {
	static  Connection conn = null;
	
	/*
	 * This is where we connect to the actual database
	 * Call this method any time every time where you 
	 * need the database
	 */
	public void Connect() throws SQLException, ClassNotFoundException, RARException{
			
			try{
	             Class.forName(DBSample.DriverName);
				System.out.println("getting driver in dbtester");
			}
	         catch(ClassNotFoundException ex){
	             System.out.println("Could not print to driver");
	         }
	         try{
				 System.out.println("before creating connection in dbtester");

				 conn =DriverManager.getConnection(DBSample.DbUrl,DBSample.DbUserName,DBSample.DbPswd);
				 System.out.println("creating connection in dbtester");
			 }
	         catch(SQLException ex){
	             System.out.println("DbUtil.connect: Unable to connect to database "+ ex.getMessage());
	             throw new RARException("Could not connect to database"+ex.getMessage());
	         }
	         System.out.println("It did it!!");
	     }//end of connect method
	
	public Connection getConnection() {
		return conn;
	}
	
	/*
	 * disables the auto commit for the current connection
	 */
	public static void disableAutoCommit(Connection conn) throws RARException{
		try {
			conn.setAutoCommit(false);
		}
		catch(SQLException ex) {
			System.out.println("DbUtil.connect: Unable to connect to database "+ ex.getMessage());
            throw new RARException("Could not connect to database"+ex.getMessage());
		}
	}//end of disableAutoCommit
	
	/*
	 * enables the auto commit for the current connection
	 */
	public static void enableAutoCommit(Connection conn) throws RARException {
		try {
			conn.setAutoCommit(true);
		}
		catch(SQLException ex) {
			System.out.println("DbUtil.connect: Unable to connect to database "+ ex.getMessage());
            throw new RARException("Could not connect to database"+ex.getMessage());
		}
	}//end of enableAutoCommit
	
	/*
	 * commit the transaction for the current connection
	 */
	
	public static void commit(Connection conn) throws RARException {
		try {
			conn.commit();
		}
		catch(SQLException ex) {
			System.out.println("DbUtil.connect: Unable to connect to database "+ ex.getMessage());
            throw new RARException("Could not connect to database"+ex.getMessage());
		}
	}//end of commit method 
	
	/**
	 * Last method, the roll back method for the current connection
	 */
	
	public static void rollback(Connection conn) throws RARException{
		try {
			conn.rollback();
		}
		catch(SQLException ex) {
			System.out.println("DbUtil.connect: Unable to connect to database "+ ex.getMessage());
            throw new RARException("Could not connect to database"+ex.getMessage());
		
		}
	}//end of rollback method
	
}//end of DB Tester class
	


