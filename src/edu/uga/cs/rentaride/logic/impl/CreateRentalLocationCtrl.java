package edu.uga.cs.rentaride.logic.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.DBTester;
import edu.uga.cs.rentaride.persistence.impl.Persistence;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CreateRentalLocationCtrl {

    private ObjectLayer objectLayer = null;


    public CreateRentalLocationCtrl(ObjectLayer objectModel){
        this.objectLayer=objectModel;
    }

    public long createRentalLocation(String name, String address, int capacity) throws RARException
    {
       /* ObjectLayerImpl objectLayerImpl = null;
        DBTester db = new DBTester();
        PersistenceLayer persistence = null;
        Connection conn = null;
        try {
            db.Connect();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver was not found");
        } catch (SQLException e) {
            System.out.println("WOuld not connect to database");
        } catch (RARException e) {
            e.printStackTrace();
        }

        conn = db.getConnection();
        objectLayerImpl = new ObjectLayerImpl();
        persistence = new PersistenceLayerImpl( conn, objectLayerImpl );
        objectLayerImpl.setPersistence( persistence );
*/
        System.out.println("in CRLCtrl name address cap "+name+" , "+address+" , "+capacity);

        RentalLocation rentalLocation=null;
        RentalLocation modelRentalLocation=null;
        List<RentalLocation> rentalLocations=null;

        modelRentalLocation=objectLayer.createRentalLocation();
        modelRentalLocation.setName(name);
        rentalLocations=objectLayer.findRentalLocation(modelRentalLocation);
        System.out.println("received rental locs in CRLctrl"+rentalLocations);

        for(RentalLocation rentalLocation1:rentalLocations){
            if(rentalLocation1.getName().equals(name)){
                System.out.println("in if RL multiple returned");
                throw new RARException("A Rental Location with this name already exists "+name);
            }
        }

    /*    if(rentalLocations.size()>0){
            throw new RARException("A Rental Location with this name already exists "+name);
        }*/

        System.out.println("in create rental ctrl");

        rentalLocation=objectLayer.createRentalLocation(name,address,capacity);
        System.out.println("after creating RL in RLCTRl");
        objectLayer.storeRentalLocation(rentalLocation);
        System.out.println("after storing RL in RLCTRl");

        //   RentalLocation rentalLocation1=objectLayerImpl.createRentalLocation(name,address,capacity);

        //persistence.storeRentalLocation(rentalLocation1);

        return rentalLocation.getId();

    }
    
    public boolean isNameValid(String name) throws RARException {
	    RentalLocation rentalLocation1;
        rentalLocation1 = objectLayer.createRentalLocation();
        List<RentalLocation> allRentalLocations;
        allRentalLocations = objectLayer.findRentalLocation(rentalLocation1);
        
		for(RentalLocation r : allRentalLocations) {
			String name1 = r.getName();
			System.out.println(name1 +" "+name);
			if(name1.equals(name)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isAddressValid(String address) throws RARException {
	    RentalLocation rentalLocation1;
        rentalLocation1 = objectLayer.createRentalLocation();
        List<RentalLocation> allRentalLocations;
        allRentalLocations = objectLayer.findRentalLocation(rentalLocation1);
        
		for(RentalLocation r : allRentalLocations) {
			String name1 = r.getAddress();
			System.out.println(name1 +" "+address);
			if(name1.equals(address)) {
				return false;
			}
		}
		
		return true;
	}




}
