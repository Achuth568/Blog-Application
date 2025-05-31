package com.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long commentId;
	
	private String content;
	
	@ManyToOne
	private Post post;
	
	
	
	
	
	

}
