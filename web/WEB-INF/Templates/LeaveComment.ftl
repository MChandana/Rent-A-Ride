<html>
  <head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
    <link rel="stylesheet" href="CSS/Rent-A-Ride.css">
  </head>
  <body>
  	<!--HEADER-->
    <h1 class="logo"><a href="customer-homepage.html">RENT A RIDE</a></h1>
    <div id="menu" class="button">USER</div>

    <ul id="drop-down-menu" class="drop-down-menu">
      <a href=""><div class="menu-header">USER</div></a>
      <a href="userprof.html"><li>Profile</li></a>
      <a href="feedback.html"><li>Feedback</li></a>
      <a href="index.html"><li>Logout</li></a>
    </ul>
    
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <form class="update-form" action="comment" method="get">
    	<select style="width:300px;" name="id">
        	<option style="font-weight: bolder;">SELECT RENTAL</option>
        	<#list rentals as rental>
        		<option>${rental.getId()}</option>
        	</#list>
    	</select>
    	<br>
    	<input type="text" name="comment">
    	<br>
		<input type="submit" name="Submit" class="small-button">
	</form>

  </body>
 </html>