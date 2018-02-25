        
           function updateInfo(){
               var paramAry=window.location.href.split('?');
               var paramArray=paramAry[1];
               var params=paramArray.split('&');
               for(i=0;i<params.length;i++){
                   var values=params[i].split('=');
                   if(values[0]=="name" && values[1]!=""){
                       document.getElementById("name").innerHTML=values[1];
                   }
                   if(values[0]=="phoneNo" && values[1]!=""){
                       document.getElementById("phoneNo").innerHTML=values[1];
                   }
                   if(values[0]=="email" && values[1]!=""){
                       document.getElementById("email").innerHTML=values[1];
                   }
                   if(values[0]=="privileges" && values[1]=="addRemoveVehicle"){
                       document.getElementById("addremoveVehicle").innerHTML="";
                   }
                   if(values[0]=="privileges" && values[1]=="adRemoveVehicleTypes"){
                       document.getElementById("addremoveVehicleTypes").innerHTML=values[1];
                   }
                   if(values[0]=="privileges" && values[1]=="removeMembers"){
                       document.getElementById("removeMembers").innerHTML=values[1];
                   }
                   if(values[0]=="privileges" && values[1]=="addRemoveRentalLocations"){
                       document.getElementById("addremoveRentalLoc").innerHTML=values[1];
                   }
                   if(values[0]=="privileges" && values[1]=="updateAdmin"){
                       document.getElementById("updateAdmin").innerHTML=values[1];
                   }
                   
               }
               
           }
           

window.onload = updateInfo;