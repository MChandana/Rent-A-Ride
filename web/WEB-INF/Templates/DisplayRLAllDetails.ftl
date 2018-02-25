<html>
<head>
    <meta charset="utf-8">
    <title>Rental Locations</title>
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
    <h3>Locations</h3>
    <div class="scroll-list">

        <br>
        <br>

        Rental Location Details:
        <table>
            <tr><th>Name</th><th>Address</th><th>Capacity</th></tr>
        <#list allLocation as allLocation>
            <tr><td>${allLocation.getName()}</td><td>${allLocation.getAddress()}</td><td>${allLocation.getCapacity()}</td>

            </tr>
        </#list>
        </table>
<br>
        Vehicles at this Location:
        <table>
            <tr><th>Tag</th><th>Make</th><th>Model</th><th>Year</th>
                <th>Mileage</th><th>Last Service</th><th>Status</th>
                <th>Rental Location</th><th>Vehicle Type</th><th>Car Condition</th></tr>
        <#list vehicle as vehicle>
            <tr><td>${vehicle.getRegistrationTag()}</td><td>${vehicle.getMake()}</td><td>${vehicle.getModel()}</td>
                <td>${vehicle.getYear()}</td><td>${vehicle.getMileage()}</td><td>${vehicle.getLastServiced()}</td>
                <td>${vehicle.getStatus()}</td><td>${vehicle.getRentalLocation().getName()}</td>
                <td>${vehicle.getVehicleType().getName()}</td><td>${vehicle.getCondition()}</td></tr>
        </#list>
        </table>

        <br>

        Reservations at this location:
        <table>
            <tr><th>PickUp Date</th><th>Reservation length (min)</th><th>Cancellation status</th>
                <th>CustomerID</th><th>Vehicle Type</th></tr>

        <#list reservation as reservation>
            <tr><td>${reservation.getPickupTime()}</td><td>${reservation.getLength()}</td>
                <td>${reservation.getCanceled()?then('Yes','No')}</td>
                <td>${reservation.getCustomer().getId()}</td><td>${reservation.getVehicleType().getName()}</td>
                </tr>
        </#list>
        </table>

        <br>

        Rentals at this location:
        <table>
            <tr><th>PickUp Date</th><th>Return Date</th><th>Late status</th><th>Charges</th>
                <th>CustomerID</th><th>VehicleID</th></tr>
        <#list rental as rental>
            <tr><td>${rental.getPickupTime()}</td><td>${rental.getReturnTime()}</td>
                <td>${rental.getLate()?then('Yes','No')}</td>
                <td>${rental.getCharges()}</td><td>${rental.getCustomer().getId()}</td>
                <td>${rental.getVehicle().getId()}</td>
            </tr>
        </#list>
        </table>

        <br>
    </div>
</div>
<br>
<br>
<a href="admin-add-location.html">Add New Location</a>

<br>
<a href="ChooseUpdate.html">Update Rental Location</a>
<br>


<p>Back to <a href="AdminLogin.html">Admin HomePage</a> </p>


</body>
</html>