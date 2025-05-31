package com.blog.payloads;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {
	
	private long postId;
	private String title;
	private String content;
	private String imageName;
	private LocalDate addedDate;
	private UserDto users;
	private CategoryDto category;
	
	private Set<CommentDto> comments= new HashSet<>();
	
	

}
