package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.persistence.impl.Persistence;
import edu.uga.cs.rentaride.persistence.impl.Persistence;

import java.util.Date;
import java.util.List;

public class AdministratorImpl extends Persistence implements Administrator {

    private long adminID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String address;
    private Date createdDate;
    private UserStatus userStatus;

    public AdministratorImpl(){
        super(-1);
        this.firstName=null;
        this.lastName=null;
        this.username=null;
        this.password=null;
        this.email=null;
        this.address=null;
        this.createdDate=null;

    }

    public AdministratorImpl(String firstName, String lastName, String username, String password, String email, String address, Date createdDate) {
        super(-1);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.createdDate = createdDate;
        this.userStatus=UserStatus.ACTIVE;

        System.out.println("in adminimpl");
        System.out.println("");
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public void setUserName(String userName) throws RARException {
        this.username = userName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createdDate = createDate;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public UserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus=userStatus;
    }

    @Override
    public long getId() {
        return adminID;
    }

    @Override
    public void setId(long id) {
        this.adminID=id;
    }

    @Override
    public boolean isPersistent() {
        return adminID>0;
    }

    }
