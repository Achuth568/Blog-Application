package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {
	
	private long id;
	
	@NotBlank
	@Size(min=6,max=20, message="title must be between 6 to 20 characters")
	private String title;
	
	@NotBlank
	@Size(min=6,max=100,message="description must be between 6 to 100 characters")
	private String description;

}
