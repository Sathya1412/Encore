package com.dbsample.mysqlcrud.repository;

import com.dbsample.mysqlcrud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    Student findByName(String name);
}
