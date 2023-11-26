package com.cmht.models;

public class BillingItem {
    private int slno;
    private String name;
    private int quantity;
    private String rate;
    private String total;

    // Constructor, getters, and setters

    // Example constructor:
    public BillingItem(int slno, String name, int quantity, String rate, String total) {
        this.slno = slno;
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.total = total;
    }

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

// Add getters and setters as needed
}

