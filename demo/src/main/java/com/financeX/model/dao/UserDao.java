package com.financeX.model.dao;

import com.financeX.model.entities.User;

public interface UserDao {

    void insert(User obj);
    User findById(Integer id);
    Boolean findByUsername(String string);
    Boolean findByPassword(String username, String string);
}
