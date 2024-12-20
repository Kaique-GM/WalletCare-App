package com.financeX.model.dao;

import com.financeX.model.entities.Income;

public interface IncomeDao {
    void insert(Integer userId, Integer monthId, Income obj);

    void delete(Integer userId, Integer income_id);

    void update(Integer userId, Income obj);

}
