<html>
<head>
    <meta charset="utf-8">
    <title>Members</title>
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
    <h3>Members</h3>
    <div class="scroll-list">
        <table>
            <tr>
            	<th>ID</th>
            	<th>First Name</th>
            	<th>Last Name</th>
            	<th>UserName</th>
            	<th>Email</th>
                <th>Address</th>
                <th>Date Created</th>
           </tr>
           <#list allCustomer as member>
           	<tr>
           		<th>${member.getId()}</th>
           		<th>${member.getFirstName()}</th>
           		<th>${member.getLastName()}</th>
           		<th>${member.getUserName()}</th>
           		<th>${member.getEmail()}</th>
           		<th>${member.getAddress()}</th>
           		<th>${member.getCreatedDate()}</th>
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