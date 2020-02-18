package com.woniu.miaosha.serviceimp;

import com.woniu.miaosha.dao.UserDao;
import com.woniu.miaosha.entity.User;
import com.woniu.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUser(User user) {
        return userDao.findUser(user);
    }

    @Override
    public User findPremession(String account) {
        return userDao.findPremession(account);
    }


}
