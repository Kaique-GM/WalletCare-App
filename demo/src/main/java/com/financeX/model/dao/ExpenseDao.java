package com.financeX.model.dao;

import com.financeX.model.entities.Expenses;

public interface ExpenseDao {
    void insert(Integer userId, Integer monthId, Expenses obj, Integer category);

    void delete(Integer userId, Integer Expense_id);

    void update(Integer userId, Expenses obj, Integer Expense_id);

}
