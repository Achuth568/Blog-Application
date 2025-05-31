package com.blog.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long postId;
	
	@Column(name="postTitle",nullable = false)
	private String title;
	
	@Column(name="content",length=1000)
	private String content;
	
	private String imageName;
	
	private LocalDate addedDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userId")
	private Users users;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="categoryId")
	private Category category;
	
	@OneToMany(mappedBy = "post")
	private Set<Comment> comments= new HashSet<>();
	
	//,cascade = CascadeType.ALL
	


	
	

}
