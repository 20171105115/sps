package com.app.daoTest;

import com.app.BaseTest;
import com.app.dao.ProductImgDao;
import com.app.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testBatchInsertProductImg(){
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        ProductImg p1 = new ProductImg();
        p1.setCreateTime(new Date());
        p1.setImgAddr("xxxx");
        p1.setProductId(1);
        productImgList.add(p1);

        ProductImg p2 = new ProductImg();
        p2.setCreateTime(new Date());
        p2.setImgAddr("yyyy");
        p2.setProductId(1);
        productImgList.add(p2);

        ProductImg p3 = new ProductImg();
        p3.setCreateTime(new Date());
        p3.setImgAddr("xxxx");
        p3.setProductId(1);
        productImgList.add(p3);

        int i = productImgDao.batchInsertImg(productImgList);
        assertEquals(3,i);
    }
}
