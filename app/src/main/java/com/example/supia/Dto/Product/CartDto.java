package com.example.supia.Dto.Product;

public class CartDto {

    int cartNo;
    int cartProductId;
    String cartProductName;
    String cartUserId;
    String cartProductQuantity;
    String cartProductPrice;
    String cartProductImagePath;


    public CartDto(int cartNo, int cartProductId, String cartProductName, String cartUserId, String cartProductQuantity, String cartProductPrice, String cartProductImagePath) {
        this.cartNo = cartNo;
        this.cartProductId = cartProductId;
        this.cartProductName = cartProductName;
        this.cartUserId = cartUserId;
        this.cartProductQuantity = cartProductQuantity;
        this.cartProductPrice = cartProductPrice;
        this.cartProductImagePath = cartProductImagePath;
    }

    public int getCartNo() {
        return cartNo;
    }

    public void setCartNo(int cartNo) {
        this.cartNo = cartNo;
    }

    public int getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(int cartProductId) {
        this.cartProductId = cartProductId;
    }

    public String getCartProductName() {
        return cartProductName;
    }

    public void setCartProductName(String cartProductName) {
        this.cartProductName = cartProductName;
    }

    public String getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(String cartUserId) {
        this.cartUserId = cartUserId;
    }

    public String getCartProductQuantity() {
        return cartProductQuantity;
    }

    public void setCartProductQuantity(String cartProductQuantity) {
        this.cartProductQuantity = cartProductQuantity;
    }

    public String getCartProductPrice() {
        return cartProductPrice;
    }

    public void setCartProductPrice(String cartProductPrice) {
        this.cartProductPrice = cartProductPrice;
    }

    public String getCartProductImagePath() {
        return cartProductImagePath;
    }

    public void setCartProductImagePath(String cartProductImagePath) {
        this.cartProductImagePath = cartProductImagePath;
    }
}
