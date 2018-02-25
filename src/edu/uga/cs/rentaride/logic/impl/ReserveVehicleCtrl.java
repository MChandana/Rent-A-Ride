package edu.uga.cs.rentaride.logic.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class ReserveVehicleCtrl {

    private ObjectLayer objectLayer=null;

    public ReserveVehicleCtrl(ObjectLayer objectLayer){
        this.objectLayer=objectLayer;
    }

    public void reserveVehicle(String loc, Timestamp puDsql, int length_in_minutes, long custId, String vt) throws RARException{

        List<VehicleType> vehicleTypes=objectLayer.findVehicleType(null);
        List<RentalLocation> rentalLocations=objectLayer.findRentalLocation(null);
        List<Customer> customers=objectLayer.findCustomer(null);
        VehicleType vehicleType=null;
        RentalLocation rentalLocation=null;
        Customer customer=null;

        System.out.println("ReserveVehicleCtrl details--------");
        System.out.println("loc "+loc);
        System.out.println("date "+puDsql);
        System.out.println("len "+length_in_minutes);
        System.out.println("cust "+custId);
        System.out.println("vt "+vt);

        for(VehicleType vehicleType1:vehicleTypes){
            System.out.println("RerserveVehicleCtrl for VT name "+vehicleType1.getName());
            System.out.println("req vt is "+vt);
            if(vehicleType1.getName().equals(vt)){
                System.out.println("RerserveVehicleCtrl getting vts if");
                vehicleType=vehicleType1;
            }
        }

        for(RentalLocation rentalLocation1:rentalLocations){
            System.out.println("RerserveVehicleCtrl for RL name "+rentalLocation1.getName());
            System.out.println("req rl is "+loc);
            if(rentalLocation1.getName().equals(loc)){
                System.out.println("RerserveVehicleCtrl getting RLs if");

                rentalLocation=rentalLocation1;
            }
        }

        for(Customer customer1:customers){
            if(customer1.getId()==custId){
                customer=customer1;
            }
        }
        System.out.println("before creating resrvation");
        System.out.println("RerserveVehicleCtrl puD "+puDsql);
        System.out.println("RerserveVehicleCtrl len "+length_in_minutes);
        System.out.println("RerserveVehicleCtrl vt "+vehicleType.getName());
        System.out.println("RerserveVehicleCtrl RL "+rentalLocation.getName());
        System.out.println("RerserveVehicleCtrl cust name "+customer.getEmail());

        Reservation reservation=objectLayer.createReservation(puDsql,length_in_minutes,vehicleType,rentalLocation,customer);
        System.out.println("before storing resrvation");

        objectLayer.storeReservation(reservation);
    }
}
