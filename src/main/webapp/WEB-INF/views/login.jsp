<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id"
	content="48162481753-b6noo6mps928frga21dfd841kvo1dhgg.apps.googleusercontent.com">

</head>
<body>
	<div class="box">
		<!-- <form> -->

		<div align="center" onclick="ClickLogin()" class="g-signin2"
			data-onsuccess="onSignIn"></div>

	</div>
	<script>
        
        var clicked=false;
        function ClickLogin()
        {
            clicked=true;
        }
        
        function onSignIn(googleUser){
        
        	if(clicked)
        	{
            var profile = googleUser.getBasicProfile();
        	var id_token = googleUser.getAuthResponse().id_token;
        	console.log("test 1"+id_token);
                $.ajax({
                	type: 'POST',
                    url: '/googleSignIn',
                    
                    
        				data:{
        					googleToken : id_token
        				},
                    success: function (data, status, xhr) {
                                        	
                    	if(data.value == 'true')
                    	{
                    		window.location.href = '/Dashboard';
                    	}
                    	else
                    	{
                    		$('#name').html('<h6>'+'User doesnot exist.Plz SignUp'+'<h6>');
                    	}
                    	
                    },
                    dataType : 'json'
                });
        	}   
        };
        </script>

</body>
</html>