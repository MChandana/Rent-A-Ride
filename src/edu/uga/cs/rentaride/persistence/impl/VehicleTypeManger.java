package edu.uga.cs.rentaride.persistence.impl;


import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.impl.HourlyPriceImpl;
import edu.uga.cs.rentaride.entity.impl.RentalLocationImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleTypeImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class VehicleTypeManger{
	 	private ObjectLayer objectLayer=null;
	    private Connection conn=null;
	    private String driver, url, pass, user;

	    public VehicleTypeManger(Connection conn,ObjectLayer objectLayer){
	        this.conn=conn;
	        this.objectLayer=objectLayer;
	    }
	    
	    public VehicleTypeManger() {
	    	driver = "com.mysql.jdbc.Driver";  
	    	url = "jdbc:mysql://uml.cs.uga.edu/team4";
	    	pass = "seprocess";
	    	 user = "team4";
	    }
	    
	    public void store(VehicleType vT) throws RARException{
	        String insertVehicleTypeSql="insert into VehicleType(Name) values(?)";
	        String updateAdministratorSql="update VehicleType set Name=? where ID=?";
	        PreparedStatement stmt=null;
	        int inscnt;
	        long adminID;
	        try{
	            if(!vT.isPersistent()){
	                stmt=(PreparedStatement)conn.prepareStatement(insertVehicleTypeSql);
	            }
	            else{
	                stmt=(PreparedStatement)conn.prepareStatement(updateAdministratorSql);
	            }
	            if(vT.getName()!=null){
	                stmt.setString(1,vT.getName());
	            }else{
	                throw new RARException("VehicleTypeManager.save: can't save vehicleType: Name undefined");
	            }
	            

	            inscnt=stmt.executeUpdate();

	            if(!vT.isPersistent()){
	                if(inscnt==1){
	                    String sql="select last_insert_id()";
	                    if(stmt.execute(sql)){
	                        ResultSet rs=stmt.getResultSet();
	                        while(rs.next()){
	                            adminID=rs.getLong(1);
	                            if(adminID>0){
	                                vT.setId(adminID);
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
	    
	    public List<VehicleType> restore(VehicleType vT) throws RARException{
	    	  String sql = "select * from VehicleType";
	          List<VehicleType> allVehicleTypes = new ArrayList<>();
	          Statement stmt = null;

	          System.out.println("in RLM restore RL");
	          
	          try{
	              stmt = conn.createStatement();
	              System.out.println("after creating stmnt and conn are "+stmt+" , "+conn);
	              if(stmt.execute(sql)){
	                  System.out.println("in if RLM execute");
	                  ResultSet rs = stmt.getResultSet();

	                 
	                  String name;
	                 
	                  while(rs.next()){
	                      name = rs.getString("name");
	                      VehicleType vh = objectLayer.createVehicleType(name);
	                      allVehicleTypes.add(vh);
	                  }
	              }

	              return allVehicleTypes;
	          }catch(Exception e){
	              throw new RARException("RentalLocationManager.restore: Couldn't restore persistent RentalLocation object; Root Cause: " + e);
	          }
	    }
	    
	    
	    public void delete(Administrator administrator) throws RARException{
	        String deleteAdminsql="delete from Admins where adminID=?";
	        PreparedStatement stmt=null;
	        int delcnt;

	        if(!administrator.isPersistent()){
	            return;
	        }

	        try{

	            stmt=(PreparedStatement)conn.prepareStatement(deleteAdminsql);
	            stmt.setLong(1,administrator.getId());
	            delcnt=stmt.executeUpdate();

	            if(delcnt==0){
	                throw new RARException("AdministratorManager.delete: failed to delete this person");
	            }

	        }catch (SQLException e){
	            throw new RARException("AdministratorManager.delete: failed to delete this person "+e.getMessage());
	        }
	    }
	    
	    public void delete(VehicleType vehicleType) throws RARException{
	        String deleteVehicleTypesql="delete from VehicleType where ID=?";
	        PreparedStatement stmt=null;
	        int delcnt;

	        if(!vehicleType.isPersistent()){
	            return;
	        }

	        try{

	            stmt=(PreparedStatement)conn.prepareStatement(deleteVehicleTypesql);
	            stmt.setLong(1,vehicleType.getId());
	            delcnt=stmt.executeUpdate();

	            if(delcnt==0){
	                throw new RARException("VehicleManager.delete: failed to delete this vehicle Type");
	            }

	        }catch (SQLException e){
	            throw new RARException("VehicleManager.delete: failed to delete this vehicle "+e.getMessage());
	        }
	    }
	    
	    public void deleteVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException{
	    	String deleteVehicleTypesql="delete from VehicleType where ID="+vehicle.getId()+"'";
	        Statement stmt=null;
	        int delcnt;

	        try {
	            stmt=conn.createStatement();
	            delcnt=stmt.executeUpdate(deleteVehicleTypesql);
	            if(delcnt==1){
	                return;
	            }
	            else{
	                throw new RARException("VehicleType.deleteVehicleType: failed to delete vehicletype for this vehicle");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RARException("RentalManager.deleteRentalVehicle: failed to delete vehicletype for this vehicle");
	        }
	    }
	    
	    
	    public void storeVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException
	    {
	    	 String insertVehicleVehicleTypesql="update VehicleType set VehicleID='"+vehicle.getId()+"'"+ " where VehicleID='"+vehicle.getId()+"'";

	         PreparedStatement stmt=null;
	         int updcnt;

	         if(!vehicleType.isPersistent()){
	             return;
	         }

	         try {

	             updcnt = stmt.executeUpdate(insertVehicleVehicleTypesql);
	             if(updcnt==1){
	                 return;
	             }
	             else
	                 throw new RARException("VehicleTypeManager.storeVehicleVehicleType: failed to save vehicle");

	         }catch (Exception e){
	             throw new RARException("VehicleTypeManager.storeRentalComment: failed to save vehicle type for the vehicle type");
	         }
	    }

	    public VehicleType restoreVehicleVehicleType( Vehicle vehicle ) throws RARException
	    {
	    	 String selectVehicleTypesql="select VehicleType from Vehicle where VehicleType="+vehicle.getVehicleType()+"'";
		        Statement stmt=null;
		        StringBuffer condition=new StringBuffer(100);
		        StringBuffer query=new StringBuffer(100);
		        List<VehicleType> vehType=new ArrayList<>();
		        condition.setLength(0);
		        VehicleType vTS = null;

		        query.append(selectVehicleTypesql);
		        /*forming query based on administrator object*/
		        if(vehicle!=null){
		            if(vehicle.getId()>=0){
		                query.append(" where ID= ' "+vehicle.getId()+"'");
		            }
		          }

		        try{
		            stmt=conn.createStatement();
		            if(stmt.execute(query.toString())){
		                ResultSet rs=stmt.getResultSet();
		                long ID;
		               String name;

		                while(rs.next()){
		                    ID = rs.getLong(1);
		                    name = rs.getString(2);

		                    vTS = objectLayer.createVehicleType();
		                    vTS.setId(ID);
		                    vTS.setName(name);
		                    //vehType.add(vTS);
		                    
		                }



		            return vTS;
		            }
		        }catch(Exception e){
		            throw  new RARException("VehicleTypeManager.restore: couldn't restore persistent vehicleType; Root cause: "+ e);
		        }
		            throw new RARException("VehicleTypeManager.restore: couldn't restore persistent vehicleTyper");
	    }

	    public List<Vehicle> restoreVehicleVehicleType( VehicleType vehicleType ) throws RARException
	    {
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

		public void deleteReservationVehicleType(VehicleType vehicleType) throws RARException {
		  	String deleteVehicleTypesql="delete from Reservation where ID="+vehicleType.getId()+"'";
	        Statement stmt=null;
	        int delcnt;

	        try {
	            stmt=conn.createStatement();
	            delcnt=stmt.executeUpdate(deleteVehicleTypesql);
	            if(delcnt==1){
	                return;
	            }
	            else{
	                throw new RARException("VehicleTypeManager.deleteVehicleType: failed to delete Reservation for this vehicleType");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RARException("VehicleTypeManager.deleteVehicleType: failed to delete Reservation for this vehicleType");
	        }
		}
		
		public void setVehicleType(String vehicleType) throws ClassNotFoundException, SQLException {
			 
			String insertVehicleTable = "INSERT INTO VehicleType"+"(name) VALUES"+
				        "(?)";
			

				  PreparedStatement stmt=null;
				  try {
						conn = DriverManager.getConnection(url, user, pass);
					} catch (SQLException e) {
						System.out.println("something not working");
						e.printStackTrace();
					}
				  stmt=(PreparedStatement)conn.prepareStatement(insertVehicleTable);
				  stmt.setString(1,vehicleType);
	              
	              stmt.executeUpdate();
		}
		
		  public long getID(String name) throws ClassNotFoundException {
		    	 Class.forName("com.mysql.jdbc.Driver");
		    	long ID = 0;
		    	
		    	String sql="select ID from VehicleType where name = '"+name+"' ;";
		    	System.out.println(sql);  
		    	ResultSet resultSet = null;
		       	 Statement stmt;
				try {
					conn = DriverManager.getConnection(url, user, pass);
					stmt = conn.createStatement();
					 if(stmt.execute(sql)){
			                ResultSet rs=stmt.getResultSet();
			                while(rs.next()){
			                	ID = rs.getLong("ID");
			                }
			       	 }
			       	 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       	 
		     
		       	 return ID;
		    
		    }
		  
		  public void updateVehicleType(String name, long iD) throws ClassNotFoundException {
				Class.forName("com.mysql.jdbc.Driver");
			   	 System.out.println("i'm here in update first name");
			 	   
					String sql = "UPDATE VehicleType SET ";
					sql +="name "+" = '"+name+"' ";
					sql += "WHERE ID = "+iD;
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
		  
		  
}
