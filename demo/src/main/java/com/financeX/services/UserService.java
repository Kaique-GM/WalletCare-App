package com.financeX.services;

import com.financeX.model.dao.DaoFactory;
import com.financeX.model.dao.UserDao;
import com.financeX.model.entities.User;

public class UserService {

    private UserDao dao = DaoFactory.createUserDao();

    public void saveOrUpdate(User obj) {
        if (obj.getId() == null) {
            if (dao.findByUsername(obj.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + obj.getUsername());
            }
            dao.insert(obj);
        } /*else {
            dao.update(obj);
        }*/
    }

    public Boolean userExists (String string){
        if (dao.findByUsername(string)) {
            return true;
        }
        return false;
    }

    public Boolean passwordMatches (String username, String string){
        if (dao.findByPassword(username, string)) {
            return true;
        }
        return false;
    }
}
