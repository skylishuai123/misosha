package com.woniu.miaosha.service;

import com.woniu.miaosha.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product findone(int pid);
    List<Product> findAll();
    void jian (Product product);
}
