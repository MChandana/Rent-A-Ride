package edu.uga.cs.rentaride.persistence.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentARideParams;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.VehicleTypeManger;


// These imports were added in addition ot the interface imports
//import edu.uga.clubs.persistence.PersistenceLayer;
import java.sql.Connection;
import java.sql.SQLException;

import edu.uga.cs.rentaride.object.ObjectLayer;

public class PersistenceLayerImpl implements PersistenceLayer
{
    private AdministratorManager adminManager = null;
    private CustomerManager customerManager = null;
    private RentalLocationManager rentalLocationManager = null;
    private RentalManager rentalManager = null;
    private ReservationManager reservationManager = null;
    private VehicleTypeManger vehicleTypeManager = null;
    private VehicleManager vehicleManager = null;
    private CommentManager commentManager = null;
    private HourlyPriceManager hourlyPriceManager = null;

    public PersistenceLayerImpl(Connection conn, ObjectLayer objectLayer)
    {
	adminManager = new AdministratorManager(conn,objectLayer);
	customerManager = new CustomerManager(conn,objectLayer);
	rentalLocationManager = new RentalLocationManager(conn,objectLayer);
	rentalManager = new RentalManager(conn,objectLayer);
	reservationManager = new ReservationManager(conn,objectLayer);
	vehicleTypeManager = new VehicleTypeManger(conn,objectLayer);
	vehicleManager = new VehicleManager(conn,objectLayer);
	commentManager = new CommentManager(conn,objectLayer);
	hourlyPriceManager = new HourlyPriceManager(conn,objectLayer);
	System.out.println("PersistenceLayerImpl.PersistenceLayerImpl(conn,objectLayer): initialized");
    }

    public List<Administrator> restoreAdministrator( Administrator modelAdministrator ) throws RARException
    {
	return adminManager.restore(modelAdministrator);
    }

    public void storeAdministrator( Administrator administrator ) throws RARException
    {
	adminManager.store(administrator);
    }

    public void deleteAdministrator( Administrator administrator ) throws RARException
    {
	adminManager.delete(administrator);
    }

    public List<Customer> restoreCustomer( Customer modelCustomer ) throws RARException
    {
	return customerManager.restore(modelCustomer);
    }

    public void storeCustomer( Customer customer ) throws RARException
    {
	customerManager.store(customer);
    }

    public List<RentalLocation> restoreRentalLocation( RentalLocation modelRentalLocation ) throws RARException
    {
	return rentalLocationManager.restore(modelRentalLocation);
    }
    public void storeRentalLocation( RentalLocation rentalLocation ) throws RARException
    {
	rentalLocationManager.store(rentalLocation);
    }

    public void deleteRentalLocation( RentalLocation rentalLocation ) throws RARException
    {
	rentalLocationManager.delete(rentalLocation);
    }

    public List<Reservation> restoreReservation( Reservation modelReservation ) throws RARException
    {
	return reservationManager.restore(modelReservation);
    }

    public void storeReservation( Reservation reservation ) throws RARException
    {
	reservationManager.store(reservation);
    }

    public void deleteReservation( Reservation reservation ) throws RARException
    {
	reservationManager.delete(reservation);
    }

    public List<Rental> restoreRental( Rental modelRental ) throws RARException
    {
	return rentalManager.restore(modelRental);
    }

    public void storeRental( Rental rental ) throws RARException
    {
	rentalManager.store(rental);
    }

    public void deleteRental( Rental rental ) throws RARException
    {
	rentalManager.delete(rental);
    }

    public List<VehicleType> restoreVehicleType( VehicleType modelVehicleType ) throws RARException
    {
    	return vehicleTypeManager.restore(modelVehicleType);
    }

    public void storeVehicleType( VehicleType vehicleType ) throws RARException
    {
	vehicleTypeManager.store(vehicleType);
    }

    public void deleteVehicleType( VehicleType vehicleType ) throws RARException
    {
	vehicleTypeManager.delete(vehicleType);
    }

    public List<Vehicle> restoreVehicle( Vehicle modelVehicle ) throws RARException
    {
	return vehicleManager.restore(modelVehicle);
    }

    public void storeVehicle( Vehicle vehicle ) throws RARException
    {
	vehicleManager.store(vehicle);
    }

    public void deleteVehicle( Vehicle vehicle ) throws RARException
    {
	vehicleManager.delete(vehicle);
    }

    public List<Comment> restoreComment( Comment modelComment ) throws RARException
    {
	return commentManager.restore(modelComment);
    }

