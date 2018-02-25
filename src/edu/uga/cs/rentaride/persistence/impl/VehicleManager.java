package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.RentalLocationImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleTypeImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class VehicleManager
{
    private ObjectLayer objectLayer = null;
    public static Connection   conn = null;
    PreparedStatement stmt = null;
    private String driver, url, pass, user;
    
    public VehicleManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public VehicleManager() {
    	 driver = "com.mysql.jdbc.Driver";
         url = "jdbc:mysql://uml.cs.uga.edu/team4";
         pass = "seprocess";
         user = "team4";
    }


    public void store( Vehicle veh ) throws RARException{
        String insertVehicleTable = "INSERT INTO Vehicle"+"(Tag, Make, Model, Year, Millege, lastService, Status, RentalLocation, VehicleType, CarCondition) VALUES"+
        "(?,?,?,?,?,?,?,?,?,?)";

        String upDateVehicleTable = "UPDATE Vehicle SET Tag = ?, Make = ?, Model = ?, Year = ?, Millege = ?, lastService = ?, Status = ?, RentalLocation = ?, VehicleType = ?, CarCondition = ? where ID = ?";

        PreparedStatement stmt=null;
        int inscnt;
        long ID;
        try{
            if(!veh.isPersistent()){
                stmt=(PreparedStatement)conn.prepareStatement(insertVehicleTable);
            }
            else{
                stmt=(PreparedStatement)conn.prepareStatement(upDateVehicleTable);
            }
            if(veh.getRegistrationTag()!=null){
                stmt.setString(1,veh.getRegistrationTag());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getMake()!=null){
                stmt.setString(2,veh.getMake());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getModel()!=null){
                stmt.setString(3,veh.getModel());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getYear() != 0){
                stmt.setInt(4,veh.getYear());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getMileage() !=0){
                stmt.setInt(5,veh.getMileage());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getLastServiced() !=null){
            	java.util.Date jDate=veh.getLastServiced();
                java.sql.Date sDate=new java.sql.Date(jDate.getTime());
                stmt.setDate(6,sDate);
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getStatus() != null){
                stmt.setString(7,VehicleStatus.INLOCATION.toString());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getRentalLocation() != null){
                stmt.setString(8,veh.getRentalLocation().getName());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getVehicleType() != null){
                stmt.setString(9,veh.getVehicleType().getName());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

            if(veh.getCondition() != null){
                stmt.setString(10,VehicleCondition.GOOD.toString());
            }else{
                throw new RARException("VehicleManager.save: can't save vehicleType: Name undefined");
            }

             inscnt=stmt.executeUpdate();

            if(!veh.isPersistent()){
                if(inscnt==1){
                    String sql="select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet rs=stmt.getResultSet();
                        while(rs.next()){
                            ID=rs.getLong(1);
                            if(ID>0){
                                veh.setId(ID);
                            }
                        }
                    }
                }
            }
            else{
                if(inscnt<1){
                    throw new RARException("VehicleManager.save: failed to save vehicleType");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RARException("VehicleManager.save: failed to save vehcileType"+e);
        }
    }




    public List<Vehicle> restore(Vehicle veh) throws RARException{

        String sql = "select * from Vehicle";
        Statement    stmt = null;
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();

        try {

            stmt = conn.createStatement();

            if( stmt.execute(sql) ) {
                System.out.println("in VM execute if");
                int id;
                String tag;
                String Make;
                String Model;
                int Year;
                int Millege;
                Date lastService;
                String VehicleT = null;
                VehicleStatus Status;
                String rentalLoc = null;
                VehicleCondition vehCondition = null;
                //Vehicle newVeh = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved clubs
                while( rs.next() ) {
                    System.out.println("in VM while rs");

                    id = rs.getInt(1);
                    tag = rs.getString(2);

                    Make = rs.getString(3);
                    Model = rs.getString(4);
                    Year = rs.getInt(5);
                    Millege = rs.getInt(6);
                    lastService = rs.getDate(7);
                    Status = VehicleStatus.INLOCATION;

                    System.out.println("before getting RL");
                  //  rentalLoc=veh.getRentalLocation();
                    //rentalLoc.setName(rs.getString(9));
                    rentalLoc=rs.getString(9);
                    System.out.println("after getting rental loc");

                    RentalLocation dummyRL=objectLayer.createRentalLocation();
                    dummyRL.setName(rentalLoc);


                    VehicleT =rs.getString(10);
                    VehicleType dummyVT=objectLayer.createVehicleType();
                    dummyVT.setName(VehicleT);
                    System.out.println("after Vehicle type");


                    vehCondition = VehicleCondition.GOOD;

                    System.out.println("after setting vehiclw condition");

                    Vehicle v = objectLayer.createVehicle(Make, Model, Year, tag, Millege, lastService, dummyVT, dummyRL, vehCondition, Status);
                    System.out.println("after creating dummy vehicle");

                    v.setId(id);

                    System.out.println("before adding vehciel to list");

                    vehicleList.add(v);
                    System.out.println("after adding vehciel to list");

                }
                System.out.println("printing vehicles : ");
                for(Vehicle vehicle:vehicleList){
                    System.out.println(vehicle.getRegistrationTag());
                }
                return vehicleList;
            }
        }
        catch( Exception e ) {
            throw new RARException( "VehicleManager.restore: Could not restore persistent Vehicle objects; Root cause: " + e );
        }

        throw new RARException( "VehicleManager.restore: Could not restore persistent vehicle objects" );
   }


 public void delete(Vehicle veh) throws RARException {

  String deleteSql = "delete from Vehicle where ID = ?";

        PreparedStatement    stmt = null;
        int                  inscnt;

        if( !veh.isPersistent() )
            return;

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteSql);
            stmt.setLong(1, veh.getId() );
            inscnt = stmt.executeUpdate();
            if( inscnt == 1 ) {
                return;
            }
           /* else
                throw new RARException( "Vehicle.delete: failed to delete a Vehicle" );
                */
        }
        catch( Exception e ) {
            e.printStackTrace();
            throw new RARException( "Vehicle.delete: failed to delete a Vehicle: " + e );
            }
    }


public List<Vehicle> restoreVehicleVehicleType(VehicleType vehicleType) throws RARException {
	 List<Vehicle> veh=new ArrayList<>();

     String selectVehicleVehicleTypesql="select * from Vehicle where VehicleType='"+vehicleType.getName()+"'";
     Statement    stmt = null;
     StringBuffer query = new StringBuffer( 100 );
     StringBuffer condition = new StringBuffer( 100 );

     condition.setLength( 0 );

     query.append( selectVehicleVehicleTypesql );

     try{
         stmt=conn.createStatement();

         if(stmt.execute(query.toString())){

             ResultSet rs=stmt.getResultSet();

            long ID;
            String Tag;
            String Make;
            String Model;
            int Year;
            int Millege;
            Date lastService;
            String Status;
            RentalLocation rL;
            VehicleType vT;
            String CarCondition;

            VehicleType vT1 = null;

             while(rs.next()){
                 ID=rs.getLong(1);
                 Tag=rs.getString(2);
                 Make =rs.getString(3);
                 Model=rs.getString(4);
                 Year=rs.getInt(5);
                 Millege= rs.getInt(6);
                 lastService = rs.getDate(7);
                 Status = rs.getString(8);

                 RentalLocationImpl vehLoc = new RentalLocationImpl();
                 vehLoc.setName(rs.getString(9));
                 rL = vehLoc;

                 VehicleTypeImpl typ = new VehicleTypeImpl();
                 typ.setName(rs.getString(10));
                 vT = typ;

                 CarCondition = rs.getString(11);



                 vT1=objectLayer.createVehicleType();
                 vT1.setName(vT.getName());

                 Vehicle v=objectLayer.createVehicle();
                 v.setId(ID);
                 v.setVehicleType(vT1);

               //  comment.getCustomer().setId(CustomerID);
                 veh.add(v);

             }
         }

         return veh;

     } catch (SQLException e) {
         e.printStackTrace();
         throw new RARException("CommentManager.restoreRentalComment: failed to restore list of comments for this rental");
     }

}


public static void deleteVehicleRentalLocation(Vehicle vehicle, RentalLocation rentalLocation) throws RARException {
	String deleteRentalsql="delete Vehicle where RentalLocationID='"+rentalLocation.getId()+"'";
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

	public void setVehicle(String tag, String make, String model, String year, String millege, String month, String day,
			
	
			String Syear, String status, String rentalLocation, String vehicleType, String carCondition) throws ClassNotFoundException, SQLException {
		 
		String insertVehicleTable = "INSERT INTO Vehicle"+"(Tag, Make, Model, Year, Millege, lastService, Status, RentalLocation, VehicleType, CarCondition) VALUES"+
			        "(?,?,?,?,?,?,?,?,?,?)";

			  PreparedStatement stmt=null;
			  
			  try {
					conn = DriverManager.getConnection(url, user, pass);
				} catch (SQLException e) {
					System.out.println("something not working");
					e.printStackTrace();
				}
			  
			  stmt=(PreparedStatement)conn.prepareStatement(insertVehicleTable);
			  stmt.setString(1,tag);
			  stmt.setString(2,make);
			  stmt.setString(3,model);
			  
			  int Iyear = Integer.parseInt(year);
			  stmt.setInt(4,Iyear);
			  
			  int Imillege = Integer.parseInt(millege);
			  stmt.setInt(5, Imillege);
			  
			  int Syear1 = Integer.parseInt(Syear);
			  int month1 = Integer.parseInt(month);
			  int day1 = Integer.parseInt(day);
			  
			  System.out.println(Syear1);
			  java.sql.Date sDate=new java.sql.Date(Syear1-1900,month1, day1);
              stmt.setDate(6,sDate);
              
              if(status.equals("INLOCATION")) {
              stmt.setString(7,VehicleStatus.INLOCATION.toString());
              }
              else
            	  stmt.setString(7, VehicleStatus.INRENTAL.toString());
              
			  
              stmt.setString(8,rentalLocation);
              stmt.setString(9,vehicleType);
              
              if(status.equals("GOOD")) {
            	  stmt.setString(10, VehicleCondition.GOOD.toString());
              }
              else
            	  stmt.setString(10, VehicleCondition.NEEDSMAINTENANCE.toString());
              
              stmt.executeUpdate();
	}
	
	   public void updateVehicle(String tag, String column1, String value) throws ClassNotFoundException{
	    	 Class.forName("com.mysql.jdbc.Driver");
	    	 System.out.println("i'm here in update first name");
	  	   
	 		String sql = "UPDATE Vehicle SET ";
	 		sql += column1+" = '"+value+"' ";
	 		sql += "WHERE Tag = '"+tag+"'";
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

	public void updateVehicle(String tag, String column1, java.sql.Date sDate) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
   	 System.out.println("i'm here in update first name");
 	   
		String sql = "UPDATE Vehicle SET ";
		sql += column1+" = '"+sDate+"' ";
		sql += "WHERE Tag = '"+tag+"'";
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

	public void updateVehicleStatus(Vehicle vehicle) throws RARException {
		String sql = "update Vehicle set Status = \"INRENTAL\" where VehicleID = "+vehicle.getId()+";";
		Statement stmt = null;
		
		int delcnt;
	
	    try {
	        stmt=conn.createStatement();
	        delcnt=stmt.executeUpdate(sql);
	        if(delcnt==1){
	            return;
	        }
	        else{
	            throw new RARException("VehicleManager.updateVehicleStatus: failed to update status");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
				throw new RARException("VehicleManager.updateVehicleStatus: failed to update status");
			} catch (RARException e1) {
				e1.printStackTrace();
			}
	    }
	}
  
	     
	}
















