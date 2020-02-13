package com.woniu.miaosha.serviceimp;

import com.woniu.miaosha.dao.ProductDao;
import com.woniu.miaosha.entity.Product;
import com.woniu.miaosha.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductDao productDao;


    @Override
    public Product findone(int pid) {
        return productDao.findone(pid);
    }

    @Override

    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    @Override
    public void jian(Product product) {
        productDao.jian(product);
    }
}
