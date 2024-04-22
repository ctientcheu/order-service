package com.polarbookshop.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(authorize -> authorize.anyExchange().authenticated())
            .oauth2ResourceServer(configurer -> configurer.jwt(withDefaults()))
            .requestCache(requestCacheSpec -> requestCacheSpec
                .requestCache(NoOpServerRequestCache.getInstance())
                // each request must include an access token, so there's no need to
                // keep a session cache alive between requests. we want it to be stateless
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .build();
    }
}
