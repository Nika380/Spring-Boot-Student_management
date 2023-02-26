package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
    List<Student> findByActive(Boolean active);
    List<Student> findAllByIdInAndActive(List<Integer> ids, Boolean active);
}
