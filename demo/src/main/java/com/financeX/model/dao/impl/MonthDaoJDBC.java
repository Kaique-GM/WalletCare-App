package com.financeX.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import com.financeX.model.dao.MonthDao;
import com.financeX.model.entities.Month;
import com.financeX.model.entities.User;
import com.financeX.services.db.DB;
import com.financeX.services.db.DbException;

public class MonthDaoJDBC implements MonthDao {

    private Connection conn;

    public MonthDaoJDBC(Connection conn) {
        this.conn = conn;
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

    @Override
    public Month findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
