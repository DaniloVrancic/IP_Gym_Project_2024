package net.etfbl.ip.gym_admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etfbl.ip.gym_admin.dto.FitnessProgramType;

public class FitnessProgramTypeDAO {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM fitness_program_type WHERE id=?";
    private static final String SQL_INSERT = "INSERT INTO fitness_program_type (name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE fitness_program_type SET name=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM fitness_program_type WHERE id=?";

    public static FitnessProgramType selectById(int id) {
        FitnessProgramType programType = null;
        Connection connection = null;
        ResultSet rs = null;
        Object values[] = {id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_ID, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                programType = new FitnessProgramType();
                programType.setId(rs.getInt("id"));
                programType.setName(rs.getString("name"));
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return programType;
    }

    public static boolean insert(FitnessProgramType programType) {
        boolean result = false;
        Connection connection = null;
        ResultSet generatedKeys = null;
        Object values[] = {programType.getName()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
            pstmt.executeUpdate();
            generatedKeys = pstmt.getGeneratedKeys();
            if (pstmt.getUpdateCount() > 0) {
                result = true;
            }
            if (generatedKeys.next())
                programType.setId(generatedKeys.getInt(1));
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

    public static boolean update(FitnessProgramType programType) {
        boolean result = false;
        Connection connection = null;
        Object values[] = {programType.getName(), programType.getId()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPDATE, false, values);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
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
        Object values[] = {id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE, false, values);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
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