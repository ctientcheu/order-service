package com.polarbookshop.orderservice.order.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    public Flux<Order> findAllByCreatedBy(String createdBy);
}
