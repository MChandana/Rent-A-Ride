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
   
        
        <h3>My Returned Rentals</h3>
    <div class="scroll-list">
        <table>
            <tr><th>Pick Up Time</th><th>What time did you return it?</th><th>Your Charge</th>
            </tr>
        <#list rentals as rentals>
            <tr><td>${rentals.getPickupTime()}</td><td>${rentals.getReturnTime()}</td>
            <td>${rentals.getCharge()}</td>
    
                
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