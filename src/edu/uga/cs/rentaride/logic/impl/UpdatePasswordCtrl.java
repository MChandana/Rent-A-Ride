package edu.uga.cs.rentaride.logic.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.persistence.impl.AdministratorManager;
import edu.uga.cs.rentaride.persistence.impl.CustomerManager;
import edu.uga.cs.rentaride.session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

public class UpdatePasswordCtrl  {

    private ObjectLayer objectLayer = null;

    public UpdatePasswordCtrl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
    }

    public UpdatePasswordCtrl(){}

    public boolean updatePassword(String email, String firstname, String lastname, String changedPassword) throws RARException, SQLException {

        Connection conn = null;
        Statement stmt = null;
        int updCount = 0;
        boolean found=false;
        Session session=null;
        boolean done=false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://uml.cs.uga.edu:3306/team4", "team4", "seprocess");
            session = new Session(conn);
            ObjectLayer objectLayer=session.getObjectLayer();

            CustomerManager customerManager = new CustomerManager(conn,objectLayer);
            List<Customer> customers = customerManager.restore(null);

            AdministratorManager administratorManager = new AdministratorManager(conn,objectLayer);
            List<Administrator> administrators = administratorManager.restore(null);

            for (Administrator administrator : administrators) {
                System.out.println("in admin for email is "+administrator.getEmail());
                if (administrator.getEmail().equals(email) && administrator.getFirstName().equals(firstname) && administrator.getLastName().equals(lastname)) {
                    System.out.println("in admin if email is "+administrator.getEmail());

                    found=true;
                    String sql = "UPDATE Admins set password='" + changedPassword + "' where email='" + email + "'";
                    stmt = conn.createStatement();
                    updCount = stmt.executeUpdate(sql);
                    System.out.println("admin after update stmt");
                    if (updCount == 1) {
                        System.out.println("admin password update successful");
                        done=true;
                    } else
                        throw new RARException("Couldn't update Admin password");
                }

            }


        for (Customer customer : customers) {
            System.out.println("in customer for email is "+customer.getEmail());
            System.out.println("fn "+customer.getFirstName());
            System.out.println("ln "+customer.getLastName());
            System.out.println("req email "+email);
            System.out.println("req fn "+firstname);
            System.out.println("req ln "+lastname);

            if (customer.getEmail().equals(email) && !found && customer.getFirstName().equals(firstname) && customer.getLastName().equals(lastname)) {
                System.out.println("in customer if email is "+customer.getEmail());
                String sql = "UPDATE Customer set password='" + changedPassword + "' where email='" + email + "'";
                stmt = conn.createStatement();
                updCount = stmt.executeUpdate(sql);
                System.out.println("customer after update stmt");
                if (updCount == 1) {
                    System.out.println("customer password update successful");
                    done=true;
                } else
                    throw new RARException("Couldn't update Customer password");
            }
        }

    } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            conn.close();
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            session.setExpiration(cal.getTime());
        }
        return done;
    }

}
