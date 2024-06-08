package com.polarbookshop.orderservice.config;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
@ConfigurationProperties(prefix = "polar")
public record ClientProperties(@NotNull URI catalogServiceUri) {}
