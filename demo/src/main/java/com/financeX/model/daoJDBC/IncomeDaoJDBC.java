package com.financeX.model.daoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financeX.model.dao.IncomeDao;
import com.financeX.model.entities.Income;
import com.financeX.services.db.DB;
import com.financeX.services.db.DbException;

public class IncomeDaoJDBC implements IncomeDao {

    private Connection conn;

    public IncomeDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Integer userId, Integer monthId, Income obj) {
        String sql = "INSERT INTO incomes (description_i, value_i, income_date, id_month, id_category, id_user) VALUES (?, ?, ? , ?, ?, ?)";

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getDescription());
            st.setBigDecimal(2, obj.getValue());
            st.setDate(3, new java.sql.Date(obj.getDate().getTime()));
            st.setInt(4, monthId);
            st.setInt(5, 1);
            st.setInt(6, userId);

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
    public void delete(Integer userId, Integer income_id) {
        String sql = "DELETE FROM incomes WHERE id_income = ? AND id_user = ? ";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, income_id);
            st.setInt(2, userId);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Integer userId, Income obj, Integer income_id) {
        String sql = "UPDATE incomes SET description_i = ?, value_i = ?, income_date = ? WHERE id_income = ? AND id_user = ?";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, obj.getDescription());
            st.setBigDecimal(2, obj.getValue());
            st.setDate(3, new java.sql.Date(obj.getDate().getTime()));
            st.setInt(4, income_id);
            st.setInt(5, userId);

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

}
