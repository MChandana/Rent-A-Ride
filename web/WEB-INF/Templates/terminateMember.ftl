<html>
  <head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
    <link rel="stylesheet" href="CSS/Rent-A-Ride.css">
  </head>
  <body>
    <!--HEADER-->
    <h1 class="logo"><a href="AdminLogin.html">RENT A RIDE</a></h1>
    <div id="menu" class="button">ADMIN</div>
    
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <div style="display: inline-block; text-align: center;">
    <form class="update-form" action="Terminate" method="get">
    	<select style="width:200px;" name="email" id="email">
    		<option>----SELECT MEMBER----</option>
    		<#list allCustomer as member>
    			<option>${member.getEmail()}</option>
    		</#list>
    	</select>
    	<input type = "submit" value="Terminate" name="Terminate" class="small-button">
    </form>
    </div>
  </body>
</html>