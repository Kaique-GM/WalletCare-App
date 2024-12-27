package com.financeX.model.dao;

import java.util.List;

import com.financeX.model.entities.Expenses;

public interface ExpenseDao {
    void insert(Integer userId, Integer monthId, Expenses obj, Integer category);

    void delete(Integer userId, Integer Expense_id);

    void update(Integer userId, Expenses obj, Integer Expense_id);

    List<Expenses> getExpenses(Integer userId, Integer monthId, Integer category);

    List<Expenses> getAllExpenses(Integer userId, Integer category, Integer year);

}
