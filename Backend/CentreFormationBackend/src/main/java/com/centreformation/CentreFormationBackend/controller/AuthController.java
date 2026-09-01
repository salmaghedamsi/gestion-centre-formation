package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.entity.Admin;
import com.centreformation.CentreFormationBackend.dto.LoginRequest;
import com.centreformation.CentreFormationBackend.dto.LoginResponse;
import com.centreformation.CentreFormationBackend.repository.AdminRepository;
import com.centreformation.CentreFormationBackend.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (admin == null || !passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }

        String token = jwtService.genererToken(admin.getEmail());

        return ResponseEntity.ok(new LoginResponse(token, admin.getEmail(), admin.getFirstName()));
    }
    @PostMapping("/register-temp")
    public Admin registerTemp(@Valid @RequestBody LoginRequest request) {
        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setFirstName("Admin");
        admin.setLastName("Test");

        return adminRepository.save(admin);
    }
}