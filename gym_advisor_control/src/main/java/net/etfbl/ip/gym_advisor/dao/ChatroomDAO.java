package net.etfbl.ip.gym_advisor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.etfbl.ip.gym_advisor.dao.DAOUtil;
import net.etfbl.ip.gym_advisor.dto.Chatroom;

public class ChatroomDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM chatroom";
	private static final String SQL_SELECT_ALL_BY_RECEIVER = "SELECT * FROM chatroom WHERE receiver_id=?";
	private static final String SQL_SELECT_ALL_BY_SENDER = "SELECT * FROM chatroom WHERE sender_id=?";
	private static final String SQL_SELECT_ALL_BY_READ = "SELECT * FROM chatroom WHERE read_msg=?"; //true or false
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM user WHERE id=?";
	
	
	private static final String SQL_DELETE = "DELETE FROM chatroom WHERE id=?";
	private static final String SQL_MARK_AS_READ = "UPDATE chatroom SET read_msg=? WHERE id=?";
	
	public static List<Chatroom> selectAllReceivedMessages(Integer receiverId)
	{
		List<Chatroom> listOfUsers = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {receiverId}; //Will search for messages with received messages;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_ALL_BY_RECEIVER, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				listOfUsers.add(new Chatroom(rs.getInt("id"), rs.getString("time_of_send"), rs.getString("text"), rs.getBoolean("read_msg"), rs.getInt("receiver_id"), rs.getInt("sender_id")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return listOfUsers;
	}
	
	public static boolean markAsRead(Chatroom chatMessage) {
	    boolean result = false;
	    Connection connection = null;
	    Object values[] = { true, chatMessage.getId() };
	    try {
	        connection = connectionPool.checkOut();
	        PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_MARK_AS_READ, false, values);
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
