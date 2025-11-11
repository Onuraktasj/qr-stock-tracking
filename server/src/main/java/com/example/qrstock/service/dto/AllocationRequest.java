package com.example.qrstock.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AllocationRequest {

    @Min(1)
    private long orderLineId;

    @NotBlank
    private String packageShortCode;

    @Min(1)
    private int qtyPcs;

    public long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public String getPackageShortCode() {
        return packageShortCode;
    }

    public void setPackageShortCode(String packageShortCode) {
        this.packageShortCode = packageShortCode;
    }

    public int getQtyPcs() {
        return qtyPcs;
    }

    public void setQtyPcs(int qtyPcs) {
        this.qtyPcs = qtyPcs;
    }
}


