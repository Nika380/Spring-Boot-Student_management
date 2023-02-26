package com.example.studentmanagementsystem.service.group;

import com.example.studentmanagementsystem.dto.GroupDto;
import com.example.studentmanagementsystem.dto.StudentDto;
import com.example.studentmanagementsystem.dto.TeacherDto;
import com.example.studentmanagementsystem.entity.Group;
import com.example.studentmanagementsystem.entity.Group_;
import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.repository.GroupRepo;
import com.example.studentmanagementsystem.repository.StudentRepo;
import com.example.studentmanagementsystem.repository.TeacherRepo;
import com.example.studentmanagementsystem.search.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupServiceInterface{

    private final GroupRepo groupRepo;
    private final StudentRepo studentRepo;
    private final TeacherRepo teacherRepo;
    @Override
    public List<GroupDto> getGroupList(SearchParams params) {
        List<Group> groups = groupRepo.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if(params.getGroupId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get(Group_.ID), params.getGroupId()));
            }
            return predicate;
        });
        return groups.stream().map(group -> {
            GroupDto groupDto = new GroupDto();
            groupDto.setId(group.getId());
            groupDto.setGroupName(group.getGroupName());
            groupDto.setStudents(group.getStudents().stream().map(student -> {
                StudentDto studentDto = new StudentDto();
                studentDto.setEmail(student.getEmail());
                studentDto.setId(student.getId());
                studentDto.setPrivateNumber(student.getPrivateNumber());
                studentDto.setFirstName(student.getFirstName());
                studentDto.setLastName(student.getLastName());
                studentDto.setBirthDate(student.getBirthDate());
                studentDto.setGroups(student.getGroups());
                return studentDto;
            }).collect(Collectors.toList()));
            groupDto.setTeachers(group.getTeachers().stream().map(teacher -> {
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setId(teacher.getId());
                teacherDto.setPrivateNumber(teacher.getPrivateNumber());
                teacherDto.setEmail(teacher.getEmail());
                teacherDto.setLastName(teacher.getLastName());
                teacherDto.setFirstName(teacher.getFirstName());
                teacherDto.setBirthDate(teacher.getBirthDate());
                teacherDto.setGroups(teacher.getGroups());
                return teacherDto;
            }).collect(Collectors.toList()));
            return groupDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Group addGroup(GroupDto dto) {

        List<Student> students = studentRepo.findAllByIdInAndActive(dto.getStudents().stream().map(student -> student.getId()).collect(Collectors.toList()), true);
        List<Teacher> teachers = teacherRepo.findAllById(dto.getTeachers().stream().map(TeacherDto::getId).collect(Collectors.toList()));

        return groupRepo.save(new Group(dto.getGroupName(), students, teachers));

    }

    @Override
    public Group updateGroup(int id, GroupDto dto) {
        var group = findById(id);
        List<Student> students = studentRepo.findAllByIdInAndActive(dto.getStudents().stream().map(StudentDto::getId).collect(Collectors.toList()), true);
        List<Teacher> teachers = teacherRepo.findAllById(dto.getTeachers().stream().map(TeacherDto::getId).collect(Collectors.toList()));
        group.setGroupName(dto.getGroupName());
        group.setStudents(students);
        group.setTeachers(teachers);

        return groupRepo.save(group);
    }

    @Override
    public String deleteGroup(int id) {
        groupRepo.delete(findById(id));
        return "Group With Id " + id + " is Deleted";
    }

    public Group findById(int id) {
        return groupRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Group Not Found"));
    }
    @Override
    public GroupDto findGroupById(int id) {
        var group = groupRepo.findById(id).orElseThrow(() -> new NotFoundException("Group Not Found"));
            GroupDto groupDto = new GroupDto();
            groupDto.setId(group.getId());
            groupDto.setGroupName(group.getGroupName());
            groupDto.setStudents(group.getStudents().stream().map(student -> {
                StudentDto studentDto = new StudentDto();
                studentDto.setEmail(student.getEmail());
                studentDto.setId(student.getId());
                studentDto.setPrivateNumber(student.getPrivateNumber());
                studentDto.setFirstName(student.getFirstName());
                studentDto.setLastName(student.getLastName());
                studentDto.setBirthDate(student.getBirthDate());
                studentDto.setGroups(student.getGroups());
                return studentDto;
            }).collect(Collectors.toList()));
            groupDto.setTeachers(group.getTeachers().stream().map(teacher -> {
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setId(teacher.getId());
                teacherDto.setPrivateNumber(teacher.getPrivateNumber());
                teacherDto.setEmail(teacher.getEmail());
                teacherDto.setLastName(teacher.getLastName());
                teacherDto.setFirstName(teacher.getFirstName());
                teacherDto.setBirthDate(teacher.getBirthDate());
                teacherDto.setGroups(teacher.getGroups());
                return teacherDto;
            }).collect(Collectors.toList()));
            return groupDto;
    }
}
