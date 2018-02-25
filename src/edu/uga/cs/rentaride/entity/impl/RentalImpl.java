/*
    Written by David Ponder
 */

package edu.uga.cs.rentaride.entity.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.persistence.impl.Persistence;
import edu.uga.cs.rentaride.persistence.impl.*;

/*
    This class represents a rental event, including a prior reservation, a vehicle being rented, and the customer.
 */

public class RentalImpl extends Persistence implements Rental {

    //Rental Attributes
    private Timestamp   pickupTime;
    private Date        returnTime;
    private boolean     isLate;
    private int         charges;
    private Reservation reservation;
    private Vehicle     vehicle;
    private Customer    customer;
    private Comment     comment;

    public RentalImpl(){
        super(-1);
        this.pickupTime = null;
        this.returnTime = null;
        this.isLate = false;
        this.charges = 0;
        this.reservation = null;
        this.vehicle = null;
        this.customer = null;
        this.comment = null;
    }

    public RentalImpl(Timestamp pickupTime, Date returnTime, boolean isLate, int charges, Reservation reservation, Vehicle vehicle, Customer customer, Comment comment){
        super(-1);
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.isLate = isLate;
        this.charges = charges;
        this.reservation = reservation;
        this.vehicle = vehicle;
        this.customer = customer;
        this.comment = comment;
    }
    
    public RentalImpl(Timestamp pickupTime, Date returnTime, boolean isLate, int charges, Reservation reservation, Vehicle vehicle, Customer customer){
        super(-1);
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.isLate = isLate;
        this.charges = charges;
        this.reservation = reservation;
        this.vehicle = vehicle;
        this.customer = customer;
    }

    public RentalImpl(Timestamp pickupTime, Reservation reservation, Vehicle vehicle2) {
  		//super(-1);
        System.out.println("bug before pickuptime");
        System.out.println("bug res id is  "+reservation.getId());
        this.pickupTime = reservation.getPickupTime();
        System.out.println("bug before reservation");

        this.reservation = reservation;
        System.out.println("bug before vehicle");

        this.vehicle = vehicle2;
  		//returnTime = reservation.getPickupTime();
  		Date rD=reservation.getPickupTime();
          rD.setTime(reservation.getPickupTime().getTime()+ TimeUnit.MINUTES.toMillis(reservation.getLength()));
          this.returnTime=rD;
        System.out.println("bug before late");

        isLate = false;
  		this.charges = 100;
        System.out.println("bug after charges");

        long customerID = reservation.getCustomer().getId();
          System.out.println("after setting res cust id");

          this.customer = reservation.getCustomer();
        System.out.println("voila RentalImpl createRental");

      }

	public Timestamp getPickupTime(){
        return this.pickupTime;
    }

    public void setPickupTime(Timestamp pickupTime){
        this.pickupTime = pickupTime;
    }

    public Date getReturnTime(){
        return this.returnTime;
    }

    public void setReturnTime(Timestamp returnTime) throws RARException{
            this.returnTime = returnTime;
    }

    public boolean getLate(){
        return this.isLate;
    }

    public int getCharges(){
        return this.charges;
    }

    public void setCharges(int charges) throws RARException{
        if(charges < 0)
            throw new RARException("ERROR: Charges must be a non-negative value!");
        else
            this.charges = charges;
    }

    public Reservation getReservation(){
        return this.reservation;
    }

    public void setReservation(Reservation reservation) throws RARException{
        if(reservation == null)
            throw new RARException("ERROR: Reservation cannot be set to a null value!");
        else
            this.reservation = reservation;
    }

    public Vehicle getVehicle(){
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) throws RARException{
        if(vehicle == null)
            throw new RARException("ERROR: Vehicle cannot be set to a null value!");
        else
            this.vehicle = vehicle;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    /* added by Chandana */
    public void setCustomer(Customer customer){
        this.customer=customer;
    }

    public Comment getComment(){
        return this.comment;
    }

    public void setComment(Comment comment){
        this.comment = comment;
    }
}
