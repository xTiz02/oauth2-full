package org.prd.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.prd.gateway.util.FilterConfig;
import org.prd.gateway.util.Util;
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

@Slf4j
@Component
public class TokenRequestFilter extends AbstractGatewayFilterFactory<FilterConfig> {

    @Override
    public GatewayFilter apply(FilterConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();

            if (exchange.getRequest().getMethod() == HttpMethod.POST &&
                    MediaType.APPLICATION_FORM_URLENCODED.includes(headers.getContentType()) &&
                    config.isPostLogger()) {

                return request.getBody()
                        .collectList()
                        .flatMap(dataBuffers -> {

                            String requestBody = dataBuffers.stream()
                                    .map(dataBuffer -> {
                                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(bytes);
                                        return new String(bytes, StandardCharsets.UTF_8);
                                    })
                                    .reduce("", String::concat);

                           log.info("Request Body: {}", requestBody);

                            MultiValueMap<String, String> formParams = Util.parseFormBody(requestBody);

                            if(!formParams.containsKey("client_id") ||
                                    !formParams.containsKey("code") ||
                                    //!formParams.containsKey("scope") ||
                                    !formParams.containsKey("grant_type") ||
                                    !formParams.containsKey("redirect_uri") ||
                                    headers.getFirst("Authorization") == null
                                    //!formParams.containsKey("code_verifier")
                            ) {
                                log.warn("Solicitud de obtención de token invalida");
                                return Util.onError(exchange, HttpStatus.UNAUTHORIZED,"Solicitud de token inválida");
                            }

                            return chain.filter(exchange);
                            //clonar el request con un nuevo body
                            /*ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                    .header("X-Custom-Header", "Intercepted")
                                    .build();*/


                        });
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