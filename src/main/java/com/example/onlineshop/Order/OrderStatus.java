package com.example.onlineshop.Order;

public enum OrderStatus {
    NEW(1, "New"),
    PROCESSING(2, "Processing"),
    DELIVERING(3, "Delivering"),
    COMPLETED(4, "Completed");

    OrderStatus(int statusNum, String name) {
        this.statusNum = statusNum;
        this.name = name;
    }

    private int statusNum;
    private String name;

    public int getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(int statusNum) {
        this.statusNum = statusNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
