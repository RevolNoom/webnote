package webnote.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webnote.beans.Note;

public class NoteDB {
	private static Connection connect;
	
	private static final String INSERT_NOTES_SQL = "INSERT INTO Note (description, dateCreated, userName) VALUES (?, ?, ?)";
	private static final String SELECT_NOTE_BY_ID = "SELECT id, description, dateCreated FROM Note WHERE id = ? and userName = ?;";
	private static final String SELECT_ALL_NOTES = "SELECT * FROM Note where userName = ?;";
	private static final String UPDATE_NOTE_SQL = "UPDATE Note SET description = ? WHERE id = ? and userName = ?;";
	private static final String DELETE_NOTE_SQL = "DELETE from Note WHERE id = ? and userName = ?;";
	private static final String CHECK_ACCOUNT_SQL = "SELECT count(*) as count FROM User where USERNAME = ? and PASSWORD = ?;";  
	private static final String CHECK_NAME_EXIST_SQL = "SELECT count(*) as count FROM User where USERNAME = ?;";
	private static final String INSERT_USER_INFO_SQL = "INSERT INTO User (userName, password) VALUES (?, ?)";
	
	  /**
	   * Establish connection to the database.

	   * @return connect: A connection to the database.
	   */
	  public static Connection getConnection() {
	    try {
	     Class.forName("org.sqlite.JDBC"); // Dynamically register JDBC class
		 String SERVER_HOME = System.getProperty("user.dir");
		 String databaseLocation = SERVER_HOME + "/db/webnote.db";
		 System.out.println("Looking for database at: " + databaseLocation);
		 connect = DriverManager.getConnection("jdbc:sqlite:" + databaseLocation);
	      System.out.println("Connect database succesfully");
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    return connect;
	  }
    
	// DB operation methods
		// Insert note
		public void insertNote(Note note, String userName) throws SQLException{
			System.out.println(INSERT_NOTES_SQL);
			try(Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(INSERT_NOTES_SQL)){
				System.out.println("hello");
				preparedStatement.setString(1, note.getDescription());
				System.out.println("hello");
				preparedStatement.setString(2, java.sql.Date.valueOf(java.time.LocalDate.now()).toString());
				preparedStatement.setString(3, userName);
				preparedStatement.executeUpdate();
			}catch(SQLException e) {
				printSQLException(e);
			}
		}
		
		// Select note by id
		public Note selectNote(int id, String userName) {
			Note note = null;
			try(Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_NOTE_BY_ID)){
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, userName);
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					String description = rs.getString("description");
					String dateCreated = rs.getString("dateCreated");
					note = new Note(id, description, dateCreated);
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
			return note;
		}
		
		// Select all notes
		public List<Note> selectAllNotes(String userName) {
			List<Note> notes = new ArrayList<>();
			System.out.println(userName);
			try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NOTES)) {
				preparedStatement.setString(1, userName);
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("id");
					String description = rs.getString("description");
					String dateCreated = rs.getString("dateCreated");
					System.out.println(id + " " + description + " " + dateCreated);
					notes.add(new Note(id, description, dateCreated));
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
			return notes;
		}
		
		// Update note
		public boolean updateNote(Note note, String userName) throws SQLException {
			boolean rowUpdated;
			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE_SQL);) {
				
				System.out.println("updated Note:"+preparedStatement);
				preparedStatement.setString(1, note.getDescription());
				preparedStatement.setInt(2, note.getId());
				preparedStatement.setString(3, userName);

				rowUpdated = preparedStatement.executeUpdate() > 0;
			}
			return rowUpdated;
		}
		
		// Delete note
		public boolean deleteNote(int id, String userName) throws SQLException {
			boolean rowDeleted;
			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NOTE_SQL);) {
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, userName);
				rowDeleted = preparedStatement.executeUpdate() > 0;
			}
			return rowDeleted;
		}

		// Method to print errors
		private void printSQLException(SQLException ex) {
			for (Throwable e : ex) {
				if(e instanceof SQLException) {
					e.printStackTrace(System.err);
					System.err.println("SQLState : " + ((SQLException) e).getSQLState());
					System.err.println("Error Code : " + ((SQLException) e).getErrorCode());
					System.err.println("Message : " + e.getMessage());
					Throwable t = ex.getCause();
					while(t != null) {
						System.err.println("Cause : " + t);
						t = t.getCause();
					}
				}
			}
		}  
		
		public boolean checkForUserAccount(String userName, String password) throws SQLException{
			boolean isExist;
			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ACCOUNT_SQL);) {
				preparedStatement.setString(1, userName);
				preparedStatement.setString(2, password);
				ResultSet rs = preparedStatement.executeQuery();
				int count = rs.getInt("count");
				
				if (count > 0) {
					isExist = true;
				}
				else {
					isExist = false;
				}
			}
			return isExist;
		}
		
		public boolean checkNameIsExist(String userName) throws SQLException {
			boolean isExist;
			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(CHECK_NAME_EXIST_SQL);) {
				preparedStatement.setString(1, userName);
				ResultSet rs = preparedStatement.executeQuery();
				int count = rs.getInt("count");
				
				if (count >= 1) {
					isExist = true;
				}
				else {
					isExist = false;
				}
			}
			return isExist;
		}
		
		public void saveUserInfo(String userName, String password) {
			try(Connection conn = getConnection();
					PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER_INFO_SQL)){
					System.out.println("hello");
					preparedStatement.setString(1, userName);
					System.out.println("hello");
					preparedStatement.setString(2, password);
					preparedStatement.executeUpdate();
				}catch(SQLException e) {
					printSQLException(e);
				}
		}
}
