package com.example.studentmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto {
    private Integer id;
    private String groupName;
    private List<TeacherDto> teachers;
    private List<StudentDto> students;
}
