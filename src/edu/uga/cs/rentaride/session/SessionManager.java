package edu.uga.cs.rentaride.session;


import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import org.apache.log4j.Logger;


import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.persistence.impl.DBTester;


/**
 * Based on the modified code from Matthew Eavenson
 *
 * @author Matthew Eavenson
 */

/** This class provides different operations for the Sessions such as
 *  creating new sessions, removing, login and logout.
 */
public class SessionManager
{
    /**
     * Map for the existing sessions
     */
    private static Map<String, Session> sessions;

    /**
     * Map for the currently logged-in users
     */
    private static Map<String, Session> loggedIn;

    //    private static Logger log = Logger.getLogger( SessionManager.class );

    static{
        sessions = new HashMap<String, Session>();
        loggedIn = new HashMap<String, Session>();
    }

    public static Session createSession()
            throws RARException
    {
        Connection conn = null;
        Session s = null;

        // open a connection to the database
        try {
            System.out.println("in session manager getting connection");
            DBTester dbTester=new DBTester();
            dbTester.Connect();
            conn=dbTester.getConnection();
            System.out.println("getting connection from DB in session manager");

        } catch (SQLException e) {
            System.out.println("Connectiong to database failed");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver manager classes not found");
        }catch (RARException e){
            throw new RARException("Couldn't connect to database");
        }

        // initialize a new Session object
        // this creates PersistenceLayer, ObjectLayer, and LogicLayer instances
        // The LogicLayer reference is stored with the Session for use in other use cases later.
        System.out.println("initializing new session in sessionManager class");
        System.out.println("conn in session manager before creatig session "+conn);
        s = new Session( conn );

        return s;
    }

    public static String storeSession( Session session )
            throws RARException
    {
        User person = session.getUser();

        if( loggedIn.containsKey(person.getUserName()) ) {
            Session qs = loggedIn.get(person.getUserName());
            qs.setUser(person);
            System.out.println("in session manager printing qs "+qs);
            return qs.getSessionId();

        }

        String ssid = secureHash( "RentARide" + System.nanoTime() );
       // String ssid="1";
        System.out.println("printing session id "+ssid);
        session.setSessionId( ssid );

        sessions.put( ssid, session );
        loggedIn.put( person.getUserName(), session );
        session.start();
        System.out.println("session started");
        return ssid;
    }

    /****************************************************
     * Logout of the current session (based on session)
     * @param  s session being used
     * @throws RARException
     */
    public static void logout(Session s)
            throws RARException
    {
        s.setExpiration(new Date());
        s.interrupt();
        removeSession(s);
    }

    /****************************************************
     * Logout of the current session (based on session)
     * @param  ssid the session being used
     * @throws RARException
     */
    public static void logout(String ssid)
            throws RARException
    {
        Session s = getSessionById(ssid);
        s.setExpiration(new Date());
        s.interrupt();
        removeSession(s);
    }

    /****************************************************
     * Remove the session
     * @param s the current session
     * @throws RARException
     */
    protected static void removeSession( Session s )
            throws RARException
    {
        try {
            s.getConnection().close();
        }
        catch( SQLException sqe ) {
            //            log.error( "SessionManager.removeSession: cannot close connection", sqe );
            throw new RARException( "SessionManager.removeSession: Cannot close connection" );
        } // try
        sessions.remove( s.getSessionId() );
        loggedIn.remove( s.getUser().getUserName() );
    }

    /****************************************************
     * Get the session by session id
     * @param ssid the current session id
     */
    public static Session getSessionById( String ssid ){
        return sessions.get( ssid );
    }

    /*********************************************************************
     * Hashes the string input using the SHA1 algorithm.
     * @param   input   string to hash.
     * @return  SHA hash of the string.
     * @throws RARException
     */
    public static String secureHash( String input )
            throws RARException
    {
        StringBuilder output = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA" );
            md.update( input.getBytes() );
            byte[] digest = md.digest();
            for( int i = 0; i < digest.length; i++ ) {
                String hex = Integer.toHexString( digest[i] );
                if( hex.length() == 1 )
                    hex = "0" + hex;
                hex = hex.substring( hex.length() - 2 );
                output.append( hex );
            }
        }
        catch( Exception e ) {
            // log.error( "SessionManager.secureHash: Invalid Encryption Algorithm", e );
            throw new RARException(
                    "SessionManager.secureHash: Invalid Encryption Algorithm" );
        }
        return output.toString();
    }

    /**********************************************************************
     * Return the logger object.
     * @return Logger object.
     * @author Arsham Mesbah
     */
    /*
    public static Logger getLog()
    {
        return log;
    }
    */
}
