package com.woniu.miaosha.serviceimp;

import com.woniu.miaosha.dao.Uss;
import com.woniu.miaosha.entity.User;
import com.woniu.miaosha.service.UssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UssImp implements UssService {
    @Autowired
    private Uss uss;

    @Cacheable("ddd")
    @Override
    public List<User> f() {
        return uss.f();
    }
}
