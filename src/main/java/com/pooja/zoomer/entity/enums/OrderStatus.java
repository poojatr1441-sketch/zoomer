package com.pooja.zoomer.entity.enums;

public enum OrderStatus {

    PENDING,
    ACCEPTED,
    REJECTED,          //order rejected by rest
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED          //cancelled by the customer
}