package com.financeX.services;

import com.financeX.model.dao.DaoFactory;
import com.financeX.model.dao.IncomeDao;
import com.financeX.model.entities.Income;

public class IncomeService {

    private IncomeDao dao = DaoFactory.createIncomeDao();

    public void insert(Integer userId, Integer monthId, Income obj) {
        dao.insert(userId, monthId, obj);
    }

    public void delete(Integer userId, Integer income_id) {
        dao.delete(userId, income_id);
    }

    public void update(Integer userId, Income obj, Integer income_id) {
        dao.update(userId, obj, income_id);
    }
}
