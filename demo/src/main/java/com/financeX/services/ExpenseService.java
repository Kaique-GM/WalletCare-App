package com.financeX.services;

import java.util.List;

import com.financeX.model.dao.DaoFactory;
import com.financeX.model.dao.ExpenseDao;
import com.financeX.model.entities.Expenses;

public class ExpenseService {

    private ExpenseDao dao = DaoFactory.createExpenseDao();

    public void insert(Integer userId, Integer monthId, Expenses obj, Integer category) {
        dao.insert(userId, monthId, obj, category);
    }

    public void delete(Integer userId, Integer expense_id) {
        dao.delete(userId, expense_id);
    }

    public void update(Integer userId, Expenses obj, Integer expense_id) {
        dao.update(userId, obj, expense_id);
    }

    public List<Expenses> getExpenses(Integer userId, Integer monthId, Integer category) {
        return dao.getExpenses(userId, monthId, category);
    }

    public List<Expenses> getAllExpenses(Integer userId, Integer category, Integer year) {
        return dao.getAllExpenses(userId, category, year);
    }

}
