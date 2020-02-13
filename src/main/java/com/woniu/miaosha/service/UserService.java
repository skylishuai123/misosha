package com.woniu.miaosha.service;

import com.woniu.miaosha.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findUser(User user);
}
