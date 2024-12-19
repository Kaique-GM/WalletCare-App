package com.financeX.model.daoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
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
    public Boolean existsByUsername (String string) {

        if (string == null) {
            return false;
        }

        String sql = "SELECT id FROM users WHERE username = ?";
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
    public Boolean existsByPassword(String username, String string) {

        if (username == null || username.isBlank() || string == null || string.isBlank()) {
            return false;
        }

        String sql = "SELECT id FROM users WHERE password_hash = ? and username = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, string);
            st.setString(2, username);
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
    public Integer findIdByUsername(String username) {
        if (username == null) {
            return null;
        }

        String sql = "SELECT id FROM users WHERE username = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
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
    public void insert12Months(User obj) {

        PreparedStatement st = null;
        String sql = "INSERT INTO months (month_name, yr, id_user) VALUES (?, ?, ?)";

        int user_id = obj.getId();
        int year = LocalDate.now().getYear();
        String months[] = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };

        try {
            for (String month : months) {
                st = conn.prepareStatement(sql);

                st.setString(1, month);
                st.setInt(2, year);
                st.setInt(3, user_id);

                st.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(st);
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
