package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

public class RentARideParams implements edu.uga.cs.rentaride.entity.RentARideParams {

    private static RentARideParams RentARideParams_Instance=null;
    private int membershipPrice;
    private int lateFee;
    private long ID;

    private RentARideParams(){
        membershipPrice=1000;
        lateFee=50;
    }

    public static RentARideParams getInstance(){
        if(RentARideParams_Instance==null){
            RentARideParams_Instance=new RentARideParams();
        }
        return RentARideParams_Instance;
    }

    @Override
    public int getMembershipPrice() {
        return membershipPrice;
    }

    @Override
    public void setMembershipPrice(int membershipPrice) throws RARException {
        this.membershipPrice=membershipPrice;
    }

    @Override
    public int getLateFee() {
        return lateFee;
    }

    @Override
    public void setLateFee(int lateFee) throws RARException {
        this.lateFee=lateFee;
    }

    @Override
    public long getId() {
        return ID;
    }

    @Override
    public void setId(long id) {
        this.ID=id;
    }

    @Override
    public boolean isPersistent() {
        return ID>0;
    }
}
