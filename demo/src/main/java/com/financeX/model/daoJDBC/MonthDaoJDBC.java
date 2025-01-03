package com.financeX.model.daoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financeX.model.dao.MonthDao;
import com.financeX.services.db.DB;
import com.financeX.services.db.DbException;
import com.financeX.utils.Alerts;

import javafx.scene.control.Alert.AlertType;

public class MonthDaoJDBC implements MonthDao {

    private Connection conn;

    public MonthDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Integer getMonthId(String monthName, Integer year, Integer userId) {
        String sql = "SELECT id_month FROM months WHERE month_name = ? AND yr = ? AND id_user = ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, monthName);
            st.setInt(2, year);
            st.setInt(3, userId);

            rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_month");
            } else {
                Alerts.showAlert("Error", null, "No month found for the year " + year, AlertType.ERROR);
                return null;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

}
