package com.financeX.model.dao;

public interface MonthDao {

    Integer getMonthId(String monthName, Integer year, Integer userId);
}
