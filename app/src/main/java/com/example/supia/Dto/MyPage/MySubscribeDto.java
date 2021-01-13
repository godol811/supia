package com.example.supia.Dto.MyPage;

public class MySubscribeDto {

    private String subscribeOrderDate;
    private int subscribeOrderQuantity;
    private String subscribeOrderAddr;
    private String subscribeOrderPayment;
    private String userId;
    private int productId;
    private int productNo;
    private String productName;
    private String productImagePath;
    private int productPrice;


//    public MySubscribeDto(String subscribeOrderDate, String subscribeOrderAddr, String subscribeOrderPayment, String userId, int productId, int productNo, int productPrice) {
//        this.subscribeOrderDate = subscribeOrderDate;
//        this.subscribeOrderAddr = subscribeOrderAddr;
//        this.subscribeOrderPayment = subscribeOrderPayment;
//        this.userId = userId;
//        this.productId = productId;
//        this.productNo = productNo;
//        this.productPrice = productPrice;
//    }


    public MySubscribeDto(String subscribeOrderPayment, String userId) {
        this.subscribeOrderPayment = subscribeOrderPayment;
        this.userId = userId;
    }

    public MySubscribeDto(String subscribeOrderDate, int subscribeOrderQuantity, String subscribeOrderAddr, String subscribeOrderPayment, String userId, int productId, int productNo, String productName, String productImagePath, int productPrice) {
        this.subscribeOrderDate = subscribeOrderDate;
        this.subscribeOrderQuantity = subscribeOrderQuantity;
        this.subscribeOrderAddr = subscribeOrderAddr;
        this.subscribeOrderPayment = subscribeOrderPayment;
        this.userId = userId;
        this.productId = productId;
        this.productNo = productNo;
        this.productName = productName;
        this.productImagePath = productImagePath;
        this.productPrice = productPrice;
    }

    public String getSubscribeOrderDate() {
        return subscribeOrderDate;
    }

    public void setSubscribeOrderDate(String subscribeOrderDate) {
        this.subscribeOrderDate = subscribeOrderDate;
    }

    public int getSubscribeOrderQuantity() {
        return subscribeOrderQuantity;
    }

    public void setSubscribeOrderQuantity(int subscribeOrderQuantity) {
        this.subscribeOrderQuantity = subscribeOrderQuantity;
    }

    public String getSubscribeOrderAddr() {
        return subscribeOrderAddr;
    }

    public void setSubscribeOrderAddr(String subscribeOrderAddr) {
        this.subscribeOrderAddr = subscribeOrderAddr;
    }

    public String getSubscribeOrderPayment() {
        return subscribeOrderPayment;
    }

    public void setSubscribeOrderPayment(String subscribeOrderPayment) {
        this.subscribeOrderPayment = subscribeOrderPayment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}//------------
