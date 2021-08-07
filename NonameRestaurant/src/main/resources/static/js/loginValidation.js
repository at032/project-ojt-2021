/*
 * import no vao thang login di
 */$(document).ready(function() { 
     $("#loginForm").validate({
         // preventDefault();
         rules: {
             email: {
                 required: true,
				 email:true
             },
             password: {
                 required: true,
					
             },
         },
         messages: {
             email: {
                 required: "Email is required",
             },
             password: {
                 required: "Password is required",
             },       
         },
        errorPlacement: function(error, element) {
             error.insertAfter(element.parent("div"))
         }
     });
 });
setTimeout(function(){
  $('#nenbienmat').remove();
}, 5000);