    public void storeComment( Comment comment ) throws RARException
    {
	commentManager.store(comment);
    }

    public void deleteComment( Comment comment ) throws RARException
    {
	commentManager.delete(comment);
    }

    public List<HourlyPrice> restoreHourlyPrice( HourlyPrice modelHourlyPrice ) throws RARException
    {
	return hourlyPriceManager.restore(modelHourlyPrice);
    }

    public void storeHourlyPrice( HourlyPrice hourlyPrice ) throws RARException
    {
	hourlyPriceManager.store(hourlyPrice);
    }

    public void deleteHourlyPrice( HourlyPrice hourlyPrice ) throws RARException
    {
	hourlyPriceManager.delete(hourlyPrice);
    }

    public RentARideParams restoreRentARideConfig() throws RARException
    {
        //NEEDS TO BE IMPLEMENTED
    	return null;
    }

    public void storeRentARideConfig( RentARideParams rentARideConfig ) throws RARException
    {

    }

    // Associations
    //
    // Customer--creates-->Reservation;   multiplicity: 1 - *
    // Reservation--hasLocation-->RentalLocation;   multiplicity: * - 1
    // Reservation--hasType-->VehicleType   multiplicity: * - 1
    // Vehicle--locatedAt-->RentalLocation;   multiplicity: * - 1
    // Vehicle--hasType-->VehicleType   multiplicity: * - 1
    // VehicleType--hasPrice-->HourlyPrice   multiplicity: 1 - 1..*
    // Rental--describedBy-->Comment   multiplicity: 1 - 0..1
    // Rental--basedOn-->Reservation   multiplicity: 0..1 - 1
    // Vehicle--usedIn-->Rental   multiplicity: 1 - *

    // Customer--creates-->Reservation;   multiplicity: 1 - *

    public void storeCustomerReservation( Customer customer, Reservation reservation ) throws RARException
    {
	if (reservation == null)
	    throw new RARException("The customer's reservation is null");
	if (!reservation.isPersistent())
	    throw new RARException("The customer's reservation is not persisitent");

		/* to be implemented in ReservationManager as reservation has CustomerID FK */

   
	customerManager.store(customer);

    }

    public Customer restoreCustomerReservation( Reservation reservation ) throws RARException
    {
	return customerManager.restoreCustomerReservation(reservation);
    }

    public List<Reservation> restoreCustomerReservation( Customer customer ) throws RARException
    {
	return customerManager.restoreCustomerReservation(customer);
    }

    public void deleteCustomerReservation( Customer customer, Reservation reservation ) throws RARException
    {
	if (reservation == null)
	    throw new RARException("The customer's reservation is null");
	if (!reservation.isPersistent())
	    throw new RARException("The customer's reservation is not persisitent");
	customerManager.deleteCustomerReservation(customer,reservation);
	//customer.deleteReservation(reservation);
	//customerManager.store(customer);
    }

    // Reservation--hasLocation-->RentalLocation;   multiplicity: * - 1
    //
    public void storeReservationRentalLocation( Reservation reservation, RentalLocation rentalLocation ) throws RARException
    {
	if (rentalLocation == null)
	    throw new RARException("The reservations's rental location is null");
	if (!rentalLocation.isPersistent())
	    throw new RARException("The reservation's rental location is not persisitent");
	reservation.setRentalLocation(rentalLocation);
	reservationManager.store(reservation);
    }

    public RentalLocation restoreReservationRentalLocation( Reservation reservation ) throws RARException
    {
	return reservation.getRentalLocation();
    }

    public List<Reservation> restoreReservationRentalLocation( RentalLocation rentalLocation ) throws RARException
    {
	return rentalLocation.getReservations();
    }

    public void deleteReservationRentalLocation( Reservation reservation, RentalLocation rentalLocation ) throws RARException
    {
	if (rentalLocation == null)
            throw new RARException("The reservations's rental location is null");
        if (!rentalLocation.isPersistent())
            throw new RARException("The reservation's rental location is not persisitent");
       
        	reservationManager.deleteReservationRentalLocation(rentalLocation, reservation);
    }

    // Reservation--hasType-->VehicleType   multiplicity: * - 1
    //
    public void storeReservationVehicleType( Reservation reservation, VehicleType vehicleType ) throws RARException
    {
	if (vehicleType == null)
            throw new RARException("The reservations's vehicle type is null");
        if (!vehicleType.isPersistent())
            throw new RARException("The reservation's vehilce type is not persisitent");
        RentalLocationManager.storeReservationVehicleType(reservation, vehicleType);
    }

