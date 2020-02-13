package com.woniu.miaosha.dao;

import com.woniu.miaosha.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface Uss {
    @Select("select * from user")
    List<User> f();
}
