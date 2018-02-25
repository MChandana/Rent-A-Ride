package edu.uga.cs.rentaride.object.impl;

import java.sql.Timestamp;
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
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.impl.AdministratorImpl;
import edu.uga.cs.rentaride.entity.impl.CommentImpl;
import edu.uga.cs.rentaride.entity.impl.CustomerImpl;
import edu.uga.cs.rentaride.entity.impl.HourlyPriceImpl;
import edu.uga.cs.rentaride.entity.impl.RentalImpl;
import edu.uga.cs.rentaride.entity.impl.RentalLocationImpl;
import edu.uga.cs.rentaride.entity.impl.ReservationImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleImpl;
import edu.uga.cs.rentaride.entity.impl.VehicleTypeImpl;
import edu.uga.cs.rentaride.object.*;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

public class ObjectLayerImpl implements ObjectLayer {

    PersistenceLayer persistence = null;

    public ObjectLayerImpl()
    {
        this.persistence = null;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(): initialized" );
    }

    public ObjectLayerImpl( PersistenceLayer persistence )
    {
        this.persistence = persistence;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(persistence): initialized" );
    }

	@Override
	public Administrator createAdministrator(String firstName, String lastName, String userName, String password,
			String email, String address, Date createDate) throws RARException {
		// TODO Auto-generated method stub
		System.out.println("in create admin obj layer");
		AdministratorImpl administrator=new AdministratorImpl(firstName,lastName,userName,password,email,address,createDate);
        Persistence.setPersistenceLayer(persistence);
        return administrator;
	}

	@Override
	public Administrator createAdministrator() {
		// TODO Auto-generated method stub
        AdministratorImpl administrator=new AdministratorImpl(null,null,null,null,null,null,null);
        administrator.setId(-1);
        Persistence.setPersistenceLayer(persistence);
        return administrator;
	}

	@Override
	public List<Administrator> findAdministrator(Administrator modelAdministrator) throws RARException {
		// TODO Auto-generated method stub
		System.out.println("in obj layer find admin");
		return persistence.restoreAdministrator(modelAdministrator);
	}

	@Override
	public void storeAdministrator(Administrator administrator) throws RARException {
		// TODO Auto-generated method stub
        persistence.storeAdministrator(administrator);

	}

	@Override
	public void deleteAdministrator(Administrator administrator) throws RARException {
		// TODO Auto-generated method stub
        persistence.deleteAdministrator(administrator);

	}

	@Override
	public Customer createCustomer(String firstName, String lastName, String userName, String password, String email,
			String address, Date createDate, Date membershipExpiration, String licenseState, String licenseNumber,
			String cardNumber, Date cardExpiration) throws RARException {
		// TODO Auto-generated method stub
        CustomerImpl customer=new CustomerImpl(firstName,lastName,userName,password,email,address,createDate,membershipExpiration,licenseState,licenseNumber,cardNumber,cardExpiration);
        Persistence.setPersistenceLayer(persistence);
        return customer;
	}

	@Override
	public Customer createCustomer() {
		// TODO Auto-generated method stub
        CustomerImpl customer=new CustomerImpl(null,null,null,null,null,null,null,null,null,null,null,null);
		customer.setId(-1);
        Persistence.setPersistenceLayer(persistence);
        return customer;
	}

	@Override
	public List<Customer> findCustomer(Customer modelCustomer) throws RARException {
		List<Customer> findCustomer;
         findCustomer = persistence.restoreCustomer(modelCustomer);
		return findCustomer;
	}

	@Override
	public void storeCustomer(Customer customer) throws RARException {
		// TODO Auto-generated method stub
        persistence.storeCustomer(customer);

	}

	@Override
	public RentalLocation createRentalLocation(String name, String address, int capacity) throws RARException {
		RentalLocationImpl rentalLocation = new RentalLocationImpl(name, address, capacity);
		Persistence.setPersistenceLayer(persistence);
		return rentalLocation;
	}
	
	public void updateRentalLocation(String column1, String value,String name) throws RARException {
		persistence.updateRentalLocation(column1,value,name);
	}

