package com.example.studentmanagementsystem.service.teacher;

import com.example.studentmanagementsystem.dto.TeacherDto;
import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.search.SearchParams;

import java.util.List;

public interface TeacherServiceInterface {
    List<TeacherDto> getTeacherList(SearchParams params);

    Teacher addSTeacher(TeacherDto dto);
    Teacher updateTeacher(int id, TeacherDto dto);
    String deleteTeacher(int id);
    Teacher findById(int id);
    String removeTeacherFromGroup(int groupId, int teacherId);
}
