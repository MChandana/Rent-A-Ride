package edu.uga.cs.rentaride.persistence.impl;


import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.CommentImpl;
import edu.uga.cs.rentaride.entity.impl.RentalImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentManager {

    private ObjectLayer objectLayer=null;
    private Connection conn=null;
    private String driver, url, pass,user;
    
    public CommentManager(Connection conn,ObjectLayer objectLayer) {
        this.objectLayer = objectLayer;
        this.conn = conn;
    }
    
    public CommentManager(){
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://uml.cs.uga.edu/team4";
        pass = "seprocess";
        user = "team4";
    }

    public void store(Comment comment) throws RARException {

        String insertCommentsql="insert into Comments(CustomerID, text, date, RentalID) values(?,?,?,?)";
        String updateCommentsql="update Comments set CustomerID=?, text=?, date=?, RentalID=? where ID=?";

        PreparedStatement stmt=null;
        int inscnt;
        long ID;

        try{

            if(!comment.isPersistent()){
                stmt=(PreparedStatement)conn.prepareStatement(insertCommentsql);
            }else{
                stmt=(PreparedStatement)conn.prepareStatement(updateCommentsql);
            }

            if(comment.getCustomer()!=null){
                long CustomerID= comment.getCustomer().getId();
                stmt.setLong(1,CustomerID);
            }
            else {
                throw new RARException("CommentManager.save failed: Customer is undefined");
            }

            if(comment.getText()!=null){
                stmt.setString(2, comment.getText());
            }
            else{
                throw new RARException("CommentManager.save failed: Text is undefined");
            }

            if(comment.getDate()!=null){
                java.util.Date jdate=comment.getDate();
                java.sql.Date sdate=new java.sql.Date(jdate.getTime());
                stmt.setDate(3, sdate);;
            }
            else{
                stmt.setNull(3, java.sql.Types.DATE);
            }

            if(comment.getRental()!=null){
                long rentalID= comment.getRental().getId();
                stmt.setLong(4,rentalID);
            }
            else {
                throw new RARException("CommentManager.save failed: Rental is undefined");
            }

            if(comment.isPersistent()){
                stmt.setLong(5,comment.getId());
            }

            inscnt=stmt.executeUpdate();

            if(!comment.isPersistent()){
                if(inscnt==1){
                    String sql="select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet rs=stmt.getResultSet();

                        while(rs.next()){
                            ID=rs.getLong(1);
                            if(ID>0){
                                comment.setId(ID);
                            }
                        }
                    }
                }
            }
            else{
                if(inscnt<1){
                    throw new RARException("CommentManager.save: failed to save comment ");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RARException("CommentManager.save: failed to save comment");
        }
    }

    public List<Comment> restore(Comment comment) throws RARException {

        String sql="select * from Comments";
        Statement stmt=null;
        List<Comment> comments=new ArrayList<>();

        try{

            stmt=conn.createStatement();

            if(stmt.execute(sql)){

                ResultSet rs= stmt.getResultSet();

                long ID;
                long CustomerID;
                long RentalID;
                String text;
                Date date;
                Rental rental;


                while(rs.next()){
                    ID=rs.getLong(1);
                    CustomerID=rs.getLong(2);
                    text=rs.getString(3);
                    date=rs.getDate(4);
                    RentalID=rs.getLong(5);
                    
                    CommentImpl c = new CommentImpl();
                    c.setText(text);
                    c.setDate(date);
                    
                    RentalImpl r = new RentalImpl();
                    r.setId(RentalID);

                    //Rental modelRental=new RentalImpl();
                    //modelRental.setId(RentalID);
                    //List<Rental> rentallist= Persistence.getPersistenceLayer().restoreRental(modelRental);
                    //Rental rental=rentallist.get(0);

                    Comment comment1=objectLayer.createComment(text,date,r);
                    comment1.setId(ID);

                    comments.add(comment1);


                }
            }

            return comments;

        }catch (Exception e){
            throw new RARException("CommentManager.save: failed to restore persistent comment");
        }

    }

    public void storeRentalComment(Rental rental,Comment comment) throws RARException{

        String insertRentalCommentsql="update Comments set RentalID='"+rental.getId()+"'"+ " where RentalID='"+rental.getId()+"'";
        Statement stmt=null;
        int updcnt;

        if(!comment.isPersistent()){
            return;
        }

        try {

        	stmt=conn.createStatement();
            if(stmt.execute(insertRentalCommentsql)){
                ResultSet rs=stmt.getResultSet();
                long adminID;
                String firstName;
                String lastName;
                String username;
                String password;
                String email;
                String address;
                Date createdDate;


                while(rs.next()){
                	adminID = rs.getLong(1);
                	firstName=rs.getString(2);
                    lastName=rs.getString(3);
                    username=rs.getString(4);
                    password=rs.getString(5);
                    email=rs.getString(6);
                    address=rs.getString(7);
                    createdDate=rs.getDate(8);

                    Administrator admin=objectLayer.createAdministrator(firstName,lastName,username,password,email,address,createdDate);
                    //admin.setId(adminID);
                    //administrators.add(admin);
                }


            }


        }catch (Exception e){
            throw new RARException("CommentManager.storeRentalComment: failed to save comment for the rental");
        }

    }

    public Rental restoreRentalComment(Comment comment) throws RARException{

        String restoreRentalsql="select * from Rental r, Comments c where c.RentalID=r.ID";

        Rental rental=null;

        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        query.append( restoreRentalsql );

        if(comment!=null){

            if(comment.getId()>0){
                query.append(" and ID='"+comment.getId()+"'");
            }
            else{
                if(comment.getDate()!=null){
                    condition.append(" and date='"+ comment.getDate()+"'");
                }

                if(comment.getText()!=null){
                    condition.append(" and text='"+comment.getText()+"'");
                }

                if(comment.getCustomer().getId()>0){
                    condition.append(" and CustomerID='"+comment.getCustomer().getId()+"'");
                }

                if(comment.getRental().getId()>0){
                    condition.append(" and RentalId='"+comment.getRental().getId()+"'");
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
                Timestamp pickUpDate;
                Timestamp returnDate;
                boolean late;
                int charges;
                long RentalID;
                long CustomerID;
                long ReservationID;
                long VehicleID;

                while(rs.next()){
                    ID=rs.getLong(1);
                    pickUpDate=rs.getTimestamp(2);
                    returnDate=rs.getTimestamp(3);
                    late=rs.getBoolean(4);
                    charges=rs.getInt(5);
                    CustomerID=rs.getLong(6);
                    ReservationID=rs.getLong(7);
                    VehicleID=rs.getLong(8);

                    rental=objectLayer.createRental();

                    rental.setId(ID);
                    rental.setCharges(charges);
                    rental.setPickupTime(pickUpDate);
                    rental.setReturnTime(returnDate);

                    Reservation reservation=objectLayer.createReservation();
                    reservation.setId(ReservationID);
                    rental.setReservation(Persistence.getPersistenceLayer().restoreReservation(reservation).get(0));

                    Vehicle vehicle=objectLayer.createVehicle();
                    vehicle.setId(VehicleID);
                    rental.setVehicle(Persistence.getPersistenceLayer().restoreVehicle(vehicle).get(0));


                }

                return rental;

            }

        }catch (Exception e){
            throw new RARException("CommentManager.restoreRentalComment: failed to restore persistent rental for this comment");
        }

        throw new RARException("CommentManager.restoreRentalComment: failed to restore persistent rental for this comment");
    }

    public List<Comment> restoreRentalComent(Rental rental) throws RARException {
        List<Comment> comments=new ArrayList<>();

        String selectRentalCommentssql="select * from Comments where RentalID='"+rental.getId()+"'";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        query.append( selectRentalCommentssql );

        try{
            stmt=conn.createStatement();

            if(stmt.execute(query.toString())){

                ResultSet rs=stmt.getResultSet();

                long ID;
                long CustomerID;
                String text;
                Date date;
                long RentalID;

                Comment comment=null;

                while(rs.next()){
                    ID=rs.getLong(1);
                    CustomerID=rs.getLong(2);
                    text=rs.getString(3);
                    date=rs.getDate(4);
                    RentalID=rs.getLong(5);

                    comment=objectLayer.createComment();
                    comment.setId(ID);
                    comment.setDate(date);
                    comment.setText(text);

                    Rental rental1=objectLayer.createRental();
                    rental1.setId(RentalID);
                    comment.setRental(Persistence.getPersistenceLayer().restoreRental(rental1).get(0));

                  //  comment.getCustomer().setId(CustomerID);
                    comments.add(comment);

                }
            }

            return comments;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RARException("CommentManager.restoreRentalComment: failed to restore list of comments for this rental");
        }

    }

    public void delete(Comment comment) throws RARException{

        String deleteCommentsql="delete from Comment where ID=?";
        PreparedStatement stmt=null;
        int delcnt;

        if(!comment.isPersistent()){
            return;
        }

        try{

            stmt=(PreparedStatement)conn.prepareStatement(deleteCommentsql);
            stmt.setLong(1,comment.getId());

            delcnt=stmt.executeUpdate();

            if(delcnt==0){
                throw new RARException("CommentManager.delete: failed to delete this comment");
            }

        }catch (SQLException e){
            throw new RARException("CommentManager.delete: failed to delete this comment");
        }

    }

    public void deleteRentalComment(Rental rental,Comment comment) throws RARException{

        String deleteCommentsql="update Comments set RentalID=0 where RentalD='"+rental.getId()+"'";
        Statement stmt=null;
        int delcnt;

        try {
            stmt=conn.createStatement();
            delcnt=stmt.executeUpdate(deleteCommentsql);
            if(delcnt==1){
                return;
            }
            else{
                throw new RARException("CommentManager.deleteRentalComment: failed to delete comment for this rental");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RARException("CommentManager.deleteRentalComment: failed to delete comment for this rental");
        }


    }
    
    public void storeComment(long CustomerID, long RentalID,String comment) throws ClassNotFoundException, SQLException {
    	Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        String insertCommentsql="insert into Comments(CustomerID, text, date, RentalID) values(?,?,?,?)";
    	 Class.forName("com.mysql.jdbc.Driver");
    	 conn = DriverManager.getConnection(url, user, pass);
    	 PreparedStatement stmt=null;
         int inscnt = 0;
         long ID;
         
         stmt=(PreparedStatement)conn.prepareStatement(insertCommentsql);
         stmt.setLong(1,CustomerID);
         stmt.setString(2, comment);
         stmt.setTimestamp(3,currentTimestamp);
         stmt.setLong(4, RentalID);
         inscnt=stmt.executeUpdate();
         
         
    	 
    	 
    }


}
