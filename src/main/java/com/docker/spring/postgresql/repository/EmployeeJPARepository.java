package com.docker.spring.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.docker.spring.postgresql.model.Employee;

public interface EmployeeJPARepository extends JpaRepository<Employee, Integer>{

}
