package com.polarbookshop.orderservice.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
@ConfigurationProperties(prefix = "polar")
public record ClientProperties(
    @NotNull
    URI catalogServiceUri
) {}
