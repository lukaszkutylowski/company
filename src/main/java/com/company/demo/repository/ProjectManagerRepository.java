package com.company.demo.repository;

import com.company.demo.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectManagerRepository extends JpaRepository<Manager, Long> {
}
