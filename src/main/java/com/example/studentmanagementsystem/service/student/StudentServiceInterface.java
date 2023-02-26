package com.example.studentmanagementsystem.service.student;

import com.example.studentmanagementsystem.dto.StudentDto;
import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.search.SearchParams;

import java.util.List;

public interface StudentServiceInterface {
    List<StudentDto> getStudentList(SearchParams params);

    Student addStudent(StudentDto dto);
    Student updateStudent(int id, StudentDto student);
    String deleteStudent(int id);
    Student findById(int id);
    String deletFromGroup(int groupId, int studentId);
}
