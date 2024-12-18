package com.financeX.model.dao;

import com.financeX.model.daoJDBC.MonthDaoJDBC;
import com.financeX.model.daoJDBC.UserDaoJDBC;
import com.financeX.services.db.DB;

public class DaoFactory {

    public static UserDao createUserDao(){
        return new UserDaoJDBC(DB.getConnection());
    }

    public static MonthDao createMonthDao(){
        return new MonthDaoJDBC(DB.getConnection());
    }

}
