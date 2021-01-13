package com.example.supia.Dto.MyPage;

public class MyLikeListDto {

    ///////////////////////////////////
    // MyLikeListActivity 를 위한 Bean //
    ///////////////////////////////////

    private int productNo;
    private String productName;
    private int productPrice;
    private String productImagePath;
    private String likeUserId;
    private int likeProductId;
    private String likeCheck;


    public MyLikeListDto(int productNo, String productName, int productPrice, String productImagePath, String likeUserId, int likeProductId, String likeCheck) {
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImagePath = productImagePath;
        this.likeUserId = likeUserId;
        this.likeProductId = likeProductId;
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

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(String likeUserId) {
        this.likeUserId = likeUserId;
    }

    public int getLikeProductId() {
        return likeProductId;
    }

    public void setLikeProductId(int likeProductId) {
        this.likeProductId = likeProductId;
    }

    public String getLikeCheck() {
        return likeCheck;
    }

    public void setLikeCheck(String likeCheck) {
        this.likeCheck = likeCheck;
    }
}
