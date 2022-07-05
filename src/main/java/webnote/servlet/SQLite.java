package webnote.servlet;

import java.sql.*;

public class SQLite 
{	
	// Create a handle to access database at $HOME/db/@databaseName
	// Don't know where your $HOME is? 
	// I got chu, there'll be error message on console
	// Go and see where SQLite is looking for the db.
	public SQLite(String databaseName)
	{
		try
		{
			_c = DriverManager.getConnection("jdbc:sqlite:db/" + databaseName);
	    } 
		catch ( Exception e ) 
		{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
	    System.out.println("Opened database successfully");
	}

	public ResultSet Query(String query)
	{
		try
		{
	        Statement s = _c.createStatement();
	        ResultSet rs = s.executeQuery(query);
	        return rs;
		}
		catch ( Exception e ) 
		{
	         System.err.println( "Query failed - " + e.getClass().getName() + ": " + e.getMessage() );
	    }
		return null;
	}
	
	// Update the database, like "Create table", "Insert" or something
	public void Update(String query)
	{
		try
		{
	        Statement s = _c.createStatement();
	        s.executeUpdate(query);	
		}
		catch ( Exception e ) 
		{
	         System.err.println( "Query failed - " + e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	    }
	}

	
	private Connection _c;
}
