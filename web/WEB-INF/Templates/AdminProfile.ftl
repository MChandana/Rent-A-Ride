<html>
  <head>
    <meta charset="utf-8">
    <title>Rent A Ride</title>
    <link rel="stylesheet" href="CSS/Rent-A-Ride.css"/>
    <link rel="stylesheet" href="CSS/stylesheet.css"/>
  </head>
  <body>
    <!--HEADER-->
    <h1 class="logo"><a href="homepage.html">RENT A RIDE</a></h1>
    <div id="menu" class="button"><a href="AdminLogin.html">${username}</a></div>
    
    <ul id="drop-down-menu" class="drop-down-menu">
      <a href=""><div class="menu-header">USER</div></a>
      <a href="userprof.html"><li>Profile</li></a>
      <a href="feedback.html"><li>Feedback</li></a>
      <a href="index.html"><li>Logout</li></a>
    </ul>

    <h2 class="log-in-page">Hello, ${fullname} !</h2>
    <img class="profimg" src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg" id="profile-image1" class="img-circle img-responsive" width="200" height="200" align="center"> 
    <br>
    <div class="profileinfo">
        <label class="profilelabel">Name:</label><span id="nameValue" class="profilevalue">${fullname}</span>
        <br/>
        <label class="profilelabel">Address:</label><span id="addressValue" class="profilevalue">${address}</span>
        <br/>
        <label class="profilelabel">Email:</label><span id="email" class="profilevalue">${email}</span>
        <br/>
        
    </div>
    <br>
    <a href="newadmininfo.html"><div id="update-button"class="btn">Update Information</div></a>
    <a href="AdminLogin.html"><div id="update-button"class="btn">Back to Admin HomePage</div></a>
    <div class="footer"></div>
    <script src="js/Rent-A-Ride.js"></script>
  </body>
</html> 
