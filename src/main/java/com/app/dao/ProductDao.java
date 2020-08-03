package com.app.dao;

import com.app.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    /**
     * 分页查询
     * 通过商品信息 例如商品名模糊查询、商品类别查询、商品拥有者查询
     *
     * @param productCondition
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);


}
