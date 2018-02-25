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
    <h3>Vehicle Types and Prices</h3>
    <div class="scroll-list">
        <table>
            <tr><th>Name</th>
                <th>Max Hours</th>
                <th>Price</th></tr>
        <#list allVehicleTypes as allVehicleTypes>
            <tr><td>${allVehicleTypes.getVehicleTypeName()}</td><td>${allVehicleTypes.getMaxHours()}</td>
            <td>${allVehicleTypes.getPrice()}</td></tr>
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

 
 	<a href="newvt.html"><button>Add A New Vehicle Type</button></a>
 
 
 <form action="updateVehicleType" method="Get">
 	<input type = "submit" value = "Update Vehicle Type">
 </form>

<br>



<p>Back to <a href="AdminLogin.html">Admin HomePage</a> </p>


</body>
</html>