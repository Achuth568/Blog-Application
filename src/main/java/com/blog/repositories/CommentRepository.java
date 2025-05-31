package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entity.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