	@Override
	public RentalLocation createRentalLocation() {
		// TODO Auto-generated method stub
		RentalLocationImpl rentalLocation = new RentalLocationImpl(null,null,0);
		rentalLocation.setId(-1);
		System.out.println("obj layer CRL null args");
		Persistence.setPersistenceLayer(persistence);
		return rentalLocation;
	}

	@Override
	public List<RentalLocation> findRentalLocation(RentalLocation modelRentalLocation) throws RARException {
		List<RentalLocation> findRentalLocation;
		findRentalLocation = persistence.restoreRentalLocation(modelRentalLocation);
		return findRentalLocation;
	}

	@Override
	public void storeRentalLocation(RentalLocation rentalLocation) throws RARException {
		// TODO Auto-generated method stub
		persistence.storeRentalLocation(rentalLocation);
	}

	@Override
	public void deleteRentalLocation(RentalLocation rentalLocation) throws RARException {
		// TODO Auto-generated method stub
		persistence.deleteRentalLocation(rentalLocation);
	}

	@Override
	public Reservation createReservation(Timestamp pickupTime, int rentalLength, VehicleType vehicleType,
			RentalLocation rentalLocation, Customer customer) throws RARException {
		
		ReservationImpl reservation = new ReservationImpl(pickupTime,rentalLength,customer,vehicleType,rentalLocation);
		return reservation;
	}

	@Override
	public Reservation createReservation() {
		ReservationImpl reservation = new ReservationImpl();
		return reservation;
	}

	@Override
	public List<Reservation> findReservation(Reservation modelReservation) throws RARException {
		List<Reservation> findReservation;
		findReservation = persistence.restoreReservation(modelReservation);
		return findReservation;
	}

	@Override
	public void storeReservation(Reservation reservation) throws RARException {
		persistence.storeReservation(reservation);
	}

	@Override
	public void deleteReservation(Reservation reservation) throws RARException {
		persistence.deleteReservation(reservation);
	}

	@Override
	public Rental createRental(Timestamp pickupTime, Reservation reservation, Vehicle vehicle) throws RARException {
		System.out.println("voila objlayer createRental");
		RentalImpl rental = new RentalImpl(pickupTime, reservation, vehicle);
		System.out.println("voila objlayer createRental after bug");

		return rental;
	}

	@Override
	public Rental createRental() {
		RentalImpl rental = new RentalImpl();
		return rental;
	}

	@Override
	public List<Rental> findRental(Rental modelRental) throws RARException {
		List<Rental> findRental;
		findRental = persistence.restoreRental(modelRental);
		return findRental;
	}

	@Override
	public void storeRental(Rental rental) throws RARException {
		persistence.storeRental(rental);
	}

	@Override
	public void deleteRental(Rental rental) throws RARException {
		persistence.deleteRental(rental);
	}

	@Override
	public VehicleType createVehicleType(String name) throws RARException {
		VehicleTypeImpl vehicleType = new VehicleTypeImpl(name);
		return vehicleType;
	}

	@Override
	public VehicleType createVehicleType() {
		VehicleTypeImpl vehicleType = new VehicleTypeImpl();
		return vehicleType;
	}

	@Override
	public List<VehicleType> findVehicleType(VehicleType modelVehicleType) throws RARException {
		List<VehicleType> findVehicleType;
		findVehicleType = persistence.restoreVehicleType(modelVehicleType);
		return findVehicleType;
	}

	@Override
	public void storeVehicleType(VehicleType vehicleType) throws RARException {
		persistence.storeVehicleType(vehicleType);
	}

	@Override
	public void deleteVehicleType(VehicleType vehicleType) throws RARException {
		persistence.deleteVehicleType(vehicleType);
	}

	@Override
	public Vehicle createVehicle(String make, String model, int year, String registrationTag, int mileage,
			Date lastServiced, VehicleType vehicleType, RentalLocation rentalLocation,
			VehicleCondition vehicleCondition, VehicleStatus vehicleStatus) throws RARException {
		
		VehicleImpl createVehicle = new VehicleImpl(make,model,year,registrationTag,mileage,lastServiced,vehicleStatus,vehicleCondition,vehicleType,rentalLocation);
		return createVehicle;
	}

	@Override
	public Vehicle createVehicle() {
		VehicleImpl createVehicle = new VehicleImpl();
		return createVehicle;
	}

