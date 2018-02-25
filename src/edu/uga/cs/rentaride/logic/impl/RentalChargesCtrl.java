package edu.uga.cs.rentaride.logic.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.*;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.util.Calendar;
import java.util.List;

public class RentalChargesCtrl {

    private ObjectLayer objectLayer=null;

    public RentalChargesCtrl(ObjectLayer objectLayer){
        this.objectLayer=objectLayer;
    }

    public void calculateCharges(User user){
        int charge=0;
        int used=1;
        int present=0;
        Vehicle tempVehicle=null;

        try {
            List<Rental> rentals=objectLayer.findRental(null);
            List<Reservation> reservations=objectLayer.findReservation(null);


          List<Customer> customers=objectLayer.findCustomer(null);
          for(Customer customer:customers){
               for(Reservation reservation1:reservations){
                  present=0;
                  if(reservation1.getCustomer().getId()==customer.getId() && reservation1.getPickupTime().getTime()- Calendar.getInstance().getTimeInMillis()<0) {
                       for (Rental rental : rentals) {
                           if (rental.getReservation().getId()==reservation1.getId()) {
                              present = 1;
                          }
                      }
                      if(present==0){
                          List<VehicleType> vehicleTypes=objectLayer.findVehicleType(null);
                          List<Vehicle> vehicles=objectLayer.findVehicle(null);
                          int veh=0;
                          for(VehicleType vehicleType:vehicleTypes){
                               if(reservation1.getVehicleType().getName().equals(vehicleType.getName())){
                                    for(Vehicle vehicle:vehicles){

                                      if(vehicle.getVehicleType().getName().equals(vehicleType.getName())){
                                          tempVehicle=vehicle;
                                          break;
                                      }
                                  }
                              }
                          }

                          Rental rental=objectLayer.createRental(reservation1.getPickupTime(),reservation1,tempVehicle);
                          List<HourlyPrice> hourlyPrices=objectLayer.findHourlyPrice(reservation1.getVehicleType());
                          for(HourlyPrice hourlyPrice:hourlyPrices){
                              charge=hourlyPrice.getPrice();
                              /*if(hourlyPrice.getVehicleType().getName().equals(reservation1.getVehicleType().getName())){
                                  System.out.println("////////////////// HP if price is "+hourlyPrice.getPrice());

                                  charge=hourlyPrice.getPrice();
                              }*/
                          }
                         rental.setCharges(charge);
                          objectLayer.storeRental(rental);
                      }
                  }
              }
          }




        } catch (RARException e) {
            e.printStackTrace();
        }


    }
}
