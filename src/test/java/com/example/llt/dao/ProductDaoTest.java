package com.example.llt.dao;

import com.example.llt.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductDaoTest {
    @Autowired
    ProductDao productDao;

    @Test
    void getAllProducts() {
        ArrayList<Product> products = productDao.getAllProducts();
        products.forEach(System.out::println);

    }

    @Test
    void insertProduct() {
        Product product = new Product(12345L, null, null, null, null, null,
                null, null, null, null);
        productDao.insertProduct(product);
    }

    @Test
    void deleteProduct(){
        Integer integer = productDao.deleteProductById(12345L);
    }
}