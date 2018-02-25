package edu.uga.cs.rentaride.entity.impl;

//import com.sun.org.apache.regexp.internal.RE;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.entity.impl.RentalImpl;
import edu.uga.cs.rentaride.persistence.impl.Persistence;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomerImpl extends Persistence implements Customer {

    private long CustomerID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String address;
    private Date createdDate;
    private String licNumber;
    private Date memberUntil;
    private String licState;
    private String creditCardNumber;
    private Date creditCardExpiration;
    private UserStatus userStatus;

    private List<Reservation> reservations;
    private List<Rental> rentals;
    private List<Comment> comments;


    public CustomerImpl(){
        super(-1);
        this.firstName=null;
        this.lastName=null;
        this.username=null;
        this.password=null;
        this.email=null;
        this.address=null;
        this.createdDate=null;
        this.licNumber=null;
        this.licState=null;
        this.memberUntil=null;
        this.userStatus=null;
        this.creditCardNumber = null;
        this.creditCardExpiration = null;

    }

    public CustomerImpl(String firstName, String lastName, String username, String password, String email, String address, Date createdDate, Date memberUntil, String licState,String licNumber, String creditCardNumber,Date creditCardExpiration) {
        super(-1);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.createdDate = createdDate;
        this.licNumber = licNumber;
        this.memberUntil = memberUntil;
        this.licState = licState;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiration = creditCardExpiration;

        userStatus=UserStatus.ACTIVE;


    }

    @Override
    public Date getMemberUntil() {
        return memberUntil;
    }

    @Override
    public void setMemberUntil(Date memberUntil) throws RARException {

            Date currentDate= Calendar.getInstance().getTime();
            if(memberUntil.compareTo(currentDate)>0)
                this.memberUntil = memberUntil;
            else
                throw new RARException("This person's membership expiration date is not valid");
    }

    @Override
    public String getLicenseState() {
        return licState;
    }

    @Override
    public void setLicenseState(String state) {
        this.licState = licState;
    }

    @Override
    public String getLicenseNumber() {
        return licNumber;
    }

    @Override
    public void setLicenseNumber(String licenseNumber) {
        this.licNumber = licNumber;
    }

    @Override
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    @Override
    public void setCreditCardNumber(String cardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public Date getCreditCardExpiration() {
        return creditCardExpiration;
    }

    @Override
    public void setCreditCardExpiration(Date cardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    @Override
    public List<Reservation> getReservations() {
        if(reservations==null) {
            try {
                if (isPersistent()) {
                    reservations = getPersistenceLayer().restoreCustomerReservation(this);
                } else {
                    throw new RARException("This person object is not persistent");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return reservations;
    }

/*
//not needed to implement
    @Override
    public List<Comment> getComments() {

        if(comments==null){
            try{
                if(isPersistent()){

                    CommentImpl modelComment=new CommentImpl();
                    modelComment.setCustomer(this);
                    comments=getPersistenceLayer().restoreComment(modelComment);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return comments;
    }

    @Override
    public List<Rental> getRentals() {
        if(rentals==null){
           try{
               if(isPersistent()){
                   RentalImpl rental=new RentalImpl();
                   rental.setCustomer(this);
                   rentals=getPersistenceLayer().restoreRental(rental);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        return rentals;
    }

    */

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public void setUserName(String userName) throws RARException {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public UserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;

    }

    @Override
    public long getId() {
        return CustomerID;
    }

    @Override
    public void setId(long id) {
        this.CustomerID=id;
    }

    @Override
    public boolean isPersistent() {
        return CustomerID>0 ;
    }

	@Override
	public List<Comment> getComments() {
		return comments;
	}

	   @Override
	    public List<Rental> getRentals() {
	        if(rentals==null) {
	            try {
	                if (isPersistent()) {
	                    rentals = getPersistenceLayer().restoreCustomerRental(this);
	                } else {
	                    throw new RARException("This person object is not persistent");
	                }
	            }catch (Exception e){
	                e.printStackTrace();
	            }
	        }
	        return rentals;
	    }
	   
	 
	@Override
	public List<Rental> restoreCustomerRental(Customer customer) throws RARException {
		// TODO Auto-generated method stub
		return null;
	}
	  
}

