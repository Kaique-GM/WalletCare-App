package com.financeX.services;

import com.financeX.model.dao.DaoFactory;
import com.financeX.model.dao.IncomeDao;
import com.financeX.model.entities.Income;

public class IncomeService {

    private IncomeDao dao = DaoFactory.createIncomeDao();

    public void insert(Integer userId, Integer monthId, Income obj){
        dao.insert(userId, monthId, obj);
    }
}
