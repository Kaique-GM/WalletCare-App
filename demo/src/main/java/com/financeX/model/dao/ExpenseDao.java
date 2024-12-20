package com.financeX.model.dao;

import com.financeX.model.entities.Expenses;

public interface ExpenseDao {
    void insert(Integer userId, Integer monthId, Expenses obj);

    Expenses findById(Integer id);

    void delete(Integer userId, Expenses obj);

    void update(Integer userId, Expenses obj);

}
