package com.financeX.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financeX.model.dao.UserDao;
import com.financeX.model.entities.User;
import com.financeX.services.db.DbException;

public class UserDaoJDBC implements UserDao {

    private Connection conn;

    public UserDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(User obj) {
        String sql = "INSER INTO users (username, password_hash) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, obj.getUsername());
            st.setString(2, obj.getPassword());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        obj.setId(id);
                    }
                }
            } else {
                throw new DbException("Unexpected error: No rows were affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public User findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
