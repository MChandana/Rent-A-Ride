package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;

import edu.uga.cs.rentaride.object.ObjectLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalLocationManager{

    private ObjectLayer objectLayer = null;
    private Connection conn = null;
    private String driver = null; 
    private String url = null; 
    private String pass = null;
    private String user = null;
    
    public RentalLocationManager() {
    	driver = "com.mysql.jdbc.Driver";  
    	url = "jdbc:mysql://uml.cs.uga.edu/team4";
    	pass = "seprocess";
    	 user = "team4";
    }
    	   
    	 

    public RentalLocationManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void store(RentalLocation rentalLocation) throws RARException{
        String insertRentalLocationSQL = "insert into RentalLocation(name, address, capacity) values(?,?,?)";
        String updateRentalLocationSQL = "update RentalLocation set name=? address=? capacity=? where ID=?";
        PreparedStatement stmt = null;
        int inscnt;
        long rentalLocationID;

        try{
            System.out.println("in try store RL RLM");
            if(!rentalLocation.isPersistent()){
                stmt = (PreparedStatement)conn.prepareStatement(insertRentalLocationSQL);
            }
            else{
                stmt = (PreparedStatement)conn.prepareStatement(updateRentalLocationSQL);
            }

            if(rentalLocation.getName() != null){
                stmt.setString(1, rentalLocation.getName());
            }
            else{
                throw new RARException("RentalLocationManager.save: Couldn't save this location: Name is undefined!");
            }

            if(rentalLocation.getAddress() != null){
                stmt.setString(2, rentalLocation.getAddress());
            }
            else{
                throw new RARException("RentalLocationManager.save: Couldn't save this location: Address is undefined!");
            }

            if(rentalLocation.getCapacity() != 0){
                stmt.setInt(3, rentalLocation.getCapacity());
            }
            else{
                throw new RARException("RentalLocationManager.save: Couldn't save this location: Capacity is undefined!");
            }

            inscnt = stmt.executeUpdate();

            if(!rentalLocation.isPersistent()){
                if(inscnt == 1){
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet rs = stmt.getResultSet();
                        while(rs.next()){
                            rentalLocationID = rs.getLong(1);
                            if(rentalLocationID > 0){
                                rentalLocation.setId(rentalLocationID);
                            }
                        }
                    }
                }
                System.out.println("stored RL in RLM successfully");
            }
            else{
                if(inscnt < 1){
                    throw new RARException("RentalLocationManager.save: Couldn't save this location.");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RARException("RentalLocationManager.save: Failed to create RentalLocation.");
        }
    }

    public List<RentalLocation> restore(RentalLocation modelRentalLocation) throws RARException{
        String sql = "select * from RentalLocation";
        List<RentalLocation> rentalLocations = new ArrayList<>();
        Statement stmt = null;

        System.out.println("in RLM restore RL");

        /*StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        

        condition.setLength(0);

        query.append(selectRentalLocationSQL);

        if(modelRentalLocation != null) {
            if (modelRentalLocation.getId() >= 0) {
                query.append(" where RentalLocationID = '" + modelRentalLocation.getId() + "'");
            } else if (modelRentalLocation.getName() != null) {
                query.append(" where Name = '" + modelRentalLocation.getName() + "'");
            } else {
                if (modelRentalLocation.getAddress() != null) {
                    condition.append(" address = '" + modelRentalLocation.getAddress() + "'");
                }
                if (modelRentalLocation.getCapacity() != 0) {
                    if (condition.length() > 0) {
                        condition.append(" and capacity = '" + modelRentalLocation.getCapacity());
                    } else {
                        condition.append(" capacity = '" + modelRentalLocation.getCapacity() + "'");
                    }
                }
            }
        }*/
        try{
            stmt = conn.createStatement();
            System.out.println("after creating stmnt and conn are "+stmt+" , "+conn);
            if(stmt.execute(sql)){
                System.out.println("in if RLM execute");
                ResultSet rs = stmt.getResultSet();

                long rentalLocationID;
                String name;
                String address;
                int capacity;
                System.out.println("before while rs.next");
                while(rs.next()){
                    name = rs.getString(1);
                    rentalLocationID = rs.getLong(2);

                    address = rs.getString(3);

                    capacity = rs.getInt(4);

                    RentalLocation rentalLocation = objectLayer.createRentalLocation(name, address, capacity);
                    rentalLocation.setId(rentalLocationID);

                    rentalLocations.add(rentalLocation);
                }
            }

            return rentalLocations;
        }catch(Exception e){
            throw new RARException("RentalLocationManager.restore: Couldn't restore persistent RentalLocation object; Root Cause: " + e);
        }
    }

    public void delete(RentalLocation rentalLocation) throws RARException{
        String deleteRentalLocationSQL = "update RentalLocation set ID=0 where ID=?";
        PreparedStatement stmt = null;
        int delcnt;

        if(!rentalLocation.isPersistent()){
            return;
        }

        try{
            stmt = (PreparedStatement)conn.prepareStatement(deleteRentalLocationSQL);
            stmt.setLong(1, rentalLocation.getId());

            delcnt = stmt.executeUpdate();

            if(delcnt == 1)
                return;
            else
                throw new RARException("Failed to delete the Rental Lccation");
        }catch(Exception e){
            throw new RARException("RentalLocationManager.deleteRentalLocation: Couldn't delete the Rental Location.");
        }
    }

	public static void storeReservationVehicleType(Reservation reservation, VehicleType vehicleType) throws RARException {
		String insertReservationVehicleTypesql="update Vehicle set RentalID='"+reservation.getId()+"'"+ " where RentalID='"+reservation.getId()+"'";

        PreparedStatement stmt=null;
        int updcnt;

        if(!vehicleType.isPersistent()){
            return;
        }

        try {

            updcnt = stmt.executeUpdate(insertReservationVehicleTypesql);
            if(updcnt==1){
                return;
            }
            else
                throw new RARException("RentalLocationManager.storeReservationVehicleType: failed to save vehicletype for the vehicle");

        }catch (Exception e){
            throw new RARException("RentalLocationManager.storeRerservationVehicleType: failed to save vehicletype for the vehicle");
        }
	}

	public void storeVehicleRentalLocation(Vehicle vehicle, RentalLocation rentalLocation) throws RARException{
		String insertReservationVehicleTypesql="update RentalLocation set ID='"+rentalLocation.getId()+"'"+ " where VehicleID='"+vehicle.getId()+"'";

        PreparedStatement stmt=null;
        int updcnt;

        if(!rentalLocation.isPersistent()){
            return;
        }

        try {

            updcnt = stmt.executeUpdate(insertReservationVehicleTypesql);
            if(updcnt==1){
                return;
            }
            else
                throw new RARException("RentalLocationManager.storeReservationVehicleType: failed to save vehicletype for the vehicle");

        }catch (Exception e){
            throw new RARException("RentalLocationManager.storeRerservationVehicleType: failed to save vehicletype for the vehicle");
        }
	}
	
	public void updateRentalLocation(String column1, String value,String name) throws RARException, ClassNotFoundException, SQLException {
		//STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");
	   
		String sql = "UPDATE RentalLocation SET ";
		sql += column1 +" = '"+value+"' ";
		sql += "WHERE name = '"+name+"'";
		Statement stmt;
		
		  conn = DriverManager.getConnection(url, user, pass);
	      stmt = conn.createStatement();
	      stmt.executeUpdate(sql);
		
		 
	

		
	}
	
	public void updateRentalLocation(String column1, int value,String name) throws RARException, SQLException, ClassNotFoundException {
		//STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");
	      
		String sql = "UPDATE RentalLocation SET ";
		sql += column1 +" = "+value+" ";
		sql += "WHERE name = '"+name+"'";
		Statement stmt;
		  conn = DriverManager.getConnection(url, user, pass);
	      stmt = conn.createStatement();
	      stmt.executeUpdate(sql);

		
	}

	
}