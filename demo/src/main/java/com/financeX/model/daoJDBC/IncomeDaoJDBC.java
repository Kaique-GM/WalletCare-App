package com.financeX.model.daoJDBC;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<Income> getIncomes(Integer userId, Integer monthId) {
        String sql = "SELECT * FROM incomes WHERE id_user = ? AND id_month = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, monthId);

            rs = st.executeQuery();
            List<Income> incomes = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id_income");
                String description = rs.getString("description_i");
                BigDecimal value = rs.getBigDecimal("value_i");
                Date date = rs.getDate("income_date");

                Income income = new Income();

                income.setId(id);
                income.setDescription(description);
                income.setValue(value);
                income.setDate(date);

                incomes.add(income);
            }

            return incomes;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Income> getAllIncomes(Integer userId, Integer year) {
        String sql = "SELECT * FROM incomes WHERE id_user = ? AND YEAR(income_date) = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, year);

            rs = st.executeQuery();
            List<Income> incomes = new ArrayList<>();

            while (rs.next()) {

                BigDecimal value = rs.getBigDecimal("value_i");

                Income income = new Income();

                income.setValue(value);

                incomes.add(income);
            }

            return incomes;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
