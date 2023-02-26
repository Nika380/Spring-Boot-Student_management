package com.example.studentmanagementsystem.service.teacher;


import com.example.studentmanagementsystem.dto.TeacherDto;
import com.example.studentmanagementsystem.entity.*;
import com.example.studentmanagementsystem.repository.GroupRepo;
import com.example.studentmanagementsystem.repository.TeacherRepo;
import com.example.studentmanagementsystem.search.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherServiceInterface{

    private final TeacherRepo teacherRepo;
    private final GroupRepo groupRepository;

    @Override
    public List<TeacherDto> getTeacherList(SearchParams params) {

        List<Teacher> teachers = teacherRepo.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = cb.and(predicate, cb.isTrue(root.get(Teacher_.ACTIVE)));
            if(params.getGroupId() != null ) {
                Join<Teacher, Group> groupJoin = root.join(Teacher_.GROUPS, JoinType.LEFT);
                predicate = cb.and(predicate, cb.equal(groupJoin.get(Group_.ID), params.getGroupId()));
            }
            if(params.getPrivateNumber() != null && params.getPrivateNumber() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Teacher_.PRIVATE_NUMBER), params.getPrivateNumber()));
            }
            if(params.getBirthDate() != null && params.getBirthDate().toString() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Teacher_.BIRTH_DATE), params.getBirthDate()));
            }
            if(params.getFirstName() != null && params.getFirstName() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Teacher_.FIRST_NAME), params.getFirstName()));
            }
            if(params.getLastName() != null && params.getLastName() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Teacher_.LAST_NAME), params.getLastName()));
            }

            return predicate;
        });
        return teachers.stream().map(teacher -> {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setId(teacher.getId());
            teacherDto.setEmail(teacher.getEmail());
            teacherDto.setPrivateNumber(teacher.getPrivateNumber());
            teacherDto.setFirstName(teacher.getFirstName());
            teacherDto.setLastName(teacher.getLastName());
            teacherDto.setBirthDate(teacher.getBirthDate());
            teacherDto.setGroups(teacher.getGroups());
            return teacherDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Teacher addSTeacher(TeacherDto dto) {
        return teacherRepo.save(new Teacher(dto.getFirstName(),
                dto.getLastName(), dto.getPrivateNumber(),
                dto.getEmail(),dto.getBirthDate()));
    }

    @Override
    public Teacher updateTeacher(int id, TeacherDto dto) {
        var teacherToUpdate = findById(id);
        teacherToUpdate.setFirstName(dto.getFirstName());
        teacherToUpdate.setLastName(dto.getLastName());
        teacherToUpdate.setEmail(dto.getEmail());
        teacherToUpdate.setBirthDate(dto.getBirthDate());
        teacherToUpdate.setPrivateNumber(dto.getPrivateNumber());
        return teacherRepo.save(teacherToUpdate);
    }

    @Override
    public String deleteTeacher(int id) {
        teacherRepo.delete(findById(id));
        return "Teacher With Id: " + id + " is Deleted";
    }

    @Override
    public Teacher findById(int id) {
        return teacherRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher with this id does not exits"));
    }

    @Override
    public String removeTeacherFromGroup(int groupId, int teacherId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        Teacher teacher = teacherRepo.findById(teacherId).
                orElseThrow(() -> new RuntimeException("Teacher not found"));

        group.setTeachers(group.getTeachers().stream().map(student1 -> {
            if(student1.getId() != teacherId) {
                return student1;
            } else {
                return  null;
            }
        }).collect(Collectors.toList()));

        teacher.setGroups(teacher.getGroups().stream().map(group1 -> {
            if(group1.getId() != groupId) {
                return group1;
            } else {
                return null;
            }
        }).collect(Collectors.toList()));

        groupRepository.save(group);
        teacherRepo.save(teacher);
        return "Success";
    }
}
