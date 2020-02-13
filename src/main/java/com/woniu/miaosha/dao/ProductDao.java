package com.woniu.miaosha.dao;

import com.woniu.miaosha.entity.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductDao {
    Product findone(int pid);
    List<Product> findAll();
    void jian (Product product);
}
