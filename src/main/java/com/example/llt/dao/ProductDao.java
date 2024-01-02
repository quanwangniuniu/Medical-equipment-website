package com.example.llt.dao;

import com.example.llt.entity.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductDao {
    @Select("select * from product")
    ArrayList<Product> getAllProducts();

    @Insert("insert into product values(#{id},#{name},#{type},#{price},#{purchase_price}," +
            "#{manufacturer},#{specification},#{efficacy},#{instruction},#{side_effect})")
    Integer insertProduct(Product product);

    @Delete("delete from product where id = #{id}")
    Integer deleteProductById(long id);
}
