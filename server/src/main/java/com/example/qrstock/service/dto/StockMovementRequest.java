package com.example.qrstock.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StockMovementRequest {

    @NotBlank
    private String packageShortCode;

    @Min(1)
    private int qtyPcs;

    private Long fromHallId;

    private Long toHallId;

    private Long actorUserId;

    private String refDoc;

    private String notes;

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

    public Long getFromHallId() {
        return fromHallId;
    }

    public void setFromHallId(Long fromHallId) {
        this.fromHallId = fromHallId;
    }

    public Long getToHallId() {
        return toHallId;
    }

    public void setToHallId(Long toHallId) {
        this.toHallId = toHallId;
    }

    public Long getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(Long actorUserId) {
        this.actorUserId = actorUserId;
    }

    public String getRefDoc() {
        return refDoc;
    }

    public void setRefDoc(String refDoc) {
        this.refDoc = refDoc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}


