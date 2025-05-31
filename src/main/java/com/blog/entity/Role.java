package com.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
	
	
	@Id
	private long roleId;
	private String roleName;
	
	

}
