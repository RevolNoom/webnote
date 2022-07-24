<%@ page import="java.sql.*, java.util.*, webnote.beans.Note" %>
<!DOCTYPE html>
<html lang="en">
	<head> 
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
		
		<style type="text/css">
			
			*{
				box-sizing:border-box;
			}		
			
			body {
				background-color: #cae48d;
				font-family: 'Poppins', sans-serifs;
				display: flex;
				flex-wrap: wrap;
				margin:0;
				padding-top: 3rem;
			}
			
			.add{
				position:fixed;
				top: 1rem;
				right: 1rem;
				background-color: #9ec862;
				border-radius: 3px;
				color: #ffffff;
				border: none;
				padding: 0.5rem 1rem;
				cursor: pointer;
			}
			
			.login{
				position:fixed;
				top: 1rem;
				right: 8rem;
				background-color: #9ec862;
				border-radius: 3px;
				color: #ffffff;
				border: none;
				padding: 0.5rem 1rem;
				cursor: pointer;
				margin-right: 0.5rem;
			}
			
			.note {
				background-color: #ffffff;
				box-shadow: 0 0 10px 4px rgba(0, 0, 0, 0.1);
				margin: 30px 20px;
				height: 300px;
				width: 300px;
			}
			
			.note .tools {
				background-color: #9ec862;
				display:flex;
				justify-content: flex-end;
				padding: 0.5rem	
			}
			
			.note .tools button {
				background-color: transparent;
				border: none;
				color: #ffffff;
				cursor: pointer;
				font-size: 1rem;
				margin-left: 0.5rem;
			}
			
			.note .tools div {
				background-color: transparent;
				border: none;
				color: #ffffff;
				font-size: 1rem;
				margin-right: 0.5rem;
			}
			
			.note textarea {
				outline: none;
				font-family: inherit;
				font-size: 1.2rem;
				border: none;
				height: 250px;
				width: 100%;
				padding: 20px;
				resize: none;
			}
			
			.hidden {
				display: none;
			}
			
			.main {
				padding: 20px;
			}
			
			.popup-box{
			  position: fixed;
			  top: 0;
			  left: 0;
			  z-index: 2;
			  height: 100%;
			  width: 100%;
			  background: rgba(0,0,0,0.4);
			}
			
		</style>
		<title>Home Page</title>
	</head>
	
	<body>
	<a href="<%=request.getContextPath()%>/new">
		<button class="add" id="add"> 
			<i class="fas fa-plus"> </i> Add notes
		</button>
	</a>
	
	<a href="<%=request.getContextPath()%>/">
		<button class="login" id="login"> 
			<i class="fa fa-sticky-note"> </i> Login
		</button>
	</a>
		
		
		<% List<Note> listNotes = (List) request.getAttribute("listNotes");
			for (int i = 0;i < listNotes.size();i++) {%>
				<div class="note">
				<div class='tools'>
					<div class="date">Date: <%= listNotes.get(i).getDateCreated() %> </div>
        			<a href="edit?id=<%= listNotes.get(i).getId() %>"><button class='edit'><i class='fas fa-edit'> </i> </button></a>
        			<a href="delete?id=<%= listNotes.get(i).getId() %>"><button class='delete'> <i class='fas fa-trash-alt'> </i> </button></a>
    			</div>
    			<div class="main"> <%= listNotes.get(i).getDescription() %> </div>
			</div>
			<%}
		%>
	
	</body>
</html>