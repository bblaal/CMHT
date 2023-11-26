package com.cmht.models;

public class Product {

    private int productID;
    private String productName;
    private double productRate;

    public Product(int productID, String productName, double productRate) {
        this.productID = productID;
        this.productName = productName;
        this.productRate = productRate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductRate() {
        return productRate;
    }

    public void setProductRate(double productRate) {
        this.productRate = productRate;
    }
}
