package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blog.config.AppConstants;
import com.blog.entity.Role;
import com.blog.repositories.RoleRepository;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role =new Role();
			
			role.setRoleId(AppConstants.ADMIN_USER);
			role.setRoleName("ROLE_ADMIN");
			
			Role role1 =new Role();
			role1.setRoleId(AppConstants.NORMAL_USER);
			role1.setRoleName("ROLE_USER");
			
			List<Role> saveRole= List.of(role,role1);
			
			List<Role> result=roleRepository.saveAll(saveRole);
			
			result.forEach(r->{
				System.out.println(r.getRoleName());
			});
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	
		
	}

}
