package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.Users;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findByUsers(Users users);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);

}
