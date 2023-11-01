package com.psr.webfluxreactiveprogramingwithmysql.repository;

import com.psr.webfluxreactiveprogramingwithmysql.entity.Student;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends R2dbcRepository<Student,Long> {
}
