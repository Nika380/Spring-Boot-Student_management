package com.example.studentmanagementsystem.service.student;

import com.example.studentmanagementsystem.dto.StudentDto;
import com.example.studentmanagementsystem.entity.Group;
import com.example.studentmanagementsystem.entity.Group_;
import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.Student_;
import com.example.studentmanagementsystem.repository.GroupRepo;
import com.example.studentmanagementsystem.repository.StudentRepo;
import com.example.studentmanagementsystem.search.SearchParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentServiceInterface{

    private final StudentRepo studentRepo;
    private final GroupRepo groupRepository;
    @Override
    public List<StudentDto> getStudentList(SearchParams params) {

        List<Student> students = studentRepo.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = cb.and(predicate, cb.isTrue(root.get(Student_.ACTIVE)));
            if(params.getGroupId() != null) {
                Join<Student, Group> groupJoin = root.join(Student_.GROUPS, JoinType.LEFT);
                predicate = cb.and(predicate, cb.equal(groupJoin.get(Group_.ID), params.getGroupId()));
            }
            if(params.getPrivateNumber() != null && params.getPrivateNumber() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Student_.PRIVATE_NUMBER), params.getPrivateNumber()));
            }
            if(params.getBirthDate() != null && params.getBirthDate().toString() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Student_.BIRTH_DATE), params.getBirthDate()));
            }
            if(params.getFirstName() != null && params.getFirstName() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Student_.FIRST_NAME), params.getFirstName()));
            }
            if(params.getLastName() != null && params.getLastName() != "") {
                predicate = cb.and(predicate, cb.equal(root.get(Student_.LAST_NAME), params.getLastName()));
            }

            return predicate;
        });
        return students.stream().map(student -> {
            StudentDto studentDto = new StudentDto();
            studentDto.setId(student.getId());
            studentDto.setEmail(student.getEmail());
            studentDto.setPrivateNumber(student.getPrivateNumber());
            studentDto.setFirstName(student.getFirstName());
            studentDto.setLastName(student.getLastName());
            studentDto.setBirthDate(student.getBirthDate());
            studentDto.setGroups(student.getGroups());
            return studentDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Student addStudent(StudentDto dto) {
        var student = new Student(dto.getFirstName(),
                dto.getLastName(),
                dto.getPrivateNumber(),dto.getBirthDate(),
                dto.getEmail());
        return studentRepo.save(student);
    }

    @Override
    public Student updateStudent(int id, StudentDto dto) {
        var studentToUpdate = findById(id);
        studentToUpdate.setFirstName(dto.getFirstName());
        studentToUpdate.setLastName(dto.getLastName());
        studentToUpdate.setPrivateNumber(dto.getPrivateNumber());
        studentToUpdate.setBirthDate(dto.getBirthDate());
        return studentRepo.save(studentToUpdate);
    }

    @Override
    public String deleteStudent(int id) {
        var student = findById(id);
        student.setActive(false);
        studentRepo.save(student);
        return "Student With Id: " + id + " is Deleted";
    }

    @Override
    public Student findById(int id) {
        return studentRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Student With this Id Does Not Exist"));
    }

    @Override
    public String deletFromGroup(int groupId, int studentId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        group.setStudents(group.getStudents().stream().map(student1 -> {
            if(student1.getId() != studentId) {
                return student1;
            } else {
                return  null;
            }
        }).collect(Collectors.toList()));

        student.setGroups(student.getGroups().stream().map(group1 -> {
            if(group1.getId() != groupId) {
                return group1;
            } else {
                return null;
            }
        }).collect(Collectors.toList()));

        groupRepository.save(group);
        studentRepo.save(student);

        return "Success";
    }
}
