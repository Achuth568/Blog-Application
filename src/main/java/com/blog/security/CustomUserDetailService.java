package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entity.Users;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repositories.UserRepository;

@Service
public class CustomUserDetailService  implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
		//loading user from database by userName or email
		
		Users user=userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user","email:"+username, 0));
		
		return user;
	}
	
	

}
