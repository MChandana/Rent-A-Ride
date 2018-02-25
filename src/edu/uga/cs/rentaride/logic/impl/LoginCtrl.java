package edu.uga.cs.rentaride.logic.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.impl.AdministratorImpl;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.AdministratorManager;
import edu.uga.cs.rentaride.persistence.impl.CustomerManager;
import edu.uga.cs.rentaride.persistence.impl.DBTester;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.rentaride.session.Session;

public class LoginCtrl {
	boolean isCorrect;
	
	public boolean isCorrect(String email, String password) throws SQLException {
		boolean checker;
		ObjectLayerImpl objectLayer = null;
		objectLayer = new ObjectLayerImpl();
		DBTester db = new DBTester();
		Connection conn = null;
		
		try {
			db.Connect();
		} catch (ClassNotFoundException e) {
			System.out.println("Driver was not found");
		} catch (SQLException e) {
			System.out.println("WOuld not connect to database");
		} catch (RARException e) {
			e.printStackTrace();
		}
		
		conn = db.getConnection();
		
		AdministratorManager ad = new AdministratorManager(conn, objectLayer);
		
		checker = ad.isCorrect(email, password);
		
		return checker;
	}
	
	public boolean UserCorrect(String email, String password) throws SQLException {
		boolean checker;
		ObjectLayerImpl objectLayer = null;
		DBTester db = new DBTester();
		Connection conn = null;
		
		try {
			db.Connect();
		} catch (ClassNotFoundException e) {
			System.out.println("Driver was not found");
		} catch (SQLException e) {
			System.out.println("WOuld not connect to database");
		} catch (RARException e) {
			e.printStackTrace();
		}
		
		conn = db.getConnection();		
		CustomerManager ad = new CustomerManager(conn, objectLayer);
		
		checker = ad.isCorrect(email, password);
		
		return checker;
	}
	
	public List<RentalLocation> getAllLocations() throws RARException{
		ObjectLayerImpl objectLayer = null;
		DBTester db = new DBTester();
		PersistenceLayer persistence = null;
		Connection conn = null;
		conn = db.getConnection();
        objectLayer = new ObjectLayerImpl();
        persistence = new PersistenceLayerImpl( conn, objectLayer );
        objectLayer.setPersistence( persistence );
        RentalLocation rentalLocation1;
        rentalLocation1 = objectLayer.createRentalLocation();
        List<RentalLocation> allRentalLocations;
        allRentalLocations = objectLayer.findRentalLocation(rentalLocation1);
        
		return allRentalLocations;
	}
	
	public String login(Session session, String email, String password) {
		
		return null;
	}

}
