package com.springboot.be_is_final.controller;

import com.springboot.be_is_final.model.User;
import com.springboot.be_is_final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<String> UserRegistration(@RequestBody User registrationData) {
        // Check if the username already exists
        if (userService.existsByUsername(registrationData.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        // Encode password and save user
        registrationData.setPassword(
                new BCryptPasswordEncoder().encode(registrationData.getPassword())
        );
        userService.saveUser(registrationData);
        return ResponseEntity.ok("User Registration successful");
    }


    @PostMapping("/login")
    public ResponseEntity<String> UserLogin(@RequestBody User loginData){
        User user = userService.findByUsername(loginData.getUsername());
        if (user != null && new BCryptPasswordEncoder().matches(loginData.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
