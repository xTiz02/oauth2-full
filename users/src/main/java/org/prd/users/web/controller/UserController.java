package org.prd.users.web.controller;

import org.prd.users.persistence.dto.UserDetailsDto;
import org.prd.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/find")
    public ResponseEntity<UserDetailsDto> findUser(@RequestParam(name = "username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PreAuthorize("hasAuthority('VIEW_PROFILE')")
    @GetMapping("/view-info")
    public ResponseEntity<UserDetailsDto> viewProfile(@RequestParam(name = "username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }


}