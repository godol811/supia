package com.example.supia.Dto.MyPage;

public class MyQnaDto {

    private String userId;
    private String qnaContent;
    private String productName;


    public MyQnaDto(String userId, String qnaContent, String productName) {
        this.userId = userId;
        this.qnaContent = qnaContent;
        this.productName = productName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQnaContent() {
        return qnaContent;
    }

    public void setQnaContent(String qnaContent) {
        this.qnaContent = qnaContent;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
