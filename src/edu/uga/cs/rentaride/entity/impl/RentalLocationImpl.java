package edu.uga.cs.rentaride.entity.impl;


import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.persistence.impl.Persistence;


public class RentalLocationImpl extends Persistence implements RentalLocation {
    //Rental Location Attributes
    private String      name;
    private String      address;
    private int         capacity;
    private List<Reservation> reservation;
    private List<Vehicle> vehicle;

    public RentalLocationImpl(){
        this.name = null;
        this.address = null;
        this.capacity = 0;
    }

    public RentalLocationImpl(String name, String address, int capacity){
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        System.out.println("in rentallocationimpl constructor");
        System.out.println("name "+name);
        System.out.println("address "+address);
        System.out.println("capacity "+capacity);
        System.out.println("this "+this);

    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) throws RARException{
        if(name == null)
            throw new RARException("ERROR: Cannot set a Rental Location's name to null!");
        else
            this.name = name;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity) throws RARException{
        if(capacity < 0)
            throw new RARException("ERROR: Cannot set a Rental Location's capacity to a negative value!");
        else
            this.capacity = capacity;
    }

	@Override
	public List<Reservation> getReservations() {
		return reservation;
	}

	@Override
	public List<Vehicle> getVehicles() {
		return vehicle;
	}
}
