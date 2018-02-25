package edu.uga.cs.rentaride.logic.impl;

import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.UserStatus;

public class terminateMembershipControl {
	ObjectLayer objectLayer;
	public terminateMembershipControl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public boolean terminate(String email) throws RARException{
		List<Customer> customers = objectLayer.findCustomer(null);
		for(Customer customer : customers){
			if(customer.getEmail().equals(email)){
				customer.setUserStatus(UserStatus.TERMINATED);
				return objectLayer.updateStatus(email);
			}
		}
		return false;
	}
}
