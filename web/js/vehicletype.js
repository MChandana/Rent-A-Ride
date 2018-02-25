function changeVehicleDesc(){
    var price=prompt("Enter new price for this vehicle type:");
    document.getElementById("price").innerHTML=price;
}

function changeVehicleDescMini(){
    var price=prompt("Enter new price for this vehicle type:");
    document.getElementById("priceMini").innerHTML=price;
}

function deleteSedan(){
    document.getElementById("sedan").innerHTML="";
    
}

function deleteMinivan(){
    document.getElementById("minivan").innerHTML="";
    
}


function addType(){
       
    var table=document.createElement('table');
        var tr1=document.createElement('tr');
        var tr2=document.createElement('tr');
        var tr3=document.createElement('tr');

        var td1=document.createElement('td');
        var td2=document.createElement('td');
        var td3=document.createElement('td');
        var td4=document.createElement('td');
        var td5=document.createElement('td');
        var td6=document.createElement('td');
        var td7=document.createElement('td');
        var td8=document.createElement('td');
        var td9=document.createElement('td');

     
        table.setAttribute("class","tablestyle");
    var name=prompt("Enter vehicle type:");
        table.setAttribute("id",name);
        
        var image=document.createElement('img');
        image.style.height='40';
        image.style.width='130';
        image.setAttribute("src",prompt("Enter image location:"));
        td1.style.backgroundColor='white';
        td1.setAttribute("rowspan",3);
        td1.appendChild(image);
        
        
        var desc=document.createElement('label');
        desc.innerHTML="Description:";
        td2.appendChild(desc);
        
        var description=document.createElement('label');
        description.innerHTML=prompt("enter description:");
        td3.appendChild(description);
        
        var edit=document.createElement('button');
        edit.setAttribute("class","btn1");
        edit.innerHTML="Edit";
    edit.setAttribute("onclick","changeVehicleDesc()")
        td4.style.backgroundColor='white';
        td4.setAttribute("rowspan",3);
        td4.appendChild(edit);
        
        var deletebtn=document.createElement('button');
        deletebtn.setAttribute("class","btn1");
        deletebtn.innerHTML="Delete";
        var str="delete"+name+"()";
        deletebtn.setAttribute("onclick",str);
        td5.style.backgroundColor='white';
        td5.setAttribute("rowspan",3);
        td5.appendChild(deletebtn);
        
        tr1.appendChild(td1);
        tr1.appendChild(td2);
        tr1.appendChild(td3);
        tr1.appendChild(td4);
        tr1.appendChild(td5);
        
        
        var price=document.createElement('label');
        price.innerHTML="Price:";
        td6.appendChild(price);
        
        var priceval=document.createElement('span');
        priceval.setAttribute("id","price");
        priceval.innerHTML=prompt("Set prices for this vehicle type per hour");
        var dailyprice=document.createElement('span');
        dailyprice.setAttribute("id","perdayprice");
        dailyprice.innerHTML=prompt("Set the price for entire day rental:");
        var pricelabel=document.createElement('label');
        var firstpart=document.createElement('label');
        firstpart.innerHTML="$";
        var middle=document.createElement('label');
        middle.innerHTML="/hr and $";
        var last=document.createElement('label');
        last.innerHTML="/day";
        td7.appendChild(firstpart);
        td7.appendChild(priceval);
        td7.appendChild(middle);
        td7.appendChild(dailyprice);
        td7.appendChild(last);
   
  
        tr2.appendChild(td6);
        tr2.appendChild(td7);
        
       
        var goodlabel=document.createElement('label');
        goodlabel.innerHTML="Good to Know:";
        td8.appendChild(goodlabel);
        
        var extrainf=document.createElement('label');
        extrainf.innerHTML=prompt("Enter additional info about this type");
        td9.appendChild(extrainf);
        
        tr3.appendChild(td8);
        tr3.appendChild(td9);
        
        table.appendChild(tr1);
        table.appendChild(tr2);
        table.appendChild(tr3);

    
            document.getElementById("newVehicle").appendChild(table);

}