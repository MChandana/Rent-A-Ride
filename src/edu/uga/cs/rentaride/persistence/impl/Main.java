package edu.uga.cs.rentaride.persistence.impl;

import java.sql.SQLException;

import edu.uga.cs.rentaride.RARException;

public class Main {
	public static void main(String [] args) {
		DBTester db = new DBTester();
		SelectTest st = new SelectTest();
		try {
			db.Connect();
			st.getInfo();
		} catch (ClassNotFoundException e) {
			System.out.println("Driver was not found");
		} catch (SQLException e) {
			System.out.println("WOuld not connect to database");
		} catch (RARException e) {
			e.printStackTrace();
		}
	}
}
