package com.example.milkdistributionmanagement;

public class CustomerInfo {
    private String customerName;
    private String customerMobile;
    private String customerAddress;
    private String customerMilk;
    private String customerMilkPrice;
    private String customerImage;
    private String count;
    private String key;

    public CustomerInfo(String customerName, String customerMobile, String customerAddress,
                        String customerMilk, String customerMilkPrice, String customerImage, String count) {
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.customerAddress = customerAddress;
        this.customerMilk = customerMilk;
        this.customerMilkPrice = customerMilkPrice;
        this.customerImage = customerImage;
        this.count = count;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerMilk() {
        return customerMilk;
    }

    public String getCustomerMilkPrice() {
        return customerMilkPrice;
    }

    public String getCustomerImage() { return customerImage; }

    public String getCount() { return count; }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CustomerInfo(){}
}