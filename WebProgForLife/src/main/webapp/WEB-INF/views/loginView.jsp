<%@ page import="java.sql.*, java.util.*, webnote.beans.Note" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>View Note Page</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<style>
	body {
		background-color: #cae48d;
				font-family: 'Poppins', sans-serifs;
				display: flex;
				flex-wrap: wrap;
				margin:0;
				padding-top: 3rem;
	}
	
	textarea {
		resize:none;
		border:1;
	}
</style>
   </head>
   <body>
      <div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<form method="post" action="login">
				<caption>
					<h2>
						Login Page
					</h2>
				</caption>
				<p style="color: red;">${errorString}</p>

				<fieldset class="form-group">
					<label>User name : </label>
					<input type="text" class="form-control" name="userName" value= "${user.userName}" />
				</fieldset>


				<fieldset class="form-group">
				<label>Password : </label>
					<input type="text" class="form-control" name="password" value= "${user.password}" />
				</fieldset>

				<button type="submit" class="btn btn-success">Login</button>
				<a href="${pageContext.request.contextPath}/register">Click here for creating account</a>
				</form>
			</div>
		</div>
	</div>
      
   </body>
</html>