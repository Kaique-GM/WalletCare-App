package com.financeX.model.dao;

import com.financeX.model.entities.Income;

public interface IncomeDao {
    void insert(Integer userId, Integer monthId, Income obj);

    Income findById(Integer id);

    void delete(Integer userId, Income obj);

    void update(Integer userId, Income obj);

}
