<html>
  <head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
    <link rel="stylesheet" href="CSS/Rent-A-Ride.css">
    <link rel="stylesheet" href="CSS/stylesheet.css"/>
  </head>
  <body>
    <!--HEADER-->
    <h1 class="logo"><a href="homepage.html">RENT A RIDE</a></h1>
    <div id="menu" class="button">USER</div>
    
    <!--<ul id="drop-down-menu" class="drop-down-menu">
      <a href=""><div class="menu-header">USER</div></a>
      <a href="userprof.html"><li>Profile</li></a>
      <a href="feedback.html"><li>Feedback</li></a>
      <a href="index.html"><li>Logout</li></a>
    </ul>-->

    <h2 class="log-in-page">Add New Vehicle</h2>
    <img class="profimg" src="images/carpic.png" id="profile-image1" class="img-circle img-responsive" width="200" height="200" align="center"> 
    <form id="register-form" action="AddVehicle" method="post">
      Tag: <input type="text" name="tag"><br>
      Make: <input type="text" name="make"><br>
      Model: <input type="text" name="model"><br>
      Year: <input type="text" name="year"><br>
      Millege: <input type="text" name="millege"><br>
      Last Service: 
      
      <select name="month" id="month">
      <option value="01">January</option>
      <option value="02">February</option>
      <option value="03">March</option>
      <option value="04">April</option>
      <option value="05">May</option>
      <option value="06">June</option>
      <option value="07">July</option>
      <option value="08">August</option>
      <option value="09">September</option>
      <option value="10">October</option>
      <option value="11">November</option>
      <option value="12">December</option>
      </select>
      
      <input type="text" name="day" placeholder="Day">
      <input type="text" name="Syear" placeholder="Syear"><br>
      
      Status:
      <select name="status" id="status">
      	<option>INRENTAL</option>
      	<option>INLOCATION</option>
      </select>
      
      RentalLocation:
      <select name="rentalLocation" id="rentallocation">
      	<#list allLocation as allLocation>
      	<option>${allLocation.getName()}</option>
      	</#list>
      	
 
      </select>
      
      VehicleType:
      <select name="vehicleType" id="vehicleType">
      	
      	<#list allVehicleTypes as allVehicleTypes>
      	<option>${allVehicleTypes.getName()}</option>
      	</#list>
      	
      </select>
      
      CarCondition:
      <select name="carCondition" id="carCondition">
      	<option>GOOD</option>
      	<option>NEEDSMAINTENANCE</option>
      </select>
      
      <input type="submit" value="Add New Vehicle" style="border: none;color: white;background-color: #499cd0">
    </form>
    <br>
     <div class="footer"></div>
     <script src="js/Rent-A-Ride.js"></script>
  </body>
</html>