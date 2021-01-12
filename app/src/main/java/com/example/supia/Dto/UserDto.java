package com.example.supia.Dto;

public class UserDto {

    private String userId;
    private String userPw;
    private String userAddr;
    private String userTel;
    private String userName;



    public UserDto(String userId, String userPw, String userAddr, String userTel) {
        this.userId = userId;
        this.userPw = userPw;
        this.userAddr = userAddr;
        this.userTel = userTel;
    }

    public UserDto(String userId, String userPw, String userAddr, String userTel, String userName) {
        this.userId = userId;
        this.userPw = userPw;
        this.userAddr = userAddr;
        this.userTel = userTel;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
