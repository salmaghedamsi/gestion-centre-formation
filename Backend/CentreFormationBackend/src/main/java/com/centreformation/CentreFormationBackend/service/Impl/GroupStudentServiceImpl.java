package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.GroupStudent;
import com.centreformation.CentreFormationBackend.repository.GroupStudentRepository;
import com.centreformation.CentreFormationBackend.service.GroupStudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupStudentServiceImpl implements GroupStudentService {

    private final GroupStudentRepository groupStudentRepository;

    @Override
    public GroupStudent findById(Long id) {
        return groupStudentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "GroupStudent not found with id: " + id
                        )
                );
    }

    @Override
    public GroupStudent findByGroupAndStudent(
            Long groupId,
            Long studentId
    ) {
        return groupStudentRepository
                .findByGroupIdAndStudentId(groupId, studentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Student is not registered in this group."
                        )
                );
    }

    @Override
    public List<GroupStudent> findAll() {
        return groupStudentRepository.findAll();
    }

    @Override
    public List<GroupStudent> findByGroupId(Long groupId) {
        return groupStudentRepository.findByGroupId(groupId);
    }

    @Override
    public List<GroupStudent> findByStudentId(Long studentId) {
        return groupStudentRepository.findByStudentId(studentId);
    }

    @Override
    public List<GroupStudent> findActiveByGroupId(Long groupId) {
        return groupStudentRepository.findByGroupIdAndActiveTrue(groupId);
    }

    @Override
    public List<GroupStudent> findActiveByStudentId(Long studentId) {
        return groupStudentRepository.findByStudentIdAndActiveTrue(studentId);
    }

    @Override
    public GroupStudent save(GroupStudent groupStudent) {
        return groupStudentRepository.save(groupStudent);
    }

    @Override
    public void deleteById(Long id) {
        if (!groupStudentRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "GroupStudent not found with id: " + id
            );
        }

        groupStudentRepository.deleteById(id);
    }
}