    public VehicleType restoreReservationVehicleType( Reservation reservation ) throws RARException
    {
	return reservation.getVehicleType();
    }

    public List<Reservation> restoreReservationVehicleType( VehicleType vehicleType ) throws RARException
    {
	return vehicleType.getReservations();
    }

    public void deleteReservationVehicleType( Reservation reservation, VehicleType vehicleType ) throws RARException
    {
	if (vehicleType == null)
            throw new RARException("The reservations's vehicle type is null");
        if (!vehicleType.isPersistent())
            throw new RARException("The reservation's vehilce type is not persisitent");
        vehicleTypeManager.deleteReservationVehicleType(vehicleType);
        
    }

    // Vehicle--locatedAt-->RentalLocation;   multiplicity: * - 1
    //
    public void storeVehicleRentalLocation( Vehicle vehicle, RentalLocation rentalLocation ) throws RARException
    {
	if (rentalLocation == null)
            throw new RARException("The vehicle's rental location is null");
        if (!rentalLocation.isPersistent())
            throw new RARException("The vehicle's rental location is not persisitent");
        rentalLocationManager.storeVehicleRentalLocation(vehicle,rentalLocation);
    }

    public RentalLocation restoreVehicleRentalLocation( Vehicle vehicle ) throws RARException
    {
	return vehicle.getRentalLocation();
    }

    public List<Vehicle> restoreVehicleRentalLocation( RentalLocation rentalLocation ) throws RARException
    {
	return rentalLocation.getVehicles();
    }

    public void deleteVehicleRentalLocation( Vehicle vehicle, RentalLocation rentalLocation ) throws RARException
    {
    	VehicleManager.deleteVehicleRentalLocation(vehicle,rentalLocation);
    }

    // Vehicle--hasType-->VehicleType   multiplicity: * - 1
    //
    public void storeVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException
    {
    	vehicleTypeManager.storeVehicleVehicleType(vehicle, vehicleType);
    }

    public VehicleType restoreVehicleVehicleType( Vehicle vehicle ) throws RARException
    {
    	return vehicleTypeManager.restoreVehicleVehicleType(vehicle);
    }

    public List<Vehicle> restoreVehicleVehicleType( VehicleType vehicleType ) throws RARException
    {
    	
    	return vehicleManager.restoreVehicleVehicleType(vehicleType);
    }

    public void deleteVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException
    {
	 vehicleTypeManager.deleteVehicleVehicleType(vehicle, vehicleType);
    }

    // VehicleType--has-->HourlyPrice   multiplicity: 1 - 1..*
    //
    public void storeVehicleTypeHourlyPrice( VehicleType vehicleType, HourlyPrice hourlyPrice ) throws RARException
    {
	if (hourlyPrice == null)
            throw new RARException("The vehicle type's hourly price is null");
        if (!hourlyPrice.isPersistent())
            throw new RARException("The vehicle type's hourly price is not persisitent");
        //vehicleType.setHourlyPrice(hourlyPrice);
        //vehicleType.store(vehicleType);
        hourlyPriceManager.storeVehicleTypeHourlyPrice(vehicleType,hourlyPrice);
    }

    public VehicleType restoreVehicleTypeHourlyPrice( HourlyPrice hourlyPrice ) throws RARException
    {
	return hourlyPriceManager.restoreVehicleType(hourlyPrice);
    }

    public List<HourlyPrice> restoreVehicleTypeHourlyPrice( VehicleType vehicleType ) throws RARException
    {
	return hourlyPriceManager.restoreVehicleTypeHourlyPrice(vehicleType);
    }

    public void deleteVehicleTypeHourlyPrice( VehicleType vehicleType, HourlyPrice hourlyPrice ) throws RARException
    {
	if (hourlyPrice == null)
            throw new RARException("The vehicle type's hourly price is null");
        if (!hourlyPrice.isPersistent())
            throw new RARException("The vehicle type's hourly price is not persisitent");
        //vehicleType.deleteHourlyPrice(hourlyPrice);
        //vehicleType.store(vehicleType);
        hourlyPriceManager.deleteVehicleTypeHourlyPrice(vehicleType,hourlyPrice);
    }

    // Rental--describedBy-->Comment   multiplicity: 1 - 0..1
    //
    public void storeRentalComment( Rental rental, Comment comment ) throws RARException
    {
	if (comment == null)
            throw new RARException("The rental's comment is null");
        if (!comment.isPersistent())
            throw new RARException("The rental's comment is not persisitent");
        //rental.setComment(comment);
        //rental.store(rental);
        commentManager.storeRentalComment(rental,comment);
    }

