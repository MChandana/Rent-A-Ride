package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentARideParams;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

public class RentARideParamsImpl extends Persistence implements RentARideParams{
	private int MemberShipPrice;
	private int LateFee;
	
	public RentARideParamsImpl(){
		MemberShipPrice = 0;
		LateFee = 0;
	}
	
	public RentARideParamsImpl(int MemPrice, int LateFee){
		MemberShipPrice = MemPrice;
		this.LateFee = LateFee;
	}
	
	public int getMembershipPrice() {
		return MemberShipPrice;
	}

	@Override
	public void setMembershipPrice(int membershipPrice) throws RARException {
		if(membershipPrice > 0) {
			MemberShipPrice = membershipPrice;
		}
		else 
			throw new RARException("Membership price is not valid");
	}

	@Override
	public int getLateFee() {
		return LateFee;
	}

	@Override
	public void setLateFee(int lateFee) throws RARException {
		if(lateFee > 0) {
			this.LateFee = lateFee;
		}
		else
			throw new RARException("Late fee is not valid");
	}
	
}
