<html>
<head>
    <meta charset="utf-8">
    <title>Reservations</title>
    <link rel="stylesheet" href="CSS/Admin.css">
</head>
<body>
<!--HEADER-->
<h1 class="logo"><a href="AdminHomePage.html">RENT A RIDE</a></h1>

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
	<h3>Reservations</h3>
	<div class="scroll-list">
		<table>
			<tr>
				<th>ID</th>
				<th>Rental Location</th>
				<th>Pick-Up Date</th>
				<th>Length (in minutes)</th>
				<th>Cancelled</th>
				<th>Customer ID</th>
				<th>Vehicle Type</th>
			</tr>
			<#list allReservation as member>
			<tr>
				<th>${member.getId()}</th>
				<th>${member.getRentalLocation().getName()}</th>
				<th>${member.getPickupTime()}</th>
				<th>${member.getLength()}</th>
				<th>${(member.getCanceled())?string('yes','no')}</th>
				<th>${member.getCustomer().getId()}</th>
				<th>${member.getVehicleType().getName()}</th>
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