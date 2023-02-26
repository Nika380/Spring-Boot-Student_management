package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.dto.StudentDto;
import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.search.SearchParams;
import com.example.studentmanagementsystem.service.student.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentService;

    @GetMapping
    public List<StudentDto> getStudentList(SearchParams params) {
        return studentService.getStudentList(params);
    }

    @PostMapping("/register")
    public Student addStudent(@RequestBody StudentDto dto) {
        return studentService.addStudent(dto);
    }

    @PutMapping("/{id}/update")
    public Student updateStudentInfo(@PathVariable int id, @RequestBody StudentDto dto) {
        return studentService.updateStudent(id, dto);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/{id}")
    public Student byId(@PathVariable int id) {
        return studentService.findById(id);
    }
    @DeleteMapping("/{studentId}/{groupId}")
    public String removeFromGroup(@PathVariable int studentId, @PathVariable int groupId) {
        return studentService.deletFromGroup(groupId,studentId);
    }
}
