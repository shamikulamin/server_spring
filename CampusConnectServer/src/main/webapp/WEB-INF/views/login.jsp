<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>

<head>

	<!-- Basics -->
	
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<title>Login</title>

	<!-- CSS -->
	
	<link rel="stylesheet" href="media/css/reset.css">
	<link rel="stylesheet" href="media/css/animate.css">
	<link rel="stylesheet" href="media/css/styles.css">
	
</head>

	<!-- Main HTML -->
	
<body>
	
	<!-- Begin Page Content -->
	
	<div id="container">
		
		<div id="header">
			<a href='#'><img src="media/images/CampusConnectLogo.png" alt="Campus Connect"></img></a>
		</div>
		
		<form name="f" action="<c:url value='j_spring_security_check' />" method="POST" >
		
		<c:if test="${param.error != null}">
			<div id="alert-div" class="alert alert-error">
				Failed to login.
				<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
					Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				</c:if>
			</div>
			<script>
				var err = document.getElementById("alert-div");
				if( err != null ) {
					err.parentNode.style.marginTop="10px";
				}
			</script>
		</c:if>
		
		<label for="name">Username:</label>
		
		<input type="name" name='username' >
		
		<label for="username">Password:</label>
		
		<p><a style="display:none;" href="#">Forgot your password?</a>
		
		<input type="password" name='password' >
		
		<div id="lower">
		
		<label style="display:none;" class="check"><input style="display:none;" name="_spring_security_remember_me" value="1" type="hidden">Keep me logged in</label>
		
		<input type="submit" value="Login">
		
		</div>
		
		</form>
		
	</div>
	
	
	<!-- End Page Content -->
	
</body>

</html>
	
	
	
	
	
		
	