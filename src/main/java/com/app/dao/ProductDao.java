package com.app.dao;

import com.app.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {

    int insertProduct(Product product);

}
