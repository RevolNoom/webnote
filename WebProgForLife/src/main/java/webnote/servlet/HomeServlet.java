package webnote.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webnote.beans.*;

@WebServlet(urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private NoteDB noteDB =  new NoteDB();
   private UserAccount userInfo = new UserAccount();

   public HomeServlet() {
       super();
   }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		// Handle endpoints
		String action = request.getServletPath();
		switch(action) {
			case "/new":
				try {
					System.out.println("[HomeServlet] - showNewForm");
					showNewForm(request, response);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "/insert":
				try {
					System.out.println("[HomeServlet] - showNewForm");
					insertNote(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
				e.printStackTrace();
				}
				break;
			case "/delete":
				try {
					deleteNote(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "/edit":
				try {
					System.out.println("[HomeServlet] - showEditForm");
					showEditForm(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "/update":
				try {
					System.out.println("[HomeServlet] - showEditForm");
					updateNote(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case "/login":
				try {
					login(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case "/register":
				try {
					register(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case "/list":
				try {
					listNotes(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
				
			default:
				try {
					loginUserAccount(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}
	
	// Default action
	private void listNotes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		System.out.println(userInfo.getUserName());
		List<Note> listNotes = noteDB.selectAllNotes(userInfo.getUserName());
		System.out.println(listNotes.size());
		request.setAttribute("listNotes", listNotes);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
		dispatcher.forward(request, response);
	}
	
	// Show new form
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("helo1");
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/editView.jsp");
		System.out.println("helo");
		dispatcher.forward(request, response);
	}
	
	// Show edit form
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		System.out.println("[HomeServlet] - showEditForm");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("[HomeServlet] - showEditForm");
		Note existingUser = noteDB.selectNote(id, userInfo.getUserName());
		System.out.println(existingUser.getId());
		request.setAttribute("note", existingUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editView.jsp");
		dispatcher.forward(request, response);
	}
	
	// Insert new note
	private void insertNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		System.out.println("[HomeServlet] - IForm");
		String description = request.getParameter("description");
		Note newNote = new Note(description);
		noteDB.insertNote(newNote, userInfo.getUserName());
		response.sendRedirect("list");
	}
	
	// Update note
	private void updateNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		System.out.println("[HomeServlet] - showEditForm");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("[HomeServlet] - showEditForm");
		String description = request.getParameter("description");
		System.out.println("[HomeServlet] - showEditForm");
		Note note = new Note(id, description);
		noteDB.updateNote(note, userInfo.getUserName());
		response.sendRedirect("list");
	}
	
	// Delete note
	private void deleteNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		noteDB.deleteNote(id, userInfo.getUserName());
		response.sendRedirect("list");
	}
	
	// Login 
	private void loginUserAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		dispatcher.forward(request, response);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");


		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			redirectToLoginOnError(request, response, "Required username and password!");
		} 
		
		// Find the user in the DB.
		boolean isExist = noteDB.checkForUserAccount(userName, password);
		System.out.println(isExist);
		// I don't care wtf this resultset has.
		// If there's an entry, that's the right pair of username and password
		if (!isExist)
			redirectToLoginOnError(request, response, "User name or password invalid");	
		else {
			userInfo.setUserName(userName);
			userInfo.setPassword(password);
			
			response.sendRedirect("list");
		}
	}
	
	private void redirectToLoginOnError(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws SQLException, ServletException, IOException
	{
		request.setAttribute("errorString", errorMsg);
		loginUserAccount(request, response);
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			redirectToRegisterOnError(request, response, "Required username and password!");
		} 
		
		// Find the user in the DB.
		boolean isExist = noteDB.checkNameIsExist(userName);
		System.out.println(isExist);

		if (isExist)
			redirectToRegisterOnError(request, response, "This username is exist");	
		else {
			userInfo.setUserName(userName);
			userInfo.setPassword(password);
			
			noteDB.saveUserInfo(userName, password);
			
			response.sendRedirect("list");
		}
	}
	
	private void redirectToRegisterOnError(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws SQLException, ServletException, IOException
	{
		request.setAttribute("errorString", errorMsg);
		registerUserAccount(request, response);
	}
	
	private void registerUserAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");
		dispatcher.forward(request, response);
	}

}
