package webnote.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String rememberMeStr = request.getParameter("rememberMe");
		//boolean remember = "Y".equals(rememberMeStr);


		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			RedirectToLoginOnError(request, response, "Required username and password!");
		} 
		
		SQLite s = new SQLite("webnote.db");
		
		// Find the user in the DB.
		ResultSet rs = s.Query("SELECT * FROM User WHERE " + userName + " == Username and " + password  +" == password;");
			
		// I don't care wtf this resultset has.
		// If there's an entry, that's the right pair of username and password
		if (rs == null)
			RedirectToLoginOnError(request, response, "User Name or password invalid");		
	
		
		//HttpSession session = request.getSession();
		//MyUtils.storeLoginedUser(session, user);

		// If user checked "Remember me".
		/*
		if (remember) {
			MyUtils.storeUserCookie(response, user);
		}
		// Else delete cookie.
		else {
			MyUtils.deleteUserCookie(response);
		}
		*/
		// Redirect to userInfo page.
		response.sendRedirect(request.getContextPath() + "/userInfo");
	}

	protected void RedirectToLoginOnError(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException
	{
		//UserAccount user = new UserAccount();
		//user.setUserName(userName);
		//user.setPassword(password);

		// Store information in request attribute, before forward.
		request.setAttribute("errorString", errorMsg);
		//request.setAttribute("user", user);

		doGet(request, response);
	}
}
