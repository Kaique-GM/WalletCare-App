package com.financeX.services;

import com.financeX.model.dao.DaoFactory;
import com.financeX.model.dao.MonthDao;
import com.financeX.model.entities.User;

public class MonthService {

    private MonthDao dao = DaoFactory.createMonthDao();

    public void insert12Months(User obj){
       dao.insert12Months(obj);
        
    }
}
