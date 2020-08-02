package com.app.service;


import com.app.dto.ImageHolder;
import com.app.dto.ProductExecution;
import com.app.entity.Product;

import java.util.List;

public interface ProductService {

    ProductExecution addProductAndImg(Product product, ImageHolder thumbnail, List<ImageHolder> imgList);

    /**
     * 修改商品信息
     *
     * @param product
     * @param thumbnail
     * @param imgList
     * @return
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imgList);

    /**
     * 通过ID获取商品信息
     * @param productId
     * @return
     */
    Product getProductById(int productId);
}
