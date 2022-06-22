package webnote;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Servlet implementation class NoteServlet
 */
public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append(GetNoteServletContent());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected String GetNoteServletContent()
	{
		return "<html>" + GetStyle() + AddTitleTextBox() + AddNoteContentTextBox() + AddSaveButton() + "</html>";
	}

	protected String GetStyle()
	{
		InputStream style = this.getClass().getResourceAsStream("rss/style.html");
		String result = new String();
		try
		{
			Scanner s = new Scanner(style);
			while (s.hasNextLine())
				result += s.nextLine();
		}
		catch (Exception e)
		{
			System.out.println("NoteServlet failed to get style");
		}
		return result;
	}
	
	protected String AddTitleTextBox()
	{
		return "<div><textarea class='note-title-textbox' placeholder='Title'></textarea></div>";
	}

	protected String AddNoteContentTextBox()
	{
		return "<div><textarea class='note-content-textbox' placeholder='Description...'></textarea></div>";
	}
	
	protected String AddSaveButton()
	{
		return "<button class='save-button'>SAVE</button>";
	}
}

