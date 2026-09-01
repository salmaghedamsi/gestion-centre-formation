package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.dto.PaymentCreationDTO;
import com.centreformation.CentreFormationBackend.dto.PaymentSummaryDTO;
import com.centreformation.CentreFormationBackend.entity.Payment;
import com.centreformation.CentreFormationBackend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/group-student/{groupStudentId}")
    public List<Payment> getByGroupStudent(@PathVariable Long groupStudentId) {
        return paymentService.findByGroupStudentId(groupStudentId);
    }
    @GetMapping("/group-student/{groupStudentId}/summary")
    public PaymentSummaryDTO getSummary(@PathVariable Long groupStudentId) {
        return paymentService.getSummary(groupStudentId);
    }

    @PostMapping
    public Payment creer(@RequestBody PaymentCreationDTO dto) {
        return paymentService.create(
                dto.getGroupStudentId(),
                dto.getPaymentDate(),
                dto.getAmount(),
                dto.getMonthsPaid(),
                dto.getSessionsPaid(),
                dto.getComment()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentService.deleteById(id);
    }

}