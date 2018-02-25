package edu.uga.cs.rentaride.logic.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.util.ArrayList;
import java.util.List;

public class DisplayRLCtrl {

    private ObjectLayer objectLayer=null;

    public DisplayRLCtrl(ObjectLayer objectLayer){
        this.objectLayer=objectLayer;
    }


    public List<Vehicle> getVehicles(String rlName){
        List<Vehicle> vehicleList= null;
        try {
            vehicleList = objectLayer.findVehicle(null);
        } catch (RARException e) {
            e.printStackTrace();
        }
        List<Vehicle> vehiclesResult=new ArrayList<>();
        if(!vehicleList.isEmpty()) {
            for (Vehicle vehicle : vehicleList) {
                if (vehicle.getRentalLocation().getName().equals(rlName)) {
                    vehiclesResult.add(vehicle);
                }
            }
        }
        return vehiclesResult;

    }

    public List<Reservation> getReservations(String rlName){
        List<Reservation> reservations= null;
        try {
            reservations = objectLayer.findReservation(null);

        } catch (RARException e) {
            e.printStackTrace();
        }
        List<Reservation> reservationsResult=new ArrayList<>();

        for (Reservation reservation : reservations) {
                if (reservation.getRentalLocation().getName().equals(rlName)) {
                    reservationsResult.add(reservation);
                }

            }

        return reservationsResult;
    }


    public List<Rental> getRentals(String rlName) throws RARException {
        List<Rental> rentals=objectLayer.findRental(null);
        List<Rental> rentalsResult=new ArrayList<>();
        List<Reservation> reservations=objectLayer.findReservation(null);
        List<Reservation> reservationsResult=new ArrayList<>();

        if(!reservations.isEmpty()){
            for(Reservation reservation:reservations){

                for(Rental rental:rentals){
                    if(rental.getReservation().getId()==reservation.getId()){
                        rentalsResult.add(rental);
                    }
                }
            }
        }

        return rentalsResult;
    }

}
