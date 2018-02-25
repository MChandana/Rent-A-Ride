package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerManager {

    private ObjectLayer objectLayer=null;
    private Connection conn=null;
    private String driver,url,pass,user;

    public CustomerManager(Connection conn,ObjectLayer objectLayer){
        this.conn=conn;
        this.objectLayer=objectLayer;
    }
    
    public CustomerManager() {
    	driver = "com.mysql.jdbc.Driver";  
    	url = "jdbc:mysql://uml.cs.uga.edu/team4";
    	pass = "seprocess";
    	 user = "team4";
    }

    public void store(Customer customer) throws RARException{
    	
    	System.out.println(customer.getFirstName());
    	String insertCustomersql="insert into Customer(firstName, lastName, username, password, email, address, createdDate, memberUntil, licState, licNumber,creditCardNumber,creditCardExpiration, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
       
    	String updateCustomersql="update Customer set firstName=?, lastName=?,username=?,password=,email=?,address=?,createdDate=?,memberUntil=?,licState=?,licNumber=?,creditCardNumber=?,creditCardExpiration=?,Status=? where CustomerID='"+customer.getId()+"'";
    	
    	PreparedStatement stmt=null;
        int inscnt;
        long CustomerID;

        try{

            if(!customer.isPersistent()){
                System.out.println("Something is not working");
            	stmt=(PreparedStatement)conn.prepareStatement(insertCustomersql);
            }else {
            	
                stmt=(PreparedStatement)conn.prepareStatement(updateCustomersql);
            }

            if(customer.getFirstName()!=null){
            	
                stmt.setString(1,customer.getFirstName());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: FirstName is undefined");
            }

            if(customer.getLastName()!=null){
          
                stmt.setString(2,customer.getLastName());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: LastName is undefined");
            }

            if(customer.getUserName()!=null){
            	
                stmt.setString(3,customer.getUserName());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: UserName is undefined");
            }

            if(customer.getPassword()!=null){
            	
                stmt.setString(4,customer.getPassword());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: Password is undefined");
            }

            if(customer.getEmail()!=null){
            	
                stmt.setString(5,customer.getEmail());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: Email is undefined");
            }

            if(customer.getAddress()!=null){
            	
                stmt.setString(6,customer.getAddress());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: address is undefined");
            }

            if(customer.getCreatedDate()!=null){
            	
                Date jdate=customer.getCreatedDate();
                java.sql.Date sdate=new java.sql.Date(jdate.getTime());
                stmt.setDate(7,sdate);
            }else{
                stmt.setNull(7, java.sql.Types.DATE);
            }

            if(customer.getLicenseNumber()!=null){
            	
                stmt.setString(10,customer.getLicenseNumber());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: LicenseNumber is undefined");
            }

            if(customer.getMemberUntil()!=null){
            	
                Date jdate=customer.getMemberUntil();
                java.sql.Date sdate=new java.sql.Date(jdate.getTime());
                stmt.setDate(8,sdate);
            }else{
                stmt.setNull(8, java.sql.Types.DATE);
            }

            if(customer.getLicenseState()!=null){
            	
                stmt.setString(9,customer.getLicenseState());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: LicenseState is undefined");
            }


            if(customer.getCreditCardNumber()!=null){
            	
                stmt.setString(11,customer.getCreditCardNumber());
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: creditCardNumber is undefined");
            }

            if(customer.getCreditCardExpiration()!=null){
            	
                Date jdate=customer.getCreditCardExpiration();
                java.sql.Date sdate=new java.sql.Date(jdate.getTime());
                stmt.setDate(12,sdate);
            }else{
                stmt.setNull(12, java.sql.Types.DATE);
            }

            if(customer.getUserStatus()!=null){
            	
                stmt.setString(13,UserStatus.ACTIVE.toString());
                System.out.println("im done in the updates");
            }else{
                throw new RARException("CustomerManager.save: couldn't save this person: Status is undefined");
            }


            inscnt=stmt.executeUpdate();
            if(!customer.isPersistent()){
                if(inscnt==1){
                    String sql="select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet rs=stmt.getResultSet();

                        while(rs.next()){
                            CustomerID=rs.getLong(1);
                            if(CustomerID>0){
                                customer.setId(CustomerID);
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
            //e.printStackTrace();
            throw new RARException("CustomerMaanger.save: failed to save Customer"+e);
        }
    }

    public List<Customer> restore(Customer modelCustomer) throws RARException{

        String sql="select * from Customer";
        Statement stmt=null;
        List<Customer> customers=new ArrayList<>();

        /*StringBuffer query=new StringBuffer(100);
        StringBuffer condition=new StringBuffer(100);
        
        condition.setLength(0);

        query.append(selectCustomersql);

        if(modelCustomer!=null){

            if(modelCustomer.getId()>=0){
                query.append(" where CustomerID='"+ modelCustomer.getId()+"'");
            }
            else if(modelCustomer.getUserName()!=null){
                query.append(" where username='"+modelCustomer.getUserName()+"'");
            }
            else if(modelCustomer.getLicenseNumber()!=null){
                query.append(" where licNumber='"+modelCustomer.getLicenseNumber()+"'");
            }

            else{

                if(modelCustomer.getFirstName()!=null){

                    condition.append(" firstName='"+modelCustomer.getFirstName()+"'");
                }

                if(modelCustomer.getLastName()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" lastName='" + modelCustomer.getLastName() + "'");
                    }else{
                        condition.append(" lastName='" + modelCustomer.getLastName() + "'");

                    }
                }

                if(modelCustomer.getPassword()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" password='" + modelCustomer.getPassword() + "'");
                    }else{
                        condition.append(" password='" + modelCustomer.getPassword() + "'");

                    }
                }

                if(modelCustomer.getEmail()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" email='" + modelCustomer.getEmail() + "'");
                    }else{
                        condition.append(" email='" + modelCustomer.getEmail() + "'");

                    }
                }

                if(modelCustomer.getAddress()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" address='" + modelCustomer.getAddress() + "'");
                    }else{
                        condition.append(" address='" + modelCustomer.getAddress() + "'");

                    }
                }

                if(modelCustomer.getCreatedDate()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" createdDate='" + modelCustomer.getCreatedDate() + "'");
                    }else{
                        condition.append(" createdDate='" + modelCustomer.getCreatedDate() + "'");

                    }
                }

                if(modelCustomer.getMemberUntil()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" memberUntil='" + modelCustomer.getMemberUntil() + "'");
                    }else{
                        condition.append(" memberUntil='" + modelCustomer.getMemberUntil() + "'");

                    }
                }

                if(modelCustomer.getLicenseState()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" licState='" + modelCustomer.getLicenseState() + "'");
                    }else{
                        condition.append(" licState='" + modelCustomer.getLicenseState() + "'");

                    }
                }

                if(modelCustomer.getCreditCardNumber()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" creditCardNumber='" + modelCustomer.getCreditCardNumber() + "'");
                    }else{
                        condition.append(" creditCardNumber='" + modelCustomer.getCreditCardNumber() + "'");

                    }
                }

                if(modelCustomer.getCreditCardExpiration()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" creditCardExpiration='" + modelCustomer.getCreditCardExpiration() + "'");
                    }else{
                        condition.append(" creditCardExpiration='" + modelCustomer.getCreditCardExpiration() + "'");

                    }
                }

                if(modelCustomer.getUserStatus()!=null){
                    if(condition.length()>0) {
                        condition.append(" and");
                        condition.append(" Status='" + modelCustomer.getUserStatus() + "'");
                    }else{
                        condition.append(" Status='" + modelCustomer.getUserStatus() + "'");

                    }
                }

                if(condition.length()>0){
                    query.append(" where ");
                    query.append(condition);
                }

            }
        }*/

        try{

            stmt=conn.createStatement();

            if(stmt.execute(sql)){
                ResultSet rs=stmt.getResultSet();

                long CustomerID;
                String firstName;
                String lastName;
                String username;
                String password;
                String email;
                String address;
                Date createdDate;
                String licNumber;
                Date memberUntil;
                String licState;
                String creditCardNumber;
                Date creditCardExpiration;
                
                UserStatus status;
                String ustatus;

                while(rs.next()){

                    CustomerID=rs.getLong(1);
                    firstName=rs.getString(2);
                    lastName=rs.getString(3);
                    username=rs.getString(4);
                    password=rs.getString(5);
                    email=rs.getString(6);
                    address=rs.getString(7);
                    createdDate=rs.getDate(8);
                    licNumber=rs.getString(11);
                    memberUntil= rs.getDate(9);
                    licState=rs.getString(10);
                    creditCardNumber=rs.getString(12);
                    creditCardExpiration= rs.getDate(13);
                    ustatus = rs.getString(14);
                    if(ustatus.equals("Terminated")) {
                    	status = UserStatus.TERMINATED;
                    }
                    else
                    	status = UserStatus.ACTIVE;

                    Customer customer= objectLayer.createCustomer(firstName,lastName,username,password,email,address,createdDate, memberUntil, licState, licNumber, creditCardNumber, creditCardExpiration);
                    customer.setId(CustomerID);

                    customers.add(customer);

                }
            }

            return customers;
        }catch (Exception e){
            throw new RARException("CustomerManager.restore: Couldn't restore persistent Customer object; Root cause:"+e);
        }

           // throw new RARException("CustomerManager.restore: Couldn't restore persistent Customer object");
    }

    public Customer restoreCustomerReservation(Reservation reservation) throws RARException{

        Customer customer=reservation.getCustomer();
        return customer;
    }

    public List<Reservation> restoreCustomerReservation(Customer customer) throws RARException{

        String selectCustomersql= "select r.ID, r.RentalLocation, r.pickUpDate, r.length_in_minutes, r.Cancelled, r.CustomerID, r.VehicleType,"+
            "rt.name, rt.ID, rt.address, rt.capacity,"+
            "vt.ID, vt.name "+
            "from Customer c join Reservation r on c.CustomerId=r.CustomerID "+
            "join RentalLocation rt on r.RentalLocation=rt.name "+
            "join VehicleType vt on r.VehicleType=vt.name";

        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        List<Reservation> reservations=new ArrayList<>();

        condition.setLength( 0 );

        query.append( selectCustomersql );

        if(customer!=null){

            if(customer.getId()>0){
                query.append(" where c.CustomerID='"+customer.getId()+"'");
            }

            else if(customer.getUserName()!=null){
                query.append(" where c.username='"+customer.getUserName()+"'");
            }

            else if(customer.getLicenseNumber()!=null){
                query.append(" where c.licNumber='"+customer.getLicenseNumber()+"'");
            }

            else{
                if(customer.getFirstName()!=null){
                    condition.append(" c.firstName='"+customer.getFirstName()+"'");
                }

                if(customer.getLastName()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.lastName='"+customer.getLastName()+"'");
                    }
                    else{
                        condition.append(" c.lastName='"+customer.getLastName()+"'");

                    }
                }

                if(customer.getPassword()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.password='"+customer.getPassword()+"'");
                    }
                    else{
                        condition.append(" c.password='"+customer.getPassword()+"'");

                    }
                }

                if(customer.getEmail()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.email='"+customer.getEmail()+"'");
                    }
                    else{
                        condition.append(" c.email='"+customer.getEmail()+"'");

                    }
                }

                if(customer.getAddress()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.address='"+customer.getAddress()+"'");
                    }
                    else{
                        condition.append(" c.address='"+customer.getAddress()+"'");

                    }

                }

                if(customer.getCreatedDate()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.createdDate='"+customer.getCreatedDate()+"'");
                    }
                    else{
                        condition.append(" c.createdDate='"+customer.getCreatedDate()+"'");

                    }
                }

                if(customer.getMemberUntil()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.memberUntil='"+customer.getMemberUntil()+"'");
                    }
                    else{
                        condition.append(" c.memberUntil='"+customer.getMemberUntil()+"'");

                    }
                }

                if(customer.getLicenseState()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.licState='"+customer.getLicenseState()+"'");
                    }
                    else{
                        condition.append(" c.licState='"+customer.getLicenseState()+"'");

                    }
                }

                if(customer.getCreditCardNumber()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.creditCardNumber='"+customer.getCreditCardNumber()+"'");
                    }
                    else{
                        condition.append(" c.creditCardNumber='"+customer.getCreditCardNumber()+"'");

                    }
                }

                if(customer.getCreditCardExpiration()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.creditCardExpiration='"+customer.getCreditCardExpiration()+"'");
                    }
                    else{
                        condition.append(" c.creditCardExpiration='"+customer.getCreditCardExpiration()+"'");

                    }
                }

                if(customer.getUserStatus()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.Status='"+customer.getUserStatus()+"'");
                    }
                    else{
                        condition.append(" c.Status='"+customer.getUserStatus()+"'");

                    }
                }

                if( condition.length() > 0 ) {
                    query.append(" where");
                    query.append( condition );
                }
            }

        }

        try{

            stmt=conn.createStatement();

            if(stmt.execute(query.toString())) {
                long id;
                String RentalLocationName;
                Timestamp pickUpDate;
                int length_in_minutes;
                int cancelled;
                String vehicleTypeName;
                String vtName;
                String rtName;
                long vtID;
                long rtID;
                String address;
                int capacity;

                Reservation reservation=null;

                ResultSet rs=stmt.getResultSet();

                while(rs.next()){

                    id=rs.getLong(1);
                    RentalLocationName=rs.getString(2);
                    pickUpDate=rs.getTimestamp(3);
                    length_in_minutes=rs.getInt(4);
                    cancelled=rs.getInt(5);
                    vehicleTypeName=rs.getString(7);
                    rtName=rs.getString(8);
                    rtID=rs.getLong(9);
                    address=rs.getString(10);
                    capacity=rs.getInt(11);
                    vtID=rs.getLong(12);
                    vtName=rs.getString(13);

                    RentalLocation rentalLocation=objectLayer.createRentalLocation();
                    rentalLocation.setId(rtID);
                    rentalLocation.setName(rtName);
                    rentalLocation.setAddress(address);
                    rentalLocation.setCapacity(capacity);

                    VehicleType vehicleType=objectLayer.createVehicleType();
                    vehicleType.setId(vtID);
                    vehicleType.setName(vtName);


                    reservation=objectLayer.createReservation(pickUpDate, length_in_minutes, vehicleType, rentalLocation, customer);

                    reservation.setId(id);
                    reservation.setLength(length_in_minutes);
                    reservation.setPickupTime(pickUpDate);
                    reservation.setRentalLocation(rentalLocation);
                    reservation.setVehicleType(vehicleType);

                    reservations.add(reservation);

                }
                return reservations;
            }


        }catch (Exception e){
            throw new RARException("CustomerManager.restoreReservations: Could not restore the reservations of the custoer; Root cause"+e);
        }

        throw new RARException("CustomerManager.restoreReservations: Could not restore the reservations of the custoer");

    }

    public void deleteCustomerReservation(Customer customer,Reservation reservation) throws RARException{

        String deleteReservationsql="update Reservation set CustomerID=0 where ID=?";
        PreparedStatement stmt=null;
        int delcnt;

        if(!reservation.isPersistent()){
            return;
        }

        try{

            stmt=(PreparedStatement)conn.prepareStatement(deleteReservationsql);
            stmt.setLong(1,reservation.getId());

            delcnt=stmt.executeUpdate();

            if(delcnt==1)
                return;
            else{
                throw new RARException("Failed to delete the reservation");
            }


        }catch (Exception e){
            throw new RARException("CustomerManager.deleteReservation: couldn't delete the reservation for the customer");
        }

    }
    
    public boolean isCorrect(String email, String password) throws SQLException {
    	boolean tester = false;
    	String sql="select * from Customer where email = '"+email+"' ;";
         String password1 = "";
        	 ResultSet resultSet = null;
        	 Statement stmt = conn.createStatement();
        	 
        	 if(stmt.execute(sql)){
                 ResultSet rs=stmt.getResultSet();
                 String name = null;
                 while(rs.next()){
                 	name = rs.getString("firstName");
                 	password1 = rs.getString("password");
                 }
                 if(name == null) {
                	 tester = false;
                 }
                 if(password1.equals(password)) {
                	 tester = true;
                 }
                 else
                	 return false;
        	 }
        	 
        	 return tester;
}
    
    public void addCustomer(String firstName,String lastName,String username,String password,String email,
    				String address,java.sql.Date today,Date today1,String state,String licNumber,
					String creditCardNumber,Date today2) throws ClassNotFoundException {
    	 Connection conn = null;
    	   Statement stmt = null;
    	   UserStatus userStatus;
    	   userStatus=UserStatus.ACTIVE;
    	   try{
    	      //STEP 2: Register JDBC driver
    	      Class.forName("com.mysql.jdbc.Driver");

    	      //STEP 3: Open a connection
    	      System.out.println("Connecting to a selected database...");
    	      conn = DriverManager.getConnection(url, user, pass);
    	      stmt = conn.createStatement();
    	      
    	      String query = " insert into Customer (firstName, lastName, username, password, email, address, createdDate, memberUntil, licState, licNumber, creditCardNumber, creditCardExpiration, Status)"
    	    	        + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?,?,?)";
    	      PreparedStatement preparedStmt = conn.prepareStatement(query);
    	      preparedStmt.setString(1, firstName);
    	      preparedStmt.setString(2, lastName);
    	      preparedStmt.setString(3, username);
    	      preparedStmt.setString(4, password);
    	      preparedStmt.setString(5, email);
    	      preparedStmt.setString(6, address);
    	      preparedStmt.setDate(7, today);
    	      preparedStmt.setDate(8, today);
    	      System.out.println("this is the state: "+state);
    	      preparedStmt.setString(9, state);
    	      preparedStmt.setString(10, licNumber);
    	      preparedStmt.setString(11, creditCardNumber);
    	      preparedStmt.setDate(12, today);
    	      preparedStmt.setString(13, UserStatus.ACTIVE.toString());
    	      
    	      
    	     

    	      // execute the preparedstatement
    	      preparedStmt.execute();
    	      
    	      conn.close();
    	      
    }
    	   catch(SQLException e) {
    		   e.printStackTrace();
    	   }
    	   }

    public void updateUserProfile(String firstname, String lastname, String username, String email, String address, String licNumber, String licState, String ccNumber, java.sql.Date ccExpiration) throws RARException{
        String sql="update Customer set firstName='"+firstname+"', lastName='"+
                lastname+"',username='"+username+"',address='"+address+
                "',licState='"+licState+"',licNumber='"+licNumber+
                "',creditCardNumber='"+ccNumber+"',creditCardExpiration='"+ccExpiration+"' where email='"+
                email+"'";
        int updCount=0;
        try {
            Statement stmt=conn.createStatement();
            updCount=stmt.executeUpdate(sql);
            if(updCount==1){
                System.out.println("User profile updated successfully");
                return;
            }else{
                throw new RARException("CustomerManager.updateUserProfile:Couldn't update user profile");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Rental> restoreCustomerRental(Customer customer) throws RARException{
    	Rental R = null;
        String selectCustomersql= "select Rental.*, Vehicle.* from Vehicle "
        		+ "join Rental on Vehicle.ID = Rental.VehicleID "
        		+ "where CustomerID = "+customer.getId()+";";

        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        List<Rental> rental =new ArrayList<>();

        condition.setLength( 0 );

        query.append( selectCustomersql );

        if(customer!=null){

            if(customer.getId()>0){
                query.append(" where c.CustomerID='"+customer.getId()+"'");
            }

            else if(customer.getUserName()!=null){
                query.append(" where c.username='"+customer.getUserName()+"'");
            }

            else if(customer.getLicenseNumber()!=null){
                query.append(" where c.licNumber='"+customer.getLicenseNumber()+"'");
            }

            else{
                if(customer.getFirstName()!=null){
                    condition.append(" c.firstName='"+customer.getFirstName()+"'");
                }

                if(customer.getLastName()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.lastName='"+customer.getLastName()+"'");
                    }
                    else{
                        condition.append(" c.lastName='"+customer.getLastName()+"'");

                    }
                }

                if(customer.getPassword()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.password='"+customer.getPassword()+"'");
                    }
                    else{
                        condition.append(" c.password='"+customer.getPassword()+"'");

                    }
                }

                if(customer.getEmail()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.email='"+customer.getEmail()+"'");
                    }
                    else{
                        condition.append(" c.email='"+customer.getEmail()+"'");

                    }
                }

                if(customer.getAddress()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.address='"+customer.getAddress()+"'");
                    }
                    else{
                        condition.append(" c.address='"+customer.getAddress()+"'");

                    }

                }

                if(customer.getCreatedDate()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.createdDate='"+customer.getCreatedDate()+"'");
                    }
                    else{
                        condition.append(" c.createdDate='"+customer.getCreatedDate()+"'");

                    }
                }

                if(customer.getMemberUntil()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.memberUntil='"+customer.getMemberUntil()+"'");
                    }
                    else{
                        condition.append(" c.memberUntil='"+customer.getMemberUntil()+"'");

                    }
                }

                if(customer.getLicenseState()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.licState='"+customer.getLicenseState()+"'");
                    }
                    else{
                        condition.append(" c.licState='"+customer.getLicenseState()+"'");

                    }
                }

                if(customer.getCreditCardNumber()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.creditCardNumber='"+customer.getCreditCardNumber()+"'");
                    }
                    else{
                        condition.append(" c.creditCardNumber='"+customer.getCreditCardNumber()+"'");

                    }
                }

                if(customer.getCreditCardExpiration()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.creditCardExpiration='"+customer.getCreditCardExpiration()+"'");
                    }
                    else{
                        condition.append(" c.creditCardExpiration='"+customer.getCreditCardExpiration()+"'");

                    }
                }

                if(customer.getUserStatus()!=null){
                    if(condition.length()>0){
                        condition.append(" and c.Status='"+customer.getUserStatus()+"'");
                    }
                    else{
                        condition.append(" c.Status='"+customer.getUserStatus()+"'");

                    }
                }

                if( condition.length() > 0 ) {
                    query.append(" where");
                    query.append( condition );
                }
            }

        }

        try{

            stmt=conn.createStatement();

            if(stmt.execute(selectCustomersql)) {
                long id;
                Timestamp pickUpDate;
                Timestamp returnDate;
                int late;
                double charges;
                int customerID;
                int reservationID;
                int vehicleID;
                String Make;
                String Model;
                VehicleStatus Status; // Need to get this
                String status;

                ResultSet rs=stmt.getResultSet();
                
                while(rs.next()){

                    id=rs.getLong(1);
                    pickUpDate = rs.getTimestamp("pickUpDate");
                    returnDate = rs.getTimestamp("returnDate");
                    late = rs.getInt("Late");
                    charges = rs.getDouble("Charges");
                    customerID = rs.getInt("CustomerID");
                    reservationID = rs.getInt("ReservationID");
                    vehicleID = rs.getInt("VehicleID");
                    Make = rs.getString("Make");
                    Model = rs.getString("Model");
                    status = rs.getString("Status");
                    Vehicle v =objectLayer.createVehicle();
                    Reservation reservation = objectLayer.createReservation();
                    
                    v.setId(vehicleID);
                    v.setMake(Make);
                    v.setModel(Model);
                    //v.setStatus(Status);
                    reservation.setId(reservationID);
     
                    
                    Rental r = objectLayer.createRental();
                    r.setId(id);
                    r.setPickupTime(pickUpDate);
                    r.setReturnTime(returnDate);
                    r.setCharges((int)charges);
                    r.setVehicle(v);
                    r.setReservation(reservation);
                    System.out.println("Reservation added to rental: " + reservation);
                    
                    rental.add(r);

                }
                return rental;
            }


        }catch (Exception e){
        	e.printStackTrace();
            throw new RARException("CustomerManager.restoreReservations: Could not restore the reservations of the custoer; Root cause"+e);
        }

        throw new RARException("CustomerManager.restoreReservations: Could not restore the reservations of the custoer");

    }

    public void terminateCustomers(Customer customer) throws ClassNotFoundException, SQLException {
    	 Class.forName("com.mysql.jdbc.Driver");
    	 conn = DriverManager.getConnection(url, user, pass);
		
    	String sql = "delete from Customer where CustomerID = "+customer.getId()+";";
		Statement stmt = null;
		stmt = conn.createStatement();
		int delcnt;

        try {
            stmt=conn.createStatement();
            delcnt=stmt.executeUpdate(sql);
            if(delcnt==1){
                return;
            }
            else{
                throw new RARException("CustomerManager.terminateCustomer: failed to terminate customer");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
				throw new RARException("CustomerManager.terminateCustomer: failed to terminate customer");
			} catch (RARException e1) {
				e1.printStackTrace();
			}
        }
		
		
	}
    
    public void terminateCustomer(Customer customer){
   	 	
   	String sql = "delete from Customer where CustomerID = "+customer.getId()+";";
		Statement stmt = null;
	//	stmt = conn.createStatement();
		int delcnt;

       try {
           stmt=conn.createStatement();
           delcnt=stmt.executeUpdate(sql);
           if(delcnt==1){
               return;
           }
           else{
               throw new RARException("CustomerManager.terminateCustomer: failed to terminate customer");
           }
       } catch (Exception e) {
           e.printStackTrace();
           try {
				throw new RARException("CustomerManager.terminateCustomer: failed to terminate customer");
			} catch (RARException e1) {
				e1.printStackTrace();
			}
       }
		
		
	}
    
    public boolean updateStatus(String email){
    	String sql = "update Customer set Status='terminated' where email='" + email +"'";
    	PreparedStatement stmt = null;
    	int delcnt;
    	
    	try{
    		stmt = (PreparedStatement)conn.prepareStatement(sql);
    		delcnt = stmt.executeUpdate();
    		
    		if(delcnt == 1){
    			System.out.println("Successfully set status to terminated");
    			return true;
    		}
    		else
    			return false;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	
    }
    
 /*   public void updatePassword(String email, String changedPassword) throws RARException {
        String sql="UPDATE Customer set password='"+changedPassword+"' where email='"+email+"'";
        int updateCount=0;
        try {
            System.out.println("in update CustPwd try");
            Statement statement=conn.createStatement();
            updateCount=statement.executeUpdate(sql);
            if(updateCount==1){
                System.out.println("in update CustPwd if");

                return;
            }else{
                throw new RARException("CustomerManager.updatePassword: couldn't update password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/
}
