package com.app.serviceTest;

import com.app.BaseTest;
import com.app.dto.ImageHolder;
import com.app.dto.ProductExecution;
import com.app.entity.PersonInfo;
import com.app.entity.Product;
import com.app.entity.ProductCategory;
import com.app.enums.ProductStateEnum;
import com.app.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("缺少轮子的奔驰");
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1);
        product.setOwner(owner);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1);
        product.setProductCategory(productCategory);

        File file = new File("/Users/a20171105115/Documents/壁纸/1.jpg");
        InputStream is = new FileInputStream(file);
        ImageHolder thumbnail = new ImageHolder(file.getName(),is);

        List<ImageHolder> imgList = new ArrayList<ImageHolder>();

        File file1 = new File("/Users/a20171105115/Documents/壁纸/1.jpg");
        InputStream is1 = new FileInputStream(file1);
        ImageHolder thumbnail1 = new ImageHolder(file.getName(),is1);
        imgList.add(thumbnail1);

        File file2 = new File("/Users/a20171105115/Documents/壁纸/1.jpg");
        InputStream is2 = new FileInputStream(file2);
        ImageHolder thumbnail2 = new ImageHolder(file.getName(),is2);
        imgList.add(thumbnail2);

        ProductExecution pe = productService.addProductAndImg(product,thumbnail,imgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }

    @Test
    public void testModifyProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("缺少篮子的奔驰");
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1);
        product.setOwner(owner);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1);
        product.setProductCategory(productCategory);

        File file = new File("/Users/a20171105115/Documents/壁纸/1.jpg");
        InputStream is = new FileInputStream(file);
        ImageHolder thumbnail = new ImageHolder(file.getName(),is);

        List<ImageHolder> imgList = new ArrayList<ImageHolder>();

        File file1 = new File("/Users/a20171105115/Documents/壁纸/1.jpg");
        InputStream is1 = new FileInputStream(file1);
        ImageHolder thumbnail1 = new ImageHolder(file.getName(),is1);
        imgList.add(thumbnail1);

        File file2 = new File("/Users/a20171105115/Documents/壁纸/1.jpg");
        InputStream is2 = new FileInputStream(file2);
        ImageHolder thumbnail2 = new ImageHolder(file.getName(),is2);
        imgList.add(thumbnail2);

        ProductExecution pe = productService.modifyProduct(product,thumbnail,imgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }
}
