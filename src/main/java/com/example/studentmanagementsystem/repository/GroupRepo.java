package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Integer>, JpaSpecificationExecutor<Group> {
}
