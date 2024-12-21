package com.financeX.model.daoJDBC;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.financeX.model.dao.ExpenseDao;
import com.financeX.model.entities.Expenses;
import com.financeX.services.db.DB;
import com.financeX.services.db.DbException;

public class ExpenseDaoJDBC implements ExpenseDao {

    private Connection conn;

    public ExpenseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Integer userId, Integer monthId, Expenses obj, Integer category) {
        String sql = "INSERT INTO expenses (description_e, value_e, expenses_date, id_month, id_category, id_user) VALUES (?, ?, ? , ?, ?, ?)";

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getDescription());
            st.setBigDecimal(2, obj.getValue());
            st.setDate(3, new java.sql.Date(obj.getDate().getTime()));
            st.setInt(4, monthId);
            st.setInt(5, category);
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
    public void delete(Integer userId, Integer id_expenses) {
        String sql = "DELETE FROM expenses WHERE id_expenses = ? AND id_user = ? ";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id_expenses);
            st.setInt(2, userId);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Integer userId, Expenses obj, Integer Expense_id) {
        String sql = "UPDATE expenses SET description_e = ?, value_e = ?, expenses_date = ? WHERE id_expenses = ? AND id_user = ?";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, obj.getDescription());
            st.setBigDecimal(2, obj.getValue());
            st.setDate(3, new java.sql.Date(obj.getDate().getTime()));
            st.setInt(4, Expense_id);
            st.setInt(5, userId);

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

  @Override
    public List<Expenses> getExpenses(Integer userId, Integer monthId, Integer category) {
        String sql = "SELECT * FROM expenses WHERE id_user = ? AND id_month = ? AND id_category = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, monthId);
            st.setInt(3, category);

            rs = st.executeQuery();
            List<Expenses> expenses = new ArrayList<>();
            
            while (rs.next()) {
                int id = rs.getInt("id_expenses");
                String description = rs.getString("description_e");
                BigDecimal value = rs.getBigDecimal("value_e");
                Date date = rs.getDate("expenses_date");

                Expenses expense = new Expenses();

                expense.setId(id);
                expense.setDescription(description);
                expense.setValue(value);
                expense.setDate(date);

                expenses.add(expense);
            }

            return expenses;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
