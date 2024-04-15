package net.etfbl.ip.gym_admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etfbl.ip.gym_admin.dto.User;



public class UserDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_BY_USERNAME_OR_EMAIL = "SELECT * FROM user WHERE username=? OR email=?";
	private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM user WHERE username = ?";
	private static final String SQL_INSERT = "INSERT INTO user (username, password, first_name, last_name, city, avatar, email, activated, type) VALUES (?,?,?,?,?,?,?,?,?)";
	
	public static User selectByUsernameOrEmail(String username, String email){
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username, email};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_USERNAME_OR_EMAIL, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("city"), rs.getString("avatar"), rs.getString("email"), rs.getBoolean("activated"), rs.getInt("type"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return user;
	}
	
	public static boolean isUserNameUsed(String username) {
		boolean result = true;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_FIND_BY_USERNAME, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				result = false;
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	public static boolean insert(User user) {
		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCity(), user.getAvatar(), user.getEmail(), user.getActivated(), user.getType() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			if(pstmt.getUpdateCount()>0) {
				result = true;
			}
			if (generatedKeys.next())
				user.setId(generatedKeys.getInt(1));
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}

}
