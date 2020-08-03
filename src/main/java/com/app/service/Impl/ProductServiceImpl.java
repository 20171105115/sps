package com.app.service.Impl;

import com.app.dao.ProductDao;
import com.app.dao.ProductImgDao;
import com.app.dto.ImageHolder;
import com.app.dto.ProductExecution;
import com.app.entity.PersonInfo;
import com.app.entity.Product;
import com.app.entity.ProductImg;
import com.app.enums.ProductStateEnum;
import com.app.service.ProductService;
import com.app.util.ImageUtil;
import com.app.util.PageUtil;
import com.app.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 增加商品信息
     * 一、判断product是否为空
     * 二、处理缩略图，获取图片地址
     * 三、处理详情图
     * 四、将缩略图地址更新到product中
     * 五、插入product信息
     *
     * @param product
     * @param thumbnail
     * @param imgList
     * @return
     */
    @Transactional
    public ProductExecution addProductAndImg(Product product, ImageHolder thumbnail, List<ImageHolder> imgList) {
        if (product != null && product.getOwner() != null && product.getOwner().getUserId() != -1) {
            //设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail != null) {
                //处理缩略图
                try {
                    addImg(product, thumbnail);
                    int effectedNum = productDao.insertProduct(product);
                    if (effectedNum > 0) {
                        //处理详情图
                        if (imgList != null && imgList.size() > 0) {
                            try {
                                addImageList(product, imgList);
                            } catch (Exception e) {
                                throw new RuntimeException("添加详情图片失败" + e.getMessage());
                            }
                        }
                        return new ProductExecution(ProductStateEnum.SUCCESS, product);
                    } else {
                        throw new RuntimeException("创建商品失败");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("添加缩略图片失败" + e.getMessage());
                }

            } else {
                return new ProductExecution(ProductStateEnum.EMPTY_IMG);
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY_PRODUCT);
        }
    }

    /**
     * 修改商品信息
     * 一、判断product是否为空
     * 二、获取商品初始信息，用来获取缩略图的图片地址
     * 二、判断缩略图是否为空，并处理
     * 三、判断详情图是否为空，并处理
     * 四、调用dao层更新商品信息
     *
     * @param product
     * @param thumbnail
     * @param imgList
     * @return
     */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imgList) {
        if (product != null && product.getProductId() != 0 && product.getOwner() != null && product.getOwner().getUserId() != -1) {
            //设置基本属性
            product.setLastEditTime(new Date());
            //判断缩略图
            Product tempProduct = productDao.selectProductById(product.getProductId());
            if (thumbnail != null && thumbnail.getImagePath() != null) {
                if (tempProduct.getProductImg() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getProductImg());
                }
                try {
                    addImg(product, thumbnail);
                } catch (Exception e) {
                    throw new RuntimeException("处理缩略图失败" + e.getMessage());
                }
            }
            if (imgList != null && imgList.size() > 0) {
                List<ProductImg> productImgList = productImgDao.queryProductImgById(product.getProductId());
                if (productImgList != null && productImgList.size() > 0) {
                    deleteImageList(productImgList);
                }
                try {
                    int effectedNum = productImgDao.deleteProductImgById(product.getProductId());
                    if (effectedNum<=0){
                        throw new RuntimeException("处理详情图失败 删除失败");
                    }
                    addImageList(product, imgList);
                } catch (Exception e) {
                    throw new RuntimeException("处理详情图失败" + e.getMessage());
                }
            }
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new RuntimeException("更新商品失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new RuntimeException("更新商品失败" + e.getMessage());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY_PRODUCT);
        }
    }

    /**
     * 通过productId获取商品信息
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(int productId) {
        return productDao.selectProductById(productId);
    }

    @Override
    public List<Product> getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex,pageSize);
        return productDao.queryProductList(productCondition,rowIndex,pageSize);
    }

    @Override
    public Product getProductDetail(int productId) {
        Product product = productDao.selectProductById(productId);
        List<ProductImg> productImgList = productImgDao.queryProductImgById(productId);
        product.setProductImgList(productImgList);
        return product;
    }

    /**
     * 删除商品详情图
     *
     * @param productImgList
     */
    private void deleteImageList(List<ProductImg> productImgList) {
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
    }

    /**
     * 处理详情图
     *
     * @param product
     * @param imgList
     */
    private void addImageList(Product product, List<ImageHolder> imgList) {
        PersonInfo owner = product.getOwner();
        String targetAddr = PathUtil.getImagePath(owner.getUserId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        for (ImageHolder image : imgList) {
            ProductImg productImg = new ProductImg();
            String relativeAddr = ImageUtil.generateBigThumbnail(image, targetAddr);
            productImg.setImgAddr(relativeAddr);
            productImg.setCreateTime(new Date());
            productImg.setLastEditTime(new Date());
            productImg.setProductId(product.getProductId());
            productImgList.add(productImg);
        }
        if (productImgList != null && productImgList.size() > 0) {
            try {
                productImgDao.batchInsertImg(productImgList);
            } catch (Exception e) {
                throw new RuntimeException("添加详情图失败" + e.getMessage());
            }
        }
    }

    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addImg(Product product, ImageHolder thumbnail) {
        PersonInfo owner = product.getOwner();
        String targetAddr = PathUtil.getImagePath(owner.getUserId());
        String relativePath = ImageUtil.generateThumbnail(thumbnail, targetAddr);
        product.setProductImg(relativePath);
    }


}
