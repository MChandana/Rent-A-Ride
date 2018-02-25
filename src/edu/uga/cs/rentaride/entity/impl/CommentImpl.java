package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.persistence.impl.Persistence;
//import edu.uga.cs.rentaride.persistence.impl.Persistent;

import java.util.Date;

public class CommentImpl extends Persistence implements Comment {

    long ID;
    long CustomerID;
    String text;
    Date date;
    long rentalID;
    Rental rental;
    Customer customer;

    public CommentImpl() {
        super(-1);
        this.text = null;
        this.date = null;
        this.customer=null;
        this.rental=null;

    }

    public CommentImpl(String text, Date date, Rental rental) {
        super(-1);
        this.text = text;
        this.date = date;
        this.customer=rental.getCustomer();
        this.rental=rental;

    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;

    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;

    }

    @Override
    public Rental getRental() {

        return rental;
    }

    @Override
    public void setRental(Rental rental) throws RARException {

        this.rental=rental;
        this.rentalID=rental.getId();
    }

    @Override
    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer=customer;
        this.CustomerID=customer.getId();
    }

    public long getCustomerID(){
        return CustomerID;
    }

    public long getRentalID(){
        return rentalID;
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
