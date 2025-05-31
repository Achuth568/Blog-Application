package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);
	
   UserDto	createUser(UserDto userDto);
   
   UserDto  updateUser(UserDto user,long id);
   
   UserDto  getUserById(long id);
   
   List<UserDto> getAllUsers();
   
   void deleteUser(long id);
   
   

}
