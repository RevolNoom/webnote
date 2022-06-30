package webnote;

import java.sql.*;

public class SQLite 
{
	static public SQLite singleton()
	{
		if (_sqlite == null)
			_sqlite = new SQLite();
		
		return _sqlite;
	}
	
	private SQLite()
	{
		try
		{
			_c = DriverManager.getConnection("jdbc:sqlite:db/onlineshop.db");
	    } 
		catch ( Exception e ) 
		{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
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
	         System.exit(0);
	    }
		return null;
	}

	static private SQLite _sqlite;
	
	private Connection _c;
}
