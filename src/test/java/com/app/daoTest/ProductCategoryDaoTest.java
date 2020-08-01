package com.app.daoTest;

import com.app.BaseTest;
import com.app.dao.ProductCategoryDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {


    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Test
    public void testQueryProductCategoryList(){

        assertEquals(3,productCategoryDao.queryProductCategoryList().size());
    }
}
