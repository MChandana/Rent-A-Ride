package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.impl.CustomerImpl;
import edu.uga.cs.rentaride.entity.impl.RentalImpl;
import edu.uga.cs.rentaride.entity.impl.RentalLocationImpl;
import edu.uga.cs.rentaride.entity.impl.ReservationImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleTypeImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class RentalManager {
	 private ObjectLayer objectLayer = null;
	    private Connection   conn = null;
	    PreparedStatement    stmt = null;
	    private String driver, url, pass, user;

	    public RentalManager( Connection conn, ObjectLayer objectLayer )
	    {
	        this.conn = conn;
	        this.objectLayer = objectLayer;
	    }
	    
	    public RentalManager(){
	        driver = "com.mysql.jdbc.Driver";
	        url = "jdbc:mysql://uml.cs.uga.edu/team4";
	        pass = "seprocess";
	        user = "team4";
	    }
	    
	    public void store(Rental rental) throws RARException{
		    String insertVehicleTable = "INSERT INTO Rental"+"(pickUpDate, returnDate, Late, Charges, CustomerID, ReservationID, VehicleID) VALUES"+
		            "(?,?,?,?,?,?,?)";
		    String upDateVehicleTable = "UPDATE Rental SET pickUpDate=? returnDate=? Late=? Charges=? CustomerID=? ReservationID=? VehicleID=? where ID = ?";
		    PreparedStatement stmt=null;
	        int inscnt;
	        long ID;
		            
		            try {
		            	
		            	if(!rental.isPersistent() )
		                    stmt = (PreparedStatement) conn.prepareStatement(insertVehicleTable);
		                else
		                    stmt = (PreparedStatement) conn.prepareStatement(upDateVehicleTable);

		                if(rental.getPickupTime() != null ) { // name is unique unique and non null
		                	 java.util.Date jDate=rental.getPickupTime();
		                	java.sql.Date sDate=new java.sql.Date(jDate.getTime());
		                	stmt.setDate(1,sDate);
		                }
		                else
							throw new RARException("Pickup Date Cannot be null");
		                
		               if(rental.getReturnTime() != null) {
		            	   java.util.Date jDate=rental.getPickupTime();
	               			java.sql.Date sDate=new java.sql.Date(jDate.getTime());
	               			stmt.setDate(2,sDate);
		               }
		               else
		            	   throw new RARException("Return Date Cannot be null");
		               
		               if(rental.getLate() != false || rental.getLate() != true)
		            	   stmt.setBoolean(3,rental.getLate());
		               else
						   throw new RARException("Late shouldn't be null");
		               
		               if(rental.getCharges() > 0 )
		            	   stmt.setInt(4,rental.getCharges());
		               else
						   throw new RARException("Charges can't be null");
		               
		               if(rental.getCustomer() != null) {
		            	  // rental.getCustomer().getId();
		            	   stmt.setLong(5, rental.getCustomer().getId());
		            	  // System.out.println(rental.getCustomer().getId());
		               }
		               else
						   throw new RARException("CustomerId can't be null");
		               
		               
		               if(rental.getReservation() != null)
		            	   stmt.setLong(6, rental.getReservation().getId());
		               else
						   throw new RARException("Reservation can't be null");
		               
		               if(rental.getVehicle() != null)
		            	   stmt.setLong(7, rental.getVehicle().getId());
		               else
						   throw new RARException("VehicleID can't be null");
		               
		               inscnt=stmt.executeUpdate();

		               if(!rental.isPersistent()){
		                   if(inscnt==1){
		                       String sql="select last_insert_id()";
		                       if(stmt.execute(sql)){
		                           ResultSet rs=stmt.getResultSet();

		                           while(rs.next()){
		                               ID=rs.getLong(1);
		                               if(ID>0){
		                                  rental.setId(ID);
		                               }
		                           }
		                       }
		                   }
		               }
		               else{
		                   if(inscnt<1)
		                           throw new RARException("CustomerManager.save: Failed to save Customer");
		               }
		               
		            }
		            catch(SQLException ex) {
		            	ex.printStackTrace();
		            }
		    }
	    
	    public void storeVehicleRental( Vehicle vehicle, Rental rental ) throws RARException{
	    	 String insertVehicleRentalsql="update Rental set VehicleID='"+vehicle.getId()+"'"+ " where VehicleID='"+vehicle.getId()+"'";

	         PreparedStatement stmt=null;
	         int updcnt;

	         if(!rental.isPersistent()){
	             return;
	         }

	         try {

	             updcnt = stmt.executeUpdate(insertVehicleRentalsql);
	             if(updcnt==1){
	                 return;
	             }
	             else
	                 throw new RARException("CommentManager.storeRentalComment: failed to save comment for the rental");

	         }catch (Exception e){
	             throw new RARException("CommentManager.storeRentalComment: failed to save comment for the rental");
	         }
	    }
	    
	    public Vehicle restoreVehicleRental( Rental rental ) throws RARException{
	    	 String selectRentalsql="select * from Vehicle v, Rental r where r.VehicleID=v.ID";
		        Statement stmt=null;
		        Vehicle ve = null;
		        StringBuffer condition=new StringBuffer(100);
		        StringBuffer query=new StringBuffer(100);
		        List<Rental> rentals=new ArrayList<>();
		        condition.setLength(0);

		        query.append(selectRentalsql);
		        /*forming query based on administrator object*/
		        if(rental!=null){
		            if(rental.getId()>=0){
		                query.append(" where adminID= ' "+rental.getId()+"'");
		            }
		        
		            else if(rental.getPickupTime()!=null){
		                query.append(" where pickUpDate='"+rental.getPickupTime()+"'");
		            }
		            else if(rental.getReturnTime()!=null){
		                query.append(" where returnDate='"+rental.getReturnTime()+"'");
		            }
		            else{
		                if(rental.getLate() != false || rental.getLate() != true ){
		                    condition.append(" Late='"+rental.getLate()+"'");
		                }
		                
		                if(rental.getCharges() !=0){
		                    if(condition.length()>0){
		                        condition.append(" and");
		                        condition.append(" Charges='"+rental.getCharges()+"'");
		                    }
		                    else{
		                        condition.append(" CustomerID='"+rental.getCustomer().getId()+"'");

		                    }
		                
		                if(rental.getCustomer() !=null){
		                    if(condition.length()>0){
		                        condition.append(" and");
		                        condition.append(" CustomerID='"+rental.getCustomer().getId()+"'");
		                    }
		                    else{
		                        condition.append(" CustomerID='"+rental.getCustomer().getId()+"'");

		                    }
		                }
		                
		                if(rental.getReservation() !=null){
		                    if(condition.length()>0){
		                        condition.append(" and");
		                        condition.append(" ReservationID='"+rental.getReservation().getId()+"'");
		                    }
		                    else{
		                        condition.append(" ReservationID='"+rental.getReservation().getId()+"'");

		                    }
		                }
		                
		                if(rental.getVehicle() !=null){
		                    if(condition.length()>0){
		                        condition.append(" and");
		                        condition.append(" VehicleID='"+rental.getVehicle().getId()+"'");
		                    }
		                    else{
		                        condition.append(" VehicleID='"+rental.getVehicle().getId()+"'");

		                    }
		                }

		                if(condition.length()>0){
		                    query.append(" where ");
		                    query.append(condition);
		                }
		            }
		        }
		        }
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(query.toString())){
		                ResultSet rs=stmt.getResultSet();
		                int RentalID;
		                Date pickUpDate;
		                Date returnDate;
		                Boolean Late;
		                int Charges;
		                Customer CustomerID;
		                Reservation reservationID;
		                Vehicle vehicleID;

		                while(rs.next()){
		                    RentalID=rs.getInt(1);
		                    pickUpDate = rs.getDate(2);
		                    returnDate = rs.getDate(3);
		                    Late = rs.getBoolean(4);
		                    Charges = rs.getInt(5);
		                    
		                    CustomerImpl cs = new CustomerImpl();
		                    cs.setId(rs.getInt(6));
		                    CustomerID = cs;
		                    
		                    ReservationImpl rI = new ReservationImpl();
		                    rI.setId(rs.getInt(7));
		                    reservationID = rI;
		                    
		                    VehicleImpl vI = new VehicleImpl();
		                    vI.setId(rs.getInt(rs.getInt(8)));
		                    vehicleID = vI;
		                    
		                    ve = objectLayer.createVehicle();
		                   
		                    
		                }
		       
		              return ve;
		            }
		        }catch(Exception e){
		            throw  new RARException("AdministratorManager.restore: couldn't restore persistent administrator; Root cause: "+ e);
		        }
		            throw new RARException("AdministratorManager.restore: couldn't restore persistent administrator");
		
		        
	    }

	public List<Rental> restore(Rental rental) throws RARException{
		String sql="select * from Rental";
		Statement stmt=null;
		List<Rental> rentals=new ArrayList<>();


		try{
			stmt=conn.createStatement();
			if(stmt.execute(sql)){
				ResultSet rs=stmt.getResultSet();
				int RentalID;
				Date pickUpDate;
				Date returnDate;
				Boolean Late;
				int Charges;
				Customer Customer;
				int CustomerID;
				Reservation reservation;
				int ReservationID;
				Vehicle vehicle;
				int VehicleID;

				while(rs.next()){
					RentalID=rs.getInt(1);
					pickUpDate = rs.getDate(2);
					returnDate = rs.getDate(3);
					Late = rs.getBoolean(4);
					Charges = rs.getInt(5);
					System.out.println("~~~~~~~~~~~~~~~~after getting charges");

					CustomerID = rs.getInt(6);
					Customer customer=null;
					List<Customer> customers=objectLayer.findCustomer(null);
					for(Customer customer1:customers){
						if(customer1.getId()==CustomerID){
							customer=customer1;
						}
					}
					System.out.println("~~~~~~~~~~~~~~~~after getting customer");

					//rental.getCustomer().setId(CustomerID);
					//Customer = rental.getCustomer();
					//System.out.println(CustomerID);

					ReservationID = rs.getInt(7);
					System.out.println("RESid is "+ReservationID);
					Reservation res=objectLayer.createReservation();
					List<Reservation> reservations=objectLayer.findReservation(null);
					for(Reservation reservation1:reservations){
						System.out.println("grrr res for resid "+reservation1.getId());
						if(reservation1.getId()==ReservationID){
							System.out.println("grrr res if");

							res=reservation1;
							System.out.println("bug RentalM res if grr");
						}
					}
					System.out.println("~~~~~~~~~~~~~~~~after getting reservation");


					//rental.getReservation().setId(ReservationID);
					//reservation = rental.getReservation();


					VehicleID = rs.getInt(8);
					Vehicle veh=null;
					List<Vehicle> vehicles=objectLayer.findVehicle(null);
					for(Vehicle vehicle1:vehicles){
						if(vehicle1.getId()==VehicleID){
							veh=vehicle1;
						}
					}
					//rental.getVehicle().setId(VehicleID);
					//vehicle = rental.getVehicle();

					System.out.println("```````````````buggs reservation details");
					System.out.println("res id "+res.getId());

					Timestamp sdate=new Timestamp(pickUpDate.getTime());
					System.out.println("```````````````before creating rental");
					Rental re =objectLayer.createRental(sdate,res,veh);
					System.out.println("```````````````after creating rental");

					re.setId(RentalID);
					rentals.add(re);
					System.out.println("after getting all rentals");

				}

				return rentals;
			}
		}catch(Exception e){
			throw  new RARException("RentalManager.restore: couldn't restore persistent Rental; Root cause: "+ e);
		}
		throw new RARException("RentalManager.restore: couldn't restore persistent Rental");



	}
	    
	    public void delete(Rental rental) throws RARException{
	        String deleteRentalsql="delete from Rental where ID=?";
	        PreparedStatement stmt=null;
	        int delcnt;

	        if(!rental.isPersistent()){
	            return;
	        }

	        try{

	            stmt=(PreparedStatement)conn.prepareStatement(deleteRentalsql);
	            stmt.setLong(1,rental.getId());
	            delcnt=stmt.executeUpdate();

	            if(delcnt==0){
	                throw new RARException("RentalManager.delete: failed to delete this person");
	            }

	        }catch (SQLException e){
	            throw new RARException("RentalManager.delete: failed to delete this person "+e.getMessage());
	        }
	    }
	    
	    public void deleteVehicleRental( Vehicle vehicle, Rental rental ) throws RARException{
	    	String deleteRentalsql="update Rental set VehicleID=0 where RentalD='"+rental.getId()+"'";
	        Statement stmt=null;
	        int delcnt;

	        try {
	            stmt=conn.createStatement();
	            delcnt=stmt.executeUpdate(deleteRentalsql);
	            if(delcnt==1){
	                return;
	            }
	            else{
	                throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
	        }


	    }
	    
	    public List<Rental> restoreVehicleRental( Vehicle vehicle ) throws RARException{
	    	  String selectRentalsql="select * from Rental where VehicleID='"+vehicle.getId()+"'";
		        Statement stmt=null;
		        StringBuffer condition=new StringBuffer(100);
		        StringBuffer query=new StringBuffer(100);
		        List<Rental> rentals=new ArrayList<>();
		      
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(query.toString())){
		                ResultSet rs=stmt.getResultSet();
		                int RentalID;
		                Timestamp pickUpDate;
		                Date returnDate;
		                Boolean Late;
		                int Charges;
		                Customer CustomerID;
		                Reservation reservationID;
		                Vehicle vehicleID;

		                while(rs.next()){
		                    RentalID=rs.getInt(1);
		                    pickUpDate = rs.getTimestamp(2);
		                    returnDate = rs.getDate(3);
		                    Late = rs.getBoolean(4);
		                    Charges = rs.getInt(5);
		                    
		                    CustomerImpl cs = new CustomerImpl();
		                    cs.setId(rs.getInt(6));
		                    CustomerID = cs;
		                    
		                    ReservationImpl rI = new ReservationImpl();
		                    rI.setId(rs.getInt(7));
		                    reservationID = rI;
		                    
		                    VehicleImpl vI = new VehicleImpl();
		                    vI.setId(rs.getInt(rs.getInt(8)));
		                    vehicleID = vI;
		                    
		                    Rental re =objectLayer.createRental(pickUpDate, reservationID,vehicleID);
		                    re.setId(RentalID);
		                    rentals.add(re);
		                   
		                    
		                }
		        }
		        }catch(Exception e){
		            throw  new RARException("AdministratorManager.restore: couldn't restore persistent administrator; Root cause: "+ e);
		        }
		            throw new RARException("AdministratorManager.restore: couldn't restore persistent administrator");
		
		        
				
	    }

		public void restoreRentalLocationReservation(Rental rental, Reservation reservation) throws RARException {
			 String insertRentalLocationReservationsql="update Rental set ID='"+rental.getId()+"'"+ " where ReservationID='"+reservation.getId()+"'";

	         PreparedStatement stmt=null;
	         int updcnt;

	         if(!rental.isPersistent()){
	             return;
	         }

	         try {

	             updcnt = stmt.executeUpdate(insertRentalLocationReservationsql);
	             if(updcnt==1){
	                 return;
	             }
	             else
	                 throw new RARException("CommentManager.storeRentalComment: failed to save comment for the rental");

	         }catch (Exception e){
	             throw new RARException("CommentManager.storeRentalComment: failed to save comment for the rental");
	         }
			}

		public void deleteRentalReservation(Rental rental, Reservation reservation) throws RARException{
			String deleteRentalsql="delete Reservation where RentalID='"+rental.getId()+"'";
	        Statement stmt=null;
	        int delcnt;

	        try {
	            stmt=conn.createStatement();
	            delcnt=stmt.executeUpdate(deleteRentalsql);
	            if(delcnt==1){
	                return;
	            }
	            else{
	                throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
	        }


			}
		
		public void createRental(long CID,long VID) throws SQLException, RARException {
			System.out.println("-----------------------------------------");
			conn = DriverManager.getConnection(url, user, pass);
	        String sql="select * from Reservation where CustomerID = "+CID+";";
	        System.out.println(sql);
	       
	        
			
	        Statement stmts=null;
	        int delcnt;
	        
	     
	        
	        //Statement stmt=null;
	        //stmt=conn.createStatement();
	        
	        String insert = "INSERT INTO Rental"+"(pickUpDate, returnDate, Late, Charges, CustomerID, ReservationID, VehicleID) VALUES"+
		            "(?,?,?,?,?,?,?)";
	        
	        Statement stmt=null;
	        PreparedStatement ps = null;
	        long ID = 0;
            long CustomerID = 0;
            String rentalLocation = null;
            Timestamp pickup = null;
            int length;
            boolean canceled;
            String vehicleType = null;
            CustomerImpl customer = new CustomerImpl();
            long newID = 0;
            
            stmt= conn.createStatement();
	        
            if(stmt.execute(sql)){
            	System.out.println("----------------------------");
            ResultSet rs= stmt.getResultSet();

           while(rs.next()){
            	ID = rs.getLong("ID");
                rentalLocation = rs.getString("RentalLocation");
                pickup = rs.getTimestamp("pickUpDate");
                System.out.println("im here!!");
                System.out.println(pickup);
                length = rs.getInt("length_in_minutes");
                canceled = rs.getBoolean("Cancelled");
                CustomerID = rs.getLong("CustomerID");
                vehicleType = rs.getString("VehicleType");    
            
            }
        }
	        
	        sql = "select Vehicle.ID from Vehicle INNER JOIN Reservation on "+
	        		 "Vehicle.RentalLocation = Reservation.RentalLocation "+
	        		 "where Vehicle.VehicleType = '"+vehicleType+"' " +"and Vehicle.Status = 'INLOCATION'";
	        if(stmt.execute(sql)){

	            ResultSet rs= stmt.getResultSet();

	           while(rs.next()){
	            	newID = rs.getLong("ID");
	            }
	        }
	        
	        System.out.println(newID);
	        
	        try {
	        	System.out.println("did i even get here"); 
	        	ps = (PreparedStatement) conn.prepareStatement(insert);
	        	Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	        	//Timestamp ct = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	     
	        	 ps.setTimestamp(1, currentTimestamp);
	        	 
	        	 ps.setDate(2,null);
	        	 
	        	 
	        	 if(currentTimestamp.after(pickup)) {
	        		 ps.setBoolean(3,true);
	        	 }
	        	 else
	        		 ps.setBoolean(3,false);
	        	 
	        	 ps.setInt(4,0);
	        	 ps.setLong(5, CID);
	        	 ps.setLong(6, ID);
	        	 ps.setLong(7,newID);
	        	 ps.executeUpdate();
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
	        }
	        
	    	String Updatesql="Update Vehicle set Status ='"+VehicleStatus.INRENTAL+"'"+" where ID = "+newID+";";
	    			
	        
	       try {
	            stmts=conn.createStatement();
	            delcnt=stmt.executeUpdate(Updatesql);
	
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
	        }
	       
	       String vsql = "Update Reservation set Cancelled = 1 where ID = "+VID+";";
	       try {
	            stmts=conn.createStatement();
	            delcnt=stmt.executeUpdate(vsql);
	            if(delcnt==1){
	                return;
	            }
	            else{
	                throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
	        }       
	        
	   }//end of create Rental

		 public List<Rental> restoreC(Customer c) throws RARException, SQLException{
			 conn = DriverManager.getConnection(url, user, pass);   
			 String sql="select * from Rental where CustomerID = "+c.getId()+";";
		        Statement stmt=null;
		        List<Rental> rentals=new ArrayList<>();
		        System.out.println(sql);
		      
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(sql)){
		                ResultSet rs=stmt.getResultSet();
		                int RentalID;
		                Timestamp pickUpDate;
		                Timestamp returnDate;
		                Boolean Late;
		                int Charges;
		                //Customer Customer;
		                int CustomerID;
		                //Reservation reservation;
		                int ReservationID;
		                //Vehicle vehicle;
		                int VehicleID;
		                VehicleImpl v = new VehicleImpl();
		                ReservationImpl r = new ReservationImpl();
		                CustomerImpl cI = new CustomerImpl();
		                
		                while(rs.next()){
		                    RentalID=rs.getInt(1);
		                    pickUpDate = rs.getTimestamp(2);
		                    returnDate = rs.getTimestamp(3);
		                    Late = rs.getBoolean(4);
		                    Charges = rs.getInt(5);
		                    CustomerID = rs.getInt(6);
		                    ReservationID = rs.getInt(7);
		                    VehicleID = rs.getInt(8);
		                    v.setID(VehicleID);
		                    r.setId(ReservationID);
		                    cI.setId(CustomerID);
		                    
		                    
		                    RentalImpl re = new RentalImpl();
		                    re.setPickupTime(pickUpDate);
		                    re.setReturnTime(returnDate);
		                    re.setCharges(Charges);
		                    re.setCustomer(c);
		                    re.setReservation(r);
		                    re.setVehicle(v);
		                    re.setId(RentalID);
		                    rentals.add(re);
		                }
		       
		              return rentals;
		            }
		        }catch(Exception e){
		           e.printStackTrace();
		        	throw  new RARException("AdministratorManager.restore: couldn't restore persistent administrator; Root cause: "+ e);
		        }
		            throw new RARException("AdministratorManager.restore: couldn't restore persistent administrator");
		
		        
				
		    }
		 
		 public void updateCharges(long VID,long CID) throws SQLException, RARException {
			 Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	         conn = DriverManager.getConnection(url, user, pass);
			 String sql="select * from Rental where CustomerID = "+CID+";";
		        Statement stmt=null;
		        System.out.println(sql);
		        Boolean Late = null;
                Timestamp pickTime = null;
		      
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(sql)){
		            	ResultSet rs=stmt.getResultSet();
		                while(rs.next()){
		                	pickTime = rs.getTimestamp(2);
		                	Late = rs.getBoolean(4);
		                   }
		            }
		        }
		            catch(Exception e) {
		            	e.printStackTrace();
		            }
		        
		        sql="select * from RentARideParams";
		        int lateFee = 0;
		        System.out.println(sql);
		        
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(sql)){
		            	ResultSet rs=stmt.getResultSet();
		                while(rs.next()){
		                	lateFee = rs.getInt("lateFee");
		                   }
		            }
		        }
		            catch(Exception e) {
		            	e.printStackTrace();
		            }
		        
		        sql = "select ReservationID from Rental where ID = "+VID+";";
		        long ReservationID = 0;
		        System.out.println(sql);
		        
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(sql)){
		            	ResultSet rs=stmt.getResultSet();
		                while(rs.next()){
		                	ReservationID = rs.getLong("ReservationID");
		                   }
		            }
		        }
		            catch(Exception e) {
		            	e.printStackTrace();
		            }
		        
		        sql = "select * from Reservation where ID ="+ReservationID+";";
		        String vehicleType = null;
		        int length = 0;
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(sql)){
		            	ResultSet rs=stmt.getResultSet();
		                while(rs.next()){
		                	vehicleType = rs.getString("VehicleType");
		                	length = rs.getInt("length_in_minutes");
		                   }
		            }
		        }
		            catch(Exception e) {
		            	e.printStackTrace();
		            }
		        
		        int price = 0;
		        sql = "select Price from HourlyPrice"+";";
		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(sql)){
		            	ResultSet rs=stmt.getResultSet();
		                while(rs.next()){
		                	price = rs.getInt("Price");
		                   }
		            }
		        }
		            catch(Exception e) {
		            	e.printStackTrace();
		            }
		        
		        int finalCharges = 0;
		        if(Late) {
		        	finalCharges = lateFee;
		        	Calendar calendar = Calendar.getInstance();
		        	calendar.setTime(pickTime);
		        	int hours = calendar.get(Calendar.HOUR_OF_DAY);
		        	
		        	calendar.setTime(currentTimestamp);
		        	int hours2 = calendar.get(Calendar.HOUR_OF_DAY);
		        	
		        	finalCharges += (hours2-hours)*price;
		        }
		        
			 
			 
			 
			 
			 
			 
			
			 String vsql = "Update Rental set returnDate = '"+currentTimestamp+"' "+"where ID = "+VID+";";
			 Statement stmts=null;
			 int delcnt;
		       try {
		            stmts=conn.createStatement();
		            delcnt=stmt.executeUpdate(vsql);
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }     
		       
		       vsql = "Update Rental set Charges = "+finalCharges+" where ID = "+VID+";";
		       try {
		            stmts=conn.createStatement();
		            delcnt=stmt.executeUpdate(vsql);
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		            throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
		        } 
		       
		 }//end of method
		 
		 public void cancel(long VID) throws SQLException, RARException {
			 conn = DriverManager.getConnection(url, user, pass);
			 String sql = "Update Reservation set Cancelled = 1 where ID = "+VID+" ;";
			 System.out.println(sql);
			 Statement stmts = null;
			 int delcnt = 0;
			 
			 try {
		            stmts=conn.createStatement();
		            delcnt = stmts.executeUpdate(sql);
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		            throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicle for this rental");
		        } 
		 }
		
	    }
	   






