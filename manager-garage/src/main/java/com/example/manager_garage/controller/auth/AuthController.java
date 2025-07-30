package com.example.manager_garage.controller.auth;

import com.example.manager_garage.service.driver.DriverService;
import com.example.manager_garage.service.user.UserService;
import com.example.manager_garage.util.JwtUtil;
import com.example.manager_garage.entity.auth.User;
import com.example.manager_garage.dto.request.LoginRequest;
import com.example.manager_garage.dto.request.RegisterRequest;
import com.example.manager_garage.dto.response.JwtResponse;
import com.example.manager_garage.entity.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.manager_garage.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        String token = jwtUtil.generateToken(user);
        Driver driver = null;
        if ("DRIVER".equalsIgnoreCase(user.getRole().name())) {
            driver = driverService.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find driver for newly created user."));
        }
        JwtResponse.UserInfo userInfo = new JwtResponse.UserInfo(
                user.getUsername(),
                null, // address thay cho email
                driver != null ? driver.getName() : null, // fullName
                user.getRole().name(),
                driver != null ? driver.getPhone() : null,
                driver != null ? driver.getAddress() : null, // address (đã chuyển lên vị trí email)
                driver != null ? driver.getDayBirth() : null,
                driver != null && driver.getLicense() != null ? driver.getLicense().getName() : null,
                driver != null && driver.getStatusDriver() != null ? driver.getStatusDriver().getName() : null);
        return ResponseEntity.ok(new JwtResponse(token, userInfo));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());
        String token = jwtUtil.generateToken(user);
        Driver driver = null;
        if ("DRIVER".equalsIgnoreCase(user.getRole().name())) {
            driver = driverService.findByUser(user).orElse(null);
        }
        JwtResponse.UserInfo userInfo = new JwtResponse.UserInfo(
                user.getUsername(),
                null, // address thay cho email
                driver != null ? driver.getName() : null, // fullName
                user.getRole().name(),
                driver != null ? driver.getPhone() : null,
                driver != null ? driver.getAddress() : null, // address (đã chuyển lên vị trí email)
                driver != null ? driver.getDayBirth() : null,
                driver != null && driver.getLicense() != null ? driver.getLicense().getName() : null,
                driver != null && driver.getStatusDriver() != null ? driver.getStatusDriver().getName() : null);
        return ResponseEntity.ok(new JwtResponse(token, userInfo));
    }

    @GetMapping("/test")
    public ResponseEntity<?> testAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity
                .ok("Authentication successful! User: " + user.getUsername() + ", Role: " + user.getRole());
    }
}