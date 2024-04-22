package com.polarbookshop.orderservice.order.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
@Table("orders")
public record Order(
    @Id
    Long id,
    String bookIsbn,
    String bookName,
    Double bookPrice,
    Integer quantity,
    OrderStatus status,
    @CreatedDate
    Instant createdDate,
    @CreatedBy
    String createdBy,
    @LastModifiedDate
    Instant lastModifiedDate,
    @LastModifiedBy
    String lastModifiedBy,
    @Version
    int version
) {
    public static Order of(String bookIsbn, String bookName, Double bookPrice, Integer quantity, OrderStatus status) {
        return new Order(null, bookIsbn, bookName, bookPrice, quantity, status, null, null, null, null, 0);
    }
}
