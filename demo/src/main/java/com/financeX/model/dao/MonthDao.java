package com.financeX.model.dao;

import com.financeX.model.entities.Month;
import com.financeX.model.entities.User;

public interface MonthDao {

    void insert12Months(User obj);
    Month findById(Integer id);
}
