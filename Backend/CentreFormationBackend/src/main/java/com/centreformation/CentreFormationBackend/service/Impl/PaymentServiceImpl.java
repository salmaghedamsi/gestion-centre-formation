package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.dto.PaymentSummaryDTO;
import com.centreformation.CentreFormationBackend.entity.*;
import com.centreformation.CentreFormationBackend.enums.PaymentType;
import com.centreformation.CentreFormationBackend.repository.PaymentRepository;
import com.centreformation.CentreFormationBackend.repository.PresenceRepository;
import com.centreformation.CentreFormationBackend.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final GroupStudentService groupStudentService;
    private final PresenceService presenceService;
    private final PresenceRepository presenceRepository;

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> findByGroupStudentId(Long groupStudentId) {
        return paymentRepository.findByGroupStudentId(groupStudentId);
    }

    @Override
    public Payment create(Long groupStudentId, LocalDate paymentDate, BigDecimal amount, Integer monthsPaid, Integer sessionsPaid, String comment) {
        GroupStudent groupStudent = groupStudentService.findById(groupStudentId);

        Payment payment = new Payment();
        payment.setGroupStudent(groupStudent);
        payment.setPaymentDate(paymentDate);
        payment.setAmount(amount);
        payment.setMonthsPaid(monthsPaid);
        payment.setSessionsPaid(sessionsPaid);
        payment.setComment(comment);

        return paymentRepository.save(payment);
    }

    @Override
    public void deleteById(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public PaymentSummaryDTO getSummary(Long groupStudentId) {

        GroupStudent groupStudent = groupStudentService.findById(groupStudentId);
        Group group = groupStudent.getGroup();
        PaymentType type = group.getPaymentType();
        Double price = group.getFormationGroup().getPrice();

        List<Payment> payments = paymentRepository.findByGroupStudentId(groupStudentId);

        BigDecimal totalAmountPaid = payments.stream()
                .map(Payment::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDate today = LocalDate.now();
        LocalDate effectiveEnd =
                (groupStudent.getEndDate() != null && groupStudent.getEndDate().isBefore(today))
                        ? groupStudent.getEndDate()
                        : today;

        int unitsPaid;
        int unitsConsumed;

        if (type == PaymentType.PER_SESSION) {

            unitsPaid = payments.stream()
                    .map(Payment::getSessionsPaid)
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();

            unitsConsumed = (int) presenceService.countPresentByStudentAndGroup(
                    groupStudent.getStudent().getId(),
                    group.getId()
            );

        } else {

            unitsPaid = payments.stream()
                    .map(Payment::getMonthsPaid)
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();

            List<Presence> presences = presenceService.findPresentByStudentAndGroup(
                    groupStudent.getStudent().getId(),
                    group.getId()
            );

            YearMonth currentMonth = YearMonth.from(today);

            Set<YearMonth> moisTermines = presences.stream()
                    .map(p -> YearMonth.from(p.getSession().getDate()))
                    .filter(mois -> mois.isBefore(currentMonth))
                    .collect(Collectors.toSet());

            unitsConsumed = moisTermines.size();}

        int balanceUnits = unitsPaid - unitsConsumed;

        return new PaymentSummaryDTO(
                type,
                price,
                totalAmountPaid,
                unitsPaid,
                unitsConsumed,
                balanceUnits
        );
    }
    @Override
    public List<Presence> findPresentByStudentAndGroup(Long studentId, Long groupId) {
        return presenceRepository.findByStudentIdAndPresentTrue(studentId).stream()
                .filter(p -> p.getSession().getGroup().getId().equals(groupId))
                .filter(p -> !p.getSession().isFree())
                .toList();
    }
}