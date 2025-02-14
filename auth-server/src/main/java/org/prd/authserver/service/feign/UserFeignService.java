package org.prd.authserver.service.feign;

import org.prd.authserver.persistence.dto.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "micro-users")
public interface UserFeignService {

    @GetMapping("/users/find")
    UserDetailsDto getUserByUsername(@RequestParam(name = "username") String username);
}