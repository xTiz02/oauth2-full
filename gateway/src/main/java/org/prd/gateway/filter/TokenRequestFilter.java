package org.prd.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.prd.gateway.util.FilterConfig;
import org.prd.gateway.util.Util;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@Component
public class TokenRequestFilter extends AbstractGatewayFilterFactory<FilterConfig> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TokenRequestFilter.class);

    public TokenRequestFilter() {
        super(FilterConfig.class);
    }


    @Override
    public GatewayFilter apply(FilterConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();

            if (headers.getFirst("Authorization") == null) {
                log.warn("No se encontró el token de autorización");
                return Util.onError(exchange, HttpStatus.UNAUTHORIZED, "No se encontró el token de autorización");
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Post-filtro
                if (config.isPostLogger()) {
                    log.info("Post filtro: {}", config.getMessage());
                }
            }));
        };
    }

}