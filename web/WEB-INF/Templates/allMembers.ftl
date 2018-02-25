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
    <h3>Vehicles</h3>
    <div class="scroll-list">
        <table>
            <tr><th>First Name</th>
            	<th>Last Name</th>
            	<th>Username</th>
            	<th>Email</th>
                <th>Address</th>
                <th>Date Created</th>
                </tr>
        	
        	<#list members as members>
            	<tr>
            		<td>members.getFirstName()</td>
            		<td>members.getLastName()</td>
            		<td>members.getUserName()</td>
            		<td>members.getEmail()</td>
            		<td>members.getAddress()</td>
            		<td>members.getCreatedDate()</td>
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
<a href="admin-add-vehicle.html">Add New Vehicle</a>

<br>



<p>Back to <a href="AdminLogin.html">Admin HomePage</a> </p>


</body>
</html>