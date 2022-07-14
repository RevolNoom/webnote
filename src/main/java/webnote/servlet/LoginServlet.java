package webnote.servlet;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import webnote.beans.UserAccount;
//import webnote.utils.DBUtils;
//import webnote.utils.MyUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	// Show Login page.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Forward to /WEB-INF/views/loginView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		RequestDispatcher dispatcher //
				= this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

		dispatcher.forward(request, response);

	}

	// When the user enters userName & password, and click Submit.
	// This method will be executed.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		///String rememberMeStr = request.getParameter("rememberMe");
		//boolean remember = "Y".equals(rememberMeStr);


		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) 
		{
			RedirectToLoginOnError(request, response, "Required username and password!");
			return;
		} 

		try
		{
			// Find the user in the DB.
			PreparedStatement ps = SQLite.get("webnote.db").prepareStatement("SELECT Count(*) FROM User WHERE ? == Username and ? == password;");
			ps.setString(1, userName);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();				
			if (rs.getInt(1) == 0)
			{
				RedirectToLoginOnError(request, response, "User Name or password invalid");		
				return;
			}
			ps.close();
			rs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error fetching user data in database.");
			RedirectToLoginOnError(request, response, "User Name or password invalid");		
			return;
		} 
	
		System.out.println("Logged in successfully");
		// Redirect to userInfo page.
		HttpSession s = request.getSession();
		s.setAttribute("username", userName);
		response.sendRedirect(request.getContextPath() + "/userInfo");
	}

	protected void RedirectToLoginOnError(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException
	{
		request.setAttribute("errorString", errorMsg);
		doGet(request, response);
	}
}
