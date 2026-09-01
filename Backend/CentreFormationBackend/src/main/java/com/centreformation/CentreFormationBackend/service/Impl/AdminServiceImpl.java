package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.Admin;
import com.centreformation.CentreFormationBackend.repository.AdminRepository;
import com.centreformation.CentreFormationBackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

}