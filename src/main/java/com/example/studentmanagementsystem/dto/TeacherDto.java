package com.example.studentmanagementsystem.dto;

import com.example.studentmanagementsystem.entity.Group;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class TeacherDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String privateNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    private LocalDate birthDate;
    private String email;
    private List<Group> groups;
}
