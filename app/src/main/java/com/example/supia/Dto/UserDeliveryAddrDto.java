package com.example.supia.Dto;

public class UserDeliveryAddrDto {
    int deliveryNo;
    String userId;
    String deliveryAddr;
    String deliveryAddDetail;
    String deliveryTel;
    String deliveryName;


    public UserDeliveryAddrDto(int deliveryNo, String userId, String deliveryAddr, String deliveryAddDetail, String deliveryTel, String deliveryName) {
        this.deliveryNo = deliveryNo;
        this.userId = userId;
        this.deliveryAddr = deliveryAddr;
        this.deliveryAddDetail = deliveryAddDetail;
        this.deliveryTel = deliveryTel;
        this.deliveryName = deliveryName;
    }

    public int getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(int deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeliveryAddr() {
        return deliveryAddr;
    }

    public void setDeliveryAddr(String deliveryAddr) {
        this.deliveryAddr = deliveryAddr;
    }

    public String getDeliveryAddDetail() {
        return deliveryAddDetail;
    }

    public void setDeliveryAddDetail(String deliveryAddDetail) {
        this.deliveryAddDetail = deliveryAddDetail;
    }

    public String getDeliveryTel() {
        return deliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }
}
