package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministratorManager {

    private ObjectLayer objectLayer=null;
    private Connection conn=null;
    private String driver,url,pass,user;


    public AdministratorManager(Connection conn,ObjectLayer objectLayer){
        this.conn=conn;
        this.objectLayer=objectLayer;
    }

    public AdministratorManager(){
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://uml.cs.uga.edu/team4";
        pass = "seprocess";
        user = "team4";
    }

    public void store(Administrator administrator) throws RARException{
        String insertAdministratorSql="insert into Admins(firstName,lastName,username,password,email,address,createdDate) values(?,?,?,?,?,?,?)";
        String updateAdministratorSql="update Admins set firstName=?, lastName=?, username=?, password=?, email=?, address=?, createdDate=? where adminId=?";
        PreparedStatement stmt=null;
        int inscnt;
        long adminID;
        try{
            if(!administrator.isPersistent()){
                stmt=(PreparedStatement)conn.prepareStatement(insertAdministratorSql);
            }
            else{
                stmt=(PreparedStatement)conn.prepareStatement(updateAdministratorSql);
            }
            if(administrator.getFirstName()!=null){
                stmt.setString(1,administrator.getFirstName());
            }else{
                throw new RARException("AdministratorManager.save: can't save administrator: firstName undefined");
            }
            if(administrator.getLastName()!=null){
                stmt.setString(2,administrator.getLastName());
            }else{
                throw new RARException("AdministratorManager.save: can't save administrator: lastName undefined");
            }
            if(administrator.getUserName()!=null){
                stmt.setString(3,administrator.getUserName());
            }else{
                throw new RARException("AdministratorManager.save: can't save administrator: userName undefined");
            }
            if(administrator.getPassword()!=null){
                stmt.setString(4,administrator.getPassword());
            }else{
                throw new RARException("AdministratorManager.save: can't save administrator: password undefined");
            }
            if(administrator.getEmail()!=null){
                stmt.setString(5,administrator.getEmail());
            }else{
                throw new RARException("AdministratorManager.save: can't save administrator: email undefined");
            }
            if(administrator.getAddress()!=null){
                stmt.setString(6,administrator.getAddress());
            }else{
                throw new RARException("AdministratorManager.save: can't save administrator: address undefined");
            }
            if(administrator.getCreatedDate()!=null){
                java.util.Date jDate=administrator.getCreatedDate();
                java.sql.Date sDate=new java.sql.Date(jDate.getTime());
                stmt.setDate(7,sDate);
            }else{
                stmt.setNull(7, java.sql.Types.DATE);
            }
            if(administrator.isPersistent()){
                stmt.setLong(8,administrator.getId());
            }

            inscnt=stmt.executeUpdate();

            if(!administrator.isPersistent()){
                if(inscnt==1){
                    String sql="select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet rs=stmt.getResultSet();
                        while(rs.next()){
                            adminID=rs.getLong(1);
                            if(adminID>0){
                                administrator.setId(adminID);
                            }
                        }
                    }
                }
            }
            else{
                if(inscnt<1){
                    throw new RARException("AdministraorManager.save: failed to save administrator");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RARException("AdministratorManager.save: failed to save admin"+e);
        }
    }

    public List<Administrator> restore(Administrator administrator) throws RARException{
       String sql = "select * from Admins";
       Statement stmt = null;
       List<Administrator> administrators=new ArrayList<>();
        try{
            System.out.println("in admin manager restore admin");
            System.out.println("conn is "+conn);
            stmt=conn.createStatement();
            System.out.println("after creating statement");

            if(stmt.execute(sql)){
                System.out.println("execute sql in if");
                ResultSet rs=stmt.getResultSet();
                System.out.println("after result set "+rs);
                long adminID;
                String firstName;
                String lastName;
                String username;
                String password;
                String email;
                String address;
                Date createdDate;
                

                while(rs.next()){
                    System.out.println("while rs.next");
                    adminID = rs.getLong(1);
                    System.out.println("after col 1");
                    firstName=rs.getString(2);
                    System.out.println("after col 2");

                    lastName=rs.getString(3);
                    System.out.println("after col 3");

                    username=rs.getString(4);
                    System.out.println("after col 4");

                    password=rs.getString(5);
                    System.out.println("after col 5");

                    email=rs.getString(6);
                    System.out.println("after col 6");

                    address=rs.getString(7);
                    System.out.println("after col 7");

                    createdDate=rs.getDate(8);
                    System.out.println("after col 8");


                    System.out.println("got all values");

                    Administrator admin=objectLayer.createAdministrator(firstName,lastName,username,password,email,address,createdDate);
                    //admin.setId(adminID);
                    System.out.println("adding admin to list");
                    administrators.add(admin);
                    System.out.println("admin detials " +firstName+" , "+lastName+" , "+email);
                }

                System.out.println("before returning admins");
            return administrators;
            }
        }catch(Exception e){
            throw  new RARException("AdministratorManager.restore: couldn't restore persistent administrator; Root cause: "+ e);
        }
            throw new RARException("AdministratorManager.restore: couldn't restore persistent administrator");
    }

    public void delete(Administrator administrator) throws RARException{
        String deleteAdminsql="delete from Admins where adminID= "+"'"+administrator.getId()+"'";
       // String selectAdmincountsql="select count(adminID) from Admins";
        Statement stmt=null;
        int delcnt;
        int count;

        if(!administrator.isPersistent()){
            return;
        }

        try{

            stmt=conn.createStatement();
            //count=stmt.executeUpdate(deleteAdminsql);
            //if(count==1){
              //  throw new RARException("AdministratorManager.delete: failed to delete this admin as there should be atleast one admin in the system");
            //}
            delcnt=stmt.executeUpdate(deleteAdminsql);

            if(delcnt==0){
                throw new RARException("AdministratorManager.delete: failed to delete this person");
            }
            else{

            }

        }catch (Exception e){
            throw new RARException("AdministratorManager.delete: failed to delete this admin "+e.getMessage());
        }
    }
    
    public boolean isCorrect(String email, String password) throws SQLException {
    	boolean tester = false;
    	String sql="select * from Admins where email = '"+email+"' ;";
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
    
    public void updateFirstName(long ID, String column1, String value) throws ClassNotFoundException{
    	 Class.forName("com.mysql.jdbc.Driver");
    	 System.out.println("i'm here in update first name");
  	   
 		String sql = "UPDATE Admins SET ";
 		sql += column1+" = '"+value+"' ";
 		sql += "WHERE adminID = "+ID;
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
    
    public long getAdminID(String email) throws ClassNotFoundException {
    	 Class.forName("com.mysql.jdbc.Driver");
    	long adminID = 0;
    	
    	String sql="select adminID from Admins where email = '"+email+"' ;";
       	 ResultSet resultSet = null;
       	 Statement stmt;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			stmt = conn.createStatement();
			 if(stmt.execute(sql)){
	                ResultSet rs=stmt.getResultSet();
	                String name = null;
	                while(rs.next()){
	                	adminID = rs.getLong("adminID");
	                }
	       	 }
	       	 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	 
     
       	 return adminID;
    	
    }

   /* public void updatePassword(String email, String changedPassword) throws RARException {
        String sql="UPDATE Admins set password='"+changedPassword+"' where email='"+email+"'";
        int updateCount=0;
        try {
            System.out.println("in update AdminPwd try");
            Statement statement=conn.createStatement();
            updateCount=statement.executeUpdate(sql);
            if(updateCount==1){
                System.out.println("in update AdminPwd if");

                return;
            }else{
                throw new RARException("AdminManager.updatePassword: couldn't update password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
