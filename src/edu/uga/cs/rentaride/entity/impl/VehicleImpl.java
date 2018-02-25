package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.impl.Persistence;
import edu.uga.cs.rentaride.entity.*;

public class VehicleImpl extends Persistence implements Vehicle{

    long ID;
    private String make;
	private String Model;
	private int Year;
	private String RegistrationTag;
	private int Mileage;
	private Date LastService;
	private VehicleStatus vehicleStatus;
	private VehicleCondition vehicleCondition;
	private VehicleType vehicleType;
	private RentalLocation rentalLocation;
	private List <Rental> Rentals;

	public VehicleImpl(){
		make = null;
		Model = null;
		Year = 0;
		RegistrationTag = null;
		Mileage = 0;
		LastService = null;
		vehicleStatus = null;
		vehicleCondition = null;
		vehicleType = null;
		rentalLocation = null;
		Rentals = null;
	}

	public VehicleImpl(String make, String model, int year, String tag, int mileage, Date LastServiced, VehicleStatus status, VehicleCondition condition, VehicleType vehicleType, RentalLocation rentalLocation){
		this.make = make;
		Model = model;
		Year = year;
		RegistrationTag = tag;
		Mileage = mileage;
		this.LastService = LastServiced;
		this.vehicleCondition = condition;
		this.vehicleType = vehicleType;
		this.rentalLocation = rentalLocation;
		vehicleStatus = VehicleStatus.INLOCATION;
		vehicleCondition = VehicleCondition.GOOD;
	}

	@Override
	public String getMake() {
		return make;
	}

	@Override
	public void setMake(String make) {
		this.make = make;
	}

	@Override
	public String getModel() {
		return Model;
	}

	@Override
	public void setModel(String model) {
		Model = model;
	}

	@Override
	public int getYear() {
		return Year;
	}

	@Override
	public void setYear(int year) throws RARException {
		if(year > 0) {
			Year = year;
		}
		else
			throw new RARException("Year is not valid");
	}

	@Override
	public String getRegistrationTag() {
		return RegistrationTag;
	}

	@Override
	public void setRegistrationTag(String registrationTag) {
		this.RegistrationTag = registrationTag;
	}

	@Override
	public int getMileage() {
		return Mileage;
	}

	@Override
	public void setMileage(int mileage) throws RARException {
		if(mileage > 0) {
			Mileage = mileage;
		}
		else throw new RARException("mileage is not valid");
	}

	@Override
	public Date getLastServiced() {
		return LastService;
	}

	@Override
	public void setLastServiced(Date lastServiced) {
		this.LastService = lastServiced;
	}

	@Override
	public VehicleStatus getStatus() {
		return vehicleStatus;
	}

	@Override
	public void setStatus(VehicleStatus status) {
		this.vehicleStatus = status;
	}

	@Override
	public VehicleCondition getCondition() {
		return vehicleCondition;
	}

	@Override
	public void setCondition(VehicleCondition condition) {
		vehicleCondition = condition;
	}

	@Override
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	@Override
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public RentalLocation getRentalLocation() {

		System.out.println("in vehicle impl getRL RL is "+rentalLocation);
		return rentalLocation;
	}

	@Override
	public void setRentalLocation(RentalLocation rentalLocation) throws RARException {
		this.rentalLocation = rentalLocation;
	}

	@Override
	public List<Rental> getRentals() {
		 if(Rentals==null){
	           try{
	               if(isPersistent()){
	                   RentalImpl rental=new RentalImpl();
	                   CustomerImpl cust = new CustomerImpl();
	                   rental.setCustomer(cust);
	                   Rentals=getPersistenceLayer().restoreRental(rental);
	               }
	           }catch (Exception e){
	               e.printStackTrace();
	           }
	        }
	        return Rentals;
	}

	public long getID(){
	    return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
