package webnote.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webnote.beans.UserAccount;

public class DBUtils {
	
	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		String sql = "select USER_NAME, GENDER, PASSWORD"
				+ "from user_account"
				+ "where USER_NAME = ? and PASSWORD = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next()) {
			String gender = rs.getString("GENDER");
			UserAccount usr = new UserAccount();
			usr.setUserName(userName);
			usr.setGender(gender);
			usr.setPassword(password);
			return usr;
		}
		return null;
	}
	
	public static UserAccount findUser(Connection conn, String userName) throws SQLException {
		String sql = "select USER_NAME, GENDER, PASSWORD"
				+ "from user_account"
				+ "where USER_NAME = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next()) {
			String gender = rs.getString("GENDER");
			String password = rs.getString("PASSWORD");
			UserAccount usr = new UserAccount();
			usr.setUserName(userName);
			usr.setGender(gender);
			usr.setPassword(password);
			return usr;
		}
		return null;
	}
	
}
