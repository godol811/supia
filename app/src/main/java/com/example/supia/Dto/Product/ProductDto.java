package com.example.supia.Dto.Product;

import java.io.Serializable;

public class ProductDto  {

    int productNo;
    String productName;
    String productPrice;
    String productQuantity;
    String productBrand;
    String productImagePath;
    String productInfo;
    String productCategory1;
    String productCategory2;
    String likeUserId;
    String likeProductId;
    String likeCheck;



    public ProductDto(int productNo, String productName, String productPrice, String productQuantity, String productBrand, String productImagePath, String productInfo, String productCategory1, String productCategory2) {
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productBrand = productBrand;
        this.productImagePath = productImagePath;
        this.productInfo = productInfo;
        this.productCategory1 = productCategory1;
        this.productCategory2 = productCategory2;
    }


    public ProductDto(int productNo, String productName, String productPrice, String productQuantity, String productBrand, String productImagePath, String productInfo, String productCategory1, String productCategory2, String likeUserId, String likeProductId, String likeCheck) {
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productBrand = productBrand;
        this.productImagePath = productImagePath;
        this.productInfo = productInfo;
        this.productCategory1 = productCategory1;
        this.productCategory2 = productCategory2;
        this.likeUserId = likeUserId;
        this.likeProductId = likeProductId;
        this.likeCheck = likeCheck;
    }

    public String getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(String likeUserId) {
        this.likeUserId = likeUserId;
    }

    public String getLikeProductId() {
        return likeProductId;
    }

    public void setLikeProductId(String likeProductId) {
        this.likeProductId = likeProductId;
    }

    public String getLikeCheck() {
        return likeCheck;
    }

    public void setLikeCheck(String likeCheck) {
        this.likeCheck = likeCheck;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductCategory1() {
        return productCategory1;
    }

    public void setProductCategory1(String productCategory1) {
        this.productCategory1 = productCategory1;
    }

    public String getProductCategory2() {
        return productCategory2;
    }

    public void setProductCategory2(String productCategory2) {
        this.productCategory2 = productCategory2;
    }
}
