package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTest {
	Statement stmt = null;
	
	public void getInfo() throws SQLException{
	
		System.out.println("Creating the statement");
		stmt = DBTester.conn.createStatement();
		
		String sql;
		sql = "SELECT * FROM Admins";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			int adminID = rs.getInt("adminID");
			System.out.println(adminID);
		}
		rs.close();
		stmt.close();
		DBTester.conn.close();
	}
}
