package com.example.llt.controller;

import com.alibaba.fastjson.JSON;
import com.example.llt.dao.ProductDao;
import com.example.llt.entity.Employee;
import com.example.llt.entity.Product;
import com.example.llt.entity.Result;
import com.example.llt.service.ProductService;
import com.example.llt.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(){
        Result result = productService.getAllProducts();
        return JSON.toJSONString(result);
    }

    @PostMapping
    public String addProduct(@RequestBody String productJson){
        String str = URLDecoder.decode(productJson);
        str = str.substring(0, str.length() - 1);
        Product product = JSON.parseObject(str, Product.class);
        product.setId(new SnowflakeIdUtils(1,1,1).nextId());
        Result result = productService.insertProduct(product);
        return JSON.toJSONString(result);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id){
        Result result = productService.deleteProductById(Long.valueOf(id));
        return JSON.toJSONString(result);
    }
}
