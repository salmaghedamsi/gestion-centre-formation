package com.centreformation.CentreFormationBackend.config;

import com.centreformation.CentreFormationBackend.entity.Admin;
import com.centreformation.CentreFormationBackend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String emailAdmin = "admin@test.com";

        boolean existeDeja = adminRepository.findByEmail(emailAdmin).isPresent();

        if (!existeDeja) {
            Admin admin = new Admin();
            admin.setEmail(emailAdmin);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("Test");

            adminRepository.save(admin);

            System.out.println("Admin de test créé : " + emailAdmin);
        }
    }

}