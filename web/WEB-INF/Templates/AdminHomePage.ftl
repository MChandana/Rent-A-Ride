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

    <div class="flex-box">
      <div>
        <div class="box">
          <h3>Vehicle Types</h3>
          <ul>
            <li>sedan</li>
            <li>truck</li>
            <li>minivan</li>
            <li>hybrid</li>
          </ul>
          <a class="link" href=""><button>add new</button></a>
        </div>
        <div class="box">
          <h3>Cars</h3>
          <form id="my-form">
            <input type="search" id="mySearch">
          </form>
          <a class="link" href=""><button>search</button></a>
          <div class="scroll-list">
            <table>
              <tr><th>ID</th><th>Make</th><th>Model</th><th>Year</th><th>Mileage</th></tr>
              <tr><td>0001</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0002</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0003</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0004</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0005</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0006</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0007</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0008</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0009</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0010</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0011</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0012</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0013</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0014</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
              <tr><td>0015</td><td>Honda</td><td>Civic</td><td>2012</td><td>15mpg</td></tr>
            </table>
          </div>
          <a class="link" href=""><button>add new</button></a>
        </div>
      </div>
      <div>
        <div class="box">
          <h3>Cities</h3>
          <ul>
            <li>Athens</li>
            <li>Atlanta</li>
            <li>Savannah</li>
          </ul>
          <a class="link" href=""><button>add new</button></a>
        </div>
        <div class="box">
          <h3>Locations</h3>
          <div class="scroll-list">
            <table>
              <tr><th>Name</th><th>Address</th><th>Capacity</th></tr>
              <#list allLocation as allLocation>
          		<tr><td>${allLocation.getName()}</td><td>${allLocation.getAddress()}</td><td>${allLocation.getCapacity()}</td></tr>
              </#list>
            </table>
          </div>
         <a href="admin-add-location.html">Add New</a>
        </div>
      </div>
      <div>
        <div class="box">
          <h3>Members</h3>
          <form id="my-form">
            <input type="search" id="mySearch">
          </form>
          <a class="link" href=""><button>search</button></a>
          <div class="scroll-list">
            <table>
              <tr><th>Last Name</th><th>First Name</th><th>Email</th><th>Joined</th></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
              <tr><td>Doe </td><td>John</td><td>johndoe@gmail.com</td><td>9/20/2017</td></tr>
            </table>
          </div>
        </div>
        <div class="box">
          <h3>Feedback</h3>
          <!--
          <form id="my-form">
            <input type="search" id="mySearch">
          </form>
          <a class="link" href=""><button>search</button></a>
        -->
            <table>
              <tr><th>Latest Feedback</th><th>User</th><th>Date</th></tr>
              <tr><td class="feedback-text">I love Rent A Ride! It's the best.</td><td>John Doe</td><td>9/20/2017</td></tr>
              <tr><td class="feedback-text">This service is the worst. I can't believe I have to pay a membership fee just to be able to rent cars!</td><td>John Doe</td><td>9/20/2017</td></tr>
              <tr><td class="feedback-text">I love Rent A Ride! It's the best.</td><td>John Doe</td><td>9/20/2017</td></tr>
            </table>
          <a class="link" href=""><button>See All Feedback</button></a>
        </div>
      </div>
    </div>

    <div class="footer"></div>
    <script src="Rent-A-Ride.js"></script>
  </body>
</html>
