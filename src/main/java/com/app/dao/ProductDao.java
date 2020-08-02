package com.app.dao;

import com.app.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {

    int insertProduct(Product product);

    /**
     * 修改商品信息
     * @param productCondition
     * @return
     */
    int updateProduct(Product productCondition);

    /**
     * 获取商品信息 通过Id
     * @param productId
     * @return
     */
    Product selectProductById(int productId);


}
