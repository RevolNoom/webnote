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
				<% if (request.getAttribute("note") != null) {%>
   		<form action="update" method="post"> 
   	  <%} else { %>
   	  	<form action="insert" method="post">
   	  <%} %>

				<caption>
					<h2>
						<% if (request.getAttribute("note") != null) {%>
   		<p> Update Note </p> 
   	  <%} else { %>
   	  	<p> Add New Note </p>
   	  <%} %>
					</h2>
				</caption>
				
				<% if (request.getAttribute("note") != null) {%>
				<%Note note = (Note) request.getAttribute("note");  %>
   		<input type="hidden" name="id" value="<%=note.getId() %>" />
   	  <%}%>


				<fieldset class="form-group">
					<% if (request.getAttribute("note") != null) {%>
					<%Note note = (Note) request.getAttribute("note");  %>
   		<label>Created Date : </label> <div class="form-control" name="date"> <%=note.getDateCreated() %></div>
   	  <%} else { %>
   	  	<label>Created Date : </label> <div class="form-control" name="date"> <%=java.sql.Date.valueOf(java.time.LocalDate.now()) %></div>
   	  <%} %>
				</fieldset>


				<fieldset class="form-group">
				<% if (request.getAttribute("note") != null) {%>
					<%Note note = (Note) request.getAttribute("note");  %>
   		<label>Note Description : </label> <textarea class="form-control" name="description"> <%= note.getDescription() %> </textarea>
   	  <%} else { %>
   	  	<label>Note Description : </label> <textarea class="form-control" name="description"> </textarea>
   	  <%} %> 
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
      
   </body>
</html>