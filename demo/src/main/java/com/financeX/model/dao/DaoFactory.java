package com.financeX.model.dao;

import com.financeX.model.dao.impl.UserDaoJDBC;
import com.financeX.services.db.DB;

public class DaoFactory {

    public static UserDao creatUserDao(){
        return new UserDaoJDBC(DB.getConnection());
    }

}
