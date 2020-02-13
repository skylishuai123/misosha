package com.woniu.miaosha.controller;

import com.woniu.miaosha.entity.User;
import com.woniu.miaosha.service.UssService;
import com.woniu.miaosha.serviceimp.UssImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private UssService ussService;
    @RequestMapping("/s")
    public List<User> ss(){
        return ussService.f();
    }
}
