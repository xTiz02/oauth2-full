package org.prd.gateway.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterConfig {
    private String message;
    private boolean preLogger;
    private boolean postLogger;
}