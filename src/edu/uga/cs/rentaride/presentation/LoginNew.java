package edu.uga.cs.rentaride.presentation;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.Location;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/Login")
public class LoginNew
        extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    static  String  templateDir = "WEB-INF/Templates";
    static  String  resultTemplateName = "CustomerLoginPage-result.ftl";
    static  String  errorTemplateName = "LoginError.ftl";


    private Configuration cfg;

    public void init()
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        //
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(
                getServletContext(),
                "WEB-INF/Templates"
        );
    }

   public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
       System.out.println("in do get login new");
   }

    public void doPost(HttpServletRequest req, HttpServletResponse res )
            throws ServletException, IOException
    {
        Template resultTemplate = null;
        HttpSession httpSession = null;
        BufferedWriter toClient = null;
        String         email = null;
        String         password = null;
        String         ssid = null;
        Session session = null;
        LogicLayer logicLayer = null;
        Template resultError=null;

        // Load templates from the WEB-INF/templates directory of the Web app.
        //
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
            resultError=cfg.getTemplate(errorTemplateName);
        }
        catch (IOException e) {
            throw new ServletException( "Login.doPost: Can't load template in: " + templateDir + ": " + e.toString());
        }

        httpSession = req.getSession();
        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid != null ) {
            System.out.println( "Already have ssid: " + ssid );
            session = SessionManager.getSessionById( ssid );
        }
        else
            System.out.println( "ssid is null" );

        // Prepare the HTTP response:
        // - Use the charset of template for the output
        // - Use text/html MIME-type
        //
        toClient = new BufferedWriter( new OutputStreamWriter( res.getOutputStream(), resultError.getEncoding() ) );
        res.setContentType("text/html; charset=" + resultError.getEncoding());
        if( session == null ) {
            try {
                session = SessionManager.createSession();
            }
            catch ( Exception e ) {
                System.err.println("couldn't create session in loginnew");
                return;
            }
        }

        logicLayer = session.getLogicLayer();
        User user=session.getUser();
        logicLayer.calCharges(user);

        // Get the parameters
        //
        email = req.getParameter( "email" );
        password = req.getParameter( "password" );

        if( email == null || password == null ) {
            System.err.println("Missing user name or password");
            res.sendRedirect("LoginError.html");
            return;
        }
        Map<String, Object> root = new HashMap<>();
        try {
            ssid = logicLayer.login( session, email, password );
            if(ssid.equals("invalid")){
                resultError.process(root,toClient);
                toClient.flush();
               //httpSession.invalidate();
                //session.run();
                SessionManager.logout(session);

            }

            if(ssid.startsWith("c")){
                ssid= ssid.substring(5);

                httpSession.setAttribute( "ssid", ssid );
                //getServletContext().getRequestDispatcher("customer-homepage.html").forward(req,res);

                List<RentalLocation> locations=logicLayer.findAllRentalLocations();
                List<VehicleType> vehicleTypes=logicLayer.findAllVehicleTypes();

                List<String> RLAddresses=new ArrayList<>();

                List<String> VTnames=new ArrayList<>();


                for(RentalLocation rentalLocation:locations){
                    String address=rentalLocation.getName();  //names of RLs
                    RLAddresses.add(address);

                }


                for(VehicleType vehicleType:vehicleTypes){
                    VTnames.add(vehicleType.getName());
                }

                root.put("location",RLAddresses);
                root.put("vehicleType",VTnames);

                res.sendRedirect("CustomerLoginPageAlt.html");

            /*    try {
                    resultTemplate.process(root,toClient);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            */
            }
else {
                ssid = ssid.substring(5);
                 httpSession.setAttribute("ssid", ssid);
                res.sendRedirect("AdminLogin.html");

            }
        }
        catch ( Exception e ) {
            System.out.println("in login new catch for error in login new");
            System.err.println("error in loginew");
                        return;
        }


        // Setup the data-model
        //
       // Map<String, Object> root = new HashMap<>();

        // Build the data-model
        //
        List<RentalLocation> allLocation=null;
        try {
            allLocation=logicLayer.findAllRentalLocations();
        } catch (RARException e) {
            System.out.println("getting all rental locations in login new from logic layer");
        }
      /*  root.put( "email", email );
        root.put("allLocation",allLocation);
        System.out.println("root .put");
*/
        // Merge the data-model and the template
        //
      /*  try {
            resultTemplate.process( root, toClient );
            toClient.flush();
            System.out.println("template processed");
        }
        catch (TemplateException e) {
            throw new ServletException( "Error while processing FreeMarker template", e);
        }
*/
        toClient.close();
    }


}

