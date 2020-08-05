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


    /**
     * 分页获取商品信息
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Product> getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 获取商品详情以及详情图
     * @param productId
     * @return
     */
    Product getProductDetail(int productId);


    /**
     * 删除商品信息
     * @param productId
     * @return
     */
    int removeProduct(int productId);


}