    public Rental restoreRentalComment( Comment comment ) throws RARException
    {
	return commentManager.restoreRentalComment(comment);
    }

    public List<Comment> restoreRentalComment( Rental rental ) throws RARException
    {
	return commentManager.restoreRentalComent(rental);
    }

    public void deleteRentalComment( Rental rental, Comment comment ) throws RARException
    {
	if (comment == null)
            throw new RARException("The rental's comment is null");
        if (!comment.isPersistent())
            throw new RARException("The rental's comment is not persisitent");
        //rental.deleteComment(comment);
        //rental.store(rental);
        commentManager.deleteRentalComment(rental,comment);
    }

    // Rental--basedOn-->Reservation   multiplicity: 0..1 - 1
    //
    //
    public void storeRentalReservation( Rental rental, Reservation reservation ) throws RARException
    {
        if (reservation == null)
            throw new RARException("The rental's reservation is null");
        if (!reservation.isPersistent())
            throw new RARException("The rental's reservation is not persisitent");
        rentalManager.restoreRentalLocationReservation(rental,reservation);
    }

    public Rental restoreRentalReservation( Reservation reservation ) throws RARException
    {
	return reservation.getRental();
    }

    public Reservation restoreRentalReservation( Rental rental ) throws RARException
    {
	return rental.getReservation();
    }

    public void deleteRentalReservation( Rental rental, Reservation reservation ) throws RARException
    {
	if (reservation == null)
            throw new RARException("The rental's reservation is null");
        if (!reservation.isPersistent())
            throw new RARException("The rental's reservation is not persisitent");
        rentalManager.deleteRentalReservation(rental,reservation);
    }

    // Vehicle--usedIn-->Rental   multiplicity: 1 - *
    //
    public void storeVehicleRental( Vehicle vehicle, Rental rental ) throws RARException
    {
    	if (rental == null)
            throw new RARException("The vehicle's rental is null");
        if (!rental.isPersistent())
            throw new RARException("The vehicle's rental is not persisitent");
        rentalManager.storeVehicleRental(vehicle, rental);
    }

    public List<Rental> restoreVehicleRental( Vehicle vehicle ) throws RARException
    {
    	return rentalManager.restoreVehicleRental(vehicle);
    }

    public Vehicle restoreVehicleRental( Rental rental ) throws RARException
    {
    	if (rental == null)
            throw new RARException("The rental's comment is null");
        if (!rental.isPersistent())
            throw new RARException("The rental's comment is not persisitent");
        return rentalManager.restoreVehicleRental(rental);
    }

    public void deleteVehicleRental( Vehicle vehicle, Rental rental ) throws RARException
    {
    	rentalManager.deleteVehicleRental(vehicle,rental);
    }

	@Override
	public void updateRentalLocation(String column1, String value, String name) {
		try {
			rentalLocationManager.updateRentalLocation(column1, value, name);
		} catch (RARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Override
    public void updateUserProfile(String firstname, String lastname, String username, String email, String address, String licNumber, String licState, String ccNumber, java.sql.Date ccExpiration) throws RARException {
        customerManager.updateUserProfile(firstname,lastname,username,email,address,licNumber,licState,ccNumber,ccExpiration);
    }

	

	

	@Override
	public List<Rental> restoreCustomerRental(Customer customer) throws RARException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void updateReservation(Customer customer) {
		reservationManager.updateReservation(customer);
		
	}

	
	public void terminateCustomer(Customer customer) {
		customerManager.terminateCustomer(customer);
	}

	
	@Override
	public void updateVehicleStatus(Vehicle v) throws RARException {
		vehicleManager.updateVehicleStatus(v);
	}
	

	@Override
	public int getCharge(Reservation r) throws RARException {
		int charge = 0;
		try {
			charge  = hourlyPriceManager.getCharge(r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charge;
	}

	
	@Override
	public int getLateFee() {
		return 0;
	}

	@Override
	public void updateRentalLocation(String column1, int value, String name) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean updateStatus(String email){
		return customerManager.updateStatus(email);
	}


 /*   @Override
    public void updateCustomerPassword(String email, String changedPassword) throws RARException {
        try{
            customerManager.updatePassword(email,changedPassword);
        }catch (RARException e){
            e.printStackTrace();
        }
    }*/

   /* @Override
    public void updateAdminPassword(String email, String changedPassword) throws RARException {
        try{
            adminManager.updatePassword(email,changedPassword);
        }catch (RARException e){
            e.printStackTrace();
        }
    }*/

}
