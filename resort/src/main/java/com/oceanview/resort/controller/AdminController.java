package com.oceanview.resort.controller;

import com.oceanview.resort.model.Admin;
import com.oceanview.resort.service.AdminService;
import com.oceanview.resort.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    public static class LoginRequest {
        private String username;
        private String password;
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        boolean success = adminService.login(
                request.getUsername(),
                request.getPassword()
        );

        if (success) {
            String token = jwtUtil.generateToken(request.getUsername());

            System.out.println("JWT TOKEN = " + token); //  PRINT

            return ResponseEntity.ok(token);
        }
        else {
            return ResponseEntity.status(401).body("INVALID_CREDENTIALS");
        }
    }
    @GetMapping
    public List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }

}

