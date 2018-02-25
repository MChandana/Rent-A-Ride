var menuButton = document.getElementById("menu");
menuButton.addEventListener("click", expandMenu);
function expandMenu() {
  document.getElementById("drop-down-menu").style.display = "inherit";
}

var pickUpButton = document.getElementById("pick-up-button");
pickUpButton.addEventListener("click", pickedUp);
function pickedUp() {
  if (pickUpButton.textContent == "pick up") {
    pickUpButton.textContent = "return";
  }
  else {
    document.getElementById("example-car").style.display = "none";
  }
}

var findButton = document.getElementById("find-button");
findButton.addEventListener("click", search);
function search() {
  document.getElementById("car-search").style.display = "inherit";
}

var rentButton = document.getElementById("rent-button");
rentButton.addEventListener("click", rent);
function rent() {
  document.getElementById("example-car").style.display = "flex";
  pickUpButton.textContent = "pick up";
}
