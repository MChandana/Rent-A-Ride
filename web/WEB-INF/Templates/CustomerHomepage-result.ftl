<html>
<head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
    <link rel="stylesheet" href="CSS/Rent-A-Ride.css">
</head>
<body>
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


<div class="select-flex-box">


    <select>
        <#list location as location>
            <option value="${location}">${location}</option>
        </#list>
    </select>

    <select>
        <option value="" disabled selected>Vehicle Type</option>
        <option value="sedan">Sedan</option>
        <option value="truck">Truck</option>
        <option value="minivan">Minivan</option>
        <option value="hybrid">Hybrid</option>
    </select>
</div>

<div id="find-button" class="button">find</div>

<div id="car-search" class="flex-box">
    <div class="rental-option">
        <div class = "rental-description">
            <p>
                Car: Honda Civic<br>
                Location:<br>
                123 Main St.<br>
            </p>
            <h4>$20 /hr</h4>
            <div id="rent-button" class="button">rent</div>
        </div>
        <img src="images/Honda-Civic-sedan.jpg" class="rental-image" alt="honda civic image"/>
    </div>
</div>

<div class="footer"></div>
<script src="js/Rent-A-Ride.js"></script>
</body>
</html>
