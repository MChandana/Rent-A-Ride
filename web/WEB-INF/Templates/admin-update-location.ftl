<html>
  <head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
    <link rel="stylesheet" href="CSS/Admin.css">
  </head>
  <body>
    <!--HEADER-->
    <h1 class="logo"><a href="admin-homepage.html">RENT A RIDE</a></h1>
    <div id="menu" class="button">ADMIN</div>

    <ul id="drop-down-menu" class="drop-down-menu">
      <a href=""><div class="menu-header">ADMIN</div></a>
      <a class="menu-link" href=""><li class="menu-element">Profile</li></a>
      <a class="menu-link" href="index.html"><li class="menu-element">Logout</li></a>
    </ul>

    <h3 class="page-title">Update Rental Location</h3>
    <!--name, address, and capacity-->
    <h4>Choose the Rental Location You Want to Change</h4>
    
    <h5 style="text-align:center;">Name of Rental Locations
    <form class="update-form" action="UpdateRentalLocation" method="post">  
    <select style="width:300px;" name="name" id="name">
        <option style="font-weight: bolder;">NAME ADDRESS CAPACITY</option>
        <#list allLocation as allLocation>
        	<option>${allLocation.getName()}</option>
        </#list>
    </select>
      
    <h4 style="text-align: center;">What Changes Do You Want To Make</h4>
    
      New Name: <input type="text" name="newname"><br>
      New Address: <input type="text" name="newaddress"><br>
      New Capacity: <input type="number" name="newcapacity"><br>
      <div class="center">
        <input type="submit" name="new-location" class="small-button">
      </div>
    </form>


    <div class="footer"></div>
    <script src="Rent-A-Ride.js"></script>
  </body>
</html>
