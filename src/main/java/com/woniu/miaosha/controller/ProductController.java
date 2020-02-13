package com.woniu.miaosha.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.miaosha.entity.Product;
import com.woniu.miaosha.entity.User;
import com.woniu.miaosha.service.ProductService;
import com.woniu.miaosha.service.UserService;
import com.woniu.miaosha.utils.RedisUtil;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Redisson redisson;
    private Map<String, Object> goods = new HashMap<>();
    private  Map<String,Object> strmap = new HashMap<>();

    private Map<String,Object> changeMaptype(Map<Object,Object> map){
        Map<String,Object> resultmap = new HashMap<>();
        Iterator<Object> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            Object key = iterator.next();
            resultmap.put((String) key,map.get(key));
        }
        return resultmap;
    }

    @RequestMapping("/show")
    public String showlogin(){
        return "login";
    }
    @RequestMapping("/success")
    public String success(){
        return "miaosha";
    }

    @RequestMapping("/all")
    @ResponseBody
    public List<Product> all(){

        List<Product> l = productService.findAll();
        for(Product product:l){
            goods.put(String.valueOf(product.getPid()),product);
        }
        redisUtil.hmset("miaosha",goods,36000);
        return l;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(User user,HttpSession session) throws JsonProcessingException {
        String result="成功";
        User user1=userService.findUser(user);
        if(user1==null){
            result ="账号不存在";
            result=new ObjectMapper().writeValueAsString(result);
            return result;
        }else {
            if(!user1.getPassword().equals(user.getPassword())){
                result ="密码不对";
                result=new ObjectMapper().writeValueAsString(result);
                return result;
            }
        }
        result=new ObjectMapper().writeValueAsString(result);
        session.setAttribute("name",user.getAccount());
        return result;
    }
    @RequestMapping("/mai")
    public String su(String pid, HttpSession httpSession){
        httpSession.setAttribute("goods",pid);
        System.out.println(pid);
        return "success";
    }
    @RequestMapping("/ll")
    @ResponseBody
    public Product ll(HttpSession session){
        String pid=session.getAttribute("goods").toString();
        Map<Object, Object> map=redisUtil.hmget("miaosha");
        strmap = changeMaptype(map);
        Product product = (Product) strmap.get(pid);
        return product;
    }
    @RequestMapping("/miao")
    @ResponseBody
    public String z(Product product){
        RLock rLock = redisson.getLock(String.valueOf(product.getPid()));
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            Map<Object, Object> p=redisUtil.hmget("miaosha");
            strmap= changeMaptype(p);
            Product product2 = (Product) strmap.get(String.valueOf(product.getPid()));
            if (product2.getCount()>product.getCount()){
                product2.setCount(product2.getCount()-product.getCount());
                redisUtil.del("miaosha");
                redisUtil.hmset("miaosha",strmap);
                productService.jian(product);
                return "秒杀成功，感谢您的参与";
            }else {
                return "库存不足，下次早点来哦";
            }
        }
        finally {
            rLock.unlock();
        }

    }

}
