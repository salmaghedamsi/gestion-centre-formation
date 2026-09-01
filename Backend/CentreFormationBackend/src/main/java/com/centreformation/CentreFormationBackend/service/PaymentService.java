package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.dto.PaymentSummaryDTO;
import com.centreformation.CentreFormationBackend.entity.Payment;
import com.centreformation.CentreFormationBackend.entity.Presence;

import java.util.List;

public interface PaymentService {
    PaymentSummaryDTO getSummary(Long groupStudentId);

    Payment findById(Long id);

    List<Payment> findAll();

    List<Payment> findByGroupStudentId(Long groupStudentId);

    Payment create(Long groupStudentId, java.time.LocalDate paymentDate, java.math.BigDecimal amount, Integer monthsPaid, Integer sessionsPaid, String comment);

    void deleteById(Long id);
    List<Presence> findPresentByStudentAndGroup(Long studentId, Long groupId);

}