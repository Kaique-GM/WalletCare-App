package com.financeX.services;

import com.financeX.model.dao.DaoFactory;
import com.financeX.model.dao.MonthDao;
import com.financeX.model.entities.User;

public class MonthService {

    private MonthDao dao = DaoFactory.createMonthDao();

    public Integer getMonthId(String monthName, Integer year, Integer userId){
        return dao.getMonthId(monthName, year, userId);
    }
}
