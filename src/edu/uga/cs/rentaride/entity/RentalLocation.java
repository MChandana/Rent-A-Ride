package edu.uga.cs.rentaride.entity;


import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;

/** This class represents a rental location in the Rent-A-Ride system.
 *
 */
public interface RentalLocation
    extends Persistable
{
    /** Return the name of this rental location
     * @return the name of this rental location
     */
    public String getName();
    
    /** Set the name of this rental location
     * @param name the new name of this rental location
     * @throws RARException in case name is non-unique or null
     */
    public void setName( String name ) throws RARException;
    
    /** Return the address of this rental location
     * @return the address of this rental location
     */
    public String getAddress();
    
    /** Set the address of this rental location
     * @param address the new address for this rental location
     */
    public void setAddress( String address );
    
    /** Return the capacity of this rental location
     * @return the capacity of this rental location
     */
    public int getCapacity();
    
    /** Set the capacity of this rental location
     * @param capacity the new capacity of this rental location
     * @throws RARException in case capacity is non-positive
     */
    public void setCapacity( int capacity ) throws RARException;
    
    /** Get a list of reservations made for this rental location.
     * @return a List of reservations made for this rental location
     */
    public List<Reservation> getReservations();
    
    // Not needed;  reservations for this location are added one-by-one by creating 
    // Reservation objects or changing existing ones for this rental location.
    // void setReservatios( List<Reservation> reservations );
    
    /** Get a list of vehicles located at this rental location.
     * @return a List of vehicles located at this rental location
     */
    public List<Vehicle> getVehicles();
    
    // Not needed;  vehicles located at this location are added one-by-one by creating
    // Vehicle objects or re-assigning existing ones to this rental location.
    // void setVehicles( List<Reservation> reservations );}
}