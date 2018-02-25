function deleteType(id){
    var x=document.getElementById(id).parentNode.parentNode.parentNode.parentNode;
    var attr=x.getAttribute("id");
   document.getElementById(attr).innerHTML="";
}

var typecount=2;

function addType(){
    typecount+=1;
     var table=document.createElement('table');
        var tr1=document.createElement('tr');
       
        var td1=document.createElement('td');
        var td2=document.createElement('td');
       
     
        table.setAttribute("class","tablestyle1");
    var name=prompt("Enter vehicle type:");
        table.setAttribute("id",name);
        
        var fig=document.createElement('figure');
        var image=document.createElement('img');
        image.style.height='40';
        image.style.width='130';
        image.setAttribute("src",prompt("Enter image location:"));
        var caption=document.createElement('figcaption');
        caption.innerHTML=prompt("Enter vehicle type name to display as caption:");
        fig.appendChild(image);
        fig.appendChild(caption);
            
        td1.appendChild(fig); 
        
        var deletebtn1=document.createElement('button');
        deletebtn1.setAttribute("class","btn1");
            var idstr="bttn"+typecount;
    deletebtn1.setAttribute("id",idstr);
        deletebtn1.innerHTML="Delete";
        deletebtn1.setAttribute("onclick","deleteType(this.id)");
    td2.appendChild(deletebtn1);
    
        tr1.appendChild(td1);
        tr1.appendChild(td2);

        table.appendChild(tr1);
       
    
            document.getElementById("newType").appendChild(table);

}