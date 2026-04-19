package com.spring.springbootapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.springbootapplication.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
