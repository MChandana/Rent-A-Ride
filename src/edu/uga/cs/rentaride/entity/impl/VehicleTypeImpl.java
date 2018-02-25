package edu.uga.cs.rentaride.entity.impl;

import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

public class VehicleTypeImpl extends Persistence implements VehicleType {

	private String name;
	private List<HourlyPrice> hrPrice;
	private List<Vehicle> veh;
	private List<Reservation> reservation;
	
	public VehicleTypeImpl(String name2) {
		this.name = name2;
	}
	
	public VehicleTypeImpl() {
		this.name = null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) throws RARException {
		this.name = name;
	}

	@Override
	public List<HourlyPrice> getHourlyPrices() {
		if(hrPrice==null){
	           try{
	               if(isPersistent()){
	                  HourlyPriceImpl hr = new HourlyPriceImpl();
	                   hrPrice=getPersistenceLayer().restoreHourlyPrice(hr);
	               }
	           }catch (Exception e){
	               e.printStackTrace();
	           }
	        }
	        return hrPrice;
	}

	@Override
	public List<Vehicle> getVehicles() {
		 if(veh==null){
	           try{
	               if(isPersistent()){
	                  VehicleImpl vehicle = new VehicleImpl();
	                   veh=getPersistenceLayer().restoreVehicle(vehicle);
	               }
	           }catch (Exception e){
	               e.printStackTrace();
	           }
	        }
	        return veh;
	}

	@Override
	public List<Reservation> getReservations() {
		if(reservation==null){
	           try{
	               if(isPersistent()){
	                  ReservationImpl reserv = new ReservationImpl();
	                   reservation=getPersistenceLayer().restoreReservation(reserv);
	               }
	           }catch (Exception e){
	               e.printStackTrace();
	           }
	        }
	        return reservation;
	}

}
