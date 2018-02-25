package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.persistence.impl.Persistence;


public class UserImpl extends Persistence implements User {

    //Attributes
    String      firstName;
    String      lastName;
    String      userName;
    String      email;
    String      password;
    Date        createdDate;
    String      address;
    UserStatus  userStatus;

    public UserImpl(){
        this.firstName = null;
        this.lastName = null;
        this.userName = null;
        this.email = null;
        this.password = null;
        this.createdDate = null;
        this.address = null;
        this.userStatus = null;
    }

    public UserImpl(String firstName, String lastName, String userName, String email, String password, Date createdDate, String address, UserStatus userStatus){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.address = address;
        this.userStatus = userStatus;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName) throws RARException{
        if(userName == null)
            throw new RARException("ERROR: Username cannot be null!");
        else
            this.userName = userName;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Date getCreatedDate(){
        return this.createdDate;
    }

    public void setCreateDate(Date createdDate){
        this.createdDate = createdDate;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public UserStatus getUserStatus(){
        return this.userStatus;
    }

    public void setUserStatus(UserStatus userStatus){
        this.userStatus = userStatus;
    }

}
