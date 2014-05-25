/**
 * Created by elvira on 29.03.14.
 */

function load(){
    $.ajax({
        url : '/getRegistered',
        type: 'get',
        success : function(response) {

            if (response != "")
            {
                $('.error').text(response);
                clearInterval(interval);
            }


        }
    })
}

//var form = document.getElementById('registerForm');
//form.addEventListener("onsubmit", goGetRegistered);
//$('#registerForm').on("submit" , goGetRegistered);

function goGetRegistered(event){
    //$('#status').text("Wait");
    interval = setInterval(function(){load()},2000 );
}
