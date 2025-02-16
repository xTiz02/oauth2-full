package org.prd.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prd.gateway.util.FilterConfig;
import org.prd.gateway.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import java.net.URI;


@Component
public class AuthenticationRequestFilter extends AbstractGatewayFilterFactory<FilterConfig> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationRequestFilter.class);

    public AuthenticationRequestFilter() {
        super(FilterConfig.class);
    }

    @Override
    public GatewayFilter apply(FilterConfig config) {
        return (exchange, chain) -> {
            if(config.isPreLogger()) {
                ServerHttpRequest request = exchange.getRequest();
                URI uri = request.getURI();
                String query = uri.getQuery();
                HttpHeaders headers = request.getHeaders();

                log.info("Request: {} {}", request.getMethod(), uri.getPath() + (query != null ? "?" + query : ""));

                MultiValueMap<String, String> queryParams = request.getQueryParams();
                if (!queryParams.containsKey("client_id") ||
                        !queryParams.containsKey("redirect_uri") ||
                        !queryParams.containsKey("scope") ||
                        !queryParams.containsKey("response_type")
                    //!queryParams.containsKey("code_challenge") ||
                    //!queryParams.containsKey("code_challenge_method") ||
                ) {
                    log.warn("Solicitud de autorización inválida");
                    return Util.onError(exchange, HttpStatus.UNAUTHORIZED, "Solicitud de autorización inválida");
                }
            }


            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Post-filtro
                if (config.isPostLogger()) {
                    log.info("Post filter: {}", config.getMessage());
                }
            }));
        };
    }



}