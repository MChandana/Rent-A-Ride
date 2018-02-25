package edu.uga.cs.rentaride.logic.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.DBTester;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

import java.sql.Connection;
import java.util.List;

public class LoginControl
{
    private ObjectLayer objectLayer = null;

    public LoginControl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
    }

    public String login(Session session, String userName, String password )
            throws RARException
    {
        List<Administrator> persons=null;
        List<Customer> customers=null;
        String ssid = null;
        Administrator modelPerson = objectLayer.createAdministrator();
        modelPerson.setEmail( userName );
        modelPerson.setPassword( password );

        Customer modelCustomer = objectLayer.createCustomer();
        modelPerson.setEmail( userName );
        modelPerson.setPassword( password );

        System.out.println("login ctrl email and password are "+userName+" , "+password);
        try {
            persons = objectLayer.findAdministrator(modelPerson);
            System.out.println("after finding admins");
            customers=objectLayer.findCustomer(modelCustomer);
            System.out.println("after finding customers");


        }catch (Exception e) {
            //customers=objectLayer.findCustomer(modelCustomer);
            e.printStackTrace();
        }

        System.out.println("size is "+persons.size());
        int flag=0;
        for(Administrator administrator:persons){
            if(administrator.getEmail().equals(userName) && administrator.getPassword().equals(password)){
                session.setUser( administrator );
                ssid = SessionManager.storeSession( session );
                System.out.println("after storing session ssid is "+ssid);
                flag=1;
                break;
            }

        }

        assert customers != null;
        if(flag==0) {
            for (Customer customer : customers) {
                if (customer.getEmail().equals(userName) && customer.getPassword().equals(password)) {
                    session.setUser(customer);
                    ssid = SessionManager.storeSession(session);
                    System.out.println("after storing session ssid is " + ssid);
                    flag = 2;
                    break;
                }

            }
        }
        if(flag==0){
            System.out.println("flag=0");
            return "invalid";
        }

        if(flag==1){
            System.out.println("flag=1");

            return "admin"+ssid;
        }

        if(flag==2){
            System.out.println("flag=2");

            return "custo"+ssid;
        }

       /* if( persons.size() > 0 ) {
            Administrator person = persons.get( 0 );
            session.setUser( person );
            ssid = SessionManager.storeSession( session );
            System.out.println("after storing session ssid is "+ssid);
        }*/
      /*  else
            throw new RARException( "SessionManager.login: Invalid User Name or Password" );*/

        return ssid;
    }

    public List<RentalLocation> getAllLocations() throws RARException{
        List<RentalLocation> rentalLocations=null;

        rentalLocations=objectLayer.findRentalLocation(null);

        return rentalLocations;
    }
}

