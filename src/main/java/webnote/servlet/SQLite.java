package webnote.servlet;

import java.sql.*;
import java.util.*;

public class SQLite 
{	
	
	static public  Connection get(String databaseName)
	{
		if (_m.get(databaseName) == null)
		{
			Connection c = SetupNewConnection(databaseName);
			if (c != null)
				_m.put(databaseName, c);
		}
		return _m.get(databaseName);
	}
	// Create a handle to access database at $HOME/db/@databaseName
	// Don't know where your $HOME is? 
	// I got chu, there'll be error message on console
	// Go and see where SQLite is looking for the db.
	static private Connection SetupNewConnection(String databaseName)
	{
		try
		{
			Class.forName("org.sqlite.JDBC"); // Dynamically register JDBC class
			String SERVER_HOME = System.getProperty("user.dir");
			String databaseLocation = SERVER_HOME + "/db/" + databaseName;
		    System.out.println("Looking for database at: " + databaseLocation);
			Connection c = DriverManager.getConnection("jdbc:sqlite:" + databaseLocation);
		    System.out.println("Opened database successfully.");
		    return c;
	    } 
		catch ( Exception e ) 
		{
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
		return null;
	}

	static private Map<String, Connection> _m = new HashMap<String, Connection>();
}
