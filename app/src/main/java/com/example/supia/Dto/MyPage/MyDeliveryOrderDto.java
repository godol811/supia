package com.example.supia.Dto.MyPage;

public class MyDeliveryOrderDto {

    private String deliveryAddr;
    private String deliveryTel;
    private String deliveryName;
    private String userId;




    public MyDeliveryOrderDto(String deliveryAddr, String deliveryTel, String deliveryName, String userId) {
        this.deliveryAddr = deliveryAddr;
        this.deliveryTel = deliveryTel;
        this.deliveryName = deliveryName;
        this.userId = userId;
    }




    public String getDeliveryAddr() {
        return deliveryAddr;
    }

    public void setDeliveryAddr(String deliveryAddr) {
        this.deliveryAddr = deliveryAddr;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}//-----------------
