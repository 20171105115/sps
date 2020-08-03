package com.app.dao;

import com.app.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductImgDao {

    int batchInsertImg(List<ProductImg> productImgList);

    /**
     * 通过productId查询所有详情图
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgById(int productId);

    /**
     * 删除详情图
     * @param productId
     * @return
     */
    int deleteProductImgById(int productId);


}
