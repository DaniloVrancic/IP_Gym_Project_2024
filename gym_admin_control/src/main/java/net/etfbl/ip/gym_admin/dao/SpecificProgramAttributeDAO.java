package net.etfbl.ip.gym_admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.etfbl.ip.gym_admin.dto.SpecificProgramAttribute;

public class SpecificProgramAttributeDAO {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM specific_program_attribute WHERE id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM specific_program_attribute";
    private static final String SQL_SELECT_BY_FITNESS_PROGRAM_TYPE_ID = "SELECT * FROM specific_program_attribute WHERE fitness_program_type_id=?";
    private static final String SQL_INSERT = "INSERT INTO specific_program_attribute (attribute_name, fitness_program_type_id) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE specific_program_attribute SET attribute_name=?, fitness_program_type_id=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM specific_program_attribute WHERE id=?";

    public static SpecificProgramAttribute selectById(int id) {
        SpecificProgramAttribute attribute = null;
        Connection connection = null;
        ResultSet rs = null;
        Object values[] = {id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_ID, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                attribute = new SpecificProgramAttribute();
                attribute.setId(rs.getInt("id"));
                attribute.setAttributeName(rs.getString("attribute_name"));
                attribute.setProgramType(rs.getInt("fitness_program_type_id"));
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return attribute;
    }

    public static List<SpecificProgramAttribute> selectAllAttributes() {
        List<SpecificProgramAttribute> allAttributes = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        Object values[] = {};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SpecificProgramAttribute attribute = new SpecificProgramAttribute();
                attribute.setId(rs.getInt("id"));
                attribute.setAttributeName(rs.getString("attribute_name"));
                attribute.setProgramType(rs.getInt("fitness_program_type_id"));
                allAttributes.add(attribute);
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return allAttributes;
    }

    public static List<SpecificProgramAttribute> selectByFitnessProgramTypeId(int programTypeId) {
        List<SpecificProgramAttribute> attributes = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        Object values[] = {programTypeId};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_FITNESS_PROGRAM_TYPE_ID, false, values);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SpecificProgramAttribute attribute = new SpecificProgramAttribute();
                attribute.setId(rs.getInt("id"));
                attribute.setAttributeName(rs.getString("attribute_name"));
                attribute.setProgramType(rs.getInt("fitness_program_type_id"));
                attributes.add(attribute);
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return attributes;
    }

    public static boolean insert(SpecificProgramAttribute attribute) {
        boolean result = false;
        Connection connection = null;
        ResultSet generatedKeys = null;
        Object values[] = {attribute.getAttributeName(), attribute.getProgramType()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
            pstmt.executeUpdate();
            generatedKeys = pstmt.getGeneratedKeys();
            if (pstmt.getUpdateCount() > 0) {
                result = true;
            }
            if (generatedKeys.next()) {
                attribute.setId(generatedKeys.getInt(1));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

    public static boolean update(SpecificProgramAttribute attribute) {
        boolean result = false;
        Connection connection = null;
        Object values[] = {attribute.getAttributeName(), attribute.getProgramType(), attribute.getId()};
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
