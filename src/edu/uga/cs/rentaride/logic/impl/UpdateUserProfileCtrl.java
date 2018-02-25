package edu.uga.cs.rentaride.logic.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.util.Date;
import java.util.List;

public class UpdateUserProfileCtrl {

    private ObjectLayer objectLayer = null;


    public UpdateUserProfileCtrl(ObjectLayer objectModel){
        this.objectLayer=objectModel;
    }


    public void updateUserProfile(String firstname, String lastname, String username, String email, String address, String licNumber, String licState, String ccNumber, java.sql.Date ccExpiration) throws RARException {

        List<Customer> customers=objectLayer.findCustomer(null);
        for(Customer customer:customers){
            if(customer.getEmail().equals(email) && customer.getFirstName().equals(firstname) && customer.getLastName().equals(lastname)){
                objectLayer.updateUserProfile(firstname,lastname,username,email,address,licNumber,licState,ccNumber,ccExpiration);
            }
        }

    }
}
