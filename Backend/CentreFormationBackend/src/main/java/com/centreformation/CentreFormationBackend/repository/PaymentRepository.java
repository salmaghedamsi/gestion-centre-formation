package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByGroupStudentId(
            Long groupStudentId
    );



    List<Payment> findByGroupStudentIdAndMonthsPaidIsNotNull(
            Long groupStudentId
    );

    List<Payment> findByGroupStudentIdAndSessionsPaidIsNotNull(
            Long groupStudentId
    );
}