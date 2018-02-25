<html>
<head>
    <meta charset="utf-8">
    <title>Comments</title>
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
            	<th>Text</th>
            	<th>Rental ID</th>
           </tr>
           <#list allComment as member>
           	<tr>
           		<th>${member.getId()}</th>
           		<th>${member.getText()}</th>
           		<th>${member.getRental().getId()}</th>
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