package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entity.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long>{

}
