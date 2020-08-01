package com.app.dto;

import com.app.entity.Product;
import com.app.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {

    private int state;

    private String stateInfo;

    private Product product;

    private List<Product> productList;

    private int count;

    public ProductExecution(){

    }
    //失败时的构造器
    public ProductExecution(ProductStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //成功时的构造器
    public ProductExecution(ProductStateEnum stateEnum,Product product){
        this.product = product;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //成功时的构造器
    public ProductExecution(ProductStateEnum stateEnum,List<Product> productList){
        this.productList = productList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
