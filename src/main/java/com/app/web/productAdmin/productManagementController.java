package com.app.web.productAdmin;

import com.app.dao.AreaDao;
import com.app.dto.ImageHolder;
import com.app.dto.ProductExecution;
import com.app.entity.Area;
import com.app.entity.PersonInfo;
import com.app.entity.Product;
import com.app.entity.ProductCategory;
import com.app.enums.ProductStateEnum;
import com.app.service.AreaService;
import com.app.service.ProductCategoryService;
import com.app.service.ProductService;
import com.app.util.CodeUtil;
import com.app.util.HttpServletRequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/productadmin")
@Controller
public class productManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;

    private static final int MAXIMAGECOUNT = 6;

    @RequestMapping(value = "/getproductinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        int productId = HttpServletRequestUtil.getInt(request,"productId");
        if (productId==0){
            modelMap.put("success",false);
            modelMap.put("errMsg", "productId为空");
            return modelMap;
        }
        Product product = productService.getProductById(productId);
        List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList();
        modelMap.put("success", true);
        modelMap.put("productCategoryList",productCategoryList);
        modelMap.put("product",product);
        return modelMap;
    }

    /**
     * 获取区域信息和分类信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getcategoryandarea",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getCategoryAndArea(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        List<ProductCategory> productCategoryList = null;

        try {
            productCategoryList = productCategoryService.getProductCategoryList();

            if (productCategoryList!=null){
                modelMap.put("success",true);

                modelMap.put("productCategoryList",productCategoryList);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","商品分类获取失败");
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg","商品分类获取失败");
        }
        return modelMap;
    }

    /**
     * 添加商品信息
     * 一、获取用户信息
     * 二、获取json字符串
     * 三、获取图片流（必须）
     * 四、获取详情图
     * 五、调用service返回信息
     * 六、返回响应
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入正确的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        CommonsMultipartFile commonsMultipartFile = null;
        //TODO 这里之后登陆需要用session替换
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1);

        String productStr = HttpServletRequestUtil.getString(request,"productStr");
        if (productStr!=null){
            try {
                product = mapper.readValue(productStr,Product.class);
                product.setOwner(owner);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("转换字符串失败"+e.getMessage());
            }

            CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext()
            );
            if (resolver.isMultipart(request)){
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
                commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                ImageHolder thumbnail = null;
                if (commonsMultipartFile!=null){
                    try {
                        thumbnail = new ImageHolder(commonsMultipartFile.getOriginalFilename(),commonsMultipartFile.getInputStream());
                    } catch (IOException e) {
                        throw new RuntimeException("缩略图获取失败"+e.getMessage());
                    }
                }
                List<ImageHolder> imgList = new ArrayList<>();
                for (int i=0;i<MAXIMAGECOUNT;i++){
                    CommonsMultipartFile imgFile = (CommonsMultipartFile)multipartHttpServletRequest.getFile("productImg"+i);
                    if (imgFile!=null){
                        try {
                            ImageHolder img = new ImageHolder(imgFile.getOriginalFilename(), imgFile.getInputStream());
                            imgList.add(img);
                        } catch (IOException e) {
                            throw new RuntimeException("详情图获取失败"+e.getMessage());
                        }
                    }else {
                        break;
                    }
                }

                ProductExecution pe = productService.addProductAndImg(product, thumbnail, imgList);
                if (ProductStateEnum.SUCCESS.getState() == pe.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("errMsg","添加商品失败");
                    modelMap.put("success",false);
                }
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","商品信息为空");
        }
        return modelMap;
    }

    /**
     * 修改图片信息
     * 一、接收product信息（获取前台穿的的JSON字符串）
     * 二、获取User信息
     * 三、获取缩略图片信息
     * 四、获取详情图信息
     * 五、更新商品信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入正确的验证码");
            return modelMap;
        }
        //TODO 这里之后登陆需要用session替换
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1);

        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request,"productStr");
        if (productStr!=null){
            try {
                product = mapper.readValue(productStr,Product.class);
                product.setOwner(owner);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("转化字符串出错"+e.getMessage());
            }
            if (product.getProductId()==0){
                modelMap.put("success",false);
                modelMap.put("errMsg","productId为空");
                return modelMap;
            }
            CommonsMultipartFile multipartFile = null;
            ImageHolder thumbnail = null;
            List<ImageHolder> imgList = new ArrayList<>();
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext()
            );
            if (resolver.isMultipart(request)){
                MultipartHttpServletRequest multipartHttpServletRequest =
                        (MultipartHttpServletRequest)request;
                multipartFile = (CommonsMultipartFile)multipartHttpServletRequest.getFile("thumbnail");
                try {
                    thumbnail = new ImageHolder(multipartFile.getOriginalFilename(),multipartFile.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException("获取缩略图失败"+e.getMessage());
                }
                for (int i=0;i<MAXIMAGECOUNT ;i++){
                    CommonsMultipartFile imgFile = (CommonsMultipartFile)multipartHttpServletRequest.getFile("productImg"+i);
                    if (imgFile!=null){
                        try {
                            ImageHolder productImg = new ImageHolder(imgFile.getOriginalFilename(), imgFile.getInputStream());
                            imgList.add(productImg);
                        } catch (IOException e) {
                            throw new RuntimeException("获取详情图失败"+e.getMessage());
                        }
                    }else {
                        break;
                    }
                }

                ProductExecution pe = productService.modifyProduct(product,thumbnail,imgList);
                if (ProductStateEnum.SUCCESS.getState() == pe.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("errMsg","修改商品失败");
                    modelMap.put("success",false);
                }
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","productStr为空");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Product productCondition = new Product();
        //尝试从前台获取数据
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        int isMine = HttpServletRequestUtil.getInt(request,"isMine");//判断是否要查看自己的所有商品
        if (isMine==1){//如是是查看自己的所有商品
            //TODO 这里之后会用 Session
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1);
            productCondition.setOwner(owner);
        }
        if (pageIndex!=0 && pageSize!=0){
            int productCategoryId = HttpServletRequestUtil.getInt(request, "productCategoryId");

            String productName = HttpServletRequestUtil.getString(request, "productName");
            Condition4Search(productCondition,productCategoryId,productName);
            List<Product> productList = productService.getProductList(productCondition,pageIndex,pageSize);

            if (productList!=null && productList.size()>0){
                modelMap.put("success",true);
                modelMap.put("productList", productList);

            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","获取失败");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请传入正确的页码以及每页的数量");
        }
        return modelMap;
    }

    /**
     * 通过条件组合
     *
     * @param productCondition
     * @param productCategoryId
     * @param productName
     */
    private void Condition4Search(Product productCondition, int productCategoryId, String productName) {
        if (productCategoryId!=-1){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName!=null){
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
    }

    @RequestMapping(value = "/getproductdetail", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductDetail(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>();
        int productId = HttpServletRequestUtil.getInt(request,"productId");
        if (productId > 0){
            Product product = productService.getProductDetail(productId);
            if (product!=null){
                modelMap.put("success",true);
                modelMap.put("product",product);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","获取商品信息失败");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","获取商品Id失败");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removeproduct", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> removeProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        int productId = HttpServletRequestUtil.getInt(request, "productId");
        if (productId!=0 && productId!=-1){
            int effectedNum = productService.removeProduct(productId);
            if (effectedNum>0){
                modelMap.put("success",true);
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","没有productId");
        }
        return modelMap;
    }



}
