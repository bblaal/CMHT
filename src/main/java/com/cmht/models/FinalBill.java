package com.cmht.models;

public class FinalBill {

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    private int invoiceNo;

    public FinalBill() {
    }

    public FinalBill(int invoiceNo, int totalQty, double discountedAmount, double totalAmount, String billingDate, String billingTime, String paymentMethod) {
        this.invoiceNo = invoiceNo;
        this.totalQty = totalQty;
        this.discountedAmount = discountedAmount;
        this.totalAmount = totalAmount;
        this.billingDate = billingDate;
        this.billingTime = billingTime;
        this.paymentMethod = paymentMethod;
    }

    private int totalQty;
    private double discountedAmount;
    private double totalAmount;
    private String billingDate;
    private String billingTime;
    private String paymentMethod;

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
