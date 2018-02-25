<html>
<head>
    <meta charset="utf-8">
    <title>Vehicles</title>
    <link rel="stylesheet" href="CSS/Admin.css">
</head>
<body>
<!--HEADER-->
<h1 class="logo"><a href="CustomerLoginPageAlt.html">RENT A RIDE</a></h1>

<div id="menu" class="button">

    <form action="Logout" method="Get">
        <input type="submit" value="Logout" name="staying">
    </form>

</div>

<ul id="drop-down-menu" class="drop-down-menu">
    <a href=""><div class="menu-header">User</div></a>
    <a class="menu-link" href=""><li class="menu-element">Profile</li></a>
    <a class="menu-link" href="index.html"><li class="menu-element">Logout</li></a>
</ul>

<br>
<br>
<br>
<br>
<div class="box">
    <h3>My Reservations</h3>
    <div class="scroll-list">
        <table>
            <tr><th>Rental Location Name</th><th>Length</th><th>Vehicle Type</th><th>Pick Up Time</th><th>Reservation ID</th>
            <th>Pick Up Rental?</th><th> </th><th>Cancel the Reservation</th></tr>
        <#list rev as rev>
            <tr><td>${rev.getRentalLocation().getName()}</td><td>${rev.getLength()}</td><td>${rev.getVehicleType().getName()}</td>
                <td>${rev.getPickupTime()}</td><td>${rev.getId()}</td>
                
                
                <td>
                <form action="createRental" method="post">
                	<input type="radio" name="id" value=${rev.getId()}>
                	<input type="submit" value="Pick Up" class="small-button">
                </form>
                </td>
                <td></td>
                
                <td>
                <form action="cancel" method="post">
                	<input type="radio" name="id" value=${rev.getId()}>
                	<input type="submit" value="Cancel" class="small-button">
                </form>
                </td>
                
                </tr>
        </#list>
        </table>
        
        <h3>My Rentals</h3>
    <div class="scroll-list">
        <table>
            <tr><th>Pick Up Time</th><th>What time did you return it?</th><th>Ready To Return It?</th>
            </tr>
        <#list rentals as rentals>
            <tr><td>${rentals.getPickupTime()}</td><td>NOT RETURNED YET</td>
            
            <td><form action="returnRental" method="post">
            <input type="radio" name="id" value=${rentals.getId()}></td>
            
            <td><input type="submit" value="Drop Off" class="small-button"></td>
                
       </tr>
        </#list>
        </table>
        
          <h3>My Returned Rentals</h3>
    <div class="scroll-list">
        <table>
            <tr><th>You Have Been Charged: </th> <th>Leave A Comment</th>
            </tr>
        <#list riri as riri>
            <tr><td>${riri.getCharges()}</td>
             </tr>
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


<br>



<p>Back to <a href="CustomerLoginPageAlt.html">HomePage</a> </p>


</body>
</html>