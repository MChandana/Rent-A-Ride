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

    <h3 class="page-title">Delete Rental Location</h3>
    <!--name, address, and capacity-->
    <h4>Choose the Rental Location You Want to Delete</h4>
    
    <h5 style="text-align:center;">Name of Rental Locations
    <form class="update-form" action="DeleteRentalLocation" method="post">  
    <select style="width:300px;" name="name" id="name">
        <option style="font-weight: bolder;">NAME ADDRESS CAPACITY</option>
        <#list allLocation as allLocation>
        	<option>${allLocation.getName()}</option>
        </#list>
    </select>
   		<input type="submit" value="SUBMIT">
    </form>


    <div class="footer"></div>
    <script src="Rent-A-Ride.js"></script>
  </body>
</html>