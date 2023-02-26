package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.dto.TeacherDto;
import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.search.SearchParams;
import com.example.studentmanagementsystem.service.teacher.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherServiceImpl teacherService;

    @GetMapping
    public List<TeacherDto> getTeachersList(SearchParams params) {
        return teacherService.getTeacherList(params);
    }

    @PostMapping("/register")
    public Teacher addTeacher(@RequestBody TeacherDto dto) {
        return teacherService.addSTeacher(dto);
    }

    @PutMapping("/{id}/update")
    public Teacher updateTeacherInfo(@PathVariable int id, @RequestBody TeacherDto dto) {
        return teacherService.updateTeacher(id,dto);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable int id) {
        return teacherService.deleteTeacher(id);
    }
    @DeleteMapping("/{teacherId}/{groupId}")
    public String removeTeacherFromGroup(@PathVariable int teacherId, @PathVariable int groupId) {
        return teacherService.removeTeacherFromGroup(groupId, teacherId);
    }

    @GetMapping("/{id}")
    public Teacher byId(@PathVariable int id) {
        return teacherService.findById(id);
    }

}
