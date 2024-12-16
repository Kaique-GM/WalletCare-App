package com.financeX.services;

import com.financeX.model.dao.DaoFactory;
import com.financeX.model.dao.UserDao;
import com.financeX.model.entities.User;

public class UserService {

    private UserDao dao = DaoFactory.createUserDao();

    public void saveOrUpdate(User obj) {
        if (obj.getId() == null) {
            if (dao.existsByUsername(obj.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + obj.getUsername());
            }
            dao.insert(obj);
        } /*else {
            dao.update(obj);
        }*/
    }

    public Boolean userExists (String string){
        if (dao.existsByUsername(string)) {
            return true;
        }
        return false;
    }

    public Boolean passwordMatches (String username, String string){
        if (dao.existsByPassword(username, string)) {
            return true;
        }
        return false;
    }

    public Integer findIdByUsername(String username){
        int id = dao.findIdByUsername(username);
        return id;
    }
}
