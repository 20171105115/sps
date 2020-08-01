package com.app.daoTest;

import com.app.BaseTest;
import com.app.dao.ProductDao;
import com.app.entity.PersonInfo;
import com.app.entity.Product;
import com.app.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest {


    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsertProduct(){
        Product product = new Product();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1);
        product.setOwner(owner);
        product.setProductCategory(productCategory);

        product.setProductName("废弃的自行车");
        product.setProductDesc("缺少一个轮子");
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        int i = productDao.insertProduct(product);
        assertEquals(1,i);
    }
}