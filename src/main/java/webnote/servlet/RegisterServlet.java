package webnote.servlet;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	// Show Login page.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Forward to /WEB-INF/views/registerView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		RequestDispatcher dispatcher //
				= this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");

		dispatcher.forward(request, response);
	}

	// When the user enters userName & password, and click Submit.
	// This method will be executed.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] params = {"username", "firstname", "lastname", "password", "retype_password"};
		for (int iii=0; iii<params.length; ++iii)
		{
			String param = request.getParameter(params[iii]);
			if (param == null || param.length() == 0)
			{
				ReloadWithNotification(request, response, "Please fill in your " + params[iii]);
				return;
			}
		}
		
		
		if ( ! request.getParameter("password").equals(request.getParameter("retype_password")))
		{
			ReloadWithNotification(request, response, "Password re-typed incorrectly. Please try again.");
			return;
		}
	
		

		
		String username = request.getParameter("username");
		if (UsernameIsDuplicated(username))
		{
			ReloadWithNotification(request, response, "Username '" + username + "' is already taken.");		
			return;
		}

		try
		{
			PreparedStatement statement = SQLite.get("webnote.db").prepareStatement("INSERT INTO User values (?, ?, ?, ?);");
			statement.setString(1, username);
			statement.setString(2, request.getParameter("firstname"));
			statement.setString(3, request.getParameter("lastname"));
			statement.setString(4, request.getParameter("password"));	
			statement.executeUpdate();
			statement.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		System.out.println("New user added to database.");
		
		// Redirect to login page on success
		request.setAttribute("errorString", "Registered successfully");
		response.sendRedirect(request.getContextPath() + "/login");
	}

	protected void ReloadWithNotification(HttpServletRequest request, HttpServletResponse response, String notification) throws ServletException, IOException
	{
		request.setAttribute("errorString", notification);
		doGet(request, response);
	}
	
	boolean UsernameIsDuplicated(String username)
	{
		try {
			Statement s = SQLite.get("webnote.db").createStatement();
			boolean result = s.executeQuery("SELECT COUNT(username) FROM User WHERE '" + username + "' == username").getInt(1) != 0;
			s.close();
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Username duplicity check failed. I'm stopping this user from creating new account.");
			return true;
		}
	}
}
