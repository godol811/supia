package com.example.supia.Dto.MyPage;

public class MyOrderListDto {

    private int orderQuantity;
    private int productPrice;
    private int productId;
    private int productNo;
    private String productName;
    private String orderPayment;
    private String userId;
    private String productImagePath;
    private int orderNo;


    public MyOrderListDto(int orderQuantity, int productPrice, int productId, int productNo, String productName, String orderPayment, String userId, String productImagePath, int orderNo) {
        this.orderQuantity = orderQuantity;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productNo = productNo;
        this.productName = productName;
        this.orderPayment = orderPayment;
        this.userId = userId;
        this.productImagePath = productImagePath;
        this.orderNo = orderNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public MyOrderListDto(int orderQuantity, int productPrice, int productId, int productNo, String productName, String orderPayment, String userId, String productImagePath) {
        this.orderQuantity = orderQuantity;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productNo = productNo;
        this.productName = productName;
        this.orderPayment = orderPayment;
        this.userId = userId;
        this.productImagePath = productImagePath;
    }


    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
