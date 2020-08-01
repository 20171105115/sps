package com.app.service.Impl;

import com.app.dao.ProductCategoryDao;
import com.app.entity.ProductCategory;
import com.app.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryDao.queryProductCategoryList();
    }
}
