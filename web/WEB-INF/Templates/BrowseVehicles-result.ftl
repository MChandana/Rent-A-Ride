<html>
<head>
    <meta charset="utf-8">
    <title>Vehicles</title>
    <link rel="stylesheet" href="CSS/Admin.css">
</head>
<body>
<!--HEADER-->
<h1 class="logo"><a href="">RENT A RIDE</a></h1>

<div id="menu" class="button">

    <form action="Logout" method="Get">
        <input type="submit" value="Logout" name="staying">
    </form>

</div>

<ul id="drop-down-menu" class="drop-down-menu">
    <a href=""><div class="menu-header">ADMIN</div></a>
    <a class="menu-link" href=""><li class="menu-element">Profile</li></a>
    <a class="menu-link" href="index.html"><li class="menu-element">Logout</li></a>
</ul>

<br>
<br>
<br>
<br>
<div class="box">
    <h3>Vehicles</h3>
    <div class="scroll-list">
        <table>
            <tr><th>Tag</th><th>Make</th><th>Model</th><th>Year</th>
                <th>Mileage</th><th>Last Service</th><th>Status</th>
                <th>Rental Location</th><th>Vehicle Type</th><th>Car Condition</th></tr>
        <#list allVehicles as vehicle>
            <tr><td>${vehicle.getRegistrationTag()}</td><td>${vehicle.getMake()}</td><td>${vehicle.getModel()}</td>
                <td>${vehicle.getYear()}</td><td>${vehicle.getMileage()}</td><td>${vehicle.getLastServiced()}</td>
                <td>${vehicle.getStatus()}</td><td>${vehicle.getRentalLocation().getName()}</td>
                <td>${vehicle.getVehicleType().getName()}</td><td>${vehicle.getCondition()}</td></tr>
        </#list>
        </table>
        <br>
        <br>
        <br>
        <br>
    </div>
</div>
<br>
<br>

 <form action="AddVehicle" method="Get">
 	<input type = "submit" value="Add New Vehicle">
 </form>
 
 <form action="UpdateVehicle" method="Get">
 	<input type = "submit" value = "Update Vehicle">
 </form>

<br>



<p>Back to <a href="AdminLogin.html">Admin HomePage</a> </p>


</body>
</html>