	@Override
	public List<Vehicle> findVehicle(Vehicle modelVehicle) throws RARException {
		List<Vehicle> findVehicle;
		findVehicle = persistence.restoreVehicle(modelVehicle);
		return findVehicle;
	}

	@Override
	public void storeVehicle(Vehicle vehicle) throws RARException {
		persistence.storeVehicle(vehicle);

	}

	@Override
	public void deleteVehicle(Vehicle vehicle) throws RARException {
		persistence.deleteVehicle(vehicle);

	}

	@Override
	public Comment createComment(String text, Date date, Rental rental) throws RARException {
		// TODO Auto-generated method stub
        CommentImpl comment=new CommentImpl(text,date,rental);
        Persistence.setPersistenceLayer(persistence);
		return comment;
	}

	@Override
	public Comment createComment() {
		// TODO Auto-generated method stub
        CommentImpl comment=new CommentImpl(null,null,null);
        Persistence.setPersistenceLayer(persistence);
        return comment;
	}

	@Override
	public List<Comment> findComment(Comment modelComment) throws RARException {
		// TODO Auto-generated method stub
		return persistence.restoreComment(modelComment);
	}

	@Override
	public void storeComment(Comment comment) throws RARException {
		// TODO Auto-generated method stub
        persistence.storeComment(comment);

	}

	@Override
	public void deleteComment(Comment comment) throws RARException {
		// TODO Auto-generated method stub
        persistence.deleteComment(comment);

	}

	@Override
	public HourlyPrice createHourlyPrice(int maxHrs, int price, VehicleType vehicleType) throws RARException {
		// TODO Auto-generated method stub
        HourlyPriceImpl hourlyPrice=new HourlyPriceImpl(maxHrs,price,vehicleType);
        Persistence.setPersistenceLayer(persistence);
		return hourlyPrice;
	}

	@Override
	public HourlyPrice createHourlyPrice() {
		// TODO Auto-generated method stub
        HourlyPriceImpl hourlyPrice=new HourlyPriceImpl(0,0,"");
        Persistence.setPersistenceLayer(persistence);
        return hourlyPrice;
	}

	@Override
	public List<HourlyPrice> findHourlyPrice(HourlyPrice modelHourlyPrice) throws RARException {
		// TODO Auto-generated method stub
		return persistence.restoreHourlyPrice(modelHourlyPrice);
	}

	@Override
	public void storeHourlyPrice(HourlyPrice hourlyPrice) throws RARException {
		// TODO Auto-generated method stub
        persistence.storeHourlyPrice(hourlyPrice);

	}

	@Override
	public void deleteHourlyPrice(HourlyPrice hourlyPrice) throws RARException {
		// TODO Auto-generated method stub
        persistence.deleteHourlyPrice(hourlyPrice);

	}

	@Override
	public RentARideParams createRentARideParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentARideParams findRentARideParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeRentARideParams(RentARideParams rentARideParams) throws RARException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserProfile(String firstname, String lastname, String username, String email, String address, String licNumber, String licState, String ccNumber, java.sql.Date ccExpiration) throws RARException {
		persistence.updateUserProfile(firstname,lastname,username,email,address,licNumber,licState,ccNumber,ccExpiration);
	}


	public void setPersistence(PersistenceLayer persistence) {
		this.persistence = persistence; 
	}

	public void updateRentalLocation(String string, int capacity, String name) {
		
		
	}
	
	@Override
	public void terminateCustomer(Customer customer) throws RARException {
        persistence.terminateCustomer(customer);
	}

	public void updateVehicleStatus(Vehicle v) throws RARException{
		persistence.updateVehicleStatus(v);
	}
	
	public void updateRental(Rental r) throws RARException{
		persistence.storeRental(r);
	}

	public int getCharge(Reservation r) throws RARException{
		return persistence.getCharge(r);
	}

	public int getLateFee() {
		return persistence.getLateFee();
	}

	public void updateReservation(Customer customer) throws RARException {
		persistence.updateReservation(customer);
	}
	
	@Override
	public List<HourlyPrice> findHourlyPrice(VehicleType vt) throws RARException {
		return persistence.restoreVehicleTypeHourlyPrice(vt);
	}
	
	@Override
	public boolean updateStatus(String email) throws RARException{
		return persistence.updateStatus(email);
	}

}
