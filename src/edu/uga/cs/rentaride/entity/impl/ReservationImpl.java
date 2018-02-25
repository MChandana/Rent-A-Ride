package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

public class ReservationImpl extends Persistence implements Reservation{
    //Attributes
    private Timestamp            pickupTime;
    private int             length;
    private Customer        customer;
    private VehicleType     vehicleType;
    private RentalLocation  rentalLocation;
    private Rental          rental;
    private Calendar        calendar;
    private Boolean			canceled;

    public ReservationImpl(){
        this.pickupTime = null;
        this.length = 0;
        this.customer = null;
        this.vehicleType = null;
        this.rentalLocation = null;
        this.rental = null;
        this.canceled = null;
    }

    public ReservationImpl(Timestamp pickupTime, int length, Customer customer, VehicleType vehicleType, RentalLocation rentalLocation, Rental rental){
        this.pickupTime = pickupTime;
        this.length = length;
        this.customer = customer;
        this.vehicleType = vehicleType;
        this.rentalLocation = rentalLocation;
        this.rental = rental;
        canceled = false;
    }
    
    public ReservationImpl(Timestamp pickupTime, int length, Customer customer, VehicleType vehicleType, RentalLocation rentalLocation){
        this.pickupTime = pickupTime;
        this.length = length;
        this.customer = customer;
        this.vehicleType = vehicleType;
        this.rentalLocation = rentalLocation;
        canceled = false;
    }

    public Timestamp getPickupTime(){

        System.out.println("bug ResImpl getPickup "+pickupTime);
        return this.pickupTime;
    }

    public void setPickupTime(Timestamp pickupTime) throws RARException{
        
        /*if(pickupTime.before(today))
            throw new RARException("ERROR: Specified time is in the past!");
        else*/
            this.pickupTime = pickupTime;
    }

    public int getLength(){
        return this.length;
    }

    public void setLength(int length) throws RARException{
        if(length < 0)
            throw new RARException("ERROR: Specified length is negative!");
        else
            this.length = length;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public void setCustomer(Customer customer) throws RARException{
        if(customer == null)
            throw new RARException("ERROR: Customer cannot be null!");
        else
            this.customer = customer;
    }

    public VehicleType getVehicleType(){
        return this.vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) throws RARException{
        if(vehicleType == null)
            throw new RARException("ERROR: A reservation's vehicle type cannot be null!");
        else
            this.vehicleType = vehicleType;
    }

    public RentalLocation getRentalLocation(){
        return this.rentalLocation;
    }

    public void setRentalLocation(RentalLocation rentalLocation) throws RARException{
        if(rentalLocation == null)
            throw new RARException("ERROR: A reservation's rental location cannot be set to null!");
        else
            this.rentalLocation = rentalLocation;
    }

    public Rental getRental(){
        return this.rental;
    }
    
    public void setCanceled(Boolean x) {
    	canceled = x;
    }
    
    public Boolean getCanceled() {
    	return canceled;
    }

    public void setRental(Rental rental){
        this.rental = rental;
    }
}
