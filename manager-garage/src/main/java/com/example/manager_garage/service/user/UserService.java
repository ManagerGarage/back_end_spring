package com.example.manager_garage.service.user;

import com.example.manager_garage.dto.request.RegisterRequest;
import com.example.manager_garage.entity.auth.Role;
import com.example.manager_garage.entity.auth.User;
import com.example.manager_garage.repository.UserRepository;
import com.example.manager_garage.service.driver.DriverService;
import com.example.manager_garage.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import com.example.manager_garage.exception.ConflictException;
import com.example.manager_garage.util.DateTimeUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new ConflictException("Username already exists");
        }
        // Nếu là DRIVER, kiểm tra phone
        if ("DRIVER".equalsIgnoreCase(request.getRole())) {
            if (request.getPhone() != null && driverRepository.findByPhone(request.getPhone()).isPresent()) {
                throw new ConflictException("Phone number already exists");
            }
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        String role = request.getRole();
        if (role == null || role.trim().isEmpty()) {
            role = "DRIVER";
        }
        user.setRole(Role.valueOf(role.toUpperCase()));
        user.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
        User savedUser = userRepository.save(user);

        // Nếu role là DRIVER thì tạo mới driver
        if ("DRIVER".equalsIgnoreCase(role)) {
            driverService.createDriverForUser(
                    savedUser,
                    request.getName(),
                    request.getPhone(),
                    request.getDayBirth(),
                    request.getAddress(),
                    request.getLicense());
        }
        return savedUser;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}