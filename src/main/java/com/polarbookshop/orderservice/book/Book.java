package com.polarbookshop.orderservice.book;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
public record Book(
    String isbn,
    String title,
    String author,
    Double price
) {}
