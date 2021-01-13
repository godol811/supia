package com.example.supia.Dto.MyPage;

public class MyReviewDto {

    private String reviewContent;
    private String reviewTitle;
    private int productNo;
    private int reviewNo;
    private int orderId;
    private String productName;
    private String userId;


    public MyReviewDto(String reviewContent, String reviewTitle, int productNo, int reviewNo, int orderId, String productName, String userId) {
        this.reviewContent = reviewContent;
        this.reviewTitle = reviewTitle;
        this.productNo = productNo;
        this.reviewNo = reviewNo;
        this.orderId = orderId;
        this.productName = productName;
        this.userId = userId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MyReviewDto(String reviewContent, String reviewTitle, int productNo, int reviewNo, int orderId, String productName) {
        this.reviewContent = reviewContent;
        this.reviewTitle = reviewTitle;
        this.productNo = productNo;
        this.reviewNo = reviewNo;
        this.orderId = orderId;
        this.productName = productName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
