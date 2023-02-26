package com.example.studentmanagementsystem.search;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SearchParams {
    private Integer groupId;
    private String firstName;
    private String lastName;
    private String privateNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
}
