<html>
<head>
    <meta charset="utf-8">
    <title>Profile</title>
    <link rel="stylesheet" href="CSS/Rent-A-Ride.css"/>
    <link rel="stylesheet" href="CSS/stylesheet.css"/>
</head>
<body>
<!--HEADER-->
<h1 class="logo"><a href="CustomerLoginPageAlt.html">RENT A RIDE</a></h1>
<div id="menu" class="button">USER</div>

<ul id="drop-down-menu" class="drop-down-menu">
    <a href=""><div class="menu-header">USER</div></a>
    <a href="/UserProfile">Profile</a>
    <a href="feedback.html">Feedback</a>
    <a href="/Logout"><li>Logout</li></a>
</ul>

<h2 class="log-in-page">Hello, ${name}!</h2>
<img class="profimg" src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg" id="profile-image1" class="img-circle img-responsive" width="200" height="200" align="center">
<br>
<div class="profileinfo">
    <label class="profilelabel">Name:</label><span id="nameValue" class="profilevalue">${name}</span>
    <br/>
    <label class="profilelabel">UserName:</label><span id="usernameValue" class="profilevalue">${username}</span>
    <br/>
    <label class="profilelabel">Email:</label><span id="emailValue" class="profilevalue">${email}</span>
    <br/>
    <label class="profilelabel">Address:</label><span id="addressValue" class="profilevalue">${address}</span>
    <br/>
    <label class="profilelabel">Start Date:</label><span id="createdValue" class="profilevalue">${createdDate}</span>
    <br/>
    <label class="profilelabel">Member until:</label><span id="memberUntilValue" class="profilevalue">${memberUntil}</span>
    <br/>
    <label class="profilelabel">License No.:</label><span id="licenseValue" class="profilevalue">${licNumber}</span>
    <br/>
    <label class="profilelabel">Licnse State:</label><span id="licStateValue" class="profilevalue">${licState}</span>
    <br/>
    <label class="profilelabel">CC Number:</label><span id="ccNumberValue" class="profilevalue">${CCNumber}</span>
    <br/>
    <label class="profilelabel">CC Expiration:</label><span id="ccExpValue" class="profilevalue">${CCExpiration}</span>
    <br/>
    <label class="profilelabel">Status:</label><span id="statusValue" class="profilevalue">${status}</span>
    <br/>
</div>
<br>
<a href="updateprof.html"><div id="update-button"class="btn">Update Information</div></a>
<div class="footer"></div>
<script src="js/Rent-A-Ride.js"></script>
</body>
</html>
