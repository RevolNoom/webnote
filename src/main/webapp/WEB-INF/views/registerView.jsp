<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Register</title>
   </head>
   <body>
      <jsp:include page="_header.jsp"></jsp:include>

      <h3>REGISTER</h3>
      <p style="color: red;">${errorString}</p>


      <form method="POST" action="${pageContext.request.contextPath}/register">
         <table border="0">
            <tr>
               <td>Username:</td>
               <td><input type="text" name="username"> </td>
            </tr>
            <tr>
               <td>First Name:</td>
               <td><input type="text" name="firstname"> </td>
            </tr>
            <tr>
               <td>Last Name:</td>
               <td><input type="text" name="lastname"> </td>
            </tr>
            <tr>
               <td>Password:</td>
               <td><input type="text" name="password"> </td>
            </tr>
            <tr>
               <td>Re-type Password:</td>
               <td><input type="text" name="retype_password"> </td>
            </tr>
            <tr>
               <td>
                  <input type="submit" value="Submit" />
               </td>
               <td>
                  <a href="${pageContext.request.contextPath}/login">I already have an account</a>
               </td>
            </tr>
         </table>
      </form>

      <jsp:include page="_footer.jsp"></jsp:include>
   </body>
</html>