package com.example.qrstock.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PackageRequest {

    @NotNull
    private Long productTypeId;

    private Long machineId;

    private String heatNo;

    private String lotNo;

    private Integer paketNo;

    @NotNull
    @Min(1)
    private Integer pieces;

    @NotNull
    @Min(1)
    private Integer lengthMm;

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getHeatNo() {
        return heatNo;
    }

    public void setHeatNo(String heatNo) {
        this.heatNo = heatNo;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Integer getPaketNo() {
        return paketNo;
    }

    public void setPaketNo(Integer paketNo) {
        this.paketNo = paketNo;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Integer getLengthMm() {
        return lengthMm;
    }

    public void setLengthMm(Integer lengthMm) {
        this.lengthMm = lengthMm;
    }
}


