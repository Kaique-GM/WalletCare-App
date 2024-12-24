package com.financeX.model.dao;

import com.financeX.model.entities.User;

public interface UserDao {

    void insert(User obj);

    User findById(Integer id);

    Boolean existsByUsername(String string);

    Boolean existsByPassword(String username, String string);

    Integer findIdByUsername(String username);

    void insert12Months(User obj);

    void insertYear(Integer year, Integer userId);
}
