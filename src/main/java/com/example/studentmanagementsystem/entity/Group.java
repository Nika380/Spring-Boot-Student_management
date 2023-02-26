package com.example.studentmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
@Data
@Entity
@NoArgsConstructor
@Table(name = "groups", schema = "public", catalog = "postgres")
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    public Group(String groupName, List<Student> students, List<Teacher> teachers) {
        this.groupName = groupName;
        this.students = students;
        this.teachers = teachers;
    }

    @Basic
    @Column(name = "group_name")
    private String groupName;

    @ManyToMany
    @JoinTable(
            name = "teachers_and_students_in_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "teachers_and_students_in_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private  List<Teacher> teachers;



}
