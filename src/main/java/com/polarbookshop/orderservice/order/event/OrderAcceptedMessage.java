package com.polarbookshop.orderservice.order.event;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
public record OrderAcceptedMessage(Long orderId) {}
