package com.example.qrstock.service;

import com.example.qrstock.domain.Order;
import com.example.qrstock.domain.OrderLine;
import com.example.qrstock.domain.ProductType;
import com.example.qrstock.repository.OrderRepository;
import com.example.qrstock.repository.ProductTypeRepository;
import com.example.qrstock.service.dto.OrderCreateRequest;
import com.example.qrstock.service.dto.OrderLineRequest;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductTypeRepository productTypeRepository;

    public OrderService(OrderRepository orderRepository, ProductTypeRepository productTypeRepository) {
        this.orderRepository = orderRepository;
        this.productTypeRepository = productTypeRepository;
    }

    @Transactional
    public Order createOrder(OrderCreateRequest request) {
        Order order = new Order();
        order.setCustomer(request.getCustomer());
        order.setTaxNo(request.getTaxNo());
        order.setCountry(request.getCountry());
        if (request.getOrderDate() != null) {
            order.setOrderDate(request.getOrderDate());
        }
        request.getLines().forEach(lineRequest -> {
            OrderLine line = new OrderLine();
            ProductType productType = productTypeRepository.findById(lineRequest.getProductTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Unknown product type"));
            line.setOrder(order);
            line.setProductType(productType);
            line.setQtyPcs(lineRequest.getQtyPcs());
            line.setLengthMm(lineRequest.getLengthMm());
            order.getLines().add(line);
        });
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}


