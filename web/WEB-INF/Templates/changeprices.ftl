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
    <h3>Membership Fees and Late Fee Prices</h3>
    <div class="scroll-list">
        <table>
            <tr><th>Membership Price</th>
                <th>Membership Late Fees</th>
            </tr>
        <#list prices as prices>
            <tr><td>${prices.getMembershipPrice()}</td><td>${prices.getLateFee()}</td></tr>
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

 
   <form id="register-form" action="ChangePrices" method="post">
     Membership Prices: <input type = "text" name = "membershipPrice"> 
     Late Fees: <input type = "text" name = "lateFees">
     
     <input type = "submit" value = "Change Prices">
    </form>

<br>



<p>Back to <a href="AdminLogin.html">Admin HomePage</a> </p>


</body>
</html>