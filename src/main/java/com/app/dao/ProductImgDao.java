package com.app.dao;

import com.app.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductImgDao {

    int batchInsertImg(List<ProductImg> productImgList);
}
