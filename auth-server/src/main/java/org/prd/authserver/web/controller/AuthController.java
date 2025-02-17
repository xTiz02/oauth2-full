package org.prd.authserver.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @GetMapping("/logout")
//    public String logout(){
//        log.info("Se bentra al logout!");
//        return "logout";
//    }
//
//    @PostMapping("/logout")
//    public String logoutOk(HttpSecurity http) throws Exception {
//        log.info("Se borro la sesion");
//        http.logout(logoutConfig -> {
//            logoutConfig.deleteCookies("JSESSIONID")
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true);
//        });
//
//
//
//        return "login?logout";
//    }
}