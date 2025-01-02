package com.dcw.cartfrenzy.dto;

public class OrderItemDto {

    private Long productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private double price;
    private Long orderId;
    private String orderUserFirstName;
    private OrderDto order;

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderUserFirstName() {
        return orderUserFirstName;
    }

    public void setOrderUserFirstName(String orderUserFirstName) {
        this.orderUserFirstName = orderUserFirstName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
