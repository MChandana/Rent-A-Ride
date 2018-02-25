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

    <ul id="drop-down-menu" class="drop-down-menu">
      <a href=""><div class="menu-header">USER</div></a>
      <a href="userprof.html"><li>Profile</li></a>
      <a href="feedback.html"><li>Feedback</li></a>
      <a href="index.html"><li>Logout</li></a>
    </ul>
	
	<form class="update-form" action="CancelReservation" method="post">  
    	<select style="width:300px;" name="name" id="name">
        	<option style="font-weight: bolder;">SELECT RESERVATION</option>
        	<#list reservations as reservation>
        		<option>${reservation[0]}: ${reservation[1]}, ${reservation[2]}, ${reservation[3]}</option>
        	</#list>
    	</select>
		<input type="submit" name="Cancel" class="small-button">
	</form>
  </body>
</html>