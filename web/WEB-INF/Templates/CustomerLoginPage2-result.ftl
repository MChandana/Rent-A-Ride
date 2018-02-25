<html>
<head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
    <link rel="stylesheet" href="CSS/Rent-A-Ride.css">

</head>
<body>

<script>
    function calculate() {
        var hours=document.getElementById("timeId");
        if(hours<=0 || hours>72){
            alert("Vehicle should be rented for atleast 1 hour and no more than 72 hours");
        }

        var pickupDate=document.getElementById("pickupDate");
        if(pickupDate.getTime()<Calendar.getInstance().getTimeInMillis()){
            alert("Reservations can be made for future times only.")
        }
    }
</script>
<!--HEADER-->
<h1 class="logo"><a href="">RENT A RIDE</a></h1>
<div id="menu" class="button">USER</div>

<!--<div>
  <form action="/UserProfile" method="get">
    <button type="submit" class="btn btn-link">User Profile</button>
  </form>
  <form action="Logout" method="get">
    <input type="submit" value="Logout">
  </form>
</div>-->


<ul id="drop-down-menu" class="drop-down-menu">
    <a href=""><div class="menu-header">USER</div></a>
    <!-- <form action="/UserProfile" method="get">
       <button type="submit" class="btn btn-link" style="color: #71b2da; background-color:white;border: none ">Profile</button>
     </form>-->
    <a href="/UserProfile">Profile</a>
    <a href="feedback.html">Feedback</a>
    <a href="/Logout"><li>Logout</li></a>
    <!-- <form action="Logout" method="get">
       <input type="submit" value="Logout" style="color: #71b2da; background-color:white;border: none ">
     </form>-->
</ul>

<h2 class="manage-ride">Manage Rides</h2>

<div id="example-car" class="rented-car">
    <p> Car: Honda Civic<br>
        Time: 11/24 10:00 AM<br>
        Location: 123 Main St.<br>
    </p>
    <div>
        <div id="pick-up-button" class="button">pick up</div>
        <div id="cancel-button" class="button">cancel</div>
    </div>
</div>

<h2 class="find-a-ride">Find a Ride</h2>





<div>


    <form action="ReserveVehicle" method="get">

        <input type="hidden" name="RL" value="${selectedLoc}">
        <input type="hidden" name="VT" value="${selectedVT}">


        <div> <label>Location:</label><input type="text" name="selectedLoc" value="${selectedLoc}" placeholder="${selectedLoc}" disabled>
    <label>Vehicle Type:</label><input type="text" name="selectedVT" value="${selectedVT}" placeholder="${selectedVT}" disabled>
<br><br><br>
            Full rental location details are:<br>
            Name:${RLocation.getName()}<br>
            Address:${RLocation.getAddress()}<br>
            Capacity:${RLocation.getCapacity()}<br>

            <br>

        </div>
    <br>

    <div>
    <#list hourlyPrices as hourlyPrices>

                Hourly price for this vehicle type is $${hourlyPrices.getPrice()}

    </#list>
    </div>

<br>

    <div style="margin-left: 10%"><label style="color: #cd472b"><h5><b>Please enter valid pickup date time. Reservation should be greater than 0 hours and no more than 72 hours.</b></h5></label><br>
    <label>Pickup Date:</label><input type="datetime-local" name="pickupDate" id="pickupDate">
   <label> No.of hours you want to rent:</label><input type="number" id="timeId" name="time" onchange="calculate()">
   </div>
    <!--<div class="rental-option">-->
    <div> <h3>Vehicles</h3></div>

    <div class="scroll-list" style="margin-left: 10%">
                <table>
                    <tr><th>Tag</th><th>Make</th><th>Model</th><th>Year</th>
                        <th>Mileage</th><th>Last Service</th><th>Status</th>

                        <th>Rental Location</th><th>Vehicle Type</th><th>Car Condition</th><th>Rent Vehicle</th></tr>
                <#list vehicle as vehicle>
                    <tr><td>${vehicle.getRegistrationTag()}</td><td>${vehicle.getMake()}</td><td>${vehicle.getModel()}</td>
                        <td>${vehicle.getYear()}</td><td>${vehicle.getMileage()}</td><td>${vehicle.getLastServiced()}</td>
                        <td>${vehicle.getStatus()}</td><td>${vehicle.getRentalLocation().getName()}</td>
                        <td>${vehicle.getVehicleType().getName()}</td><td>${vehicle.getCondition()}</td>
                       <td>
                                <input type="submit" value="Rent" style="border: none" class="button" id="reserve-button">
                        </td>
                    </tr>
                </#list>
                </table>
    </form>

            </div>
   <!-- </div>-->

<!--</div>-->





<div class="footer"></div>
</body>
</html>
