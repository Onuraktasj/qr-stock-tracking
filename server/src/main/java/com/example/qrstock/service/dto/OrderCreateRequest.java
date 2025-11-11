package com.example.qrstock.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

public class OrderCreateRequest {

    @NotEmpty
    private String customer;

    private String taxNo;

    private String country;

    private LocalDate orderDate;

    @NotEmpty
    @Valid
    private List<OrderLineRequest> lines;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLineRequest> getLines() {
        return lines;
    }

    public void setLines(List<OrderLineRequest> lines) {
        this.lines = lines;
    }
}


