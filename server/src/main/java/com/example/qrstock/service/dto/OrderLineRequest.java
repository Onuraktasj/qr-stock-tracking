package com.example.qrstock.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderLineRequest {

    @NotNull
    private Long productTypeId;

    private Integer lengthMm;

    @Min(1)
    private int qtyPcs;

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getLengthMm() {
        return lengthMm;
    }

    public void setLengthMm(Integer lengthMm) {
        this.lengthMm = lengthMm;
    }

    public int getQtyPcs() {
        return qtyPcs;
    }

    public void setQtyPcs(int qtyPcs) {
        this.qtyPcs = qtyPcs;
    }
}


