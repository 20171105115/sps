package com.app.dao;

import com.app.entity.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao {

    /**
     * 查询所有商品分类
     * @return
     */
    List<ProductCategory> queryProductCategoryList();

}
