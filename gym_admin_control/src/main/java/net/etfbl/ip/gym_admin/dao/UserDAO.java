package net.etfbl.ip.gym_admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.etfbl.ip.gym_admin.dto.User;



public class UserDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM user WHERE username=?";
	private static final String SQL_SELECT_BY_USERNAME_AND_TYPE	= "SELECT * FROM user WHERE username=? AND type=?";
	private static final String SQL_SELECT_USERS_AND_ADVISORS	= "SELECT * FROM user WHERE type=? OR type=?";
	private static final String SQL_SELECT_BY_TYPE	= "SELECT * FROM user WHERE type=?";
	private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM user WHERE email=?";
	private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM user WHERE username = ?";
	private static final String SQL_INSERT = "INSERT INTO user (username, password, first_name, last_name, city, avatar, email, activated, type) VALUES (?,?,?,?,?,?,?,?,?)";

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM user WHERE id=?";
	private static final String SQL_UPDATE = "UPDATE user SET username=?, password=?, first_name=?, last_name=?, city=?, avatar=?, email=?, activated=?, type=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM user WHERE id=?";

	public static User selectUsersByUsername(String username){
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username, 3}; // (3 = will select regular users)
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_USERNAME_AND_TYPE, false, values);
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

	public static User selectAdminByUsername(String username){
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username, 1}; // the one represents the type (1 = ADMIN)
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_USERNAME_AND_TYPE, false, values);
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
	
	public static List<User> selectAllUsersAndAdvisors(){
		List<User> listOfUsers = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {2,3}; //Will search for type=3 users;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_USERS_AND_ADVISORS, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				listOfUsers.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("city"), rs.getString("avatar"), rs.getString("email"), rs.getBoolean("activated"), rs.getInt("type")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return listOfUsers;
	}

	public static List<User> selectAllRegularUsers()
	{
		List<User> listOfUsers = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {3}; //Will search for type=3 users;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_TYPE, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				listOfUsers.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("city"), rs.getString("avatar"), rs.getString("email"), rs.getBoolean("activated"), rs.getInt("type")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return listOfUsers;
	}

	public static User selectByEmail(String email){
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {email};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_EMAIL, false, values);
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

	public static User selectById(int id) {
	    User user = null;
	    Connection connection = null;
	    ResultSet rs = null;
	    Object values[] = {id};
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
	                SQL_SELECT_BY_ID, false, values);
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
			if (generatedKeys.next()) {
				user.setId(generatedKeys.getInt(1));
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}


	public static boolean update(User user) {
	    boolean result = false;
	    Connection connection = null;
	    Object values[] = { user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCity(), user.getAvatar(), user.getEmail(), user.getActivated(), user.getType(), user.getId() };
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPDATE, false, values);
	        int rowsAffected = pstmt.executeUpdate();
	        if(rowsAffected > 0) {
	            result = true;
	        }
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        connectionPool.checkIn(connection);
	    }
	    return result;
	}

	public static boolean remove(int id) {
	    boolean result = false;
	    Connection connection = null;
	    Object values[] = { id };
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE, false, values);
	        int rowsAffected = pstmt.executeUpdate();
	        if(rowsAffected > 0) {
	            result = true;
	        }
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        connectionPool.checkIn(connection);
	    }
	    return result;
	}


}
