package com.woniu.miaosha.dao;

import com.woniu.miaosha.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    @Select("select * from user where account=#{account} ")
    User findUser(User user);

}
