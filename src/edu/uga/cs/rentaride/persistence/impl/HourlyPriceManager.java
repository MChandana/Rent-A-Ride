package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.impl.HourlyPriceImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class HourlyPriceManager {

    private ObjectLayer objectLayer=null;
    private Connection conn=null;
    private String driver, url, pass, user;

    public HourlyPriceManager(Connection conn, ObjectLayer objectLayer) {
        this.objectLayer = objectLayer;
        this.conn = conn;
    }
    
    public HourlyPriceManager(){
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://uml.cs.uga.edu/team4";
        pass = "seprocess";
        user = "team4";
    }


    public void store(HourlyPrice hourlyPrice) throws RARException {

        String insertHPsql="insert into HourlyPrice(VehicleType,MaxHrs,Price) values(?,?,?)";
        String updateHPsql="update HourlyPrice set VehicleType=?, MaxHrs=?, Price=? where ID=?";

        PreparedStatement stmt=null;
        int inscnt;
        long ID;

        try{

            if(!hourlyPrice.isPersistent()){
                stmt=(PreparedStatement)conn.prepareStatement(insertHPsql);
            }else{
                stmt=(PreparedStatement)conn.prepareStatement(updateHPsql);
            }

            if(hourlyPrice.getVehicleType()!=null){
                String vehicleTypeName=hourlyPrice.getVehicleType().getName();
                stmt.setString(1,vehicleTypeName);
            }else{
                throw new RARException("HourlyPriceManager.save failed: VehicleType is undefined");
            }

            if(hourlyPrice.getMaxHours()>0){
                stmt.setInt(2,hourlyPrice.getMaxHours());
            }else{
                throw new RARException("HourlyPriceManager.save failed: MaxHours is undefined");
            }

            if(hourlyPrice.getPrice()>0){
                stmt.setInt(3,hourlyPrice.getPrice());
            }else{
                throw new RARException("HourlyPriceManager.save failed: Price is undefined");
            }

            if(hourlyPrice.isPersistent()){
                stmt.setLong(4,hourlyPrice.getId());
            }

            inscnt=stmt.executeUpdate();

            if(!hourlyPrice.isPersistent())
            {
                if(inscnt==1){
                    String sql="select last_insert_id()";

                    if(stmt.execute(sql)){
                        ResultSet rs=stmt.getResultSet();

                        while(rs.next()){
                            ID=rs.getLong(1);
                            if(ID>0){
                                hourlyPrice.setId(ID);
                            }
                        }
                    }
                }
            }
            else{
                if(inscnt<1){
                    throw new RARException("HourlyPriceManager.save: failed to save this hourlyprice");

                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RARException("HourlyPriceManager.save: failed to save this hourlyprice");
        }
    }

    public List<HourlyPrice> restore(HourlyPrice hourlyPrice) throws RARException{

        String selectHPsql="select * from HourlyPrice";
        Statement stmt=null;
        StringBuffer query=new StringBuffer(100);
        StringBuffer condition=new StringBuffer(100);
        List<HourlyPrice> hourlyPrices=new ArrayList<>();

        condition.setLength(0);

        query.append(selectHPsql);

        if(hourlyPrice!=null){
            if(hourlyPrice.getId()>0){
                query.append(" where ID='"+hourlyPrice.getId()+"'");
            }
            else{

                if(hourlyPrice.getVehicleType()!=null){
                    condition.append(" VehicleType='"+hourlyPrice.getVehicleType()+"'");
                }
                if(hourlyPrice.getMaxHours()>0){
                    if(condition.length()>0){
                        condition.append(" and MaxHrs='"+hourlyPrice.getMaxHours()+"'");
                    }else{
                        condition.append(" MaxHrs='"+hourlyPrice.getMaxHours()+"'");
                    }
                }

                if(hourlyPrice.getPrice()>0){
                    if(condition.length()>0){
                        condition.append(" and Price='"+hourlyPrice.getPrice()+"'");
                    }else{
                        condition.append(" Price='"+hourlyPrice.getPrice()+"'");
                    }
                }

                if(condition.length()>0){
                    query.append(" where ");
                    query.append(condition);
                }
            }
        }

        try{

            stmt=conn.createStatement();
            if(stmt.execute(query.toString())){

                ResultSet rs=stmt.getResultSet();

                long ID;
                String vehicleTypeName;
                int MaxHrs;
                int Price;

                while (rs.next()){
                    ID=rs.getLong(1);
                    vehicleTypeName=rs.getString(2);
                    MaxHrs=rs.getInt(3);
                    Price=rs.getInt(4);

                    HourlyPrice hourlyPrice1=objectLayer.createHourlyPrice();
                    hourlyPrice1.setId(ID);
                    hourlyPrice1.setMaxHours(MaxHrs);
                    hourlyPrice1.setPrice(Price);

                    VehicleType vehicleType=objectLayer.createVehicleType();
                    vehicleType.setName(vehicleTypeName);
                    hourlyPrice1.setVehicleType(Persistence.getPersistenceLayer().restoreVehicleType(vehicleType).get(0));

                    hourlyPrices.add(hourlyPrice1);

                }
            }

            return hourlyPrices;

        }catch (Exception e){
            throw new RARException("HourlyPriceManager restore failed: Couldn't restore persistent Hourly Prices");
        }
    }

    public void storeVehicleTypeHourlyPrice(VehicleType vehicleType,HourlyPrice hourlyPrice)throws RARException{

        String insertVTHPsql="update HourlyPrice set VehicleType=? where ID='"+hourlyPrice.getId()+"'";

        PreparedStatement stmt=null;
        int updcnt;
        String vehicleTypeName;

        if(!hourlyPrice.isPersistent()){
            return;
        }

        try{

            stmt=(PreparedStatement)conn.prepareStatement(insertVTHPsql);

            vehicleTypeName=vehicleType.getName();

            stmt.setString(1,vehicleTypeName);

            updcnt=stmt.executeUpdate();

            if(updcnt==1){
                return;
            }
            else{
                throw new RARException("HourlyPriceManager.storeVehicleTypeHourlyPrice failed");
            }


        }catch (Exception e){
            throw new RARException("HourlyPriceManager.storeVehicleTypeHourlyPrice failed");
        }
    }

    public VehicleType restoreVehicleType(HourlyPrice hourlyPrice) throws RARException{

        String selectVehicleTypesql="select * from VehicleType where ID='"+hourlyPrice.getVehicleType().getId()+"'";

        VehicleType vehicleType=null;

        Statement stmt=null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        query.append( selectVehicleTypesql );

        if(hourlyPrice!=null){

            if(hourlyPrice.getId()>0){
                query.append(" and ID='"+hourlyPrice.getId()+"'");
            }

            else{

                if(hourlyPrice.getVehicleType()!=null){
                    condition.append(" and VehicleType='"+hourlyPrice.getVehicleType().getName()+"'");
                }

                if(hourlyPrice.getMaxHours()>0){
                    condition.append(" and MaxHrs='"+hourlyPrice.getMaxHours()+"'");
                }

                if(hourlyPrice.getPrice()>0){
                    condition.append(" and Price='"+hourlyPrice.getPrice()+"'");
                }

                if(condition.length()>0){
                    query.append(condition);
                }

            }

        }

        try{

            stmt=conn.createStatement();
            if(stmt.execute(query.toString())){
                ResultSet rs=stmt.getResultSet();

                long ID;
                String vehicleTypeName;

                while(rs.next()){
                    ID=rs.getLong(1);
                    vehicleTypeName=rs.getString(2);

                    vehicleType=objectLayer.createVehicleType();
                    vehicleType.setId(ID);
                    vehicleType.setName(vehicleTypeName);

                    return vehicleType;
                }
            }

        }catch (Exception e){
            throw new RARException("HourlyPriceManager.restoreVehicleTypeHourlyPrice: failed to restore Vehicle type for this hourly price");
        }

        throw new RARException("HourlyPriceManager.restoreVehicleTypeHourlyPrice: failed to restore Vehicle type for this hourly price");

    }

    public List<HourlyPrice> restoreVehicleTypeHourlyPrice(VehicleType vehicleType) throws RARException{

        String selectHPsql="select * from HourlyPrice where VehicleType='"+vehicleType.getName()+"'";
        Statement stmt=null;
        List<HourlyPrice> hourlyPrices=new ArrayList<>();

        try{

            stmt=conn.createStatement();

            if(stmt.execute(selectHPsql)){
                ResultSet rs=stmt.getResultSet();

                long ID;
                String vehicleTypeName;
                int MaxHrs;
                int price;

                while (rs.next()){

                    ID=rs.getLong(1);
                    vehicleTypeName=rs.getString(2);
                    MaxHrs=rs.getInt(3);
                    price=rs.getInt(4);

                    HourlyPrice hourlyPrice=objectLayer.createHourlyPrice();
                    hourlyPrice.setId(ID);
                    hourlyPrice.setPrice(price);
                    hourlyPrice.setMaxHours(MaxHrs);

                    VehicleType vehicleType1=objectLayer.createVehicleType();
                    vehicleType1.setName(vehicleTypeName);
                    hourlyPrice.setVehicleType(Persistence.getPersistenceLayer().restoreVehicleType(vehicleType1).get(0));

                    hourlyPrices.add(hourlyPrice);

                }
            }
            return hourlyPrices;

        }catch (Exception e){
            e.printStackTrace();
            throw new RARException("HourlyPriceManager.restoreVehicleTypeHourlyPrice: failed to restore hourly prices for this vehicle type");
        }


    }

    public void deleteVehicleTypeHourlyPrice(VehicleType vehicleType,HourlyPrice hourlyPrice) throws RARException{

        String deleteVTHPsql="update HourlyPrice set VehicleType=? where VehicleType='"+vehicleType.getName()+";";
        PreparedStatement stmt=null;
        int delcnt;

        try {

            stmt = (PreparedStatement) conn.prepareStatement(deleteVTHPsql);
            stmt.setNull(1, Types.VARCHAR);

            delcnt=stmt.executeUpdate();
            if(delcnt==1){
                return;
            }
            else{
                throw new RARException("HourlyPriceManager.deleteVehicleTypeHourlyPrice failed: couldn't delete hourly price for this vehicle type");
            }

        }catch (SQLException e){
            throw new RARException("HourlyPriceManager.deleteVehicleTypeHourlyPrice failed: couldn't delete hourly price for this vehicle type");
        }
    }

    public void delete(HourlyPrice hourlyPrice) throws RARException{

        String deleteHPsql="delete from HourlyPrice where ID=?";
        PreparedStatement stmt=null;
        int delcnt;

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteHPsql);

            stmt.setLong(1,hourlyPrice.getId());
            delcnt=stmt.executeUpdate();

            if(delcnt==0){
                throw new RARException("HourlyPrice.delete failed: Couldn't delete this hourlyPrice");
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RARException("HourlyPrice.delete failed: Couldn't delete this hourlyPrice");
        }
    }
    
    public List<HourlyPrice> allPrices() throws ClassNotFoundException{
    	String vehicleType;
    	int MaxHrs;
    	int Price;
    	List<HourlyPrice> hr = new ArrayList<>();
    	 Class.forName("com.mysql.jdbc.Driver");
     	
    	 String sql = "SELECT VehicleType.name, HourlyPrice.Price, HourlyPrice.MaxHrs ";
    	 sql += "FROM VehicleType ";
    	 sql += "RIGHT JOIN HourlyPrice ";
    	 sql += "ON VehicleType.name = HourlyPrice.VehicleType";
    	
        	 ResultSet resultSet = null;
        	 Statement stmt;
 		try {
 			conn = DriverManager.getConnection(url, user, pass);
 			stmt = conn.createStatement();
 			 if(stmt.execute(sql)){
 	                ResultSet rs=stmt.getResultSet();
 	                String name = null;
 	                while(rs.next()){
 	                	vehicleType = rs.getString("name");
 	                	Price = rs.getInt("Price");
 	                	MaxHrs = rs.getInt("MaxHrs");
 	                	HourlyPriceImpl hpi = new HourlyPriceImpl(MaxHrs,Price,vehicleType);
 	                	hr.add(hpi);
 	                }
 	       	 }
 	       	 
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        	 
    	return hr;
    }
    
	public void setHourlyPrice(String vehicleType, int MaxHrs, int Price) throws ClassNotFoundException, SQLException {
		String insertVehicleTable = "INSERT INTO HourlyPrice"+"(VehicleType, MaxHrs, Price) VALUES"+
		        "(?,?,?)";
	

		  PreparedStatement stmt=null;
		  try {
				conn = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				System.out.println("something not working");
				e.printStackTrace();
			}
		  stmt=(PreparedStatement)conn.prepareStatement(insertVehicleTable);
		  stmt.setString(1,vehicleType);
		  stmt.setInt(2,MaxHrs);
		  stmt.setInt(3,Price);
          
          stmt.executeUpdate();
}
	
	public void updateHourlyPrice(String column1, String value1, long ID) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
   	 System.out.println("i'm here in update first name");
 	   
		String sql = "UPDATE VehicleType SET ";
		sql += column1+" = '"+value1+"' ";
		sql += "WHERE ID = "+ID;
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
	
	  public long getID(String name) throws ClassNotFoundException {
	    	 Class.forName("com.mysql.jdbc.Driver");
	    	long ID = 0;
	    	
	    	String sql="select ID from HourlyPrice where VehicleType = '"+name+"' ;";
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

	public void updateHourlyPrice(String column1, int max, long iD) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	   	 System.out.println("i'm here in update first name");
	 	   
			String sql = "UPDATE HourlyPrice SET ";
			sql += column1+" = "+max+" ";
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
	
	
	public int getCharge(Reservation r) throws RARException, SQLException{
		String sql = "select HourlyPrice.Price from HourlyPrice "
				+ "join VehicleType on VehicleType.name = HourlyPrice.VehicleType "
				+ "join Vehicle on Vehicle.VehicleType = VehicleType.name "
				+ "join Rental on Rental.VehicleID = Vehicle.ID "
				+ "join Reservation on Reservation.ID = Rental.ReservationID "
				+ "where Reservation.ID = "+r.getId()+" ;";
		
			int charge = 0;
        	 ResultSet resultSet = null;
        	 Statement stmt = conn.createStatement();
        	 
        	 if(stmt.execute(sql)){
                 ResultSet rs=stmt.getResultSet();
                 	charge = 0;
                 while(rs.next()){
                 	charge = rs.getInt("Price");
                 }
                 
        	 }
        	 
        	 return charge;
	}

}
