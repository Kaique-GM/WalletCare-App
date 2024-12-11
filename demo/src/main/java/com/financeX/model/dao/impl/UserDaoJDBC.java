package com.financeX.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financeX.model.dao.UserDao;
import com.financeX.model.entities.User;
import com.financeX.services.db.DB;
import com.financeX.services.db.DbException;

public class UserDaoJDBC implements UserDao {

    private Connection conn;

    public UserDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(User obj) {
        String sql = "INSER INTO users (username, password_hash) VALUES (?, ?)";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getUsername());
            st.setString(2, obj.getPassword());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        obj.setId(id);
                    }
                    DB.closeResultSet(rs);
                }

            } else {
                throw new DbException("Unexpected error: No rows were affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public User findById(Integer id) {
        if (id == null || id < 0) {
            return null;
        }

        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                User user = instantiateUser(rs);
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public Boolean findByUsername(String string) {

        if (string == null) {
            return false;
        }

        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, string);
            rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Boolean findByPassword(String string) {

        if (string == null) {
            return false;
        }

        String sql = "SELECT * FROM users WHERE password_hash = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, string);
            rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private User instantiateUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        return user;
    }
}
