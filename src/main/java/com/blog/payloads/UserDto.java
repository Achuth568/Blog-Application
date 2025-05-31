package com.blog.payloads;


import java.util.HashSet;
import java.util.Set;

import com.blog.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
	
    private long id;
    
    @NotEmpty
    @Size(min=4,message="name must be more than 4 characters")
	private String name;
    
    @Email(message="invalid email address provided")
	private String email;
    
    @NotEmpty
    @Size(min=4,max=100,message="password must be between 4 and 12")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{4,100}$", message = "Password must meet the criteria")
	private String password;
    
    @NotEmpty
	private String about;
    
    private Set<RoleDto> roles=new HashSet<>();

}
