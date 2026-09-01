package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.GroupStudent;
import com.centreformation.CentreFormationBackend.entity.Group;
import com.centreformation.CentreFormationBackend.repository.GroupRepository;
import com.centreformation.CentreFormationBackend.repository.GroupStudentRepository;
import com.centreformation.CentreFormationBackend.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupStudentRepository groupStudentRepository;

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Groupe non trouvé avec l'id " + id));
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group CreateGroup(Group groups) {
        return groupRepository.save(groups);
    }

    @Override
    public Group updateGroup(Long id, Group groups) {
        Group existing = getGroupById(id);

        existing.setName(groups.getName());
        existing.setStartDate(groups.getStartDate());
        existing.setEndDate(groups.getEndDate());
        existing.setMaxPlaces(groups.getMaxPlaces());
        existing.setTeacher(groups.getTeacher());
        existing.setFormationGroup(groups.getFormationGroup());
        existing.setPaymentType(groups.getPaymentType());

        return groupRepository.save(existing);
    }
    @Override
    public long countActiveByGroupId(Long groupId) {
        return groupStudentRepository.countByGroupIdAndActiveTrue(groupId);
    }

    @Override
    public void delete(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Groupe non trouvé avec l'id " + id);
        }
        groupRepository.deleteById(id);
    }

    @Override
    public List<GroupStudent> findByGroup_Id(Long groupId) {
        return List.of();
    }

}