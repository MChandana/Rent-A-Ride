package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.CustomerImpl;
import edu.uga.cs.rentaride.entity.impl.RentalImpl;
import edu.uga.cs.rentaride.entity.impl.RentalLocationImpl;
import edu.uga.cs.rentaride.entity.impl.ReservationImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleTypeImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationManager {

    private ObjectLayer objectLayer=null;
    private Connection conn=null;
    private String driver,url,pass,user;

    public ReservationManager(Connection conn,ObjectLayer objectLayer) {
        this.objectLayer = objectLayer;
        this.conn = conn;
    }
    
    public ReservationManager(){
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://uml.cs.uga.edu/team4";
        pass = "seprocess";
        user = "team4";
    }

    public void store(Reservation reservation) throws RARException {
    	  String insertReservationsql="insert into Reservation(RentalLocation,pickUpDate,length_in_minutes,cancelled,customerID,vehicleType) values(?,?,?,?,?,?)";
          String updateReservationsql="update Reservation set RentalLocation=? pickUpDate=? length_in_minutes=? cancelled=? customerID=? vehicleType=? where ID=?";
          PreparedStatement stmt=null;
          int inscnt;
          long ID;

          try{

              if(!reservation.isPersistent()){
                  stmt=(PreparedStatement)conn.prepareStatement(insertReservationsql);
              }else {
                  stmt=(PreparedStatement)conn.prepareStatement(updateReservationsql);
              }

              if(reservation.getRentalLocation() !=null){
                  stmt.setString(1,reservation.getRentalLocation().getName());
              }else{
                  throw new RARException("ReservationManager.save: couldnt save reservation location is invalid");
              }

              if(reservation.getPickupTime()!=null){
            	  Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            	  stmt.setTimestamp(2,currentTimestamp);
              }else{
                  throw new RARException("CustomerManager.save: couldn't save this person: LastName is undefined");
              }

              if(reservation.getLength()!=0){
                  stmt.setInt(3,reservation.getLength());
              }else{
                  throw new RARException("CustomerManager.save: couldn't save this person: UserName is undefined");
              }

              if(reservation.getCanceled() !=null){
                  stmt.setBoolean(4,reservation.getCanceled());
              }else{
                  throw new RARException("CustomerManager.save: couldn't save this person: Password is undefined");
              }

              if(reservation.getCustomer() != null){
                  stmt.setLong(5,reservation.getCustomer().getId());
              }else{
                  throw new RARException("CustomerManager.save: couldn't save this person: Email is undefined");
              }

              if(reservation.getVehicleType()!=null){
                  stmt.setString(6,reservation.getVehicleType().getName());
              }else{
                  throw new RARException("CustomerManager.save: couldn't save this person: address is undefined");
              }


              inscnt=stmt.executeUpdate();

              if(!reservation.isPersistent()){
                  if(inscnt==1){
                      String sql="select last_insert_id()";
                      if(stmt.execute(sql)){
                          ResultSet rs=stmt.getResultSet();

                          while(rs.next()){
                              ID=rs.getLong(1);
                              if(ID>0){
                                 reservation.setId(ID);
                              }
                          }
                      }
                  }
              }
              else{
                  if(inscnt<1)
                          throw new RARException("CustomerManager.save: Failed to save Customer");
              }

          }catch (SQLException e){
              e.printStackTrace();
              throw new RARException("CustomerMaanger.save: failed to save Customer"+e);
          }
    }

    public List<Reservation> restore(Reservation reservation) throws RARException {

        String sql="select * from Reservation"+";";
        Statement stmt=null;
        List<Reservation> reservations=new ArrayList<>();
        System.out.print("im here!!, please help");
        try{
        	System.out.println(conn);
        	stmt=conn.createStatement();

            if(stmt.execute(sql)){

                ResultSet rs= stmt.getResultSet();

                long ID;
                long CustomerID = 0;
                String rentalLocation = null;
                RentalLocationImpl rl = new RentalLocationImpl();
                VehicleTypeImpl vt = new VehicleTypeImpl();
                Timestamp pickup;
                int length;
                boolean canceled;
                String vehicleType;
                CustomerImpl customer = new CustomerImpl();

                while(rs.next()){
                	ID = rs.getLong("ID");
                    rentalLocation = rs.getString("RentalLocation");
                    pickup = rs.getTimestamp("pickUpDate");
                    length = rs.getInt("length_in_minutes");
                    canceled = rs.getBoolean("Cancelled");
                    CustomerID = rs.getLong("CustomerID");
                    vehicleType = rs.getString("VehicleType");
                    System.out.println("this is where i get all the values");
               
                    vt.setName(vehicleType);
                    customer.setId(ID);
                    rl.setName(rentalLocation);
                    System.out.println("testing 1");
                    Reservation reservation1 = objectLayer.createReservation(pickup, length, vt, rl, customer);
                    reservation1.setId(ID);
                    reservations.add(reservation1);
                    System.out.println("testing 2");
                }
            }

            return reservations;

        }catch (Exception e){
            throw new RARException("ReservationManager.save: failed to restore persistent reservation "+e);
        }

    }
    

    public void storeRentalLocationReservation(RentalLocation rentalLocation,Reservation reservation) throws RARException{

        String insertRentalLocationReservationsql="update Comments set RentalLocationID='"+rentalLocation.getId()+"'"+ " where RentalLocationID='"+rentalLocation.getId()+"'";

        PreparedStatement stmt=null;
        int updcnt;

        if(!reservation.isPersistent()){
            return;
        }

        try {

            updcnt = stmt.executeUpdate();
            if(updcnt==1){
                return;
            }
            else
                throw new RARException("ReservationManager.storeRentalLocationReservation: failed to save reservation for the rental location");

        }catch (Exception e){
            throw new RARException("ReservationManager.storeRentalLocationReservation: failed to save reservation for the rental location");
        }

    }

    public RentalLocation restoreReservationRentalLocation(Reservation reservation) throws RARException{

        String restoreRentalLocationsql="select * from RentalLocation r, Reservation c where c.RentalLocationID=r.ID";

        RentalLocation rentalLocation=null;

        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        query.append( restoreRentalLocationsql );

        if(reservation!=null){

            if(reservation.getId()>0){
                query.append(" and ID='"+reservation.getId()+"'");
            }
            else{
                if(reservation.getPickupTime()!=null){
                    condition.append(" and pickup='"+ reservation.getPickupTime()+"'");
                }

                if(reservation.getLength()!=0){
                    condition.append(" and length='"+reservation.getLength()+"'");
                }

               if(reservation.getCanceled() != false){
                condition.append(" and canceled='"+reservation.getCanceled()+"'");
               }

                if(reservation.getCustomer().getId()>0){
                    condition.append(" and CustomerID='"+reservation.getCustomer().getId()+"'");
                }

                if(reservation.getRentalLocation().getId()>0){
                    condition.append(" and RentalLocationId='"+reservation.getRentalLocation().getId()+"'");
                }

		if(reservation.getVehicleType().getId()>0){
                    condition.append(" and VehicleTypeId='"+reservation.getVehicleType().getId()+"'");
                }

                if( condition.length() > 0 ) {
                    query.append( condition );
                }

            }
        }


        try{

            stmt=conn.createStatement();

            if(stmt.execute(query.toString())){

                ResultSet rs=stmt.getResultSet();

                long ID;
		String name;
		String address;
		int capacity;
                long ReservationID;
                long VehicleID;

                while(rs.next()){
                    ID=rs.getLong(1);
                    name=rs.getString(2);
                    address=rs.getString(3);
                    capacity=rs.getInt(4);
                    ReservationID=rs.getLong(5);
                    VehicleID=rs.getLong(6);

                    rentalLocation=objectLayer.createRentalLocation();

                    rentalLocation.setId(ID);
                    rentalLocation.setName(name);
                    rentalLocation.setAddress(address);
                    rentalLocation.setCapacity(capacity);

                }

                return rentalLocation;

            }

        }catch (Exception e){
            throw new RARException("ReservationManager.restoreRentalLocationReservation: failed to restore persistent rental location for this reservation");
        }

        throw new RARException("ReservationManager.restoreRentalLocationReservation: failed to restore persistent rental locationfor this reservation");
    }

    public List<Reservation> restoreRentalLocationReservation(RentalLocation rentalLocation) throws RARException {
        List<Reservation> reservations=new ArrayList<>();

        String selectRentalLocationReservationssql="select * from Reservations where RentalLocationID='"+rentalLocation.getId()+"'";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        query.append( selectRentalLocationReservationssql );

        try{
            stmt=conn.createStatement();

            if(stmt.execute(query.toString())){

                ResultSet rs=stmt.getResultSet();

                long ID;
                long CustomerID;
                long RentalLocationID;
                long VehicleTypeID;
                Timestamp pickup;
                int length;
                boolean canceled;

                ReservationImpl reservation=null;

                while(rs.next()){
		    ID=rs.getLong(1);
                    CustomerID=rs.getLong(2);
                    RentalLocationID=rs.getLong(3);
                    VehicleTypeID=rs.getLong(4);
                    pickup=rs.getTimestamp(5);
                    length=rs.getInt(6);
                    canceled=rs.getBoolean(7);

                    reservation= (ReservationImpl) objectLayer.createReservation();
                    reservation.setId(ID);
                    reservation.setPickupTime(pickup);
                    reservation.setLength(length);
		    reservation.setCanceled(canceled);

                    RentalLocation rentalLocation1=objectLayer.createRentalLocation();
                    rentalLocation1.setId(RentalLocationID);
                    reservation.setRentalLocation(Persistence.getPersistenceLayer().restoreRentalLocation(rentalLocation1).get(0));

                    //rentalLocation1.add(reservation);

                }
            }

            return reservations;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RARException("ReservationManager.restoreReservationRentalLocation: failed to restore list of reservations for this rental location");
        }

    }

    public void delete(Reservation reservation) throws RARException{

        String deleteReservationsql="delete from Reservation where ID=?";
        PreparedStatement stmt=null;
        int delcnt;

        if(!reservation.isPersistent()){
            return;
        }

        try{

            stmt=(PreparedStatement)conn.prepareStatement(deleteReservationsql);
            stmt.setLong(1,reservation.getId());

            delcnt=stmt.executeUpdate();

            if(delcnt==0){
                throw new RARException("ReservationManager.delete: failed to delete this reservation");
            }

        }catch (SQLException e){
            throw new RARException("ReservationManager.delete: failed to delete this reservation");
        }

    }

    public void deleteReservationRentalLocation(RentalLocation rentalLocation,Reservation reservation) throws RARException{

        String deleteReservationsql="update Rervations set RentalLocationID=0 where RentalLocationID='"+rentalLocation.getId()+"'";
        Statement stmt=null;
        int delcnt;

        try {
            stmt=conn.createStatement();
            delcnt=stmt.executeUpdate(deleteReservationsql);
            if(delcnt==1){
                return;
            }
            else{
                throw new RARException("ReservationManager.deleteReservationRentalLocation: failed to delete reservation for this rental location");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RARException("ReservationManager.deleteReservationRentalLocation: failed to delete reservation for this rental location");
        }


    }

    public void updateReservation(Customer customer) {
		String sql = "update Reservation set Cancelled = 1 where CustomerID = "+customer.getId()+";";
		Statement stmt = null;
		
		int delcnt;

        try {
            stmt=conn.createStatement();
            delcnt=stmt.executeUpdate(sql);
            if(delcnt==1){
                return;
            }
            else{
                throw new RARException("ReservationManager.deleteReservationRentalLocation: update reservation for customer");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
				throw new RARException("ReservationManager.deleteReservationRentalLocation: failed to delete reservation for this customer");
			} catch (RARException e1) {
				e1.printStackTrace();
			}
        }
		
		
	}
    
    public List<Reservation> restoreID(long CID) throws RARException, SQLException {
    	conn = DriverManager.getConnection(url, user, pass);
        String sql="select * from Reservation where CustomerID = "+CID+";";
        Statement stmt=null;
        List<Reservation> reservations=new ArrayList<>();
        System.out.print("im here!!, please help");
        try{
        	System.out.println(conn);
        	stmt=conn.createStatement();

            if(stmt.execute(sql)){

                ResultSet rs= stmt.getResultSet();

                long ID;
                long CustomerID = 0;
                String rentalLocation = null;
                RentalLocationImpl rl = new RentalLocationImpl();
                VehicleTypeImpl vt = new VehicleTypeImpl();
                Timestamp pickup;
                int length;
                boolean canceled;
                String vehicleType;
                CustomerImpl customer = new CustomerImpl();

                while(rs.next()){
                	ID = rs.getLong("ID");
                    rentalLocation = rs.getString("RentalLocation");
                    pickup = rs.getTimestamp("pickUpDate");
                    length = rs.getInt("length_in_minutes");
                    canceled = rs.getBoolean("Cancelled");
                    CustomerID = rs.getLong("CustomerID");
                    vehicleType = rs.getString("VehicleType");
                    System.out.println("this is where i get all the values");
               
                    vt.setName(vehicleType);
                    customer.setId(ID);
                    rl.setName(rentalLocation);
                    System.out.println("testing 1");
                    ReservationImpl reservation1 = new ReservationImpl();
                    reservation1.setCustomer(customer);
                    reservation1.setCanceled(canceled);
                    reservation1.setId(ID);
                    reservation1.setLength(length);
                    reservation1.setRentalLocation(rl);
                    reservation1.setPickupTime(pickup);
                    reservation1.setVehicleType(vt);
                    
                    
                    
                    
                    reservations.add(reservation1);
                    System.out.println("testing 2");
                }
            }


        }catch (Exception e){
           e.printStackTrace();
        }
		return reservations;

    }
    
}
