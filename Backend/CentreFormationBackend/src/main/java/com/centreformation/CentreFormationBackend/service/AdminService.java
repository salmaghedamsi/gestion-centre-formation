package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.Admin;

import java.util.Optional;

public interface AdminService {

    Optional<Admin> findByEmail(String email);

    Admin save(Admin admin);

}