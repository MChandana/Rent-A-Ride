package edu.uga.cs.rentaride.logic;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.session.Session;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface LogicLayer {

    public List<RentalLocation> findAllRentalLocations() throws RARException;

    public long createRentalLocation(String name,String address,int capacity) throws RARException;

    public void               logout( String ssid ) throws RARException;
    public String             login(Session session, String userName, String password ) throws RARException;

	public boolean isNameValid(String name);

	public void updateRentalLocation(String string, String newname, String name) throws RARException;

	public void updateRentalLocation(String string, int capacity, String name);
	
	public Customer createCustomer(String firstName,String lastName, String username, String password, String email,
			String address,Date today,Date today1,String state,String licNumber,
			String creditCardNumber, Date today3) throws RARException;

    public List<Vehicle> findAllVehicles() throws RARException;

    public List<Customer> findAllCustomers() throws RARException;

    public Customer createCustomer() throws RARException;

    public void updateCustomer(String firstname, String lastname, String username,String email, String address, String licNumber, String licState, String ccNumber, java.sql.Date ccExpiration) throws RARException;

	public List<Administrator> findAllAdmins() throws RARException;

	public List<VehicleType> findAllVehicleTypes() throws RARException;
	
	public List<Reservation> findAllReservations() throws RARException;

	public List<Rental> findAllRentals(Customer customer) throws RARException;

	void cancelReservation(Customer customer) throws RARException;

	public void newRental(Timestamp pickupTime, Reservation reservation, Vehicle vehicle) throws RARException;

	public void createComment(String comment, Date date, Rental rental) throws RARException;

	public void terminateCustomer(Customer customer) throws RARException;

	public void updateVehicleStatus(Vehicle v) throws RARException;
		
	public void updateRental(Rental r) throws RARException;

	public int getCharge(Reservation r) throws RARException;

	public int getLateFee();

	public List<Reservation> findAllReservations(Customer current) throws RARException;

	public List<Comment> findAllComments() throws RARException;
	

    public List<Reservation> displayRLReservations(String rlName) throws RARException;

    public List<Vehicle> displayRLVehicles(String rlName) throws RARException;

    public List<Rental> displayRLRentals(String rlName) throws RARException;

    public void calCharges(User user);

	void reserveVehicle(String loc, Timestamp puDsql, int length_in_minutes, long custId, String vt) throws RARException;
	
	 public List<HourlyPrice> findHourlyPrices(VehicleType vt) throws RARException;

	 public boolean terminateMembership(String email) throws RARException;


}
