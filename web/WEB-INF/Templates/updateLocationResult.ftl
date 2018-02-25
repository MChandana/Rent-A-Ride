<html>
  <head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
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
            <table>
              <tr><th>Name</th><th>Address</th><th>Capacity</th></tr>
              <#list allLocation as allLocation>
          		<tr><td>${allLocation.getName()}</td><td>${allLocation.getAddress()}</td><td>${allLocation.getCapacity()}</td></tr>
              </#list>
            </table>
            <br>
		<br>
		<br>
		<br>
          </div>
          <br>
		<br>
         <a href="admin-add-location.html">Add New</a>

<p>You Have Update This Rental Location: ${name}</p>
<br>

<p>Back to <a href="ChooseUpdate.html">Update More Rental Locations</a> </p>
<p>Back to <a href="AdminHomePage.html">Admin HomePage</a> </p>

</body>
</html>