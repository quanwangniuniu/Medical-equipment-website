package com.example.llt.service;

import com.example.llt.dao.ProductDao;
import com.example.llt.entity.Product;
import com.example.llt.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Result getAllProducts(){
        ArrayList<Product> productList = productDao.getAllProducts();
        return Result.success(productList);
    }

    public Result insertProduct(Product product){
        Integer result = productDao.insertProduct(product);
        return Result.success(result);
    }

    public Result deleteProductById(long id){
        Integer result = productDao.deleteProductById(id);
        return Result.success(result);
    }
}
