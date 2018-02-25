package edu.uga.cs.rentaride.logic.impl;

//import com.sun.org.apache.regexp.internal.RE;
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
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.rentaride.session.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogicLayerImpl implements LogicLayer {

    private ObjectLayerImpl objectLayer=null;

    public LogicLayerImpl( Connection conn )
    {
        objectLayer = new ObjectLayerImpl();
        PersistenceLayer persistenceLayer = new PersistenceLayerImpl( conn, objectLayer );
        objectLayer.setPersistence( persistenceLayer );
        System.out.println( "LogicLayerImpl.LogicLayerImpl(conn): initialized" );
    }

    public LogicLayerImpl( ObjectLayer objectLayer )
    {
        this.objectLayer = (ObjectLayerImpl) objectLayer;
        System.out.println( "LogicLayerImpl.LogicLayerImpl(objectLayer): initialized" );
    }

    @Override
    public long createRentalLocation(String name, String address, int capacity) throws RARException {
        CreateRentalLocationCtrl createRentalLocationCtrl=new CreateRentalLocationCtrl(objectLayer);
        System.out.println("create rental loc in logic layer "+createRentalLocationCtrl);
        return createRentalLocationCtrl.createRentalLocation(name,address,capacity);
    }

    @Override
    public void logout(String ssid) throws RARException {

    }

    @Override
    public String login(Session session, String userName, String password) throws RARException {
        LoginControl loginCtrl=new LoginControl(objectLayer);
        System.out.println("in logiclayerimpl login calling loginctrl");
        return loginCtrl.login(session,userName,password);
    }

	@Override
	public boolean isNameValid(String name) {
		RentalLocation rentalLocation1;
        rentalLocation1 = objectLayer.createRentalLocation();
        List<RentalLocation> allRentalLocations = null;
        try {
			allRentalLocations = objectLayer.findRentalLocation(rentalLocation1);
		} catch (RARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		for(RentalLocation r : allRentalLocations) {
			String name1 = r.getName();
			System.out.println(name1 +" "+name);
			if(name1.equals(name)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void updateRentalLocation(String string, String newname, String name) {
		try {
			objectLayer.updateRentalLocation(string, newname, name);
		} catch (RARException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateRentalLocation(String string, int capacity, String name) {
		objectLayer.updateRentalLocation(string, capacity, name);
		
	}

	@Override
	public Customer createCustomer(String firstName, String lastName, String username, String password, String email,
			String address, Date today, Date today1, String state, String licNumber, String creditCardNumber,
			Date today3) throws RARException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<Vehicle> findAllVehicles() throws RARException {
       List<Vehicle> vehicles=new ArrayList<>();
       vehicles=objectLayer.findVehicle(null);
        return vehicles;
    }

    @Override
    public List<Customer> findAllCustomers() throws RARException {
        List<Customer> customers=objectLayer.findCustomer(null);
        return customers;
    }

    @Override
    public Customer createCustomer() throws RARException {
        Customer customer=objectLayer.createCustomer();
        return customer;
    }

    @Override
    public void updateCustomer(String firstname, String lastname, String username, String email, String address, String licNumber, String licState, String ccNumber, java.sql.Date ccExpiration) throws RARException {
        UpdateUserProfileCtrl updateUserProfileCtrl=new UpdateUserProfileCtrl(objectLayer);
        updateUserProfileCtrl.updateUserProfile(firstname,lastname,username,email,address,licNumber,licState,ccNumber,ccExpiration);
    }

	@Override
	public List<Administrator> findAllAdmins() throws RARException {
		List<Administrator> allAdmins = objectLayer.findAdministrator(null);
		return allAdmins;
	}

	@Override
	public List<VehicleType> findAllVehicleTypes() throws RARException {
		List<VehicleType> allVehicleTypes = objectLayer.findVehicleType(null);
		return allVehicleTypes;
	}


	@Override
	public void cancelReservation(Customer customer) throws RARException {
		objectLayer.updateReservation(customer);
	}

	@Override
	public void createComment(String comment, Date date, Rental rental) throws RARException {
		objectLayer.createComment(comment, date, rental);
	}


	@Override
	public void terminateCustomer(Customer customer) throws RARException {
		objectLayer.terminateCustomer(customer);
	}

	@Override
	public void updateVehicleStatus(Vehicle v) throws RARException {
		objectLayer.updateVehicleStatus(v);
	}

	public void updateRental(Rental r) throws RARException{
		objectLayer.updateRental(r);
	}

	@Override
	public int getCharge(Reservation r) throws RARException {
		return objectLayer.getCharge(r);
	}

	@Override
	public int getLateFee() {
		return objectLayer.getLateFee();
	}

	@Override
	public List<Reservation> findAllReservations(Customer customer) throws RARException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rental> findAllRentals(Customer customer) throws RARException {
		return customer.getRentals();
	}

	@Override
	public void newRental(Timestamp pickupTime, Reservation reservation, Vehicle vehicle) throws RARException {
		objectLayer.createRental(pickupTime, reservation, vehicle);
	}

	 @Override
	    public List<Reservation> findAllReservations() throws RARException{
	    	List<Reservation> reservations = new ArrayList<>();
	    	reservations = objectLayer.findReservation(null);
	    	return reservations;
	    }

	 @Override
	    public List<Comment> findAllComments() throws RARException{
	    	List<Comment> comments = new ArrayList<>();
	    	comments = objectLayer.findComment(null);
	    	return comments;
	    }

	 
	    @Override
	    public void calCharges(User user) {
	        RentalChargesCtrl rentalChargesCtrl=new RentalChargesCtrl(objectLayer);
	        rentalChargesCtrl.calculateCharges(user);
	    }

		
	    @Override
	    public List<Reservation> displayRLReservations(String rlName) throws RARException {
	        DisplayRLCtrl displayRLCtrl=new DisplayRLCtrl(objectLayer);
	        return displayRLCtrl.getReservations(rlName);
	    }
		
	    @Override
	    public List<Vehicle> displayRLVehicles(String rlName) throws RARException {
	        DisplayRLCtrl displayRLCtrl=new DisplayRLCtrl(objectLayer);
	        return displayRLCtrl.getVehicles(rlName);
	    }

	    @Override
	    public List<Rental> displayRLRentals(String rlName) throws RARException {
	        DisplayRLCtrl displayRLCtrl=new DisplayRLCtrl(objectLayer);
	        return displayRLCtrl.getRentals(rlName);
	    }

	    @Override
	    public List<RentalLocation> findAllRentalLocations() throws RARException {
	        List<RentalLocation> rentalLocations=new ArrayList<>();
	        rentalLocations=objectLayer.findRentalLocation(null);
	        return rentalLocations;
	    }

	    @Override
	    public void reserveVehicle(String loc, Timestamp puDsql, int length_in_minutes, long custId, String vt) throws RARException {
	       ReserveVehicleCtrl reserveVehicleCtrl=new ReserveVehicleCtrl(objectLayer);
	       reserveVehicleCtrl.reserveVehicle(loc,puDsql,length_in_minutes,custId,vt);
	    }


	    @Override
	    public List<HourlyPrice> findHourlyPrices(VehicleType vt) throws RARException {
	        return objectLayer.findHourlyPrice(vt);
	    }
	    
	    @Override
	    public boolean terminateMembership(String email) throws RARException{
	    	boolean terminated = false;
	    	terminateMembershipControl memControl = new terminateMembershipControl(objectLayer);
	    	terminated = memControl.terminate(email);
	    	return terminated;
	    }
	


  /*  @Override
    public void updatePassword(String email, String changedPassword) throws RARException {
        UpdatePasswordCtrl updatePasswordCtrl=new UpdatePasswordCtrl(objectLayer);
        try {
            updatePasswordCtrl.updatePassword(email,changedPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

}
