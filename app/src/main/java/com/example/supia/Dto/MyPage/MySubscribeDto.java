package com.example.supia.Dto.MyPage;

public class MySubscribeDto {

    private String subscribeOrderDate;
    private int subscribeOrderQuantity;
    private String subscribeOrderAddr;
    private String subscribeOrderAddrDetail;
    private String subscribeOrderPayment;
    private String userId;
    private int productId;
    private int productNo;
    private String productName;
    private String productImagePath;
    private int productPrice;
    private int subscribeProductPrice;
    private String subscribeProductName;
    private String productBrand;
    private String productInfo;
    private String subscribeOrderNextDate;










    public MySubscribeDto(String subscribeProductName,int subscribeProductPrice,int subscribeOrderQuantity, String productImagePath, int productNo,int productId,  String productBrand,String productInfo, int productPrice,String productName) {
        this.subscribeProductName = subscribeProductName;
        this.subscribeProductPrice = subscribeProductPrice;
        this.subscribeOrderQuantity = subscribeOrderQuantity;
        this.productImagePath = productImagePath;
        this.productNo = productNo;
        this.productId = productId;
        this.productBrand = productBrand;
        this.productInfo = productInfo;
        this.productPrice = productPrice;
        this.productName = productName;
        this.subscribeOrderNextDate = subscribeOrderNextDate;
    }






    public String getSubscribeOrderNextDate() {
        return subscribeOrderNextDate;
    }

    public void setSubscribeOrderNextDate(String subscribeOrderNextDate) {
        this.subscribeOrderNextDate = subscribeOrderNextDate;
    }

    public void setSubscribeProductPrice(int subscribeProductPrice) {
        this.subscribeProductPrice = subscribeProductPrice;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public MySubscribeDto(String subscribeOrderDate, int subscribeOrderQuantity, String subscribeOrderAddr, String subscribeOrderAddrDetail, String subscribeOrderPayment, String userId, int productId, int productNo, String productName, String productImagePath, int productPrice,String subscribeOrderNextDate) {
        this.subscribeOrderDate = subscribeOrderDate;
        this.subscribeOrderQuantity = subscribeOrderQuantity;
        this.subscribeOrderAddr = subscribeOrderAddr;
        this.subscribeOrderAddrDetail = subscribeOrderAddrDetail;
        this.subscribeOrderPayment = subscribeOrderPayment;
        this.userId = userId;
        this.productId = productId;
        this.productNo = productNo;
        this.productName = productName;
        this.productImagePath = productImagePath;
        this.productPrice = productPrice;
        this.subscribeOrderNextDate = subscribeOrderNextDate;
    }


    public int getSubscribeProductPrice() {
        return subscribeProductPrice;
    }

    public void setSubscribeProductPrice() {
        this.subscribeProductPrice = subscribeProductPrice;
    }

    public String getSubscribeProductName() {
        return subscribeProductName;
    }

    public void setSubscribeProductName(String subscribeProductName) {
        this.subscribeProductName = subscribeProductName;
    }


    public String getSubscribeOrderAddrDetail() {
        return subscribeOrderAddrDetail;
    }

    public void setSubscribeOrderAddrDetail(String subscribeOrderAddrDetail) {
        this.subscribeOrderAddrDetail = subscribeOrderAddrDetail;
    }

    public MySubscribeDto(String subscribeOrderPayment, String userId) {
        this.subscribeOrderPayment = subscribeOrderPayment;
        this.userId = userId;}


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
