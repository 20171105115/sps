package com.app.service;


import com.app.dto.ImageHolder;
import com.app.dto.ProductExecution;
import com.app.entity.Product;

import java.util.List;

public interface ProductService {

    ProductExecution addProductAndImg(Product product, ImageHolder thumbnail, List<ImageHolder> imgList);
}
