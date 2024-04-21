package net.etfbl.ip.gym_advisor.dao;

public class ChatroomDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM chatroom";
	private static final String SQL_SELECT_ALL_BY_RECEIVER = "SELECT * FROM chatroom WHERE receiver_id=?";
	private static final String SQL_SELECT_ALL_BY_SENDER = "SELECT * FROM chatroom WHERE sender_id=?";
	private static final String SQL_SELECT_ALL_BY_READ = "SELECT * FROM chatroom WHERE read_msg=?"; //true or false
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM user WHERE id=?";
	
	
	private static final String SQL_DELETE = "DELETE FROM chatroom WHERE id=?";
}
