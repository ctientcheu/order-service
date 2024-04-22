package com.polarbookshop.orderservice.order.domain;

import com.polarbookshop.orderservice.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.Objects;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
@DataR2dbcTest
@Testcontainers
@Import(DataConfig.class)
class OrderRepositoryTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.2");

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void findOrderByIdWhenNotExisting() {
        StepVerifier.create(orderRepository.findById(394L))
            .expectNextCount(0)
            .verifyComplete();
    }

    @Test
    void createRejectedOrder() {
        var rejectedOrder = OrderService.buildRejectedOrder( "1234567890", 3);
        StepVerifier.create(orderRepository.save(rejectedOrder))
            .expectNextMatches(order -> order.status().equals(OrderStatus.REJECTED))
            .verifyComplete();
    }

    @Test
    void whenCreateOrderNotAuthenticatedThenNoAuditMetadata() {
        var rejectedOrder = OrderService.buildRejectedOrder( "1234567890", 3);
        StepVerifier.create(orderRepository.save(rejectedOrder))
            .expectNextMatches(order -> Objects.isNull(order.createdBy()) &&
                Objects.isNull(order.lastModifiedBy()))
            .verifyComplete();
    }

    @Test
    @WithMockUser("marlena")
    void whenCreateOrderAuthenticatedThenAuditMetadata() {
        var rejectedOrder = OrderService.buildRejectedOrder( "1234567890", 3);
        StepVerifier.create(orderRepository.save(rejectedOrder))
            .expectNextMatches(order -> order.createdBy().equals("marlena") &&
                order.lastModifiedBy().equals("marlena"))
            .verifyComplete();
    }
}